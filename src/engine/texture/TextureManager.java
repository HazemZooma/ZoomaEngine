package engine.texture;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLContext;

import java.util.HashMap;
import java.util.Map;

class TextureManager {
    private static final TextureManager instance = new TextureManager();
    private static final Map<String, Texture> textureCache = new HashMap<>();

    private TextureManager() {
    }

    static TextureManager getInstance() {
        return instance;
    }

    Texture get(String filePath) {
        return textureCache.computeIfAbsent(filePath, path -> {
            GL2 gl = GLContext.getCurrentGL().getGL2();
            Texture texture = new Texture(path);
            texture.uploadToGPU(gl);
            return texture;
        });
    }

    static void deleteAll() {
        GL2 gl = GLContext.getCurrentGL().getGL2();

        for (Texture texture : textureCache.values()) {
            if (texture.isLoaded()) {
                gl.glDeleteTextures(1, new int[]{texture.getTextureID()}, 0);
            }
        }

        textureCache.clear();
    }
}
