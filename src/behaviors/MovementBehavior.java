package behaviors;

import App_constants.Constants;
import objects.core.GameObject;


public interface MovementBehavior {
    float SPEED = 2.0f;
    float SCREEN_LEFT = Constants.World.ORTHO_LEFT;
    float SCREEN_RIGHT = Constants.World.ORTHO_RIGHT;
    float SCREEN_TOP = Constants.World.ORTHO_TOP;
    float SCREEN_BOTTOM = Constants.World.ORTHO_BOTTOM;
    float SCREEN_WIDTH = Constants.World.ORTHO_WIDTH;
    float SCREEN_HEIGHT = Constants.World.ORTHO_HEIGHT;

    void move(GameObject obj);
}
