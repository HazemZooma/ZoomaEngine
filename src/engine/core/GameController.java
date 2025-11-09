package engine.core;

import App_constants.Constants;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.FPSAnimator;
import engine.core.collisions.CollisionManager;
import engine.core.input.InputHandler;
import engine.grahpics.Colors;
import entities.base.Updatable;
import entities.factory.FishFactory;
import entities.fish.PlayerFish;
import scene.GamePlayScene;
import scene.PauseMenuScene;
import scene.SceneType;

import java.awt.event.KeyEvent;

public class GameController implements Updatable, GLEventListener {
    private final SceneManager sceneManager;
    private final InputHandler inputHandler;
    private final FPSAnimator animator;
    private SceneType currentSceneType;

    public GameController(SceneManager sceneManager, InputHandler inputHandler , FPSAnimator animator) {
        this.sceneManager = sceneManager;
        this.inputHandler = inputHandler;
        this.currentSceneType = SceneType.MAIN_MENU;
        this.animator = animator;
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
        gl.glClearColor(Colors.LIGHT_BLUE[0], Colors.LIGHT_BLUE[1], Colors.LIGHT_BLUE[2], 1.0f);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(Constants.World.MIN_X, Constants.World.MAX_X, Constants.World.MIN_Y, Constants.World.MAX_Y, -1, 1);

        PlayerFish player = new PlayerFish.Builder(20, 10).at(0, 0).build();
        GamePlayScene scene = new GamePlayScene(player , new CollisionManager(), new FishFactory() , 10);

        sceneManager.addScene(SceneType.GAMEPLAY , scene);
        sceneManager.addScene(SceneType.GAME_OVER , scene);
        sceneManager.addScene(SceneType.PAUSE_MENU , new PauseMenuScene());
        sceneManager.switchScene(SceneType.GAMEPLAY);

        inputHandler.bindGlobalKey(KeyEvent.VK_ESCAPE , this::handleEscapeKey);
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        update(gl);
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
        inputHandler.setCanvasSize(width , height);
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void update(GL2 gl) {
        currentSceneType = sceneManager.getCurrentSceneType();
        if (currentSceneType != SceneType.GAME_OVER) {
            sceneManager.getCurrentScene().render(gl);
        }
    }


    private void handleEscapeKey() {
        switch (currentSceneType) {
            case GAMEPLAY:
                switchToScene(SceneType.PAUSE_MENU);
                break;
            case PAUSE_MENU:
                switchToScene(SceneType.GAMEPLAY);
                break;
            case SETTINGS:
            case INSTRUCTIONS:
                switchToScene(SceneType.MAIN_MENU);
                break;
        }
        System.out.println(currentSceneType);
    }

    public void switchToScene(SceneType newSceneType) {
        sceneManager.switchScene(newSceneType);
        currentSceneType = newSceneType;
    }

    public boolean isGamePlaying() {
        return currentSceneType == SceneType.GAMEPLAY;
    }

    public boolean isGamePaused() {
        return currentSceneType == SceneType.PAUSE_MENU;
    }

    public boolean isInMenu() {
        return currentSceneType == SceneType.MAIN_MENU ||
                currentSceneType == SceneType.PAUSE_MENU ||
                currentSceneType == SceneType.SETTINGS ||
                currentSceneType == SceneType.INSTRUCTIONS;
    }

    public SceneType getCurrentSceneType() {
        return currentSceneType;
    }
}