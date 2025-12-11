package engine.text;

import com.jogamp.opengl.GL2;

interface TextEngine {
    float[] calculateDimensions(String text);
    void draw(GL2 gl, Text text);
}
