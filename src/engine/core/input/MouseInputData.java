package engine.core.input;

public class MouseInputData {

    private double x;
    private double y;

    private double dx;
    private double dy;

    private int button = -1;
    private boolean pressed;
    private boolean released;
    private boolean dragging;

    public MouseInputData() {}

    public void updatePosition(double newX, double newY) {
        this.dx = newX - this.x;
        this.dy = newY - this.y;
        this.x = newX;
        this.y = newY;

        this.dragging = pressed;
    }

    public double getX() { return x; }
    public double getY() { return y; }

    public double getDx() { return dx; }
    public double getDy() { return dy; }

    public int getButton() { return button; }
    public void setButton(int button) { this.button = button; }

    public boolean isPressed() { return pressed; }
    public void setPressed(boolean pressed) {
        this.pressed = pressed;
        if (!pressed) dragging = false;
    }

    public boolean isReleased() { return released; }
    public void setReleased(boolean released) {
        this.released = released;
        if (released) dragging = false;
    }

    public boolean isDragging() { return dragging; }
}
