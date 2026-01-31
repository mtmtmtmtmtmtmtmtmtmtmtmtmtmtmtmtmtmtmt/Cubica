package ir.mt.render;

import ir.mt.render.shader.ShaderHelper;
import ir.mt.render.shader.ShaderUtils;

import static org.lwjgl.opengl.GL33.*;

public class Renderer2D {

    private int vao;
    private int vbo;
    private int shaderProgram;
    private int uColorLocation;

    public void init() {
        // Load shaders from resources
        String vertexShaderPath = "/shaders/vertex.glsl";
        String fragmentShaderPath = "/shaders/fragment.glsl";

        String vertexShader = ShaderUtils.loadShaderFromResources(vertexShaderPath);
        String fragmentShader = ShaderUtils.loadShaderFromResources(fragmentShaderPath);

        shaderProgram = ShaderHelper.createProgram(vertexShader, fragmentShader);
        uColorLocation = glGetUniformLocation(shaderProgram, "uColor");

        // Setup VAO / VBO
        vao = glGenVertexArrays();
        vbo = glGenBuffers();

        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 2 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);
        glBindVertexArray(0);
    }

    /**
     * Render any set of vertices as triangles.
     *
     * @param vertices Array of floats [x1,y1, x2,y2, ...] in NDC coordinates (-1 to 1)
     * @param r Red 0-1
     * @param g Green 0-1
     * @param b Blue 0-1
     */
    public void render(float[] vertices, float r, float g, float b) {
        glUseProgram(shaderProgram);
        glUniform4f(uColorLocation, r, g, b, 1f);

        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);

        // Upload dynamic vertices
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_DYNAMIC_DRAW);

        // Each 3 vertices = 1 triangle
        int vertexCount = vertices.length / 2; // because each vertex has 2 floats (x,y)
        glDrawArrays(GL_TRIANGLES, 0, vertexCount);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    public void cleanup() {
        if (vbo != 0) {
            glDeleteBuffers(vbo);
            vbo = 0;
        }
        if (vao != 0) {
            glDeleteVertexArrays(vao);
            vao = 0;
        }
        if (shaderProgram != 0) {
            glDeleteProgram(shaderProgram);
            shaderProgram = 0;
        }
    }

}
