package behaviors;

import entities.fish.Fish;

public class BounceMovement implements MovementBehavior {

    private float dx;
    private float dy;

    public BounceMovement(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void move(Fish fish) {
        float[] transform = fish.getTransformation().getTranslation();
        float x = transform[0];
        float y = transform[1];
//        System.out.println("x: " + x + " y: " + y);
        x += dx;
        y += dy;

        float halfWidth = fish.getWidth() / 2;
        float halfHeight = fish.getHeight() / 2;


        if (x + halfWidth >= SCREEN_RIGHT) {
            x = SCREEN_RIGHT - halfWidth;
            dx = -dx;
            if (!fish.isFacingRight()) fish.flip();
        } else if (x - halfWidth <= SCREEN_LEFT) {
            x = SCREEN_LEFT + halfWidth;
            dx = -dx;
            if (fish.isFacingRight()) fish.flip();
        }
        if (y + halfHeight >= SCREEN_TOP) {
            y = SCREEN_TOP - halfHeight;
            dy = -dy;
        } else if (y - halfHeight <= SCREEN_BOTTOM) {
            y = SCREEN_BOTTOM + halfHeight;
            dy = -dy;
        }
//        System.out.println("X : " + x + "Y : " + y);
        fish.getTransformation().translateTo(x, y);
    }

}
