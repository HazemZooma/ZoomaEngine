package entities.fish;

import base_shapes.Circle;
import base_shapes.Triangle;
import com.jogamp.opengl.GL2;
import engine.core.collisions.CollisionDetector;
import engine.grahpics.Colors;
import entities.base.Entity;


public abstract class Fish extends Entity {

    private final float width, height;
    private final Circle body;
    private final Triangle tail;
    private final Circle eye;
    private final Circle pupil;
    private final Triangle topFin;
    private final Triangle mouth;
//    private Rectangle boundingBox;

    private boolean isFacingRight;
    private mouthState currentMouthState = mouthState.CLOSED;

    private long eatStartTime = 0;
    private long eatDuration = 500;
    private boolean isEating = false;


    protected Fish(Builder<?> builder) {
        super(builder.posX, builder.posY);
        this.width = builder.width;
        this.height = builder.height;
        body = Circle.createEllipse(builder.posX, builder.posY, builder.width, builder.height, builder.bodyColor);

        tail = Triangle.createIsosceles(
                (builder.posX - (builder.width / 2) - (builder.height)),
                builder.posY,
                (int) builder.width,
                (int) builder.height,
                builder.bodyColor);
        tail.getTransformation().rotateAroundSelf(-90);

        float eyeX = (builder.posX + builder.width / 4);
        float eyeY = (builder.posY);
        eye = Circle.createCircle(eyeX, eyeY, builder.height / 2, builder.eyeColor);
        pupil = Circle.createCircle(eyeX, eyeY, builder.height / 4, builder.pupilColor);

        topFin = Triangle.createIsosceles(builder.posX, (builder.posY + builder.height),
                (int) builder.width, (int) builder.height, builder.bodyColor);

        float mouthX = builder.posX + (builder.width) - builder.height / 2;
        float mouthY = builder.posY;
        mouth = Triangle.createIsosceles(mouthX, mouthY, (int) (builder.height), (int) (builder.height), Colors.LIGHT_BLUE);
        mouth.getTransformation().rotateAroundSelf(90);

    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    @Override
    protected void onDraw(GL2 gl) {
        body.draw(gl);
        tail.draw(gl);
        topFin.draw(gl);
        eye.draw(gl);
        pupil.draw(gl);

        updateEatingAnimation();
        if (currentMouthState == mouthState.OPEN) {
            mouth.draw(gl);
//            boundingBox.draw(gl);
        }
    }

    public void eat() {
        eatStartTime = System.currentTimeMillis();
        isEating = true;
        openMouth();
    }

    private void updateEatingAnimation() {
        if (isEating) {
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - eatStartTime;
            getTransformation().scaleBy(1.001f, 1.001f);

            if (elapsedTime >= eatDuration) {
                isEating = false;
                closeMouth();
            } else if (elapsedTime >= eatDuration / 2) {
                closeMouth();
            }
        }
    }

    public void eat(long duration) {
        if (!isEating) {
            isEating = true;
            eatStartTime = System.currentTimeMillis();
            eatDuration = duration;
            openMouth();
        }
    }


    private void openMouth() {
        if (currentMouthState == mouthState.CLOSED) {
            currentMouthState = mouthState.OPEN;
        }
    }

    private void closeMouth() {
        if (currentMouthState == mouthState.OPEN) {
            currentMouthState = mouthState.CLOSED;
        }
    }

    public void toggleMouth() {
        currentMouthState = currentMouthState == mouthState.OPEN ? mouthState.CLOSED : mouthState.OPEN;
    }

    public boolean isFacingRight() {
        return isFacingRight;
    }

    public void flip() {
        isFacingRight = !isFacingRight;
        getTransformation().scaleBy(-1, 1);
    }


    public CollisionDetector.Rect getMouthBounds() {
        float x = getTransformation().getTranslateX() + getWidth()/2;
        float y = getTransformation().getTranslateY();

        float mouthHeight = height;

        float left;
        float right;
        float top = y + mouthHeight / 2f;
        float bottom = y - mouthHeight / 2f;

        if (!isFacingRight) {
            left = x + width / 2f;
            right = x + width / 2f;
        } else {
            left = x - width / 2f;
            right = x - width / 2f;
        }

//        this.boundingBox = new Rectangle(x , y , (int) width , (int) height , Colors.BLACK);
        return new CollisionDetector.Rect(left, right, top, bottom);
    }


    public CollisionDetector.Rect getBoundingBox() {
        float x = getTransformation().getTranslateX();
        float y = getTransformation().getTranslateY();

        float tailExtension = tail.getHeight();

        float totalWidth = (width) * 2 + tailExtension;
        float totalHeight = height * 2;

        float left = x - totalWidth / 2f - tailExtension;
        float right = x + totalWidth / 2f;
        float top = y + totalHeight / 2f;
        float bottom = y - totalHeight / 2f;

        return new CollisionDetector.Rect(left, right, top, bottom);
    }

    public enum mouthState {
        OPEN, CLOSED
    }

    public abstract static class Builder<T extends Builder<T>> {
        private final float width, height;
        private float posX = 0, posY = 0;
        private float[] bodyColor = Colors.BLUE;
        private float[] eyeColor = Colors.WHITE;
        private float[] pupilColor = Colors.BLUE;

        public Builder(float width, float height) {
            this.width = width;
            this.height = height;
        }

        public T at(float posX, float posY) {
            this.posX = posX;
            this.posY = posY;
            return self();
        }

        public T bodyColor(float[] color) {
            this.bodyColor = color;
            return self();
        }

        public T eyeColor(float[] color) {
            this.eyeColor = color;
            return self();
        }

        public T pupilColor(float[] color) {
            this.pupilColor = color;
            return self();
        }

        protected abstract T self();
    }
}