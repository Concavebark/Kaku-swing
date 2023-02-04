package com.C4S.kaku_swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.C4S.kaku_swing.GUI.*;

public class colorListener extends Component implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Change ColorA" -> {
                Color initialColor = boardColorA;
                boardColorA = JColorChooser.showDialog(this, "Select a Color", initialColor);

                saveLoadHandler.writeToSettingsFile("colorA", String.valueOf(saveLoadHandler.generateRGBCode(boardColorA.getRed(), boardColorA.getGreen(), boardColorA.getBlue())));

            }
            case "Change ColorB" -> {
                Color initialColor = boardColorB;
                boardColorB = JColorChooser.showDialog(this, "Select a Color", initialColor);

                saveLoadHandler.writeToSettingsFile("colorB", String.valueOf(saveLoadHandler.generateRGBCode(boardColorB.getRed(), boardColorB.getGreen(), boardColorB.getBlue())));
            }
            case "Change Highlight Color" -> {
                Color initialColor = highlightColor;
                highlightColor = JColorChooser.showDialog(this, "Select a Color", initialColor);

                saveLoadHandler.writeToSettingsFile("colorH", String.valueOf(saveLoadHandler.generateRGBCode(highlightColor.getRed(), highlightColor.getGreen(), highlightColor.getBlue())));
            }
        }

    }
}