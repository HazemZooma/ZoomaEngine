package scene.settings;

import com.jogamp.opengl.GL2;
import command.SwitchSceneCommand;
import engine.graphics.Colors;
import engine.graphics.RenderableObject;
import engine.sound.SoundManager;
import engine.text.Text;
import engine.ui.core.Button;
import engine.ui.core.Modal;
import engine.ui.core.Slider;
import engine.ui.factory.ButtonFactory;
import engine.ui.factory.ModalFactory;
import engine.shapes.Circle;
import engine.shapes.Rectangle;
import engine.shapes.RoundedRectangle;
import scene.Scene;
import scene.SceneType;

import java.awt.event.KeyEvent;

/*
 * slider have width , height , value at the left or right , track color, fill color , thumb color
 * implement toggle switch
 * need to correct the functionality of set size
 * */

public class SettingScene extends Scene<RenderableObject> {

    private Text titleText;
    private Text volumeText;
    private Text soundEffectsText;
    private Text controlsText;

    private Button backButton;
    private Button showControllersButton;
    private Button configureControllersButton;
    private Button resetButton;

    private Rectangle audioSection;
    private Rectangle controlsSection;

    private Modal resetModal;
    private Modal showControllersModal;
    private Modal configureControllerModal;

    private Slider masterVolumeSlider;

    public SettingScene() {
        sceneBackgroundColor = new float[]{0.1f, 0.1f, 0.2f};
    }

    private void initializeUI() {
        createTitle();
        createSectionBackgrounds();
        createAudioSettings();
        createControlsSettings();
        createActionButtons();
        initModals();
    }

    private void createTitle() {
        titleText = new Text(-90, 120, "SETTINGS", 30, new float[]{0.2f, 0.2f, 0.6f, 1f});
        add(titleText);
    }

    private void createSectionBackgrounds() {
        // Audio section background
        audioSection = new Rectangle(0, 50, 350, 100,
                new float[]{0.2f, 0.2f, 0.3f, 0.8f});

        add(audioSection);

        controlsSection = new Rectangle(0, -60, 350, 100,
                new float[]{0.2f, 0.2f, 0.3f, 0.8f});
//        controlsSection.setRadius(15f);
        add(controlsSection);
    }

    private void createAudioSettings() {
        volumeText = new Text(-40, 85, "AUDIO", 18, new float[]{0.6f, 0.8f, 1f, 1f});
        add(volumeText);

        Text masterLabel = new Text(-170, 55, "Master Volume", 12, Colors.WHITE);
        add(masterLabel);


        masterVolumeSlider = new Slider.Builder()
                .at(0, 20)
                .size(200, 40)
                .range(0, 1, SoundManager.getGlobalVolume())
                .track(new RoundedRectangle.Builder()
                        .width(200)
                        .height(8)
                        .radius(4)
                        .color(new float[]{0.3f, 0.3f, 0.3f, 1.0f})
                        .build())
                .thumb(Circle.createCircle(0, 0, 15, Colors.BLUE))
                .fill(new Rectangle(0, 0, 100, 8, new float[]{0.0f, 0.8f, 0.2f, 1.0f}))
                .onValueChanged(SoundManager::setGlobalVolume)
                .build();

        uiManager.add(masterVolumeSlider);

    }

    private void createGraphicsSettings() {

//        graphicsText = new Text(0, 20, "GRAPHICS",
//                FontType.STROKE_MONO_ROMAN, 12f, new float[]{0.6f, 0.8f, 1f, 1f});
//        add(graphicsText);
//
//        Text fullscreenLabel = new Text(-120, -10, "Fullscreen:",
//                FontType.STROKE_MONO_ROMAN, 12f, Colors.WHITE);
//        add(fullscreenLabel);

//        fullscreenToggle = new ToggleSwitch.Builder()
//                .at(50, -10)
//                .width(60)
//                .height(25)
//                .initialState(false)
//                .onColor(new float[]{0.2f, 0.6f, 0.9f, 1f})
//                .offColor(new float[]{0.3f, 0.3f, 0.5f, 1f})
//                .thumbColor(Colors.WHITE)
//                .onToggle((enabled) -> {
//                    System.out.println("Fullscreen: " + enabled);
//                })
//                .build();
//        uiManager.add(fullscreenToggle);

        // VSync toggle

//        vsyncToggle = new ToggleSwitch.Builder()
//                .at(50, -40)
//                .width(60)
//                .height(25)
//                .initialState(true)
//                .onColor(new float[]{0.2f, 0.7f, 0.4f, 1f})
//                .offColor(new float[]{0.3f, 0.3f, 0.5f, 1f})
//                .thumbColor(Colors.WHITE)
//                .onToggle((enabled) -> {
//                    System.out.println("VSync: " + enabled);
//                })
//                .build();
//        uiManager.add(vsyncToggle);
    }

    private void createControlsSettings() {
        controlsText = new Text(-50, -25, "CONTROLS", 15, new float[]{0.6f, 0.8f, 1f, 1f});
        add(controlsText);

        showControllersButton = ButtonFactory.createRoundedButton(0, -50, 180 , 30 , 10,Colors.GRAY,
                "Show Controls", m -> showControllersModal.show());


        configureControllersButton = ButtonFactory.createRoundedButton(0, -90, 180 , 30 , 10,Colors.GRAY,
                "Configure Controls", m -> configureControllerModal.show());


        showControllersButton.getText().setColor(Colors.WHITE);
        uiManager.add(showControllersButton , configureControllersButton);
    }

    private void createActionButtons() {

        backButton = ButtonFactory.createBackButtonWithArrow(-220, 120,20 , Colors.BLUE,
                (m) -> SwitchSceneCommand.pop());

        uiManager.add(backButton);
        resetButton = ButtonFactory.createRoundedButton(0, -130, 120 , 30 , 20,Colors.RED,
                "Reset Defaults", m -> resetModal.show());


        resetButton.getText().setColor(Colors.WHITE);

        uiManager.add(resetButton);

        setupButtonHoverEffects();
    }

    private void setupButtonHoverEffects() {
        backButton.setOnHoverEnter((m) -> {
//            backButton.setBackgroundColor(new float[]{0.9f, 0.9f, 1f, 1f});
            backButton.getTransformation().setScale(1.1f, 1.1f);
        });
        backButton.setOnHoverExit((m) -> {
//            backButton.setBackgroundColor(new float[]{0.7f, 0.7f, 0.9f, 1f});
            backButton.getTransformation().setScale(1f, 1f);
        });

        resetButton.setOnHoverEnter((m) -> {
//            resetButton.setBackgroundColor(new float[]{0.9f, 0.4f, 0.4f, 1f});
            resetButton.getTransformation().setScale(1.05f, 1.05f);
        });
        resetButton.setOnHoverExit((m) -> {
//            resetButton.setBackgroundColor(new float[]{0.8f, 0.3f, 0.3f, 1f});
            resetButton.getTransformation().setScale(1f, 1f);
        });

        showControllersButton.setOnHoverEnter((m) -> {
//            controlsButton.setBackgroundColor(new float[]{0.5f, 0.5f, 0.8f, 1f});
            showControllersButton.getTransformation().setScale(1.05f, 1.05f);
        });
        showControllersButton.setOnHoverExit((m) -> {
//            controlsButton.setBackgroundColor(new float[]{0.4f, 0.4f, 0.7f, 1f});
            showControllersButton.getTransformation().setScale(1f, 1f);
        });

        configureControllersButton.setOnHoverEnter((m) -> {
//            controlsButton.setBackgroundColor(new float[]{0.5f, 0.5f, 0.8f, 1f});
            configureControllersButton.getTransformation().setScale(1.05f, 1.05f);
        });
        configureControllersButton.setOnHoverExit((m) -> {
//            controlsButton.setBackgroundColor(new float[]{0.4f, 0.4f, 0.7f, 1f});
            configureControllersButton.getTransformation().setScale(1f, 1f);
        });
    }

    private void initModals(){
        showControllersModal = ModalFactory.createInfoModalWithoutButton(
                "Controls Configuration",
                "Move: Mouse\nShoot: Left Click / Space\nPause: ESC\nMenu: M\n"
        );
        showControllersModal.hide();
        showControllersModal.setAlignment(Modal.ModalTextAlignment.TOP_LEFT);
        uiManager.add(showControllersModal);

        configureControllerModal = ModalFactory.builder()
                .size(250 , 150)
                .build();

        float configureModalWidth = configureControllerModal.getWidth();
        float configureModalHeight = configureControllerModal.getHeight();

        Button player1 =  ButtonFactory.createSecondaryButton(-configureModalWidth/4 , configureModalHeight/4 ,
                100 , 30 , "Player1" ,
                e -> {
               this.switchToPlayerControl(1);
                });

        Button player2 = ButtonFactory.createSecondaryButton(configureModalWidth/4 , configureModalHeight/4 ,
                100 , 30 , "Player2" ,
                e -> {
                    this.switchToPlayerControl(2);
                });

        Button player3 =  ButtonFactory.createSecondaryButton(-configureModalWidth/4 , -configureModalHeight/4 ,
                100 , 30 , "Player3" ,
                e -> switchToPlayerControl(3));

        Button player4 =  ButtonFactory.createSecondaryButton(configureModalWidth/4 , -configureModalHeight/4 ,
                100 , 30 , "Player4" ,
                e -> switchToPlayerControl(4));




        configureControllerModal.addChildren(player1 , player2 , player3 , player4);


        configureControllerModal.hide();

        uiManager.add(configureControllerModal);

        resetModal = ModalFactory.createConfirmationModal(
                "Are you sure you want to reset all settings to default?",
                "Settings reset to defaults"
                ,(m)->{
                    resetToDefaults();
                    ((Modal) m.getTarget().getParent()).hide();
                }
        );

        resetModal.setAlignment(Modal.ModalTextAlignment.CENTER);

        resetModal.hide();


        uiManager.add(resetModal);
    }

    private void resetToDefaults() {

        if (masterVolumeSlider != null) {
            SoundManager.setGlobalVolume(0.5f);
            masterVolumeSlider.setValue(0.5f);
        }

    }

    @Override
    public void render(GL2 gl) {
        super.render(gl);
        animateTitle();
    }

    private void switchToPlayerControl(int id){
        SwitchSceneCommand.push(SceneType.SETTINGS_PLAYER_CONTROLS , scene -> {
            ((SettingsPlayerController) scene).setId(id);
        });
    }

    private void animateTitle() {
        long time = System.currentTimeMillis();
        float pulse = (float) Math.sin(time * 0.003) * 0.03f + 1f;

        if (titleText != null) {
            titleText.getTransformation().setScale(pulse, pulse);
        }
    }

    @Override
    public void initialize() {
        super.initialize();
        initializeUI();

        inputContext.bindKey(KeyEvent.VK_ESCAPE, SwitchSceneCommand::pop);
    }

    @Override
    public void onExit() {
        super.onExit();
        System.out.println("Exiting settings scene - settings would be saved here");
    }
}