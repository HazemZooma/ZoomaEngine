package engine.ui;

import engine.grahpics.RenderableObject;

public abstract class UIElement extends RenderableObject {

//    public UIElement(float x, float y, float width, float height, Runnable onClick) {
//        super(x , y);
//    }

    public boolean handleClick(float x, float y) {
        return false;
    }

}
