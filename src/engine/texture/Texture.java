package engine.texture;

import com.jogamp.opengl.GL2;

import java.io.IOException;

class Texture {
    private final String filePath;
    private int id = 0;
    private int width;
    private int height;
    private boolean loaded = false;
    private TextureReader.Texture texture;

    Texture(String filePath) {
        this.filePath = filePath;
    }

    private void loadImageData() {
        if (texture != null) return;
        try {
            texture = TextureReader.readTexture(filePath, true);
            width = texture.getWidth();
            height = texture.getHeight();
        } catch (IOException e) {
            System.err.println("Failed to load texture: " + filePath);
            e.printStackTrace();
        }
    }

    public void uploadToGPU(GL2 gl) {
        if (loaded) return;

        loadImageData();
        if (texture == null) return;

        int[] ids = new int[1];
        gl.glGenTextures(1, ids, 0);
        id = ids[0];

        gl.glBindTexture(GL2.GL_TEXTURE_2D, id);

        gl.glTexImage2D(
                GL2.GL_TEXTURE_2D,
                0,
                GL2.GL_RGBA,
                width,
                height,
                0,
                GL2.GL_RGBA,
                GL2.GL_UNSIGNED_BYTE,
                texture.getPixels()
        );

        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR_MIPMAP_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);

        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP_TO_EDGE);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP_TO_EDGE);

        gl.glGenerateMipmap(GL2.GL_TEXTURE_2D);

        gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);
        loaded = true;
    }

    void bind(GL2 gl) {
        if (loaded) gl.glBindTexture(GL2.GL_TEXTURE_2D, id);
    }

    void destroy(GL2 gl) {
        if (id != 0) {
            gl.glDeleteTextures(1, new int[]{id}, 0);
            id = 0;
        }
    }

    public int getWidth()  { return width; }
    public int getHeight() { return height; }
    public int getTextureID() { return id; }
    public boolean isLoaded() { return loaded; }
}
