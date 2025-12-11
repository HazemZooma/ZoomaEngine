package scene.game;

import behaviors.MoveToMouseBehavior;
import com.jogamp.opengl.GL2;
import command.SwitchSceneCommand;
import engine.background_animations.StarField;
import engine.core.collisions.CollisionManager;
import engine.core.input.MouseInputData;
import engine.graphics.Colors;
import objects.chicken_game.Rocket;
import objects.core.Enemy;
import objects.core.GameObject;
import objects.core.Player;

import scene.Scene;
import scene.SceneType;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class GameScene extends Scene<GameObject> {
    private final List<GameObject> toAdd = new ArrayList<>();
    private final CollisionManager collisionManager;
    private Player player;
    private List<Enemy> enemies;
    StarField starField = new StarField(2000);

    public GameScene() {
        sceneBackgroundColor = Colors.BLACK;
        this.collisionManager = CollisionManager.getInstance();
    }

    private void initializeInput() {

//        inputContext.bindMouse(MouseEvent.BUTTON1,
//                new FireCommand(this::randomFiring));
//        inputContext.bindKey(KeyEvent.VK_SPACE, new FireCommand(this::randomFiring));
        inputContext.bindKey(KeyEvent.VK_ESCAPE, () -> SwitchSceneCommand.push(SceneType.PAUSE_MENU));
    }


    public void render(GL2 gl) {

        super.render(gl);

        starField.draw(gl);
        starField.update();


        forEach(GameObject::update);

        collisionManager.checkCollisions(renderableObjects);

        removeIf(GameObject::isDead);

        add(toAdd);
        toAdd.clear();
    }

    private void initializePlayer(){
        MouseInputData data = inputContext.getMouseState();
        player = new Rocket(0 , 0 , new MoveToMouseBehavior(data) , null);
        player.setSize(80 ,80);
//        player.getTransformation().setScale(2, 2);
        player.switchSkin(1);
        add(player);
    }

    @Override
    public void initialize() {
        if(!initialized){
            initializeInput();
            initializePlayer();
        }
    }

    public void spawn(GameObject obj) {
        toAdd.add(obj);
    }

}
