package engine.shapes;

import com.jogamp.opengl.GL2;
import engine.graphics.Colors;
import engine.graphics.RenderableObject;


public abstract class Shape extends RenderableObject {
    protected float[] color = Colors.WHITE;
    protected float opacity = 1f;

    public Shape() {
    }

    public Shape(float x, float y, float[] color) {
        this(x,y,color, 1f);
    }

    public Shape(float x , float y , float[] color , float opacity){
        super(x,y);
        this.color = color;
        this.opacity = opacity;

    }

    public float getOpacity() {
        return opacity;
    }

    public void setOpacity(float opacity) {
        this.opacity = Math.max(0f , opacity);
    }

    public float[] getColor() {
        return color.clone();
    }

    public void setColor(float[] color) {
        this.color = color;
    }

    protected void applyColor(GL2 gl) {
        gl.glColor4f(color[0] , color[1] , color[2] , opacity);
    }
}
