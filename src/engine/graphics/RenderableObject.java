// java
// File: src/engine/graphics/RenderableObject.java
package engine.graphics;

import com.jogamp.opengl.GL2;
import engine.math.Transformation;

public abstract class RenderableObject {
    protected final Transformation transformation = new Transformation();
    protected RenderableObject parent;

    protected RenderableObject() {
    }

    protected RenderableObject(float posX , float posY) {
        this.transformation.translateTo(posX,posY);
    }

    public Transformation getTransformation() {
        return transformation;
    }

    public final void draw(GL2 gl) {
        gl.glPushMatrix();
        transformation.apply(gl);
        onDraw(gl);
        gl.glPopMatrix();
    }

    public abstract float getWidth();
    public abstract float getHeight();
    protected abstract void onDraw(GL2 gl);

    public void setParent(RenderableObject parent) {
        this.parent = parent;
    }

    public RenderableObject getParent() {
        return parent;
    }
}
