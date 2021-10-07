package src.main.java;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

//TODO: Think about move highlighting. Could change button color based on available moves.

public class GUI {

    private static Board board = new Board();

    private static JFrame gameFrame; // Required a script-wide scope reference to the JFrame used by our game loop
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

    public static Color boardColorA = new Color(Integer.parseInt((saveLoadHandler.readFromSaveFile("colorA"))));
    public static Color boardColorB = new Color(Integer.parseInt((saveLoadHandler.readFromSaveFile("colorB"))));
    public static Color highlightColor = new Color(Integer.parseInt((saveLoadHandler.readFromSaveFile("colorH"))));

    private static JButton[][] b = new JButton[8][8];
    private static ArrayList<Integer> moveData = new ArrayList<Integer>(4);

    public static void attributeImages() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                PieceInfo pieceAfil = board.getPiece(x,y).getAffiliation();
                PieceInfo pieceType = board.getPiece(x,y).getType();
                switch(pieceType) {
                    case ROOK:
                        b[x][y].setIcon(applyResizedImageToButton((pieceAfil == PieceInfo.WHITE ? board.whiteRook : board.blackRook), b[x][y]));
                        break;
                    case KNIGHT:
                        b[x][y].setIcon(applyResizedImageToButton((pieceAfil == PieceInfo.WHITE ? board.whiteKnight : board.blackKnight), b[x][y]));
                        break;
                    case BISHOP:
                        b[x][y].setIcon(applyResizedImageToButton((pieceAfil == PieceInfo.WHITE ? board.whiteBishop : board.blackBishop), b[x][y]));
                        break;
                    case QUEEN:
                        b[x][y].setIcon(applyResizedImageToButton((pieceAfil == PieceInfo.WHITE ? board.whiteQueen : board.blackQueen), b[x][y]));
                        break;
                    case KING:
                        b[x][y].setIcon(applyResizedImageToButton((pieceAfil == PieceInfo.WHITE ? board.whiteKing : board.blackKing), b[x][y]));
                        break;
                    case PAWN:
                        b[x][y].setIcon(applyResizedImageToButton((pieceAfil == PieceInfo.WHITE ? board.whitePawn : board.blackPawn), b[x][y]));
                        break;
                    default:
                        b[x][y].setIcon(applyResizedImageToButton(board.blank, b[x][y]));
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

    public static void optionsMenu(int _width, int _height) { // I don't think these variables are actually needed
        JFrame optionsFrame = new JFrame("Options");
        optionsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Might need to change to hide while we update settings files in the background
        optionsFrame.setSize(_width, _height);

        JPanel optionsPanel = new JPanel(); // should probably use gridBag but idfk rn

        JButton colorChangeA = new JButton("Change ColorA");
        JButton colorChangeB = new JButton("Change ColorB");
        JButton highlightChange = new JButton("Change Highlight Color");

        colorChangeA.addActionListener(new colorStuffListener());
        colorChangeB.addActionListener(new colorStuffListener());
        highlightChange.addActionListener(new colorStuffListener());

        optionsPanel.add(colorChangeA);
        optionsPanel.add(colorChangeB);
        optionsPanel.add(highlightChange);
        optionsFrame.add(optionsPanel);
        optionsFrame.setVisible(true);
    }

    public static void genMainMenu(String _MainMenuTitle, int _width, int _height) {

        mainMenuFrame = new JFrame(_MainMenuTitle);
        setTitle(_MainMenuTitle); // should be done differently, sloppy code.
        setWidth(_width); // could probably be done outside of method call. eg: make these calls prior to calling genMainMenu
        setHeight(_height); // same issue as with the setWidth() call
        mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenuFrame.setSize(width, height);

        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        mainMenuFrame.setLayout(grid);
        GridBagLayout layout = new GridBagLayout();
        mainMenuFrame.setLayout(layout); //two setLayout calls idk about that one chief

        JLabel gameLabel = new JLabel("Kaku-swing Chess");
        JButton startButton = new JButton("Start Game");
        JButton optionButton = new JButton("Options");
        JButton quitButton = new JButton("Exit Game");

        startButton.addActionListener(new ButtonClickListener());
        optionButton.addActionListener(new ButtonClickListener());
        quitButton.addActionListener(new ButtonClickListener());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainMenuFrame.add(gameLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainMenuFrame.add(startButton, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainMenuFrame.add(optionButton, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;
        mainMenuFrame.add(quitButton, gbc);

        mainMenuFrame.setVisible(true); // could probably make this separate if more calls are required before setting frame visible
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

    private static class colorStuffListener extends Component implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            switch (command) {
                case "Change ColorA" -> {
                    Color initialColor = boardColorA;
                    boardColorA = JColorChooser.showDialog(this, "Select a Color", initialColor);

                    saveLoadHandler.writeToSaveFile("colorA", String.valueOf(saveLoadHandler.generateRGBCode(boardColorA.getRed(), boardColorA.getGreen(), boardColorA.getBlue())));
                }
                case "Change ColorB" -> {
                    Color initialColor = boardColorB;
                    boardColorB = JColorChooser.showDialog(this, "Select a Color", initialColor);

                    saveLoadHandler.writeToSaveFile("colorB", String.valueOf(saveLoadHandler.generateRGBCode(boardColorB.getRed(), boardColorB.getGreen(), boardColorB.getBlue())));
                }
                case "Change Highlight Color" -> {
                    Color initialColor = highlightColor;
                    highlightColor = JColorChooser.showDialog(this, "Select a Color", initialColor);

                    saveLoadHandler.writeToSaveFile("colorH", String.valueOf(saveLoadHandler.generateRGBCode(highlightColor.getRed(), highlightColor.getGreen(), highlightColor.getBlue())));
                }
            }

        }
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

    private static class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            Object thing = e.getSource();
            switch (command) {
                case "Start Game":
                    gameScreen(width, height); // run important logic for game start here
                    break;
                case "Options":
                    // Obviously this will eventually be the Option menu with random included options
                    optionsMenu(width, height);
                    break;
                case "Exit Game":
                    System.exit(0); // "Safely" exit the program
                    break;
                case "Quit":
                    gameFrame.dispose();
                    break;
                default:
                    // it doesn't do anything, but it should eventually report an error in like a log file or something.
                    break;
            }
        }
    }
}
