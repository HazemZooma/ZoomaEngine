package behaviors;

import App_constants.Constants;
import entities.fish.Fish;

public interface MovementBehavior {
    float SPEED = 2.0f;
    float SCREEN_LEFT = Constants.World.MIN_X;
    float SCREEN_RIGHT = Constants.World.MAX_X;
    float SCREEN_TOP = Constants.World.MAX_Y;
    float SCREEN_BOTTOM = Constants.World.MIN_Y;
    float SCREEN_WIDTH = Constants.World.WIDTH;
    float SCREEN_HEIGHT = Constants.World.HEIGHT;

    void move(Fish fish);
}
