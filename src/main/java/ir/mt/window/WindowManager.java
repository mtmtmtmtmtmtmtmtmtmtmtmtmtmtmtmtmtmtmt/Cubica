package ir.mt.window;

import org.lwjgl.opengl.GL;

import static java.sql.Types.NULL;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11C.*;

public class WindowManager {
    private long window;

    public void showWindow() {
        initializeWindow();
        loop();
        cleanup();
    }

    private void initializeWindow() {
        // Initialize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        // Create the window
        window = glfwCreateWindow(800, 600, "Cubica - Yet Another Minecraft Clone", NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);

        // Enable v-sync
        glfwSwapInterval(1);

        // Show the window
        glfwShowWindow(window);

        // Create OpenGL capabilities
        GL.createCapabilities();
    }

    private void loop() {
        // Set clear color
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            // Swap buffers
            glfwSwapBuffers(window);

            // Poll events (keyboard, mouse, etc.)
            glfwPollEvents();
        }
    }

    private void cleanup() {
        glfwDestroyWindow(window);
        glfwTerminate();
    }

    private long getWindow() {
        return window;
    }
}
