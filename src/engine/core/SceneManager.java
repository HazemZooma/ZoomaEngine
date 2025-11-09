package engine.core;



import scene.Scene;
import scene.SceneType;
import java.util.HashMap;
import java.util.Map;

public class SceneManager {
    private static SceneManager INSTANCE;
    private final Map<SceneType, Scene> scenes = new HashMap<>();
    private Scene currentScene;
    private SceneType currentSceneType;

    private SceneManager() {
    }

    public static SceneManager getInstance() {
        if(INSTANCE == null) INSTANCE = new SceneManager();
        return INSTANCE;
    }

    public void addScene(SceneType type, Scene scene) {
        scenes.put(type, scene);
    }

    public void switchScene(SceneType newSceneType) {
        if (currentScene != null) {
            currentScene.onExit();
        }

        currentScene = scenes.get(newSceneType);
        currentSceneType = newSceneType;

        if (currentScene != null) {
            currentScene.onEnter();
        }
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public SceneType getCurrentSceneType() {
        return currentSceneType;
    }
}
