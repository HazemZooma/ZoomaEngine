package base_shapes;

import com.jogamp.opengl.GL2;

import engine.grahpics.Colors;
import engine.grahpics.RenderableObject;


public abstract class Shape extends RenderableObject {
    protected float[] color = Colors.WHITE;

    public Shape() {}

    public Shape(float x, float y, float[] color) {
        this.transformation.translateBy(x, y);
        this.color = color;
    }

    public float[] getColor() {
        return color.clone();
    }

    public void setColor(float[] color) {
        this.color = color;
    }

    protected void applyColor(GL2 gl) {
        gl.glColor3fv(color, 0);
    }
}
