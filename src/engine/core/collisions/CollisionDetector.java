package engine.core.collisions;

public class CollisionDetector {
    public boolean intersects(Rect a, Rect b) {
        return !(a.right < b.left || a.left > b.right ||
                a.top < b.bottom || a.bottom > b.top);
    }

    public static class Rect {
        public final float left, right, top, bottom;

        public Rect(float left, float right, float top, float bottom) {
            this.left = left;
            this.right = right;
            this.top = top;
            this.bottom = bottom;
        }
    }
}

