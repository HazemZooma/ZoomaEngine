package engine.texture;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import engine.graphics.Colors;
import engine.graphics.RenderableObject;

public class Sprite extends RenderableObject {

    private float width;
    private float height;
    private float[] color;
    private float opacity;

    private final String filePath;
    private Texture texture;

    public Sprite(float x, float y, String filePath) {
        this(x, y, filePath, 20, 20);
    }

    public Sprite(float x, float y, String filePath, float width, float height) {
        this(x, y, filePath, width, height, Colors.WHITE);
    }

    public Sprite(float x, float y, String filePath, float width, float height, float[] color) {
        this(x, y, filePath, width, height, color, 1f);
    }

    public Sprite(float x, float y, String filePath, float width, float height, float[] color, float opacity) {
        super(x, y);
        this.filePath = filePath;
        this.width = width;
        this.height = height;
        this.color = color;
        this.opacity = opacity;

        this.texture = TextureManager.getInstance().get(filePath);
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    public void setWidth(float width){
        this.width = width;
    }

    public void setHeight(float height){
        this.height= height;
    }

    @Override
    protected void onDraw(GL2 gl) {

        if (texture == null || !texture.isLoaded()) {
            texture = TextureManager.getInstance().get(filePath);
        }

        boolean blendWasEnabled = gl.glIsEnabled(GL.GL_BLEND);
        gl.glEnable(GL.GL_BLEND);
//        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

        gl.glColor4f(color[0], color[1], color[2], opacity);

        gl.glEnable(GL.GL_TEXTURE_2D);
        texture.bind(gl);

        gl.glBegin(GL2.GL_QUADS);
        gl.glTexCoord2f(0, 0); gl.glVertex3f(-width / 2f, -height / 2f, -1);
        gl.glTexCoord2f(1, 0); gl.glVertex3f(width / 2f, -height / 2f, -1);
        gl.glTexCoord2f(1, 1); gl.glVertex3f(width / 2f, height / 2f, -1);
        gl.glTexCoord2f(0, 1); gl.glVertex3f(-width / 2f, height / 2f, -1);
        gl.glEnd();

        gl.glDisable(GL.GL_TEXTURE_2D);

        if (!blendWasEnabled) {
            gl.glDisable(GL.GL_BLEND);
        }
    }

}
