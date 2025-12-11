package engine.ui.core;

import com.jogamp.opengl.GL2;
import engine.graphics.Colors;
import engine.sound.SoundManager;
import engine.text.Text;
import engine.ui.Events.UIEvent;
import engine.ui.behaviors.Clickable;
import engine.ui.behaviors.Hoverable;

import java.util.function.Consumer;

public class Button extends UIElement implements Clickable, Hoverable {
    private final Text text;
    private Consumer<UIEvent> onClick;
    private Consumer<UIEvent> onHoverEnter;
    private Consumer<UIEvent> onHoverExit;

    private Button(Builder<?> builder) {
        super(builder);
        this.onClick = builder.onClick;
        this.onHoverEnter = builder.onHoverEnter;
        this.onHoverExit = builder.onHoverExit;

        if(builder.textContent != null && !builder.textContent.trim().isEmpty()){
            text = new Text(getTransformation().getTranslateX(),getTransformation().getTranslateY(), builder.textContent , builder.fontSize , builder.textColor);
            float textWidth = text.getWidth() ;
            float textHeight = text.getHeight();
            float textX = getTransformation().getTranslateX() -  textWidth/2;
            float textY = getTransformation().getTranslateY() -  textHeight/2;
            text.getTransformation().translateTo(textX, textY);
            addChildren(text);
        }
        else{
            text = null;
        }
    }


    @Override
    public boolean isInside(double mouseX, double mouseY) {
        float x = getTransformation().getTranslateX();
        float y = getTransformation().getTranslateY();

        if (background != null) {
            return background.isInside(x, y, getWidth(), getHeight(), mouseX, mouseY);
        }
        return false;
    }

    public Text getText() {
        return text;
    }

    @Override
    public void onHoverEnter(UIEvent event) {
        if (onHoverEnter != null && isEnabled() && isVisible()) {
            hovered = true;
            SoundManager.play("resources/assets/audio/button.wav");
            onHoverEnter.accept(event);
        }
    }

    @Override
    public void onHoverExit(UIEvent event) {
        if (onHoverExit != null && isEnabled() && hovered && isVisible()) {
            hovered = false;
            onHoverExit.accept(event);
        }
    }

    @Override
    public void onClick(UIEvent event) {
        if (onClick != null && isEnabled() && isVisible()) {
            onClick.accept(event);
        }
    }

    @Override
    public Consumer<UIEvent> getOnHoverExit() {
        return onHoverExit;
    }

    @Override
    public void setOnHoverExit(Consumer<UIEvent> event) {
        this.onHoverExit = event;
    }

    @Override
    public Consumer<UIEvent> getOnHoverEnter() {
        return onHoverEnter;
    }

    @Override
    public void setOnHoverEnter(Consumer<UIEvent> onHoverEnter) {
        this.onHoverEnter = onHoverEnter;
    }

    @Override
    public Consumer<UIEvent> getOnClick() {
        return onClick;
    }

    @Override
    public void setOnClick(Consumer<UIEvent> onClick) {
        this.onClick = onClick;
    }

    public static class Builder<T extends Builder<T>> extends UIElement.Builder<T> {
        private Consumer<UIEvent> onClick;
        private Consumer<UIEvent> onHoverEnter;
        private Consumer<UIEvent> onHoverExit;
        private String textContent = "Hazem";
        private float[] textColor = Colors.WHITE;
        private int fontSize = 10;
        private String textFont;

        @Override
        protected T self() {
            return (T) this;
        }

        public T onClick(Consumer<UIEvent> onClick) {
            this.onClick = onClick;
            return self();
        }

        public T onHoverEnter(Consumer<UIEvent> onHoverEnter) {
            this.onHoverEnter = onHoverEnter;
            return self();
        }

        public T onHoverExit(Consumer<UIEvent> onHoverExit) {
            this.onHoverExit = onHoverExit;
            return self();
        }

        public T textColor(float[] textColor) {
            this.textColor = textColor;
            return self();
        }

        public T textSize(int textSize) {
            this.fontSize = textSize;
            return self();
        }

        public T textFont(String textFont) {
            this.textFont = textFont;
            return self();
        }

        public T textContent(String content) {
            this.textContent = content;
            return self();
        }

        public Button build() {
            return new Button(this);
        }
    }
}