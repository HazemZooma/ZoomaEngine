package engine.core.collisions;

import objects.core.GameObject;

class CollisionDetector {

    public boolean collides(GameObject a, GameObject b) {
        float ax = a.getTransformation().getTranslateX();
        float ay = a.getTransformation().getTranslateY();

        float bx = b.getTransformation().getTranslateX();
        float by = b.getTransformation().getTranslateY();


        float aWidth = a.getWidth() * a.getTransformation().getScaleX();
        float aHeight = a.getHeight() * a.getTransformation().getScaleY();

        float bWidth = b.getWidth() * b.getTransformation().getScaleX();
        float bHeight = b.getHeight() * b.getTransformation().getScaleY();

        float aHalfW = aWidth / 2f;
        float aHalfH = aHeight / 2f;
        float bHalfW = bWidth / 2f;
        float bHalfH = bHeight / 2f;

        return Math.abs(ax - bx) < (aHalfW + bHalfW) &&
                Math.abs(ay - by) < (aHalfH + bHalfH);
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

