package engine.core;

import App_constants.Constants;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.util.awt.TextRenderer;
import engine.core.input.InputHandler;
import engine.graphics.Colors;
import engine.shapes.ShapeType;
import engine.texture.Sprite;
import engine.ui.core.Button;
import engine.ui.factory.ButtonFactory;
import scene.*;
import scene.game.GameScene;
import scene.settings.SettingScene;
import scene.settings.SettingsPlayerController;

import java.awt.*;

public class GameController implements GLEventListener {
    private final SceneManager sceneManager;
    private final InputHandler inputHandler;
    private SceneType currentSceneType;
    Sprite hazem;
    Button button;
    TextRenderer textRenderer;

    public GameController(SceneManager sceneManager, InputHandler inputHandler) {
        this.sceneManager = sceneManager;
        this.inputHandler = inputHandler;
        this.currentSceneType = SceneType.MAIN_MENU;
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(Constants.World.ORTHO_LEFT, Constants.World.ORTHO_RIGHT, Constants.World.ORTHO_BOTTOM, Constants.World.ORTHO_TOP, -1, 1);
        gl.glClearColor(Colors.LIGHT_BLUE[0], Colors.LIGHT_BLUE[1], Colors.LIGHT_BLUE[2], 1.0f);

        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glEnable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

        sceneManager.register(SceneType.MAIN_MENU, new SceneDescriptor(MainMenuScene::new, true));
        sceneManager.register(SceneType.GAMEPLAY, new SceneDescriptor(GameScene::new, false));
        sceneManager.register(SceneType.PAUSE_MENU, new SceneDescriptor(PauseMenuScene::new, true));
        sceneManager.register(SceneType.SETTINGS, new SceneDescriptor(SettingScene::new, true));
        sceneManager.register(SceneType.INSTRUCTIONS, new SceneDescriptor(InstructionScene::new, true));
        sceneManager.register(SceneType.SETTINGS_PLAYER_CONTROLS, new SceneDescriptor(() -> new SettingsPlayerController(1) , true));
        sceneManager.push(SceneType.MAIN_MENU);

        hazem = new Sprite(0,0 , "resources/assets/textures/entities/rocket1.png" , 60 , 60);
        button = ButtonFactory.builder()
                        .geometricBackground(ShapeType.RECTANGLE , Colors.WHITE).
                build();
        textRenderer =new TextRenderer(new Font("SansSerif" , Font.BOLD ,  12));
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();

//        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

//        hazem.draw(gl);
//
//        textRenderer.beginRendering(800, 600);
//        textRenderer.setColor(1f, 1f, 1f, 1f);
//
//        textRenderer.draw("hazem", 0,0);
//
//        textRenderer.endRendering();

        update(gl);
//        button.draw(gl);
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
        EngineContext.get().updateViewport(width, height);

//        glAutoDrawable.getGL().getGL2().glViewport(0, 0, width, height);
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }


    public void update(GL2 gl) {
        currentSceneType = sceneManager.getCurrentSceneType();
        sceneManager.getCurrentScene().render(gl);
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