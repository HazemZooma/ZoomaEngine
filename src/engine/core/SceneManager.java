package engine.core;

import scene.Scene;
import scene.SceneType;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class SceneManager {

    private static final SceneManager INSTANCE = new SceneManager();
    private final Map<SceneType, SceneDescriptor> descriptors = new HashMap<>();
    private final Deque<Scene> sceneStack = new ArrayDeque<>();

    public static SceneManager getInstance() {
        return INSTANCE;
    }

    public void register(SceneType type, SceneDescriptor descriptor) {
        descriptors.put(type, descriptor);
    }

    private Scene getScene(SceneType type) {
        SceneDescriptor descriptor = descriptors.get(type);
        if (descriptor == null)
            throw new IllegalArgumentException("SceneType not registered: " + type);
        return descriptor.getScene();
    }

    public void switchTo(SceneType type) {
        if (!sceneStack.isEmpty()) {
            sceneStack.peek().onExit();
        }
        Scene scene = clearOccurrences(type);
        sceneStack.push(scene);
    }

    public void push(SceneType type) {
        if (!sceneStack.isEmpty()) {
            sceneStack.peek().onExit();
        }
        Scene scene = clearOccurrences(type);
        sceneStack.push(scene);

        assert sceneStack.peek() != null;
        sceneStack.peek().onResume();

    }

    public void push(SceneType type, Consumer<Scene> configurator) {
        if (!sceneStack.isEmpty()) sceneStack.peek().onExit();

        Scene scene = getScene(type);
        configurator.accept(scene);
        sceneStack.push(scene);

        assert sceneStack.peek() != null;
        sceneStack.peek().onResume();

    }


    public void pop() {
        if (!sceneStack.isEmpty()) {
            sceneStack.peek().onExit();
            sceneStack.pop();
        } else {
            Scene scene = getScene(SceneType.MAIN_MENU);
            sceneStack.push(scene);
        }
    }

    private Scene clearOccurrences(SceneType type) {
        Scene scene = getScene(type);
        if (sceneStack.contains(scene)) {
            while (!sceneStack.isEmpty() && sceneStack.peek() != scene) {
                sceneStack.peek().onExit();
                sceneStack.pop();
            }
            if (!sceneStack.isEmpty())
                sceneStack.pop();
        }
        return scene;
    }

    public Scene getCurrentScene() {
        return sceneStack.peek();
    }

    public SceneType getCurrentSceneType() {
        if (sceneStack.peek() != null) {
            return sceneStack.peek().getSceneType();
        }
        return null;
    }


    public void clearCache(SceneType type) {
        SceneDescriptor desc = descriptors.get(type);
        if (desc != null) desc.clearCache();
    }


    public void clearAllCaches() {
        descriptors.values().forEach(SceneDescriptor::clearCache);
    }
}
