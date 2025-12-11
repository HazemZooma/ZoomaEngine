package objects.core;

import behaviors.MovementBehavior;
import behaviors.ShootingBehavior;

public abstract class Player extends GameObject {

    private MovementBehavior movementBehavior;
    private ShootingBehavior shootingBehavior;

    public Player(float x, float y,
                  MovementBehavior movementBehavior,
                  ShootingBehavior shootingBehavior, String[] spriteNames) {
        super(x, y, spriteNames);

        this.movementBehavior = movementBehavior;
        this.shootingBehavior = shootingBehavior;
    }

    public Player(float x, float y, MovementBehavior movementBehavior, ShootingBehavior shootingBehavior, String pathName) {
        this(x, y, movementBehavior, shootingBehavior, new String[]{pathName});
    }

    public void setShootingBehavior(ShootingBehavior shootingBehavior) {
        this.shootingBehavior = shootingBehavior;
    }

    public void setMovementBehavior(MovementBehavior movementBehavior) {
        this.movementBehavior = movementBehavior;
    }

    @Override
    public void update() {
        if (movementBehavior != null){
            movementBehavior.move(this);
        }

        if (shootingBehavior != null)
            shootingBehavior.shoot(this);
    }
}
