package engine.ui.core;

import com.jogamp.opengl.GL2;
import engine.graphics.Colors;
import engine.graphics.RenderableObject;
import engine.ui.background.GeometricBackground;
import engine.ui.background.UIBackground;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class UIElement extends RenderableObject {

    protected float width, height;
    protected float minWidth = 20f;
    protected float minHeight = 20f;
    protected float paddingHorizontal = 10f;
    protected float paddingVertical = 10f;
    protected boolean visible = true;
    protected boolean enabled = true;
    protected boolean hovered = false;
    protected UIBackground background;
    protected float backgroundOpacity = 1f;
    protected float childrenOpacity = 1f;
    List<RenderableObject> children = new ArrayList<>();

    protected UIElement(Builder<?> builder) {
        super(builder.x, builder.y);
        setWidth(builder.width);
        setHeight(builder.height);
        setMinWidth(builder.minWidth);
        setMinHeight(builder.minHeight);
        setPadding(builder.paddingHorizontal, builder.paddingVertical);
        this.background = builder.background;
        setBackgroundOpacity(builder.backgroundOpacity);
        setChildrenOpacity(builder.childrenOpacity);

    }

    public UIElement(float x, float y, float width, float height) {
        super(x, y);
        setWidth(width);
        setHeight(height);
    }

    public float getChildrenOpacity() {
        return childrenOpacity;
    }

    public void setChildrenOpacity(float childrenOpacity) {
        if(childrenOpacity < 0) this.childrenOpacity = 0f;
        else if (childrenOpacity > 1) this.childrenOpacity = 1f;
        else this.childrenOpacity = childrenOpacity;

//        else {
           children.stream().filter(c -> c instanceof UIElement)
                    .map(c -> ((UIElement) c))
                    .forEach(c -> {
                        c.setBackgroundOpacity(childrenOpacity);
                        c.setChildrenOpacity(childrenOpacity);
                    });
//        }


    }

    public float getBackgroundOpacity() {
        return backgroundOpacity;
    }

    public void setBackgroundOpacity(float backgroundOpacity) {
        if(backgroundOpacity < 0) this.backgroundOpacity = 0f;
        else if (backgroundOpacity > 1) this.backgroundOpacity = 1f;
        else this.backgroundOpacity = backgroundOpacity;
    }

    public void addChildren(RenderableObject... children) {
        Arrays.stream(children).forEach(child -> {
            child.setParent(this);
            if(child instanceof UIElement uiElement)uiElement.setBackgroundOpacity(childrenOpacity);
            this.children.add(child);
        });

        parentToChildSize();
//        childToParent();
    }

    public void addChildren(List<? extends RenderableObject> children) {
        children.forEach(child -> {
            child.setParent(this);
            if(child instanceof UIElement uiElement)uiElement.setBackgroundOpacity(childrenOpacity);
            this.children.add(child);
        });

        parentToChildSize();
//        childToParent();
    }

    public boolean isHovered() {
        return hovered;
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

    public abstract boolean isInside(double mouseX, double mouseY);

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        if (width < 0) {
            System.out.println("Width can't be negative");
            return;
        }

        this.width = Math.max(minWidth, width);
        this.paddingHorizontal = Math.min(this.width / 3, this.paddingHorizontal);
        parentToChildSize();
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        if (height < 0) {
            System.out.println("Height can't be negative");
            return;
        }
        this.height = Math.max(minHeight, height);
        this.paddingVertical = Math.min(this.height / 3, this.paddingVertical);
        parentToChildSize();
    }

    private void parentToChildSize() {
        float maxChildWidth = 0;
        float maxChildHeight = 0;
        for (RenderableObject child : children) {
            maxChildWidth = Math.max(child.getWidth(), maxChildWidth);
            maxChildHeight = Math.max(child.getHeight(), maxChildHeight);
        }

        if (maxChildWidth > getWidth()) {
            setWidth(maxChildWidth);
        }

        if (maxChildHeight > getHeight()) {
            setHeight(maxChildHeight);
        }


        setMinWidth(Math.max(maxChildWidth, minWidth));
        setMinHeight(Math.max(maxChildHeight, minHeight));

    }

//    private void childToParent() {
//        for (RenderableObject child : children) {
//            Transformation transformation = child.getTransformation();
//            float childLeft = transformation.getTranslateX() - child.getWidth()/2;
//            float childRight = transformation.getTranslateX() + child.getWidth()/2;
//            float childTop = transformation.getTranslateY() + child.getHeight()/2;
//            float childBottom = transformation.getTranslateY() - child.getHeight()/2;
//
//            RenderableObject parent = child.getParent();
//            if(parent == null || child.getClass().equals(Modal.ModalHeader.class)) continue;
//
//            float parentLeft = parent.getTransformation().getTranslateX() - parent.getWidth()/2 + paddingHorizontal;
//            float parentRight = parent.getTransformation().getTranslateX() + parent.getWidth()/2 - paddingHorizontal;
//            float parentTop = parent.getTransformation().getTranslateY() + parent.getHeight()/2 - paddingVertical;
//            float parentBottom = parent.getTransformation().getTranslateY() - parent.getHeight()/2 + paddingVertical;
//
//            if(childRight > parentRight) {
//                float adjustment = parentRight - child.getWidth()/2;
//                child.getTransformation().translateTo(adjustment, child.getTransformation().getTranslateY());
//                childRight = adjustment + child.getWidth()/2;
//                childLeft = adjustment - child.getWidth()/2;
//            }
//
//            if(childLeft < parentLeft) {
//                float adjustment = parentLeft + child.getWidth()/2;
//                child.getTransformation().translateTo(adjustment, child.getTransformation().getTranslateY());
//                childRight = adjustment + child.getWidth()/2;
//                childLeft = adjustment - child.getWidth()/2;
//            }
//
//            if(childTop > parentTop) {
//                float adjustment = parentTop - child.getHeight()/2;
//                child.getTransformation().translateTo(child.getTransformation().getTranslateX(), adjustment);
//                childTop = adjustment + child.getHeight()/2;
//                childBottom = adjustment - child.getHeight()/2;
//            }
//
//            if(childBottom < parentBottom) {
//                float adjustment = parentBottom + child.getHeight()/2;
//                child.getTransformation().translateTo(child.getTransformation().getTranslateX(), adjustment);
//                childTop = adjustment + child.getHeight()/2;
//                childBottom = adjustment - child.getHeight()/2;
//            }
//        }
//    }

    public List<RenderableObject> getChildren() {
        return children;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public float getMinWidth() {
        return minWidth;
    }

    public void setMinWidth(float minWidth) {
        this.minWidth = Math.max(0f, minWidth);
        if (this.width < this.minWidth) {
            this.width = this.minWidth;
        }
    }

    public float getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(float minHeight) {
        this.minHeight = Math.max(0f, minHeight);
        if (this.height < this.minHeight) {
            this.height = this.minHeight;
        }
    }

    public float getPaddingHorizontal() {
        return paddingHorizontal;
    }

    public void setPaddingHorizontal(float paddingHorizontal) {
        this.paddingHorizontal = Math.max(0f, paddingHorizontal);
    }

    public float getPaddingVertical() {
        return paddingVertical;
    }

    public void setPaddingVertical(float paddingVertical) {
        this.paddingVertical = Math.max(0f, paddingVertical);
    }

    public void setPadding(float horizontal, float vertical) {
        setPaddingHorizontal(horizontal);
        setPaddingVertical(vertical);
    }

    public void setPadding(float padding) {
        setPadding(padding, padding);
    }

    public void show() {
        setVisible(true);
    }

    public void hide() {
        setVisible(false);
    }

    @Override
    public void onDraw(GL2 gl) {
        if (!visible || background == null) return;

        background.draw(gl, getWidth(), getHeight() , getBackgroundOpacity());

//        System.out.println(getBackgroundOpacity());


        for (RenderableObject child : children) {
            child.draw(gl);
        }
    }

    public UIBackground getBackground() {
        return background;
    }

    public void setBackground(UIBackground background) {
        if (background == null)
            throw new NullPointerException("background can't be null");
        this.background = background;
    }

    public abstract static class Builder<T extends Builder<T>> {
        protected float x;
        protected float y;
        protected float width;
        protected float height;
        protected float minWidth = 20f;
        protected float minHeight = 20f;
        protected float paddingHorizontal = 0;
        protected float paddingVertical = 0;
        protected float backgroundOpacity = 1f;
        protected float childrenOpacity = 1f;
        protected UIBackground background = new GeometricBackground.Builder().color(Colors.BLUE).build();

        public T at(float x, float y) {
            this.x = x;
            this.y = y;
            return self();
        }

        public T size(float width, float height) {
            this.width = width;
            this.height = height;
            return self();
        }

        public T MinSize(float minWidth, float minHeight) {
            this.minWidth = minWidth;
            this.minHeight = minHeight;
            return self();
        }

        public T padding(float horizontal, float vertical) {
            this.paddingHorizontal = horizontal;
            this.paddingVertical = vertical;
            return self();
        }

        public T padding(float padding) {
            return padding(padding, padding);
        }

        public T background(UIBackground background) {
            this.background = background;
            return self();
        }

        public T backgroundOpacity(float opacity) {
            this.backgroundOpacity = opacity;
            return self();
        }

        public T childrenOpacity(float opacity) {
            this.childrenOpacity = opacity;
            return self();
        }

        protected abstract T self();
    }
}