package com.C4S.kaku_swing;

import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import net.jameskoehler.kaku.*;

//TODO: Think about move highlighting. Could change button color based on available moves.

public class GUI {

    private static Board board = new Board();

    /* Two ways to go about importing images for use with the GUI,
        looping through res folder and looking for names,
        or hardcoding, start with hardcoding
        TODO: we should implement the folder thing soon
     */
    private static ImageIcon whiteRook = new ImageIcon("res/whiteRook.png");
    private static ImageIcon whiteKnight = new ImageIcon("res/whiteKnight.png");
    private static ImageIcon whiteBishop = new ImageIcon("res/whiteBishop.png");
    private static ImageIcon whiteQueen = new ImageIcon("res/whiteQueen.png");
    private static ImageIcon whiteKing = new ImageIcon("res/whiteKing.png");
    private static ImageIcon whitePawn = new ImageIcon("res/whitePawn.png");
    //--------------------------------------------------------------------------//
    private static ImageIcon blackRook = new ImageIcon("res/blackRook.png");
    private static ImageIcon blackKnight = new ImageIcon("res/blackKnight.png");
    private static ImageIcon blackBishop = new ImageIcon("res/blackBishop.png");
    private static ImageIcon blackQueen = new ImageIcon("res/blackQueen.png");
    private static ImageIcon blackKing = new ImageIcon("res/blackKing.png");
    private static ImageIcon blackPawn = new ImageIcon("res/blackPawn.png");
    //------------------------------------------------------------------------//
    private static ImageIcon blank = new ImageIcon("res/Blank.png");

    public static JFrame gameFrame; // Required a script-wide scope reference to the JFrame used by our game loop
    private static JFrame mainMenuFrame; // Same thing as gameFrame, this is just an easier solution instead of having to think about it.

    public static String title = "Err: Title not reassigned";
    public static int width = 400;
    public static int height = 400;

    public static String pOneString = "sugma";//these will be updated with values after options are implemented
    public static String pTwoString = "ligma";
    public static JLabel playerOneLabel;
    public static JLabel playerTwoLabel;
    public static boolean turnWhite = true;

    //public static Color boardColorA = Color.WHITE;
    //public static Color boardColorB = Color.BLACK;
    //public static Color highlightColor = Color.CYAN;
    public static Color turnIndicatorColor = Color.GREEN;

    public static Color boardColorA = new Color(Integer.parseInt((saveLoadHandler.readFromSettingsFile("colorA"))));
    public static Color boardColorB = new Color(Integer.parseInt((saveLoadHandler.readFromSettingsFile("colorB"))));
    public static Color highlightColor = new Color(Integer.parseInt((saveLoadHandler.readFromSettingsFile("colorH"))));

    private static JButton[][] b = new JButton[8][8];
    private static ArrayList<Integer> moveData = new ArrayList<Integer>(4);

    public static void attributeImages() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                PieceInfo pieceAfil = board.getPiece(x,y).getAffiliation();
                PieceInfo pieceType = board.getPiece(x,y).getType();
                switch(pieceType) {
                    case ROOK:
                        b[x][y].setIcon(applyResizedImageToButton((pieceAfil == PieceInfo.WHITE ? whiteRook : blackRook), b[x][y]));
                        break;
                    case KNIGHT:
                        b[x][y].setIcon(applyResizedImageToButton((pieceAfil == PieceInfo.WHITE ? whiteKnight : blackKnight), b[x][y]));
                        break;
                    case BISHOP:
                        b[x][y].setIcon(applyResizedImageToButton((pieceAfil == PieceInfo.WHITE ? whiteBishop : blackBishop), b[x][y]));
                        break;
                    case QUEEN:
                        b[x][y].setIcon(applyResizedImageToButton((pieceAfil == PieceInfo.WHITE ? whiteQueen : blackQueen), b[x][y]));
                        break;
                    case KING:
                        b[x][y].setIcon(applyResizedImageToButton((pieceAfil == PieceInfo.WHITE ? whiteKing : blackKing), b[x][y]));
                        break;
                    case PAWN:
                        b[x][y].setIcon(applyResizedImageToButton((pieceAfil == PieceInfo.WHITE ? whitePawn : blackPawn), b[x][y]));
                        break;
                    default:
                        b[x][y].setIcon(applyResizedImageToButton(blank, b[x][y]));
                        break;
                }
            }
        }
    }

    public static ImageIcon applyResizedImageToButton(ImageIcon imageIcon, JButton button) {
        Dimension buttonSize = button.getSize();
        Image img = imageIcon.getImage();
        Image resizedImage = img.getScaledInstance(buttonSize.width, buttonSize.height, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    public static void createCheckerBoard(JPanel checkerBoardPanel) {
        turnWhite = true;
        board = new Board();
        checkerBoardPanel.setLayout(new GridLayout(8,8));
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                b[x][y] = new JButton();
                if ((x+y) % 2 == 0) {
                    b[x][y].setBackground(boardColorA);
                } else {
                    b[x][y].setBackground(boardColorB);
                }
                b[x][y].addActionListener(new ChessClickListener());
                checkerBoardPanel.add(b[x][y]);
            }
        }
    }

    // TODO!!!: FIX THE FACT THAT GAMESCREEN CAN BE EXITED WITH THE TOP-RIGHT CLOSE BUTTON AND NOT ACTUALLY RETURN TO THE MAIN MENU
    // TODO: Fix this method, this probably isn't the best way to do this, uhh prototype blah blah blah.
    public static void gameScreen(int _width, int _height) { // probably won't need the window name unless we want to affix server sided stuff to it
        gameFrame = new JFrame("In-Game");
        JPanel checkerBoardPanel = new JPanel();
        checkerBoardPanel.setPreferredSize(new Dimension(width-100, width-100)); //using a single variable here to try to make sure the checkerboard is a square
        JPanel exteriorPanel = new JPanel();
        exteriorPanel.setBorder(new EmptyBorder(10,10,10,10));
        gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Disposes assets on close but doesn't close the program, hoping that this clears memory like I think it should.
        gameFrame.setSize(width, height); // PLEASE CHANGE THE SIZING SET HERE THIS SHOULDN'T MAKE IT PAST TESTING

        GridBagLayout gonk = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        exteriorPanel.setLayout(gonk);

        JButton quitGameButton = new JButton("Quit");
        playerOneLabel = new JLabel(pOneString); //obviously playerOneLabel and playerTwoLabel will be changed later
        playerTwoLabel = new JLabel(pTwoString); //Use color to indicate who has the turn

        playerOneLabel.setForeground(turnIndicatorColor);

        quitGameButton.addActionListener(new ButtonClickListener()); //kill this window

        createCheckerBoard(checkerBoardPanel);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        exteriorPanel.add(playerOneLabel, gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        exteriorPanel.add(playerTwoLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(10,20,10,0);
        exteriorPanel.add(checkerBoardPanel, gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        exteriorPanel.add(quitGameButton, gbc);

        gameFrame.add(exteriorPanel, BorderLayout.CENTER);
        gameFrame.pack();
        gameFrame.setVisible(true);
        attributeImages();
    }

    public static void setTitle(String _title) { title = _title; }
    public static String getTitle() { return title; }

    public static void setWidth(int _width) { width = _width; }
    public static int getWidth() { return width; }

    public static void setHeight(int _height) { height = _height; }
    public static int getHeight() { return height; }

    private static ArrayList<Integer> findCoordsFromButton(JButton _button) {
        ArrayList<Integer> location = new ArrayList<>(4);
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (_button.equals(b[x][y])) {
                    location.add(x);
                    location.add(y);
                    System.out.println(board.getPiece(x,y).getAffiliation() + " " +board.getPiece(x,y).getType());
                }
            }
        }
        return location;
    }

    private static boolean pieceSelected = false;
    //TODO: Figure out implementation to discover if the selected piece is movable by the current player
    //TODO: Also figure out a good way to store 4 ints as movement data eg: oldX, oldY, newX, newY
    private static class ChessClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource(); // TODO: RIGHT HERE BUDD
            boolean playerHasMoved = false;
            if (!pieceSelected) {
                if (turnWhite && board.getPiece(findCoordsFromButton(button).get(0), findCoordsFromButton(button).get(1)).getAffiliation() == PieceInfo.WHITE) {
                    moveData.add(findCoordsFromButton(button).get(0));
                    moveData.add(findCoordsFromButton(button).get(1));
                    pieceSelected = true;
                } else if (!turnWhite && board.getPiece(findCoordsFromButton(button).get(0), findCoordsFromButton(button).get(1)).getAffiliation() == PieceInfo.BLACK) {
                    moveData.add(findCoordsFromButton(button).get(0));
                    moveData.add(findCoordsFromButton(button).get(1));
                    pieceSelected = true;
                } else { pieceSelected = false; }

                if (pieceSelected) {
                    for (int y = 0; y < 8; y++)
                        for (int x = 0; x < 8; x++)
                            if (board.isMoveValid(moveData.get(0), moveData.get(1), y, x) && board.getPiece(moveData.get(0), moveData.get(1)).getType() != PieceInfo.BLANK)
                                b[y][x].setBackground(highlightColor);
                }

            } else if (pieceSelected) {
                moveData.add(findCoordsFromButton(button).get(0));
                moveData.add(findCoordsFromButton(button).get(1)); // then use this to move piece in the Board class
                //TODO: this currently works as movement and everything, but really should change it to be something that the player can actually see and understand
                if (board.movePiece(moveData.get(0), moveData.get(1), moveData.get(2), moveData.get(3)) == 0) {
                    playerHasMoved = true;
                    if (turnWhite) {
                        playerTwoLabel.setForeground(Color.BLACK);
                        playerOneLabel.setForeground(turnIndicatorColor);
                    } else {
                        playerOneLabel.setForeground(Color.BLACK);
                        playerTwoLabel.setForeground(turnIndicatorColor);
                    }
                } else if (board.movePiece(moveData.get(0), moveData.get(1), moveData.get(2), moveData.get(3)) == 2) {
                    //white win
                } else if (board.movePiece(moveData.get(0), moveData.get(1), moveData.get(2), moveData.get(3)) == 3) {
                    //black win
                } else {
                    moveData.clear();
                }
                pieceSelected = false;

                for(int y = 0; y < 8; y++)
                    for(int x = 0; x < 8; x++)
                        b[y][x].setBackground(((y + x) % 2 == 0 ? boardColorA : boardColorB));
            }
            if (playerHasMoved) {
                turnWhite = !turnWhite; // switch turns lol
                moveData.clear(); //clears all move data so that moveData can be reused.
                pieceSelected = false;
                attributeImages();
            }
        }
    }

//    private static class ButtonClickListener implements ActionListener {
//        public void actionPerformed(ActionEvent e) {
//            String command = e.getActionCommand();
//            Object thing = e.getSource();
//            switch (command) {
//                case "Start Game":
//                    gameScreen(width, height); // run important logic for game start here
//                    break;
//                case "Options":
//                    // Obviously this will eventually be the Option menu with random included options
//                    new optionsMenu("Options", JFrame.DISPOSE_ON_CLOSE, width, height, new colorListener());
//                    break;
//                case "Exit Game":
//                    System.exit(0); // "Safely" exit the program
//                    break;
//                case "Quit":
//                    gameFrame.dispose();
//                    break;
//                default:
//                    // it doesn't do anything, but it should eventually report an error in like a log file or something.
//                    break;
//            }
//        }
//    }
}
