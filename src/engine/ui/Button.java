package engine.ui;

import com.jogamp.opengl.GL2;

public class Button extends UIElement {
    private final Runnable onClick;
    private final float width;
    private final float height;

    public Button(float x, float y, float width, float height, Runnable onClick) {
//        super(x, y, width, height , onClick);
        this.width = width;
        this.height = height;
        this.onClick = onClick;
    }

    public boolean isInside(double glX, double glY) {
        float x = getTransformation().getTranslateX();
        float y = getTransformation().getTranslateY();
        return glX >= x && glX <= x + width &&
                glY >= y && glY <= y + height;
    }

    public void click() {
        if (onClick != null) onClick.run();
    }

    @Override
    protected void onDraw(GL2 gl) {

    }
}

