package ir.mt.render;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Renderer {

    private int vao;
    private int vbo;
    private int shaderProgram;
    private boolean initialized = false;

    /** Must call this AFTER OpenGL context exists! */
    public void init() {
        if (initialized) return;

        setupVAO();
        setupShaders();
        initialized = true;
    }

    /** Render shape given vertices and draw mode */
    public void render(float[] vertices, int drawMode) {
        if (!initialized) throw new IllegalStateException("Renderer not initialized! Call init() after GL context.");

        int vertexCount = vertices.length / 3;

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_DYNAMIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glUseProgram(shaderProgram);
        glBindVertexArray(vao);
        glDrawArrays(drawMode, 0, vertexCount);
        glBindVertexArray(0);
        glUseProgram(0);
    }

    private void setupVAO() {
        vao = glGenVertexArrays();
        vbo = glGenBuffers();

        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);

        // placeholder vertex, will be replaced in render()
        glBufferData(GL_ARRAY_BUFFER, new float[]{0f, 0f, 0f}, GL_DYNAMIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    private void setupShaders() {
        String vertexShaderSource = """
            #version 330 core
            layout(location = 0) in vec3 aPos;
            void main() {
                gl_Position = vec4(aPos, 1.0);
            }
            """;

        String fragmentShaderSource = """
            #version 330 core
            out vec4 FragColor;
            void main() {
                FragColor = vec4(1.0, 0.0, 0.0, 1.0);
            }
            """;

        int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShader, vertexShaderSource);
        glCompileShader(vertexShader);
        checkCompileErrors(vertexShader, "VERTEX");

        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShader, fragmentShaderSource);
        glCompileShader(fragmentShader);
        checkCompileErrors(fragmentShader, "FRAGMENT");

        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexShader);
        glAttachShader(shaderProgram, fragmentShader);
        glLinkProgram(shaderProgram);
        checkCompileErrors(shaderProgram, "PROGRAM");

        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
    }

    private void checkCompileErrors(int shader, String type) {
        int success;
        if (type.equals("PROGRAM")) {
            success = glGetProgrami(shader, GL_LINK_STATUS);
            if (success == GL_FALSE) System.err.println("Program linking failed:\n" + glGetProgramInfoLog(shader));
        } else {
            success = glGetShaderi(shader, GL_COMPILE_STATUS);
            if (success == GL_FALSE) System.err.println("Shader compile failed:\n" + glGetShaderInfoLog(shader));
        }
    }

    public void cleanup() {
        if (!initialized) return;

        glDeleteVertexArrays(vao);
        glDeleteBuffers(vbo);
        glDeleteProgram(shaderProgram);
        initialized = false;
    }
}
