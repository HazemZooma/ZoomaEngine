package engine.ui;

import java.util.ArrayList;
import java.util.List;

public class UIManager {
    private final List<UIElement> elements = new ArrayList<>();

    public void add(UIElement e) {
        elements.add(e);
    }

    public void handleClick(double glX, double glY) {
        for (UIElement e : elements) {
            if (e instanceof Button button && button.isInside(glX, glY)) {
                button.click();
                break;
            }
        }
    }
}
