package engine.ui.core;

import com.jogamp.opengl.GL2;
import engine.graphics.Colors;
import engine.graphics.RenderableObject;
import engine.ui.behaviors.Draggable;
import engine.shapes.Circle;
import engine.shapes.RoundedRectangle;
import engine.shapes.Rectangle;

import java.util.function.Consumer;

public class Slider extends UIElement implements Draggable {
    private final float minValue;
    private final float maxValue;
    private float currentValue;
    private Orientation orientation;
    private RenderableObject track;
    private RenderableObject thumb;
    private RenderableObject fill;
    private boolean dragging = false;
    private boolean hovered = false;
    private boolean showFill = false;
    private float thumbOffset = 0;

    private float[] trackColor = Colors.GRAY;
    private float[] fillColor = Colors.BLUE;

    private Consumer<Float> onValueChanged;

    private Slider(float x, float y, float width, float height,
                   float min, float max, float initialValue,
                   Orientation orientation) {
        super(x, y, width, height);
        this.minValue = min;
        this.maxValue = max;
        this.currentValue = Math.max(min, Math.min(max, initialValue));
        this.orientation = orientation;

        createDefaultTrack();
        createDefaultThumb();
        createDefaultFill();

        updateThumbPosition();
        updateFill();
    }

    private void createDefaultTrack() {
        this.track = new RoundedRectangle.Builder()
                .width(width)
                .height(height / 4)
                .radius(height / 8)
                .color(trackColor)
                .build();
    }

    private void createDefaultThumb() {
        this.thumb = Circle.createCircle(
                0, 0,
                Math.min(width, height) / 3,
                new float[]{0.2f, 0.5f, 0.8f, 1.0f}
        );
    }

    private void createDefaultFill() {
        if (orientation == Orientation.HORIZONTAL) {
            this.fill = new Rectangle( -width/2, 0, 0f, height / 3f, fillColor);
        } else {
            this.fill = new Rectangle(0, -height/2, width / 2f, 0f, fillColor);
        }
        this.showFill = true;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
        updateThumbPosition();
        updateFill();
    }

    public void setThumbOffset(float offset) {
        this.thumbOffset = offset;
    }

    public float getValue() {
        return currentValue;
    }

    public void setValue(float value) {
        this.currentValue = Math.max(minValue, Math.min(maxValue, value));
        updateThumbPosition();
        updateFill();
    }

    public float getNormalizedValue() {
        return (currentValue - minValue) / (maxValue - minValue);
    }

    private void updateThumbPosition() {
        if (thumb == null) return;

        float normalized = getNormalizedValue();
        float trackLength = (orientation == Orientation.HORIZONTAL) ? width : height;
        float thumbPos = normalized * trackLength - trackLength / 2;

        if (orientation == Orientation.HORIZONTAL) {
            thumb.getTransformation().translateTo(thumbPos, thumbOffset);
        } else {
            thumb.getTransformation().translateTo(thumbOffset, thumbPos);
        }
    }

    private void updateFill() {
        if (!showFill || fill == null) return;

        float normalized = getNormalizedValue();

        if (orientation == Orientation.HORIZONTAL) {
            if (fill instanceof Rectangle rectangle) {
                float newWidth = width * normalized;
                rectangle.setWidth(newWidth);
                float left = -width / 2f;
                float centerX = left + newWidth / 2f;
                rectangle.getTransformation().translateTo(centerX, rectangle.getTransformation().getTranslateY());
            } else if (fill instanceof RoundedRectangle roundedRectangle) {
                roundedRectangle.setWidth(width * normalized);
            }

        } else {
            if (fill instanceof Rectangle rectangle) {
                float newHeight = height * normalized;
                rectangle.setHeight(newHeight);
                float bottom = -height / 2f;
                float centerY = bottom + newHeight / 2f;
                rectangle.getTransformation().translateTo(rectangle.getTransformation().getTranslateX(), centerY);
            } else if (fill instanceof RoundedRectangle roundedRectangle) {
                roundedRectangle.setHeight(height * normalized);
            }
        }
    }

    @Override
    public void onDraw(GL2 gl) {
        if (!visible) return;

        if (track != null) {
            track.draw(gl);
        }

        if (showFill && fill != null) {
            fill.draw(gl);
        }

        if (thumb != null) {
            updateThumbPosition();
            thumb.draw(gl);
        }
    }

    @Override
    public boolean isInside(double mouseX, double mouseY) {
        float x = getTransformation().getTranslateX();
        float y = getTransformation().getTranslateY();

        return mouseX >= x - width / 2 && mouseX <= x + width / 2 &&
                mouseY >= y - height / 2 && mouseY <= y + height / 2;
    }

    public boolean isThumbInside(double mouseX, double mouseY) {
        float x = getTransformation().getTranslateX();
        float y = getTransformation().getTranslateY();

        if (thumb == null) return false;

        float localX = (float) (mouseX - x);
        float localY = (float) (mouseY - y);

        float thumbX = thumb.getTransformation().getTranslateX();
        float thumbY = thumb.getTransformation().getTranslateY();
        float thumbSize = Math.max(width, height) / 3f;

        return Math.abs(localX - thumbX) < thumbSize &&
                Math.abs(localY - thumbY) < thumbSize;
    }

    @Override
    public void onDragStart(float x, float y) {
        dragging = true;
        updateValueFromPosition(x, y);
    }

    @Override
    public void onDrag(float deltaX, float deltaY, float currentX, float currentY) {
        updateValueFromPosition(currentX, currentY);
    }

    @Override
    public void onDragEnd(float x, float y) {
        dragging = false;
        updateValueFromPosition(x, y);
    }

    @Override
    public boolean isDragging() {
        return dragging;
    }

    private void updateValueFromPosition(float screenX, float screenY) {
        float x = getTransformation().getTranslateX();
        float y = getTransformation().getTranslateY();

        float localX = screenX - x;
        float localY = screenY - y;

        float normalized;
        if (orientation == Orientation.HORIZONTAL) {
            normalized = (localX + width / 2f) / width;
        } else {
            normalized = (localY + height / 2f) / height;
        }

        normalized = Math.max(0f, Math.min(1f, normalized));
        currentValue = minValue + normalized * (maxValue - minValue);
        updateThumbPosition();
        updateFill();

        if (onValueChanged != null) {
            onValueChanged.accept(currentValue);
        }
    }

    public void setTrackColor(float[] color) {
        this.trackColor = color;
        if (track != null && track instanceof RoundedRectangle roundedRectangle) {
            roundedRectangle.setColor(color);
        }
    }

    public void setFillColor(float[] color) {
        this.fillColor = color;
        if (fill != null) {
            if (fill instanceof Rectangle rectangle) {
                rectangle.setColor(color);
            } else if (fill instanceof RoundedRectangle roundedRectangle) {
                roundedRectangle.setColor(color);
            }
        }
    }

    public RenderableObject getTrack() { return track; }
    public void setTrack(RenderableObject track) { this.track = track; }

    public RenderableObject getThumb() { return thumb; }
    public void setThumb(RenderableObject thumb) { this.thumb = thumb; }

    public RenderableObject getFill() { return fill; }
    public void setFill(RenderableObject fill) {
        this.fill = fill;
        this.showFill = (fill != null);
        updateFill();
    }

    public boolean isShowingFill() { return showFill; }
    public void setShowFill(boolean showFill) { this.showFill = showFill; }

    public enum Orientation {HORIZONTAL, VERTICAL}

    public static class Builder extends UIElement.Builder<Builder> {

        private float min = 0, max = 100, initial = 50;
        private Slider.Orientation orientation = Slider.Orientation.HORIZONTAL;
        private RenderableObject track;
        private RenderableObject thumb;
        private RenderableObject fill;
        private float thumbOffset = 0;
        private float[] trackColor = Colors.GRAY;
        private float[] fillColor = Colors.BLUE;
        private Consumer<Float> onValueChanged;

        public Builder at(float x, float y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public Builder size(float width, float height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public Builder onValueChanged(Consumer<Float> onValueChanged) {
            this.onValueChanged = onValueChanged;
            return this;
        }

        @Override
        protected Builder self() {
            return this;
        }

        public Builder range(float min, float max, float initial) {
            this.min = min;
            this.max = max;
            this.initial = initial;
            return this;
        }

        public Builder orientation(Slider.Orientation orientation) {
            this.orientation = orientation;
            return this;
        }

        public Builder track(RenderableObject track) {
            this.track = track;
            return this;
        }

        public Builder thumb(RenderableObject thumb) {
            this.thumb = thumb;
            return this;
        }

        public Builder fill(RenderableObject fill) {
            this.fill = fill;
            return this;
        }

        public Builder thumbOffset(float offset) {
            this.thumbOffset = offset;
            return this;
        }

        public Builder trackColor(float[] color) {
            this.trackColor = color;
            return this;
        }

        public Builder fillColor(float[] color) {
            this.fillColor = color;
            return this;
        }

        public Slider build() {
            Slider slider = new Slider(x, y, width, height, min, max, initial, orientation);

            if (track != null) slider.setTrack(track);
            if (thumb != null) slider.setThumb(thumb);
            if (fill != null) slider.setFill(fill);

            slider.setThumbOffset(thumbOffset);
            slider.setTrackColor(trackColor);
            slider.setFillColor(fillColor);

            slider.onValueChanged = onValueChanged;

            slider.updateThumbPosition();
            slider.updateFill();

            return slider;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}