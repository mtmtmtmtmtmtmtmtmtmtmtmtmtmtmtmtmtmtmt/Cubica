package ir.mt;

import ir.mt.render.Renderer;
import ir.mt.window.WindowManager;
import static org.lwjgl.opengl.GL33.*; // for GL_TRIANGLE_STRIP

public class Main {
    public static void main(String[] args) {

        float[] squareVertices = {
                -0.5f,  0.5f, 0.0f,  // top-left
                -0.5f, -0.5f, 0.0f,  // bottom-left
                0.5f,  0.5f, 0.0f,  // top-right
                0.5f, -0.5f, 0.0f   // bottom-right
        };

        Renderer renderer = new Renderer();
        WindowManager wm = new WindowManager(renderer, () -> squareVertices, GL_TRIANGLE_STRIP);

        wm.run(); // WindowManager will call renderer.init() at the right time
    }
}
