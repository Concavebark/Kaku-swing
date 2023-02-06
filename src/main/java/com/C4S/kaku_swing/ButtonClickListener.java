package com.C4S.kaku_swing;

import javax.security.auth.login.LoginException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.C4S.kaku_swing.GUI.*; // TODO: ONCE GAMESCREEN IS MOVED, CHANGE THIS

public class ButtonClickListener implements ActionListener {


    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Start Game":
                new GameScreen(width, height); // run important logic for game start here
                break;
            case "Options":
                // Obviously this will eventually be the Option menu with random included options
                new optionsMenu("Options", JFrame.DISPOSE_ON_CLOSE, width, height, new colorListener());
                break;
            case "Exit Game":
                System.exit(0); // "Safely" exit the program
                break;
            case "Quit":
                GameScreen.gameFrame.dispose();
                break;
            default:
                break;
        }
    }
}