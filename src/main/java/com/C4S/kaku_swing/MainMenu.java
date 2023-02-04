package com.C4S.kaku_swing;

import javax.swing.*;
import java.awt.*;

public class MainMenu {


    private String mainMenuTitle = "Kaku-swing Chess";
    private JFrame mainMenuFrame = new JFrame(mainMenuTitle);
    private int width, height;

    /***
     * MainMenu is the main menu for the Swing variation of the Kaku chess engine
     * @param mainMenuTitle the title displayed by the main menu JFrame
     * @param width the width of the JFrame
     * @param height the height of the JFrame
     */
    public MainMenu(String mainMenuTitle, int width, int height) {

        mainMenuFrame.setTitle(mainMenuTitle);
        mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenuFrame.setSize(width, height);

        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        mainMenuFrame.setLayout(grid);
        GridBagLayout layout = new GridBagLayout();
        mainMenuFrame.setLayout(layout); //double check that having to setLayout calls is okay and non-redundant

        JLabel gameLabel = new JLabel(mainMenuTitle);
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

        mainMenuFrame.setVisible(true);
    }

    public String getMainMenuTitle() {
        return mainMenuTitle;
    }

    public void setMainMenuTitle(String mainMenuTitle) {
        this.mainMenuTitle = mainMenuTitle;
    }

    public JFrame getMainMenuFrame() {
        return mainMenuFrame;
    }

    public void setMainMenuFrame(JFrame mainMenuFrame) {
        this.mainMenuFrame = mainMenuFrame;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}