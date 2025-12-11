package scene.game;

import command.SwitchSceneCommand;
import engine.graphics.Colors;
import engine.ui.core.Modal;
import engine.ui.factory.ModalFactory;
import scene.Scene;
import scene.SceneType;

public class GameOverScene extends Scene {

    private int finalScore;
    private int status;

    public GameOverScene(){

    }

    public GameOverScene(int finalScore, int status) {
        sceneType = SceneType.GAME_OVER;
        sceneBackgroundColor = Colors.LIGHT_BLUE;
        this.finalScore = finalScore;
        this.status = status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setFinalScore(int finalScore) {
        this.finalScore = finalScore;
    }

    private void createUI(int score) {


        String modalTitle = status == 0 ? "You Lose !" : "Congratulations!";

        Modal gameOverModal = ModalFactory.createConfirmationModal(modalTitle ,
                "Your Score is\n    " + score + " !",
                e -> SwitchSceneCommand.switchTo(SceneType.GAMEPLAY),
                e -> SwitchSceneCommand.switchTo(SceneType.MAIN_MENU),
                "Retry" , "Quit" , 250 , 150
                );

        gameOverModal.setAlignment(Modal.ModalTextAlignment.CENTER);

        uiManager.add(gameOverModal);
    }

    public void initialize() {
        super.initialize();
        createUI(finalScore);
    }


}
