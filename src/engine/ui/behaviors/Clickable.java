package engine.ui.behaviors;

import engine.ui.Events.UIEvent;

import java.util.function.Consumer;

public interface Clickable {
    void setOnClick(Consumer<UIEvent> onClick);
    Consumer<UIEvent> getOnClick();
    void onClick(UIEvent event);
}