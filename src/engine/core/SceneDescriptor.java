package engine.core;



import scene.Scene;

import java.util.function.Supplier;

public class SceneDescriptor {
    private final Supplier<Scene> sceneFactory;
    private Scene cachedInstance;
    private final boolean isCashed;

    public SceneDescriptor(Supplier<Scene> sceneFactory, boolean isCashed) {
        this.sceneFactory = sceneFactory;
        this.isCashed = isCashed;
    }

    public SceneDescriptor(Supplier<Scene> sceneFactory) {
        this(sceneFactory, true);
    }

    public Scene getScene() {
        if (isCashed) {
            if (cachedInstance == null) {
                cachedInstance = sceneFactory.get();
            }
            return cachedInstance;
        } else {
            return sceneFactory.get();
        }
    }

    public void clearCache() {
        cachedInstance = null;
    }

    public boolean isCached() {
        return isCashed;
    }
}