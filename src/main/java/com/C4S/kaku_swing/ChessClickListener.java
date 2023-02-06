package com.C4S.kaku_swing;

import net.jameskoehler.kaku.PieceInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static com.C4S.kaku_swing.GameScreen.turnIndicatorColor;

public class ChessClickListener implements ActionListener {

    public static boolean pieceSelected;

    private static ArrayList<Integer> moveData = new ArrayList<Integer>(4);
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource(); // TODO: RIGHT HERE BUDD
        boolean playerHasMoved = false;
        if (!pieceSelected) {
            if (GUI.turnWhite && GUI.board.getPiece(findCordsFromButton(button).get(0), findCordsFromButton(button).get(1)).getAffiliation() == PieceInfo.WHITE) {
                moveData.add(findCordsFromButton(button).get(0));
                moveData.add(findCordsFromButton(button).get(1));
                pieceSelected = true;
            } else if (!GUI.turnWhite && GUI.board.getPiece(findCordsFromButton(button).get(0), findCordsFromButton(button).get(1)).getAffiliation() == PieceInfo.BLACK) {
                moveData.add(findCordsFromButton(button).get(0));
                moveData.add(findCordsFromButton(button).get(1));
                pieceSelected = true;
            } else { pieceSelected = false; }

            if (pieceSelected) {
                for (int y = 0; y < 8; y++)
                    for (int x = 0; x < 8; x++)
                        if (GUI.board.isMoveValid(moveData.get(0), moveData.get(1), y, x) && GUI.board.getPiece(moveData.get(0), moveData.get(1)).getType() != PieceInfo.BLANK)
                            GameScreen.guiButtons[y][x].setBackground(GameScreen.highlightColor);
            }

        } else if (pieceSelected) {
            moveData.add(findCordsFromButton(button).get(0));
            moveData.add(findCordsFromButton(button).get(1)); // then use this to move piece in the Board class
            //TODO: this currently works as movement and everything, but really should change it to be something that the player can actually see and understand
            if (GUI.board.movePiece(moveData.get(0), moveData.get(1), moveData.get(2), moveData.get(3)) == 0) {
                playerHasMoved = true;
                if (GUI.turnWhite) {
                    GameScreen.playerTwoLabel.setForeground(Color.BLACK);
                    GameScreen.playerOneLabel.setForeground(turnIndicatorColor);
                } else {
                    GameScreen.playerOneLabel.setForeground(Color.BLACK);
                    GameScreen.playerTwoLabel.setForeground(turnIndicatorColor);
                }
            } else if (GUI.board.movePiece(moveData.get(0), moveData.get(1), moveData.get(2), moveData.get(3)) == 2) {
                //white win
            } else if (GUI.board.movePiece(moveData.get(0), moveData.get(1), moveData.get(2), moveData.get(3)) == 3) {
                //black win
            } else {
                moveData.clear();
            }
            pieceSelected = false;

            for(int y = 0; y < 8; y++)
                for(int x = 0; x < 8; x++)
                    GameScreen.guiButtons[y][x].setBackground(((y + x) % 2 == 0 ? GameScreen.boardColorA : GameScreen.boardColorB));
        }
        if (playerHasMoved) {
            GUI.turnWhite = !GUI.turnWhite; // switch turns lol
            moveData.clear(); //clears all move data so that moveData can be reused.
            pieceSelected = false;
            GameScreen.attributeImages();
        }
    }

    private  ArrayList<Integer> findCordsFromButton(JButton _button) {
        ArrayList<Integer> location = new ArrayList<>(4);
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (_button.equals(GameScreen.guiButtons[x][y])) {
                    location.add(x);
                    location.add(y);
                    //System.out.println(GUI.board.getPiece(x,y).getAffiliation() + " " + GUI.board.getPiece(x,y).getType());
                }
            }
        }
        return location;
    }
}
