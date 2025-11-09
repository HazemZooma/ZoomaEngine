package entities.fish;

import behaviors.MovementBehavior;


public class BotFish extends Fish {
    private MovementBehavior movementBehavior;

    public BotFish(Builder builder) {
        super(builder);
    }

    public void setMovementBehavior(MovementBehavior movementBehavior) {
        this.movementBehavior = movementBehavior;
    }

    public void move() {
        if (movementBehavior != null) movementBehavior.move(this);
    }


    public static class Builder extends Fish.Builder<Builder> {

        public Builder(float width, float height) {
            super(width, height);
        }

        @Override
        protected Builder self() {
            return this;
        }

        public BotFish build() {
            return new BotFish(this);
        }
    }
}
