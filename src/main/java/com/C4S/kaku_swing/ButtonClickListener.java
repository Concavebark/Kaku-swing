package com.C4S.kaku_swing;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.C4S.kaku_swing.GUI.*;

public class ButtonClickListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Start Game":
                gameScreen(width, height); // run important logic for game start here
                break;
            case "Options":
                // Obviously this will eventually be the Option menu with random included options
                new optionsMenu("Options", JFrame.DISPOSE_ON_CLOSE, width, height, new colorListener());
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