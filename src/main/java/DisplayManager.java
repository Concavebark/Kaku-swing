import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class DisplayManager {

    private static long window;

    public static void createDisplay() {

        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW! Fuck!");

        //glfwDefaultWindowHints(); // apparently optional
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        int WIDTH = 640;
        int HEIGHT = 480;

        //Creating the window ig
        window = glfwCreateWindow(WIDTH, HEIGHT, "HELLO FELLOW FORTNITE GAMERS", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window! Fuck!");

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true);
        });

        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        glfwSetWindowPos(
                window,
                (vidmode.width() - WIDTH) /2,
                (vidmode.height() - HEIGHT) / 2
        );

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1); //enables v-sync (kill this later lmao)

        glfwShowWindow(window);
    }

    public static boolean isCloseRequested() {
        return glfwWindowShouldClose(window);
    }

    public static void updateDisplay() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

        glfwSwapBuffers(window); //Drawing needs to be done before this, aka before the updateDisplay call
        glfwPollEvents();
    }

    public static void destroyDisplay() {
        cleanUp();
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public static void cleanUp() {
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
    }
}