package engine.ui;

import com.jogamp.opengl.GL2;
import engine.core.input.MouseInputData;
import engine.ui.Events.UIEvent;
import engine.ui.behaviors.Clickable;
import engine.ui.behaviors.Draggable;
import engine.ui.behaviors.Hoverable;
import engine.ui.core.Button;
import engine.ui.core.Modal;
import engine.ui.core.Slider;
import engine.ui.core.UIElement;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UIManager {

    private final List<UIElement> elements = new ArrayList<>();
    private Hoverable hoveredElement = null;
    private UIElement pressedElement = null;
    private Draggable pressedDraggable = null;

    public void add(UIElement... e) {
        elements.addAll(List.of(e));
    }

    public void add(List<UIElement> e) {
        elements.addAll(e);
    }

    public void remove(UIElement e) {
        elements.remove(e);
    }

    public void clear() {
        elements.clear();
    }

    public List<UIElement> getUIElements() {
        return Collections.unmodifiableList(elements);
    }

    public void update() {
    }

    public void handleInput(MouseInputData input) {
        double mx = input.getX();
        double my = input.getY();

        UIElement top = null;
        for (int i = elements.size() - 1; i >= 0; i--) {
            UIElement e = elements.get(i);
            if (!e.isVisible()) continue;

            if (e instanceof Modal modal) {
                if (modal.isInside(mx, my)) {
                    List<Button> buttons = modal.getChildren().stream().filter(c -> c instanceof Button)
                            .map(c -> (Button) c).toList();

                    for (Button button : buttons) {
                        if (button.isInside(mx, my)) {
                            top = button;
                            break;
                        }
                    }

                    if (top == null) {
                        top = modal;
                    }
                    break;
                }
            } else if (e instanceof Slider s && s.isInside(mx, my)) {
                top = e;
                break;
            }
            else if (e.isInside(mx, my)) {
                top = e;
                break;
            }
        }

        Hoverable newHover = (top instanceof Hoverable h) ? h : null;
        if (hoveredElement != newHover) {
            if (hoveredElement != null) {
                UIEvent exitEvent = UIEvent.hoverExit(
                        (UIElement) hoveredElement,
                        (float) mx,
                        (float) my
                );
                hoveredElement.onHoverExit(exitEvent);
            }

            if (newHover != null) {
                UIEvent enterEvent = UIEvent.hoverEnter(
                        top,
                        (float) mx,
                        (float) my
                );
                newHover.onHoverEnter(enterEvent);
            }
            hoveredElement = newHover;
        }

        if (input.isPressed() && input.getButton() == MouseEvent.BUTTON1) {
            pressedElement = top;
            if (pressedElement instanceof Draggable d) {
                pressedDraggable = d;
                    d.onDragStart((float) mx ,(float) my);
                }
            }

            if (input.isDragging()) {
                if (pressedDraggable != null) {

                    pressedDraggable.onDrag(
                            (float) input.getDx(),
                            (float) input.getDy(),
                            (float) mx,
                            (float) my
                    );
                }
            }

            if (input.isReleased()) {
                if (pressedDraggable != null) {
                    pressedDraggable.onDragEnd((float) mx, (float) my);
                    pressedDraggable = null;
                } else {
                    if (pressedElement != null && pressedElement == top && pressedElement instanceof Clickable c) {
                        UIEvent clickEvent = UIEvent.click(
                                pressedElement,
                                (float) mx,
                                (float) my
                        );
                        if (c instanceof Hoverable hoverable) {
                            UIEvent exitEvent = UIEvent.hoverExit(
                                    (UIElement) hoveredElement,
                                    (float) mx,
                                    (float) my
                            );
                            hoverable.onHoverExit(exitEvent);
                        }
                        c.onClick(clickEvent);
                    }
                }
                pressedElement = null;
            }
        }

    public void draw(GL2 gl) {
        for (UIElement e : elements) {
            if (e.isVisible()) {
                e.draw(gl);
            }
        }
    }
}