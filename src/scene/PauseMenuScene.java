package scene;

import com.jogamp.opengl.GL2;
import command.SwitchSceneCommand;
import engine.graphics.Colors;
import engine.graphics.RenderableObject;
import engine.text.Text;
import engine.ui.background.GeometricBackground;
import engine.ui.core.Button;
import engine.ui.factory.ButtonFactory;

import java.awt.event.KeyEvent;

public class PauseMenuScene extends Scene<RenderableObject> {
    private Text pauseTitle;

    public PauseMenuScene() {
        sceneType = SceneType.PAUSE_MENU;
    }


    private void initializeUI() {
        pauseTitle = new Text(-80, 115, "PAUSED", 35, new float[]{0.2f, 0.2f, 0.6f, 1f});
        add(pauseTitle);

        createButtonGrid();

        Text instructionText = new Text(-70, -140, "Press ESC to resume", 12, Colors.MIDNIGHT_BLUE);
        add(instructionText);
    }

    private void createButtonGrid() {
        float buttonSpacing = 60f;
        float startY = 70f;

        Button resumeButton = ButtonFactory.createPrimaryButton(0, startY, "RESUME",
                (e) ->SwitchSceneCommand.pop());

        ((GeometricBackground)resumeButton.getBackground()).setColor(Colors.LIGHT_GREEN);

        resumeButton.setOnHoverEnter((e) ->{
            ((GeometricBackground)resumeButton.getBackground()).setColor(Colors.FOREST_GREEN);
            resumeButton.getTransformation().setScale(1.2f , 1.2f);
            resumeButton.getText().setColor(Colors.BLUE);
        });

        resumeButton.setOnHoverExit((e) ->{
                    ((GeometricBackground) resumeButton.getBackground()).setColor(Colors.LIGHT_GREEN);
                    resumeButton.getTransformation().setScale(1 , 1);
                    resumeButton.getText().setColor(Colors.MIDNIGHT_BLUE);
                });



        Button restartButton = ButtonFactory.createPrimaryButton(0, startY - buttonSpacing, "RESTART",
                (e) -> SwitchSceneCommand.switchTo(SceneType.GAMEPLAY));
        add(restartButton);

        Button settingsButton = ButtonFactory.createPrimaryButton(-120, startY - buttonSpacing * 2,
                "Settings" , (e) -> SwitchSceneCommand.switchTo(SceneType.SETTINGS));


        Button menuButton = ButtonFactory.createPrimaryButton(120, startY - buttonSpacing * 2,
                "Menu" , (e) -> SwitchSceneCommand.switchTo(SceneType.MAIN_MENU));


        Button quitButton = ButtonFactory.createPrimaryButton(0, startY - buttonSpacing * 3, "QUIT",
                (e) -> System.exit(0));


        uiManager.add(resumeButton ,restartButton ,  settingsButton  ,  menuButton ,  quitButton);
    }





    @Override
    public void render(GL2 gl) {
        super.render(gl);
        animateElements();
    }

    private void animateElements() {
        long time = System.currentTimeMillis();
        float pulse = (float) Math.sin(time * 0.005) * 0.05f + 1f;

        if (pauseTitle != null) {
            pauseTitle.getTransformation().setScale(pulse, pulse);
        }
    }

    @Override
    public void initialize() {
        super.initialize();
        initializeUI();
        inputContext.bindKey(KeyEvent.VK_ESCAPE,  SwitchSceneCommand::pop);
        inputContext.bindKey(KeyEvent.VK_R, () -> System.out.println("hazem"));
        inputContext.bindKey(KeyEvent.VK_M, () -> SwitchSceneCommand.switchTo(SceneType.MAIN_MENU));
        inputContext.bindKey(KeyEvent.VK_S, () -> SwitchSceneCommand.switchTo(SceneType.SETTINGS));
        inputContext.bindKey(KeyEvent.VK_Q , () -> System.exit(0));
    }

}