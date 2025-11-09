package entities.factory;


import App_constants.Constants;
import behaviors.BounceMovement;
import behaviors.MovementBehavior;
import engine.grahpics.Colors;
import entities.fish.BotFish;


import java.util.Random;

public class FishFactory {
    private final Random random = new Random();

    public BotFish createBot() {
        float w = 20 + random.nextFloat() * 10;
        float h = 8 + random.nextFloat() * 8;

        BotFish bot = new BotFish.Builder(w, h)
                .bodyColor(Colors.randomColor())
                .at(0,  0).build();
        bot.getTransformation().translateTo(randomStartX() , randomStartY());



        MovementBehavior mv = new BounceMovement(randomInitialDx() , randomInitialDy());
        bot.setMovementBehavior(mv);

        return bot;
    }

    private float randomStartX() {
        return random.nextBoolean() ?  Constants.World.MAX_X : Constants.World.MIN_X;
    }

    private float randomStartY() {
        return (Constants.World.MIN_Y + random.nextFloat() * Constants.World.WIDTH);
    }

    private float randomInitialDx() {
        return random.nextBoolean() ? MovementBehavior.SPEED : -MovementBehavior.SPEED;
    }

    private float randomInitialDy() {
        return (random.nextFloat() - 0.5f) * MovementBehavior.SPEED;
    }
}

