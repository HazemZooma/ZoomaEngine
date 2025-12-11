package objects.core;

import behaviors.BotBehavior;

public abstract class Enemy extends GameObject {

    private BotBehavior ai;

    public Enemy(float x, float y, String[] textures, BotBehavior ai) {
        super(x, y, textures);
        this.ai = ai;
    }

    public void setAi(BotBehavior ai) {
        this.ai = ai;
    }

    @Override
    public void update() {
        if (ai != null) {
            ai.updateAI(this);
        }
    }

    @Override
    public abstract void onCollision(GameObject other);
}
