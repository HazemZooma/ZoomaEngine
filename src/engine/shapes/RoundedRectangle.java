package engine.shapes;

import App_constants.Constants;
import com.jogamp.opengl.GL2;
import engine.graphics.Colors;

import java.util.ArrayList;
import java.util.List;

public class RoundedRectangle extends Shape {
    private final List<Circle> corners;
    private float width;
    private float height;
    private float radius;
    private Rectangle innerRectangle;
    private Rectangle outerRectangle;

    private RoundedRectangle(Builder builder) {
        super(builder.posX, builder.posY , builder.color );
        this.width = builder.width;
        this.height = builder.height;
        this.radius = builder.radius;

        this.corners = new ArrayList<>();

        initRectangle();

        setOpacity(opacity);
    }

    private void initRectangle() {
        float halfWidth = width / 2.0f;
        float halfHeight = height / 2.0f;

        float maxRadius = Math.min(halfWidth, halfHeight);
        this.radius = Math.min(this.radius, maxRadius);

        this.innerRectangle = new Rectangle(0, 0, (this.width - (2 * radius)), this.height, color);
        this.outerRectangle = new Rectangle(0, 0, this.width, (this.height - 2 * radius), color);

        float bottomCornerCenterY = -halfHeight + radius;
        float topCornerCenterY = halfHeight - radius;
        float leftCornerCenterX = -halfWidth + radius;
        float rightCornerCenterX = halfWidth - radius;

        corners.add(Circle.createArc(leftCornerCenterX, bottomCornerCenterY,
                radius, Constants.Math.ONE_EIGHTY_DEGREES,
                Constants.Math.TWO_SEVENTY_DEGREES, color));

        corners.add(Circle.createArc(rightCornerCenterX - 1, bottomCornerCenterY,
                radius, Constants.Math.TWO_SEVENTY_DEGREES,
                Constants.Math.THREE_SIXTY, color));

        corners.add(Circle.createArc(rightCornerCenterX - 1, topCornerCenterY,
                radius, 0,
                Constants.Math.NINETY_DEGREES, color));

        corners.add(Circle.createArc(leftCornerCenterX, topCornerCenterY,
                radius, Constants.Math.NINETY_DEGREES,
                Constants.Math.ONE_EIGHTY_DEGREES, color));
    }

    @Override
    public void onDraw(GL2 gl) {
        outerRectangle.draw(gl);
        innerRectangle.draw(gl);
        for (Circle corner : corners) {
            corner.draw(gl);
        }
    }

    public void setColor(float[] color) {
        for (Circle corner : corners) {
            corner.setColor(color);
        }
        innerRectangle.setColor(color);
        outerRectangle.setColor(color);
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float v) {
        radius = v;
    }

    @Override
    public void setOpacity(float opacity){
        this.innerRectangle.setOpacity(opacity);
        this.outerRectangle.setOpacity(opacity);
        this.corners.forEach(c -> c.setOpacity(opacity));
    }

    public static class Builder {
        private float posX , posY;
        private float width = 100f;
        private float height = 40f;
        private float radius = 10f;
        private float[] color = Colors.WHITE;
        private float minWidth = 20f;
        private float minHeight = 20f;

        public Builder at(float posX ,float posY){
            this.posX = posX;
            this.posY = posY;
            return this;
        }

        public Builder width(float width) {
            this.width = width;
            return this;
        }

        public Builder height(float height) {
            this.height = height;
            return this;
        }

        public Builder radius(float radius) {
            this.radius = radius;
            return this;
        }

        public Builder color(float[] color) {
            this.color = color;
            return this;
        }

        public Builder minWidth(float minWidth) {
            this.minWidth = minWidth;
            return this;
        }

        public Builder minHeight(float minHeight) {
            this.minHeight = minHeight;
            return this;
        }

        public RoundedRectangle build() {
            width = Math.max(width, minWidth);
            height = Math.max(height, minHeight);

            return new RoundedRectangle(this);
        }
    }
}
