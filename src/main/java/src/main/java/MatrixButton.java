package src.main.java;

import javax.swing.*;
import java.io.Serial;

//Simple StackOverflow solution to solve possible issue with finding which square on the board the player selected

public class MatrixButton extends JButton {
    @Serial
    private static final long serialVersionUID = 6900259199910841340L;
    private final int buttonX;
    private final int buttonY;

    public MatrixButton(int buttonX, int buttonY) {
        this.buttonX = buttonX;
        this.buttonY = buttonY;
    }

    public int getX() {
        return buttonX;
    }

    public int getY() {
        return buttonY;
    }
}
