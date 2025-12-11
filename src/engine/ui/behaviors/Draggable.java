package engine.ui.behaviors;

public interface Draggable {

    void onDragStart(float x, float y);
    void onDrag(float deltaX, float deltaY, float currentX, float currentY);
    void onDragEnd(float x, float y);
    boolean isDragging();
}
