package behaviors;

import objects.core.GameObject;

public class BounceMovement implements MovementBehavior {

    private float dx;
    private float dy;

    public BounceMovement(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void move(GameObject player) {
        float[] transform = player.getTransformation().getTranslation();
        float x = transform[0];
        float y = transform[1];

        x += dx;
        y += dy;

        float halfWidth = player.getWidth() / 2;
        float halfHeight = player.getHeight() / 2;


        if (x + halfWidth >= SCREEN_RIGHT) {
            x = SCREEN_RIGHT - halfWidth;
            dx = -dx;
        } else if (x - halfWidth <= SCREEN_LEFT) {
            x = SCREEN_LEFT + halfWidth;
            dx = -dx;
        }
        if (y + halfHeight >= SCREEN_TOP) {
            y = SCREEN_TOP - halfHeight;
            dy = -dy;
        } else if (y - halfHeight <= SCREEN_BOTTOM) {
            y = SCREEN_BOTTOM + halfHeight;
            dy = -dy;
        }

        player.getTransformation().translateTo(x, y);
    }

}
