package com.C4S.kaku_swing;

import net.jameskoehler.kaku.Board;
import net.jameskoehler.kaku.PieceInfo;

import static com.C4S.kaku_swing.GUI.board;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/***
 * This class should be focused around capturing info from the engine and displaying it
 * as well as doing all the setup required for the images to be put on the JButtons
 */
public class GameScreen {

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

    public static JFrame gameFrame;

    public static JButton[][] guiButtons = new JButton[8][8];

    public static Color turnIndicatorColor = Color.GREEN;

    public static String pOneString = "sugma";//these will be updated with values after options are implemented
    public static String pTwoString = "ligma";


    public static Color boardColorA = new Color(Integer.parseInt((saveLoadHandler.readFromSettingsFile("colorA"))));
    public static Color boardColorB = new Color(Integer.parseInt((saveLoadHandler.readFromSettingsFile("colorB"))));
    public static Color highlightColor = new Color(Integer.parseInt((saveLoadHandler.readFromSettingsFile("colorH"))));

    public static JLabel playerOneLabel = new JLabel();
    public static JLabel playerTwoLabel = new JLabel();

    public GameScreen(int width, int height) { // probably won't need the window name unless we want to affix server sided stuff to it
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
        ChessClickListener.pieceSelected = false;
    }

    public static void createCheckerBoard(JPanel checkerBoardPanel) {
        GUI.turnWhite = true;
        board = new Board();
        checkerBoardPanel.setLayout(new GridLayout(8,8));
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                guiButtons[x][y] = new JButton();
                if ((x+y) % 2 == 0) {
                    guiButtons[x][y].setBackground(boardColorA);
                } else {
                    guiButtons[x][y].setBackground(boardColorB);
                }
                guiButtons[x][y].addActionListener(new ChessClickListener());
                checkerBoardPanel.add(guiButtons[x][y]);
            }
        }
    }

    public static void attributeImages() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                PieceInfo pieceAfil = board.getPiece(x,y).getAffiliation();
                PieceInfo pieceType = board.getPiece(x,y).getType();
                switch(pieceType) {
                    case ROOK:
                        guiButtons[x][y].setIcon(applyResizedImageToButton((pieceAfil == PieceInfo.WHITE ? whiteRook : blackRook), guiButtons[x][y]));
                        break;
                    case KNIGHT:
                        guiButtons[x][y].setIcon(applyResizedImageToButton((pieceAfil == PieceInfo.WHITE ? whiteKnight : blackKnight), guiButtons[x][y]));
                        break;
                    case BISHOP:
                        guiButtons[x][y].setIcon(applyResizedImageToButton((pieceAfil == PieceInfo.WHITE ? whiteBishop : blackBishop), guiButtons[x][y]));
                        break;
                    case QUEEN:
                        guiButtons[x][y].setIcon(applyResizedImageToButton((pieceAfil == PieceInfo.WHITE ? whiteQueen : blackQueen), guiButtons[x][y]));
                        break;
                    case KING:
                        guiButtons[x][y].setIcon(applyResizedImageToButton((pieceAfil == PieceInfo.WHITE ? whiteKing : blackKing), guiButtons[x][y]));
                        break;
                    case PAWN:
                        guiButtons[x][y].setIcon(applyResizedImageToButton((pieceAfil == PieceInfo.WHITE ? whitePawn : blackPawn), guiButtons[x][y]));
                        break;
                    default:
                        guiButtons[x][y].setIcon(applyResizedImageToButton(blank, guiButtons[x][y]));
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
}
