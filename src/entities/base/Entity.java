package entities.base;

import engine.core.collisions.CollisionDetector;
import engine.grahpics.RenderableObject;

public abstract class Entity extends RenderableObject {
    private boolean isDead = false;

    protected Entity() {}

    protected Entity(float posX, float posY) {
        super(posX, posY);
    }



    public boolean isDead() {
        return isDead;
    }

    public void kill() {
        isDead = true;
    }

    public abstract CollisionDetector.Rect getBoundingBox();

}
