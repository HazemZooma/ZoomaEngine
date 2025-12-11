package engine.text;


import com.jogamp.opengl.GL2;
import engine.core.EngineContext;
import engine.graphics.Colors;
import engine.graphics.RenderableObject;
import engine.ui.core.UIElement;

import java.util.Arrays;

public class Text extends RenderableObject {
    private String content;
    private float[] color = Colors.BLUE;

    private TextEngine textEngine;

    public Text(float x, float y) {
        this(x, y, "hazem", 20, Colors.WHITE, "SansSerif");
    }

    public Text(float x, float y, String content) {
        this(x, y, content, 20, Colors.WHITE, "SansSerif");
    }

    public Text(float x, float y, String content, int fontSize) {
        this(x, y, content, fontSize, Colors.WHITE, "SansSerif");
    }

    public Text(float x, float y, String content, int fontSize, float[] color) {
        this(x, y, content, fontSize, color, "SansSerif");
    }

    public Text(float x, float y, String content, int fontSize, float[] color, String fontName) {
        super(x, y);
        this.content = content;
        fontSize = EngineContext.get().orthoToPixelHeight(fontSize);
        this.color = color;
        this.textEngine = new JoglTextEngine(fontName, fontSize);

    }

    @Override
    public float getWidth() {
        return getSize()[0];
    }

    @Override
    public float getHeight() {
        return getSize()[1];
    }

    @Override
    public void onDraw(GL2 gl) {
        textEngine.draw(gl, this);
    }



    public float[] getSize() {
        return textEngine.calculateDimensions(content);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String newMessage) {
        this.content = newMessage;
    }


    public void setParent(UIElement parent) {
        this.parent = parent;
    }

    public float[] getColor() {
        return Arrays.copyOf(color, color.length);
    }

    public void setColor(float[] color) {
        this.color = color;
    }

    public void setFont(String fontName, int fontSize) {
        this.textEngine = new JoglTextEngine(fontName, fontSize);
    }
}
