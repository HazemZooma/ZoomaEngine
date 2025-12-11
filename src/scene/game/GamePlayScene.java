//package scene.game;
//
//import com.jogamp.opengl.GL2;
//import command.FireCommand;
//import behaviors.MoveToMouseBehavior;
//import command.SwitchSceneCommand;
//import engine.background_animations.StarField;
//import engine.core.SceneDescriptor;
//import engine.core.SceneManager;
//import engine.core.collisions.CollisionManager;
//import engine.graphics.Colors;
//import engine.ui.core.Modal;
//import objects.base.Entity;
//import objects.factory.FishFactory;
//import objects.fish.BotFish;
//import objects.fish.PlayerFish;
//import scene.Scene;
//import scene.SceneType;
//
//
//import javax.swing.*;
//import java.awt.event.KeyEvent;
//import java.awt.event.MouseEvent;
//import java.util.List;
//
//public class GamePlayScene extends Scene {
//    private static final int MIN_BOT_COUNT = 8;
//    private static final double WIN_SCALE_THRESHOLD = 2.5;
//    private final CollisionManager collisionManager;
//    private final FishFactory fishFactory;
//    private PlayerFish player;
//    private double prevX;
//    private StarField starField = new StarField(2000);
//
//
//    public GamePlayScene() {
//        sceneType = SceneType.GAMEPLAY;
//        sceneBackgroundColor = Colors.BLACK;
//
//        collisionManager = new CollisionManager();
//        fishFactory = new FishFactory();
//
//        add(starField);
//
//    }
//
//    @Override
//    public void initialize() {
//        super.initialize();
//        initializePlayer();
//        initializeInput();
//        spawnInitialBots((int) (Math.random() * 20));
//    }
//
//
//
//    private void initializePlayer() {
//        player = new PlayerFish.Builder(20, 10).at(0, 0).build();
//        System.out.println(player == null);
////        player.setMouthColor(sceneBackgroundColor);
//        add(player);
//    }
//
//
//    private void initializeInput() {
//        inputContext.bindMouse(MouseEvent.NOBUTTON,
//                new MoveToMouseBehavior(() -> player, inputContext.getMouseState()));
//        inputContext.bindMouse(MouseEvent.BUTTON1,
//                new FireCommand(this::randomFiring));
//        inputContext.bindKey(KeyEvent.VK_SPACE, new FireCommand(this::randomFiring));
//        inputContext.bindKey(KeyEvent.VK_ESCAPE, () -> SwitchSceneCommand.push(SceneType.PAUSE_MENU));
//    }
//
//    private void spawnInitialBots(int count) {
//        for (int i = 0; i < count; i++) {
//            addBotFish();
//        }
//    }
//
//    private void addBotFish() {
//        BotFish botFish = fishFactory.createBot();
////        botFish.setMouthColor(sceneBackgroundColor);
//        add(botFish);
//    }
//
//    private Entity randomFiring() {
//        var bots = getEntitiesOfType(BotFish.class);
//        if (bots.isEmpty()) return null;
//        return bots.get((int) (Math.random() * bots.size()));
//    }
//
//    private void checkFlip() {
//        double glX = getInputContext().getMouseState().getX();
//
//        if (glX > prevX && player.isFacingRight()) {
//            player.flip();
//        } else if (glX < prevX && !player.isFacingRight()) {
//            player.flip();
//        }
//
//        prevX = player.getTransformation().getTranslateX();
//    }
//
//    private void updateBots() {
//        getEntitiesOfType(BotFish.class).forEach(BotFish::move);
//    }
//
//    private void handleCollisions() {
//        List<BotFish> bots = getEntitiesOfType(BotFish.class);
//        for (BotFish bot : bots) {
//            if (collisionManager.canEat(player, bot)) {
//                bot.destroy();
//                player.eat();
//            }
//        }
//    }
//
//    private void manageBotPopulation() {
//        removeIf(e -> (e instanceof Entity entity && entity.isDead()));
//        while (getEntitiesOfType(BotFish.class).size() < MIN_BOT_COUNT && !isWin()) {
//            addBotFish();
//        }
//    }
//
//    private void checkWinCondition() {
//        if (isWin()) {
//            removeIf(e -> !(e instanceof Modal));
//            SceneManager sceneManager = SceneManager.getInstance();
//            Scene gameOver = new GameOverScene(500 , 1 );
//            sceneManager.register(SceneType.GAME_OVER , new SceneDescriptor(() -> gameOver));
//            SwitchSceneCommand.push(SceneType.GAME_OVER);
//        }
//    }
//
//    private boolean isWin() {
//        return Math.abs(player.getTransformation().getScaleX()) > WIN_SCALE_THRESHOLD;
//    }
//
//    private void showWinModal() {
//        SwingUtilities.invokeLater(() -> {
//            int result = JOptionPane.showOptionDialog(null,
//                    "You won! What would you like to do?",
//                    "Game Over",
//                    JOptionPane.YES_NO_OPTION,
//                    JOptionPane.INFORMATION_MESSAGE,
//                    null,
//                    new String[]{"Restart", "Exit"},
//                    "Restart");
//
//            if (result == JOptionPane.YES_OPTION) {
//                resetGame();
//            } else if (result == JOptionPane.NO_OPTION) {
//                System.exit(0);
//            }
//        });
//    }
//
//    private void resetGame() {
//        player.getTransformation().translateTo(0, 0);
//        player.getTransformation().setScale(1, 1);
//
//        removeIf(e -> e instanceof BotFish);
//        spawnInitialBots(MIN_BOT_COUNT);
//
//        SwitchSceneCommand.pop();
//        SwitchSceneCommand.push(sceneType);
//    }
//
//    public PlayerFish getPlayer() {
//        return player;
//    }
//
//    @Override
//    public void render(GL2 gl) {
//        super.render(gl);
//
//        if (player == null) return;
//
//        updateBots();
//        handleCollisions();
//        manageBotPopulation();
//        checkFlip();
//        checkWinCondition();
//        starField.update();
//    }
//}