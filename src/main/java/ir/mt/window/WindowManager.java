package ir.mt.window;

import ir.mt.render.Renderer;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

import java.util.function.Supplier;

public class WindowManager {

    private long window;
    private final Renderer renderer;
    private final Supplier<float[]> verticesSupplier;
    private final int drawMode;

    public WindowManager(Renderer renderer, Supplier<float[]> verticesSupplier, int drawMode) {
        this.renderer = renderer;
        this.verticesSupplier = verticesSupplier;
        this.drawMode = drawMode;
    }

    public void run() {
        init();
        loop();
        cleanup();
    }

    private void init() {
        if (!glfwInit()) throw new IllegalStateException("GLFW init failed");

        window = glfwCreateWindow(800, 600, "Cubica - Yet Another Minecraft Clone", 0, 0);
        if (window == 0) throw new RuntimeException("Failed to create window");

        glfwMakeContextCurrent(window);
        glfwShowWindow(window);

        // Create OpenGL capabilities — MUST be done before any OpenGL call
        GL.createCapabilities();

        renderer.init();
    }


    private void loop() {
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT);

            // Get vertices from Main
            float[] vertices = verticesSupplier.get();

            // Render them
            renderer.render(vertices, drawMode);

            glfwSwapBuffers(window);
            glfwPollEvents();
        }

        cleanup();
    }

    private void cleanup() {
        renderer.cleanup();
        glfwDestroyWindow(window);
        glfwTerminate();
    }
}
