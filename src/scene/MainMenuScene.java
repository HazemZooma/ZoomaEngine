package scene;

import com.jogamp.opengl.GL2;
import command.SwitchSceneCommand;
import engine.background_animations.GradientSky;
import engine.graphics.Colors;
import engine.graphics.RenderableObject;
import engine.text.Text;
import engine.texture.Sprite;
import engine.ui.core.Button;
import engine.ui.factory.ButtonFactory;

import java.awt.event.KeyEvent;

public class MainMenuScene extends Scene<RenderableObject> {
    private Text title;
    private final GradientSky gradientSky;
    private Sprite hazem;

    public MainMenuScene() {
        sceneType = SceneType.MAIN_MENU;
        sceneBackgroundColor = Colors.RED;
        int randomSkyState = (int) (Math.random() * 5);
        GradientSky.TimeOfDay timeOfDay = GradientSky.TimeOfDay.values()[randomSkyState];
        gradientSky = new GradientSky(timeOfDay);
        hazem = new Sprite(0,0,"resources/assets/textures/entities/rocket1.png");
    }

    public void initUI(){

        title = new Text(-100, 0, "Zooma", 50, Colors.RED);

        Button playGame = ButtonFactory.createPrimaryButton(-150, -100, 120, 30,
                "play game", e -> SwitchSceneCommand.push(SceneType.GAMEPLAY));

        Button settings = ButtonFactory.createPrimaryButton(
                0, -100, 120, 30, "Settings",
                e -> SwitchSceneCommand.push(SceneType.SETTINGS));

        Button instructions = ButtonFactory.createPrimaryButton(
                150, -100, 120, 30, "Instructions",
                e -> SwitchSceneCommand.push(SceneType.INSTRUCTIONS));

//        Modal infoModal = ModalFactory.createInfoModal("Information", "This is an info message" ,
//                Modal::hide);
//
//        Modal confirmModal = ModalFactory.createConfirmationModal(
//                "Confirm Delete",
//                "Are you sure you want to delete this item?",
//                modal -> {
//                    System.out.println("item deleted");
//                    modal.hide();
//                },
//                modal ->{
//                    System.out.println("item deleted bardo hhh");
//                    modal.hide();
//                }
//        );
//
//        Modal warningModal = ModalFactory.createWarningModal("warning" , "zooma");




//        Modal customModal = ModalFactory.builder()
//                .title("Custom Modal")
//                .message("Built")
//                .messageSize(10)
//                .headerColor(Colors.PURPLE)
//                .hasCloseButton(false)
//                .addButton(35 ,15 ,"Action 1", m -> System.out.println("Action 1"), Colors.PURPLE)
//                .addButton(35 ,15 , "Action 2", m -> System.out.println("Action 2"), Colors.ORANGE)
//                .addButton("test" , null , Colors.RED)
//                .build();


//        Modal withContent = new Modal.Builder()
//                .title("Complex Modal")
//                .at(0 , 0)
//                .size(200, 200)
//                .addContentElement(instructions)
//                .addButton("OK", Modal::hide, Colors.WHITE , Colors.BLUE)
//                .build();

//        Button circularButton = ButtonFactory.createBackButtonWithArrow(0, 0, 20,
//                Colors.RED, e -> System.out.println("OK clicked"));
//
//        Button imageButton = ButtonFactory.createImageButton(300, 300, 100, 50,
//                "path/to/image.png", "Image", e -> System.out.println("Image button clicked"));
//
//        Button customButton = ButtonFactory.builder()
//                .at(-200, -100)
//                .Size(40, 40)
//                .geometricBackground(ShapeType.ROUNDED_RECTANGLE, Colors.GRASS_GREEN)
//                .withHoverEffect(Colors.randomColor(), Colors.GRASS_GREEN)
//                .textContent("Custom")
//                .textColor(Colors.WHITE)
//                .textFont(FontType.STROKE_MONO_ROMAN)
//                .textSize(10)
//                .onClick(e -> System.out.println("Custom button clicked"))
//                .build();
//
//        Button primaryStyled = ButtonFactory.builder()
//                .geometricBackground(ShapeType.ROUNDED_RECTANGLE, Colors.BLUE)
//                .primaryStyle()
//                .at(-100, 100)
//                .Size(40, 45)
//                .textContent("Submit")
//                .onClick(e -> System.out.println("Submit clicked"))
//                .build();
//
//
//        Button secondaryStyled = ButtonFactory.builder()
//                .geometricBackground(ShapeType.RECTANGLE, Colors.LIGHT_GRAY)
//                .secondaryStyle()
//                .at(0, 100)
//                .Size(60, 45)
//                .textSize(10)
//                .textContent("Cancel")
//                .onClick(e -> System.out.println("Cancel clicked"))
//                .build();
//
//        Button testButton = ButtonFactory.createOutlineButton(0, -100, 40, 40, "hazem",
//                m -> System.out.println("hazem")
//        );
//
//        Button button1 = new Button.Builder()
//                .textContent("Click Me")
//                .textSize(20)
//                .textColor(Colors.RED)
//                .onClick(event -> {
//                    System.out.println("Button clicked at: " +
//                            event.getMouseX() + ", " + event.getMouseY());
//
//                    Button clickedButton = (Button) event.getTarget();
//                    System.out.println(clickedButton.getWidth());
//
//                    event.consume();
//                })
//                .onHoverEnter(event -> {
//                    System.out.println("Mouse entered button");
//                    ((GeometricBackground) event.getTarget().getBackground()).setColor(Colors.LIGHT_GRAY);
//                })
//                .onHoverExit(event -> {
//                    System.out.println("Mouse left button");
//                    ((GeometricBackground) event.getTarget().getBackground()).setColor(Colors.BLUE);
//                })
//                .build();
//
//
//        Button button2 = ButtonFactory.createPrimaryButton(0, -100, "settings", () -> SceneManager.getInstance().push(SceneType.SETTINGS));
//
//
//        Button button3 = ButtonFactory.createPrimaryButton(150,
//                -100, "Instruction", new SwitchSceneCommand(SceneType.INSTRUCTIONS));


//        Command command =() -> System.out.println("hazem");
//        infoModal.getButtons().getFirst().setOnClick(command);

        uiManager.add(playGame , settings, instructions);
        gradientSky.startColorCycle(0.001f);


        add(gradientSky , hazem , title);
    }

    @Override
    public void render(GL2 gl) {
        super.render(gl);
        gradientSky.update();
        hazem.getTransformation().translateTo(0 , 100);
    }

    public void initialize() {
        super.initialize();
        initUI();
        inputContext.bindKey(KeyEvent.VK_P, () -> System.out.println(sceneType.toString()));
        inputContext.bindKey(KeyEvent.VK_S, () -> System.out.println("hazem"));
    }
}
