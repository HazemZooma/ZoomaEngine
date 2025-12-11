package engine.ui.background;

import com.jogamp.opengl.GL2;
import engine.graphics.Colors;
import engine.shapes.*;

public class GeometricBackground implements UIBackground {
    private Shape shape;
    private ShapeType shapeType;
    private float[] color;
    private float radius;
    private boolean isDirty = true;

    private GeometricBackground(Builder builder) {
        this.shapeType = builder.shapeType;
        this.color = builder.color;
        this.radius = builder.radius;
    }

    @Override
    public void draw(GL2 gl, float width, float height , float opacity) {
        if (shape == null || isDirty){
//            System.out.println(isDirty);
            updateShape(width, height , opacity);
        }

        shape.draw(gl);
    }

    @Override
    public boolean isInside(float x, float y, float width, float height, double mouseX, double mouseY) {
        if (shapeType == ShapeType.RECTANGLE) {
            return isInsideRectangle(x, y, width, height, mouseX, mouseY);
        } else if (shapeType == ShapeType.CIRCLE) {
            return isInsideCircle(x, y, width, height, mouseX, mouseY);
        } else {
            return isInsideRoundedRectangle(x, y, width, height, mouseX, mouseY);
        }
    }

    private boolean isInsideRectangle(float x, float y, float width, float height, double mouseX, double mouseY) {
        return mouseX >= x - width / 2f && mouseX <= x + width / 2f &&
                mouseY >= y - height / 2f && mouseY <= y + height / 2f;
    }

    private boolean isInsideCircle(float x, float y, float width, float height, double mouseX, double mouseY) {
        double r = Math.min(width, height) / 2.0;
        double dx = mouseX - x;
        double dy = mouseY - y;
        return (dx * dx + dy * dy) <= (r * r);
    }

    private boolean isInsideRoundedRectangle(float x, float y, float width, float height, double mouseX, double mouseY) {
        float left = x - width / 2f;
        float right = x + width / 2f;
        float bottom = y - height / 2f;
        float top = y + height / 2f;
        float r = Math.max(0f, Math.min(radius, Math.min(width, height) / 2f));

        if (mouseX >= left + r && mouseX <= right - r && mouseY >= bottom + r && mouseY <= top - r) {
            return true;
        }
        if (mouseX >= left + r && mouseX <= right - r && mouseY >= bottom && mouseY <= top) {
            return true;
        }
        if (mouseY >= bottom + r && mouseY <= top - r && mouseX >= left && mouseX <= right) {
            return true;
        }
        if (isPointInCornerCircle(mouseX, mouseY, left + r, bottom + r, r)) return true;
        if (isPointInCornerCircle(mouseX, mouseY, right - r, bottom + r, r)) return true;
        if (isPointInCornerCircle(mouseX, mouseY, right - r, top - r, r)) return true;
        return isPointInCornerCircle(mouseX, mouseY, left + r, top - r, r);
    }

    private boolean isPointInCornerCircle(double pointX, double pointY,
                                          double circleCenterX, double circleCenterY, double radius) {
        double dx = pointX - circleCenterX;
        double dy = pointY - circleCenterY;
        return (dx * dx + dy * dy) <= (radius * radius);
    }

    private Shape createShape(float width, float height , float opacity , float radius) {

//        System.out.println("entered");

        return switch (shapeType) {
            case RECTANGLE -> {
                Rectangle dummy = new Rectangle(0, 0, width, height , color);
                dummy.setOpacity(opacity);
                yield dummy;
            }
            case CIRCLE -> {
                Circle dummy =  Circle.createCircle(0, 0, radius, color);
                dummy.setOpacity(opacity);

                yield dummy;
            }
            default -> {
                RoundedRectangle dummy = new RoundedRectangle.Builder()
                        .at(0, 0)
                        .width(width)
                        .height(height)
                        .radius(radius)
                        .color(color)
                        .build();
                dummy.setOpacity(opacity);

                yield dummy;
            }
        };
    }


    private void updateShape(float width, float height , float opacity) {

        if (radius == 0 && shapeType == ShapeType.ROUNDED_RECTANGLE) {
            shapeType = ShapeType.RECTANGLE;
        }

        float adjustedRadius = radius;
        if (shapeType == ShapeType.CIRCLE) {
            adjustedRadius = Math.min(width, height) / 2f;
        } else if (shapeType == ShapeType.ROUNDED_RECTANGLE) {
            adjustedRadius = Math.min(radius, Math.min(width, height) / 2f);
        }

        if (Math.abs(radius - adjustedRadius) < 0.01f && shape != null){
//            System.out.println("current shape type : " +  curr.name() + "shape type : " + shapeType.name());
            isDirty = false;
            return;
        }

        this.radius = adjustedRadius;

//        System.out.println("entered");

        this.shape = createShape(width, height , opacity , adjustedRadius);
    }

    public float[] getColor() {
        return color;
    }

    public void setColor(float[] color) {
        this.color = color != null ? color : Colors.WHITE;
        isDirty = true;
    }

    public ShapeType getShapeType() {
        return shapeType;
    }

    public void setShapeType(ShapeType shapeType) {
        this.shapeType = shapeType == null ? ShapeType.RECTANGLE : shapeType;
        isDirty = true;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = Math.max(0f, radius);
        isDirty = true;
    }

    public static class Builder {
        private float radius = 10f;
        private float[] color = Colors.BLUE;
        private ShapeType shapeType = ShapeType.RECTANGLE;

        public Builder() {
        }

        public Builder radius(float radius) {
            this.radius = Math.max(0f, radius);
            return this;
        }

        public Builder color(float[] color) {
            this.color = color != null ? color : Colors.BLUE;
            return this;
        }

        public Builder color(float red, float green, float blue) {
            this.color = new float[]{red, green, blue};
            return this;
        }

        public Builder color(float red, float green, float blue, float alpha) {
            this.color = new float[]{red, green, blue, alpha};
            return this;
        }

        public Builder shapeType(ShapeType shapeType) {
            this.shapeType = shapeType != null ? shapeType : ShapeType.RECTANGLE;
            return this;
        }

        public GeometricBackground build() {
            return new GeometricBackground(this);
        }
    }
}