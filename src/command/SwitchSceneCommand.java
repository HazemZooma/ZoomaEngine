package command;

import engine.core.SceneManager;
import scene.Scene;
import scene.SceneType;

import java.util.function.Consumer;

public record SwitchSceneCommand(SceneType type, String transition , Consumer<Scene> configurator) implements Command {

    public SwitchSceneCommand(SceneType type , String transition) {
        this(type , transition , null);
    }

    public static void push(SceneType type) {
        new SwitchSceneCommand(type, "PUSH").execute();
    }

    public static void push(SceneType type , Consumer<Scene> configurator){new SwitchSceneCommand(type , "PUSH_WITH_CONFIGURATION" , configurator).execute(); }

    public static void switchTo(SceneType type) {
        new SwitchSceneCommand(type, "SWITCH").execute();
    }

    public static void pop() {
        new SwitchSceneCommand(null, "POP").execute();
    }

    @Override
    public void execute() {
        SceneManager sceneManager = SceneManager.getInstance();
        switch (transition) {
            case "PUSH" -> sceneManager.push(type);
            case "PUSH_WITH_CONFIGURATION" ->sceneManager.push(type , configurator);
            case "SWITCH" -> sceneManager.switchTo(type);
            case "POP" -> sceneManager.pop();
        }
    }
}
