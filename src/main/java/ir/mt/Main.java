package ir.mt;

import ir.mt.render.Renderer2D;
import ir.mt.window.WindowManager;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11C.*;

public class Main {
    public static void main(String[] args) {

        WindowManager window = new WindowManager();
        window.initializeWindow();
        Renderer2D renderer = new Renderer2D();
        renderer.init();

        while (!glfwWindowShouldClose(window.getWindow())) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            float dx = -0.7f; // move left
            float dy = 0.8f; // move up

            float[] triangle = {
                    0.0f + dx, 0.1f + dy,
                    -0.2f + dx, -0.5f + dy,
                    0.2f + dx, -0.5f + dy
            };

            renderer.render(triangle, 1, 0, 0);

            glfwSwapBuffers(window.getWindow());
            glfwPollEvents();
        }

        renderer.cleanup();
        window.cleanup();
    }
}
