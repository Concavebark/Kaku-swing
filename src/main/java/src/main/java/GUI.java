package src.main.java;

import javax.swing.*;
import java.awt.*;

public class GUI {

    public static String title = "My First GUI";
    public static int width = 400;
    public static int height = 400;

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


    // STOPPED LAST WAS ON https://www.javatpoint.com/java-gridbaglayout
    public static void genMainMenu(String _MainMenuTitle, int _width, int _height) {
        // should have just a few buttons, start, quit, probably something like an options menu
        setTitle(_MainMenuTitle); // should be done differently, sloppy code.
        setWidth(_width); // could probably be done outside of method call. eg: make these calls prior to calling genMainMenu
        setHeight(_height); // same issue as with the setWidth() call
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);

        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        frame.setLayout(grid);
        GridBagLayout layout = new GridBagLayout();
        frame.setLayout(layout); //two setLayout calls idk about that one chief

        JLabel gameLabel = new JLabel("Kaku-swing Chess");
        JButton startButton = new JButton("Start Game");
        JButton optionButton = new JButton("Options");
        JButton quitButton = new JButton("Exit Game");


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        frame.add(gameLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        frame.add(startButton, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 1;
        frame.add(optionButton, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;
        frame.add(quitButton, gbc);

        frame.setVisible(true); // could probably make this separate if more calls are required before setting frame visible
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
}
