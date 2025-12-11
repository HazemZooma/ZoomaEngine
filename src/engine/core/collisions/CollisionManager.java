package engine.core.collisions;


import objects.core.GameObject;

import java.util.List;

public class CollisionManager {
    private final CollisionDetector detector = new CollisionDetector();
    private static final CollisionManager instance = new CollisionManager();

    public static CollisionManager getInstance(){
        return instance;
    }

    public void checkCollisions(List<GameObject> objects) {

//        List<GameObject> collidableObjects = objects.stream()
//                .filter(obj -> obj.isCollidable() && obj.isActive())
//                .toList();

        for (int i = 0; i < objects.size(); i++) {
            for (int j = i + 1; j < objects.size(); j++) {
                GameObject a = objects.get(i);
                GameObject b = objects.get(j);

                if (detector.collides(a, b)) {
                    handleCollision(a, b);
                }
            }
        }
    }

    private void handleCollision(GameObject a, GameObject b) {
        a.onCollision(b);
        b.onCollision(a);

//        for (CollisionListener listener : listeners) {
//            listener.onCollision(a, b);
//        }
//
//        resolveCollision(a, b);
    }


}
