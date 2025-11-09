package engine.core.collisions;

import entities.fish.Fish;

public class CollisionManager {
    private final CollisionDetector detector = new CollisionDetector();

    public boolean canEat(Fish player, Fish bot) {
        return detector.intersects(player.getMouthBounds(), bot.getBoundingBox());
    }
}
