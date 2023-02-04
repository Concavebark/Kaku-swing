package com.C4S.kaku_swing;

import javax.swing.*;
import java.awt.event.ActionListener;

public class optionsMenu {

    private String optionsFrameTitle = "Options";
    private JFrame optionsFrame = new JFrame(optionsFrameTitle);
    private int width, height;
    private JPanel optionsPanel = new JPanel();

    private JButton colorChangeA, colorChangeB, highlightChange;

    /***
     * OptionsMenu is a generated JFrame class that displays options for Swing variation of Kaku the empty param call is set up to be easy to use quickly for testing purposes and is not designed to be used for full time use
     */
    public optionsMenu() {
        optionsFrame.setTitle(optionsFrameTitle);
        optionsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        optionsFrame.setSize(400, 400);

        colorChangeA = new JButton("Change ColorA");
        colorChangeB = new JButton("Change ColorB");
        highlightChange = new JButton("Change Highlight Color");

        colorChangeA.addActionListener(new colorListener());
        colorChangeB.addActionListener(new colorListener());
        highlightChange.addActionListener(new colorListener());

        optionsPanel.add(colorChangeA);
        optionsPanel.add(colorChangeB);
        optionsPanel.add(highlightChange);
        optionsFrame.add(optionsPanel);

        optionsFrame.setVisible(true);
    }
    /***
     * OptionsMenu is a generated JFrame class that displays options for Swing variation of Kaku
     * @param optionsFrameTitle set title of the Options Menu JFrame
     * @param closeOperation set the JFrame's default close operation ( JFrame.DISPOSE_ON_CLOSE )
     * @param width set the width of the JFrame
     * @param height set the height of the JFrame
     * @param colorListener the action listener for handling color changes
     */
    public optionsMenu(String optionsFrameTitle, int closeOperation, int width, int height, ActionListener colorListener) {
        optionsFrame.setTitle(optionsFrameTitle);
        optionsFrame.setDefaultCloseOperation(closeOperation);
        optionsFrame.setSize(width, height);

        colorChangeA = new JButton("Change ColorA");
        colorChangeB = new JButton("Change ColorB");
        highlightChange = new JButton("Change Highlight Color");

        colorChangeA.addActionListener(colorListener);
        colorChangeB.addActionListener(colorListener);
        highlightChange.addActionListener(colorListener);

        optionsPanel.add(colorChangeA);
        optionsPanel.add(colorChangeB);
        optionsPanel.add(highlightChange);
        optionsFrame.add(optionsPanel);

        optionsFrame.setVisible(true);
    }

    public String getOptionsFrameTitle() {
        return optionsFrameTitle;
    }

    public void setOptionsFrameTitle(String optionsFrameTitle) {
        this.optionsFrameTitle = optionsFrameTitle;
    }

    public JFrame getOptionsFrame() {
        return optionsFrame;
    }

    public void setOptionsFrame(JFrame optionsFrame) {
        this.optionsFrame = optionsFrame;
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

    public JPanel getOptionsPanel() {
        return optionsPanel;
    }

    public void setOptionsPanel(JPanel optionsPanel) {
        this.optionsPanel = optionsPanel;
    }

    public JButton getColorChangeA() {
        return colorChangeA;
    }

    public void setColorChangeA(JButton colorChangeA) {
        this.colorChangeA = colorChangeA;
    }

    public JButton getColorChangeB() {
        return colorChangeB;
    }

    public void setColorChangeB(JButton colorChangeB) {
        this.colorChangeB = colorChangeB;
    }

    public JButton getHighlightChange() {
        return highlightChange;
    }

    public void setHighlightChange(JButton highlightChange) {
        this.highlightChange = highlightChange;
    }

}
