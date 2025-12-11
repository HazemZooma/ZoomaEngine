package scene.settings;

import com.jogamp.opengl.GL2;
import command.SwitchSceneCommand;
import engine.graphics.Colors;
import engine.graphics.RenderableObject;
import engine.text.Text;
import engine.ui.background.GeometricBackground;
import engine.ui.core.Button;
import engine.ui.core.Modal;
import engine.ui.factory.ButtonFactory;
import engine.ui.factory.ModalFactory;
import scene.Scene;
import scene.SceneType;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class SettingsPlayerController extends Scene<RenderableObject> {
    private int id;
    private Map<String , Integer> playerControls = new HashMap<>();
    Text title;

    public SettingsPlayerController(int id) {
        this.id = id;
        sceneType = SceneType.PAUSE_MENU;
        sceneBackgroundColor = Colors.WHITE;
    }

    public void setId(int id) {
        this.id = id;
    }

    private void createUI() {

        title = new Text(-40 , 120 , "Player " + id , 20 , Colors.BLUE);

        Button backButton = ButtonFactory.createBackButtonWithArrow(-220, 120, 15, Colors.BLUE,
                e -> SwitchSceneCommand.pop());

        Modal instructionModal = ModalFactory.builder()
                .at(100 , 0)
                .message(
                        "😢 الشديد للأسف أعملها ملحقتش \n" +
                                "               محمد على الله صلى")
                .messageColor(Colors.BLUE)
                .hasHeader(false)
                .hasCloseButton(false)
                .messageSize(20)
                .size(300, 200)
                .build();

        Modal bottomModal = ModalFactory.builder()
                .at(0, -90)
                .message("زوما☝")
                .backgroundOpacity(0)
                .childrenOpacity(0)
                .hasCloseButton(false)
                .hasHeader(false)
                .messageSize(20)
                .size(100, 100)
                .build();


        instructionModal.setAlignment(Modal.ModalTextAlignment.CENTER);

        add(title);
        uiManager.add(backButton, instructionModal, bottomModal);

        ((GeometricBackground) instructionModal.getBackground()).setColor(Colors.WHITE);
    }

    @Override
    public void onResume(){
        if(title != null)
            this.title.setContent("Player " + id);
        uiManager.getUIElements().forEach(e ->{
            if(e instanceof Modal modal){
                modal.show(); // اللي بعمله هنا ده عبط محدش يعمل زي كدا , مكسل أعدل الكلام العربي بس
            }
        });
    }


    public void initialize() {
        super.initialize();
        createUI();
        inputContext.bindKey(KeyEvent.VK_ESCAPE, SwitchSceneCommand::pop);
    }
}
