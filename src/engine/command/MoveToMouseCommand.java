package engine.command;

import engine.core.input.MouseInputData;
import entities.fish.PlayerFish;

public class MoveToMouseCommand implements Command {
    private final PlayerFish player;

    public MoveToMouseCommand(PlayerFish player) {
        this.player = player;
    }

    @Override
    public void execute(MouseInputData input) {
        if (input == null) return;
        player.getTransformation().translateTo((float) input.glX(), (float) input.glY());
    }
}
