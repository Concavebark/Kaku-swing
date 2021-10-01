package src.main.java;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUI {

    private static Board board = new Board();

    private static final ImageHandler imgHandler = new ImageHandler();

    private static JFrame gameFrame; // Required a script-wide scope reference to the JFrame used by our game loop
    private static JFrame mainMenuFrame; // Same thing as gameFrame, this is just an easier solution instead of having to think about it.

    public static String title = "Err: Title not reassigned";
    public static int width = 400;
    public static int height = 400;
    private static JButton[][] b = new JButton[8][8];
    private static ArrayList<Integer> moveData = new ArrayList<Integer>(4);

    //this later will be deprecated but i'm gonna use it to allow for something to fall back on
    public static void initStandardGUI() {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);

        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("FILE");
        JMenu m2 = new JMenu("Help");
        mb.add(m1);
        mb.add(m2);
        JMenuItem m11 = new JMenuItem("Open");
        JMenuItem m22 = new JMenuItem("Save as");
        m1.add(m11);
        m1.add(m22);

        JPanel panel = new JPanel();
        JLabel label = new JLabel("Enter Text");
        JTextField tf = new JTextField(10);
        JButton send = new JButton("Send");
        JButton reset = new JButton("Reset");
        panel.add(label);
        panel.add(tf);
        panel.add(send);
        panel.add(reset);

        JTextArea ta = new JTextArea();

        frame.getContentPane().add(BorderLayout.SOUTH, panel);
        frame.getContentPane().add(BorderLayout.NORTH, mb);
        frame.getContentPane().add(BorderLayout.CENTER, ta);

        frame.setVisible(true);
    }


    public static void createCheckerBoard(JPanel checkerBoardPanel) {
        checkerBoardPanel.setLayout(new GridLayout(8,8));
        //JButton b[][] = new JButton[8][8];
        for (int x = 0; x < 8; x++) {
            for (int y =0; y < 8; y++) {
                b[x][y] = new JButton();
                if ((x+y) % 2 == 0) {
                    b[x][y].setBackground(Color.WHITE);
                } else {
                    b[x][y].setBackground(Color.BLACK);
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
        JPanel exteriorPanel = new JPanel(); // will need a specific layout that i'll need to look into later
        exteriorPanel.setBorder(new EmptyBorder(10,10,10,10));
        //exteriorPanel.setPreferredSize(new Dimension(width, height));
        gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Disposes assets on close but doesn't close the program, hoping that this clears memory like I think it should.
        gameFrame.setSize(width, height); // PLEASE CHANGE THE SIZING SET HERE THIS SHOULDN'T MAKE IT PAST TESTING

        GridBagLayout gonk = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        exteriorPanel.setLayout(gonk);

        JButton quitGameButton = new JButton("Quit");
        JLabel playerOneLabel = new JLabel("sugma"); //obviously playerOneLabel and playerTwoLabel will be changed later
        JLabel playerTwoLabel = new JLabel("sugma");

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
    }

    public static void imageTesting(int _width, int _height) {
        JFrame testingFrame = new JFrame("Sugma");
        testingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        testingFrame.setSize(_width, _height);

        //ImageIcon whiteRook = imgHandler.getSimpleImage("/res/whiteRook.jpg");
        //ImageIcon whiteRook = new ImageIcon("res/whiteRook.jpg");

        ImageIcon whiteRook = imgHandler.getSimpleImage("res/whiteRook.jpg");

        int w = whiteRook.getIconWidth();
        int h = whiteRook.getIconHeight();

        JButton testButt = new JButton(whiteRook);
        testButt.setBounds(40, 80, w/8, h/8);

        testingFrame.add(testButt);
        testingFrame.setVisible(true);
    }

    // STOPPED LAST WAS ON https://www.javatpoint.com/java-gridbaglayout
    public static void genMainMenu(String _MainMenuTitle, int _width, int _height) {
        // should have just a few buttons, start, quit, probably something like an options menu

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

    private static void createBaseFrame() { //this isn't implemented correctly.
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
    }

    public static void setTitle(String _title) { title = _title; }
    public static String getTitle() { return title; }

    public static void setWidth(int _width) { width = _width; }
    public static int getWidth() { return width; }

    public static void setHeight(int _height) { height = _height; }
    public static int getHeight() { return height; }

    private static ArrayList<Integer> findButton(JButton _button) {
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

    //TODO: Figure out implementation to discover if the selected piece is movable by the current player
    //TODO: Also figure out a good way to store 4 ints as movement data eg: oldX, oldY, newX, newY
    private static class ChessClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            boolean pieceSelected = false;
            boolean playerHasMoved = false;
            if (!pieceSelected) {
                moveData.add(findButton(button).get(0));
                moveData.add(findButton(button).get(1));
                pieceSelected = true;
            } else if (pieceSelected) {
                moveData.add(findButton(button).get(0));
                moveData.add(findButton(button).get(1)); // then use this to move piece in the Board class
                board.movePiece(moveData.get(1), moveData.get(0), moveData.get(3), moveData.get(2));
                playerHasMoved = true;
                pieceSelected = false;
            }
            if (playerHasMoved) {
                moveData.clear(); //clears all move data so that moveData can be reused.
                pieceSelected = false;
                // THIS SHOULD ALSO BE THE POINT WHERE THE "TURN" GETS SWITCHED OVER, AND I JUST REALIZED I DON'T HAVE TURNS IMPLEMENTED YET
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
                    mainMenuFrame.setVisible(false);
                    break;
                case "Options":
                    // Obviously this will eventually be the Option menu with random included options
                    imageTesting(width, height);
                    break;
                case "Exit Game":
                    System.exit(0); // "Safely" exit the program
                    break;
                case "Quit":
                    mainMenuFrame.setVisible(true);
                    gameFrame.dispose();
                    break;
                default:
                    // it doesn't do anything, but it should eventually report an error in like a log file or something.
                    break;
            }
        }
    }
}
