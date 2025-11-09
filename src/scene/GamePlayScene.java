package scene;

import com.jogamp.opengl.GL2;
import engine.core.SceneManager;
import engine.core.collisions.CollisionManager;
import engine.command.MoveToMouseCommand;
import engine.ui.Modal;
import entities.base.Entity;
import entities.factory.FishFactory;
import entities.fish.BotFish;
import entities.fish.PlayerFish;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.List;

public class GamePlayScene extends Scene {
    private final PlayerFish player;
    private final CollisionManager collisionManager;
    private final FishFactory fishFactory;
    double prevX;

    public GamePlayScene(PlayerFish player,
                         CollisionManager collisionManager,
                         FishFactory fishFactory,
                         int initialBotCount) {
        this.player = player;
        this.collisionManager = collisionManager;
        this.fishFactory = fishFactory;

        addEntity(player);
        spawnInitialBots(initialBotCount);
    }

    private void spawnInitialBots(int count) {
        for (int i = 0; i < count; i++) {
            addEntity(fishFactory.createBot());
        }
    }

    @Override
    public void update(GL2 gl) {
        super.update(gl);
        checkFlip();
        updateBots();
        handleCollisions();
        manageBotPopulation();
        checkWinCondition();
    }

    @Override
    public void onEnter() {
        getInputContext().bindMouse(MouseEvent.NOBUTTON, new MoveToMouseCommand(player));
    }

    public void checkFlip() {
        double glX = getInputContext().getMouseCoordinates()[0];

        if (glX > prevX && player.isFacingRight()) {
            player.flip();
        } else if (glX < prevX && !player.isFacingRight()) {
            player.flip();
        }

        prevX = player.getTransformation().getTranslateX();
    }


    private void updateBots() {
        getEntitiesOfType(BotFish.class).forEach(BotFish::move);
    }

    private void handleCollisions() {
        List<BotFish> bots = getEntitiesOfType(BotFish.class);
        for (BotFish bot : bots) {
            if (collisionManager.canEat(player, bot)) {
                bot.kill();
                player.eat();
            }
        }
    }

    private void manageBotPopulation() {
        getRenderableObjects().removeIf(e -> (e instanceof Entity && ((Entity) e).isDead()));

        while (getEntitiesOfType(BotFish.class).size() < 8 && !isWin()) {
            addEntity(fishFactory.createBot());
        }
    }

    private void checkWinCondition() {
        if (isWin()) {
            getRenderableObjects().removeIf(e -> !(e instanceof Modal));
            SceneManager.getInstance().switchScene(SceneType.GAME_OVER);
            winModal();
        }
    }

    private boolean isWin() {
        return Math.abs(player.getTransformation().getScaleX()) > 2;
    }


    public void winModal() {
        SwingUtilities.invokeLater(() -> {
            int result = JOptionPane.showOptionDialog(null,
                    "You won! What would you like to do?",
                    "Game Over",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new String[]{"Restart", "Exit"},
                    "Restart");

            if (result == JOptionPane.YES_OPTION) {
                addEntity(player);
                player.getTransformation().translateTo(0,0);
                player.getTransformation().setScale(1,1);
                SceneManager.getInstance().switchScene(SceneType.GAMEPLAY);
            } else if (result == JOptionPane.NO_OPTION) {
                System.exit(0);
            }
        });
    }


    public PlayerFish getPlayer() {
        return player;
    }
}