import org.lwjgl.opengl.GL;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public class Main {
    public static void main(String[] args) {
        DisplayManager.createDisplay();
        GL.createCapabilities();

        while (!DisplayManager.isCloseRequested()) {
            //glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
            DisplayManager.updateDisplay();
        }

        DisplayManager.destroyDisplay();
    }

    /**
     * Determines if the OpenGL context supports version 3.2.
     *
     * @return true, if OpenGL context supports version 3.2, else false
     */
    public static boolean isDefaultContext() {
        return GL.getCapabilities().OpenGL32;
    }
}
