package command.UI;

import command.Command;
import engine.core.input.MouseInputData;
import engine.ui.UIManager;

public record UIManagerInputCommand(UIManager uiManager, MouseInputData mouseInputData) implements Command {
    @Override
    public void execute() {
        if (uiManager != null)
            uiManager.handleInput(mouseInputData);
    }
}
