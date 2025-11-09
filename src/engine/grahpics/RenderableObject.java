package engine.grahpics;

import com.jogamp.opengl.GL2;
import engine.math.Transformation;


public abstract class RenderableObject {
    protected final Transformation transformation = new Transformation();

    protected RenderableObject() {}

    protected RenderableObject(float posX , float posY) {
        this.transformation.translateBy(posX, posY);
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

    protected abstract void onDraw(GL2 gl);


}
