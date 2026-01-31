package ir.mt.render.shader;

import java.io.IOException;

public final class ShaderUtils {
    // Load shader from resources (inside jar)
    public static String loadShaderFromResources(String path) {
        try (var is = ShaderUtils.class.getResourceAsStream(path)) {
            if (is == null) throw new RuntimeException("Shader not found: " + path);
            return new String(is.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
