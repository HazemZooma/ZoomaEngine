package engine.ui.Events;

import engine.ui.core.UIElement;

public class UIEvent {
    private final UIElement target;
    private final EventType type;
    private final float mouseX;
    private final float mouseY;
    private boolean consumed = false;

    public enum EventType {
        CLICK,
        HOVER_ENTER,
        HOVER_EXIT,
        DRAG
    }

    public UIEvent(UIElement target, EventType type, float mouseX, float mouseY) {
        this.target = target;
        this.type = type;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    public UIElement getTarget() { return target; }
    public EventType getType() { return type; }
    public float getMouseX() { return mouseX; }
    public float getMouseY() { return mouseY; }
    public boolean isConsumed() { return consumed; }

    public void consume() { this.consumed = true; }

    public static UIEvent click(UIElement target, float mouseX, float mouseY) {
        return new UIEvent(target, EventType.CLICK, mouseX, mouseY);
    }

    public static UIEvent hoverEnter(UIElement target, float mouseX, float mouseY) {
        return new UIEvent(target, EventType.HOVER_ENTER, mouseX, mouseY);
    }

    public static UIEvent hoverExit(UIElement target, float mouseX, float mouseY) {
        return new UIEvent(target, EventType.HOVER_EXIT, mouseX, mouseY);
    }

    public static UIEvent drag(UIElement target, float mouseX, float mouseY) {
        return new UIEvent(target ,EventType.DRAG , mouseX ,mouseY );
    }
}