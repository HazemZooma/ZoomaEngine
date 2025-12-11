package engine.ui.behaviors;

import engine.ui.Events.UIEvent;

import java.util.function.Consumer;

public interface Hoverable {
    void setOnHoverEnter(Consumer<UIEvent> onHoverEnter);
    void setOnHoverExit(Consumer<UIEvent> onHoverExit);
    Consumer<UIEvent> getOnHoverEnter();
    Consumer<UIEvent> getOnHoverExit();
    void onHoverEnter(UIEvent event);
    void onHoverExit(UIEvent event);
}