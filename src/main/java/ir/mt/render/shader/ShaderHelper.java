package ir.mt.render.shader;

import static org.lwjgl.opengl.GL33.*;

public final class ShaderHelper {

    private ShaderHelper() {}

    public static int createProgram(String vertexSrc, String fragmentSrc) {
        int vertex = compile(GL_VERTEX_SHADER, vertexSrc);
        int fragment = compile(GL_FRAGMENT_SHADER, fragmentSrc);

        int program = glCreateProgram();
        glAttachShader(program, vertex);
        glAttachShader(program, fragment);
        glLinkProgram(program);

        if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
            throw new RuntimeException(
                    "SHADER LINK ERROR:\n" + glGetProgramInfoLog(program)
            );
        }

        glDetachShader(program, vertex);
        glDetachShader(program, fragment);
        glDeleteShader(vertex);
        glDeleteShader(fragment);

        return program;
    }

    private static int compile(int type, String source) {
        int shader = glCreateShader(type);
        glShaderSource(shader, source);
        glCompileShader(shader);

        if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
            String name = type == GL_VERTEX_SHADER ? "VERTEX" : "FRAGMENT";
            throw new RuntimeException(
                    name + " SHADER COMPILE ERROR:\n" + glGetShaderInfoLog(shader)
            );
        }

        return shader;
    }
}
