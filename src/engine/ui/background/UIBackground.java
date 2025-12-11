package engine.ui.background;

import com.jogamp.opengl.GL2;

public interface UIBackground {
    void draw(GL2 gl, float width, float height , float opacity);
    boolean isInside(float x, float y, float width, float height, double mouseX, double mouseY);
}