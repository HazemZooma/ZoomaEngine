package scene;

import command.SwitchSceneCommand;
import engine.graphics.Colors;
import engine.graphics.RenderableObject;
import engine.ui.background.GeometricBackground;
import engine.ui.core.Button;
import engine.ui.core.Modal;
import engine.ui.factory.ButtonFactory;
import engine.ui.factory.ModalFactory;

import java.awt.event.KeyEvent;

public class InstructionScene extends Scene<RenderableObject> {

    public InstructionScene() {
        sceneType = SceneType.PAUSE_MENU;
        sceneBackgroundColor = Colors.WHITE;
    }

    private void createUI() {

       Button backButton = ButtonFactory.createBackButtonWithArrow(-220, 120, 15, Colors.BLUE,
                e -> SwitchSceneCommand.pop());

        Modal instructionModal = ModalFactory.builder()
                .title("Instructions")
                .message(
                        " الله إلا إله لا \n" +
                                "محمد على الله صلى")
                .messageColor(Colors.BLUE)
                .hasHeader(false)
                .hasCloseButton(false)
                .messageSize(30)
                .size(300, 200)
                .build();

        Modal bottomModal = ModalFactory.builder()
                .at(0,-100)
                .message("حازم☝")
                .backgroundOpacity(0)
                .childrenOpacity(0)
                .hasCloseButton(false)
                .hasHeader(false)
                .messageSize(20)
                .size(100 , 100)
                .build();






        instructionModal.setAlignment(Modal.ModalTextAlignment.CENTER);


        uiManager.add(backButton, instructionModal , bottomModal);

        ((GeometricBackground) instructionModal.getBackground()).setColor(Colors.WHITE);
    }

    @Override
    public void onResume(){

        uiManager.getUIElements().forEach(e ->{
            if(e instanceof Modal modal){
                System.out.println(modal);
                modal.show(); // اللي بعمله هنا ده عبط محدش يعمل زي كدا , مكسل أعدل الكلام العربي بس
            }
        });
    }


    public void initialize() {
        super.initialize();
        createUI();
        inputContext.bindKey(KeyEvent.VK_ESCAPE,  SwitchSceneCommand::pop);
    }
}
