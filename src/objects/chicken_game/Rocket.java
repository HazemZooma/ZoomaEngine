package objects.chicken_game;

import behaviors.MovementBehavior;
import behaviors.ShootingBehavior;
import objects.core.GameObject;
import objects.core.Player;

public class Rocket extends Player {

    private float speed = 5.0f;
    private float animTimer = 0;
    private int animIndex = 0;

    public Rocket(float x, float y , MovementBehavior movementBehavior , ShootingBehavior shootingBehavior) {
        super(x, y,movementBehavior, shootingBehavior ,new String[]{
                "entities/rocket1.png",
                "entities/rocket2.png",
                "entities/rocket3.png",
                "entities/rocket4.png"
        });
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void onCollision(GameObject other) {
        setDead(true);
    }
}
