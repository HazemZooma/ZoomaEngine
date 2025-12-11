package behaviors;

import engine.core.input.MouseInputData;
import objects.core.GameObject;


public record MoveToMouseBehavior(MouseInputData mouseData) implements MovementBehavior {

    @Override
    public void move(GameObject player) {
        if (mouseData == null) return;

        if (player != null) {
            player.getTransformation().translateTo((float) mouseData.getX(), (float) mouseData.getY());
        }
    }

}
