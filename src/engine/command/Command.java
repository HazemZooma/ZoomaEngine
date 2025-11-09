package engine.command;

import engine.core.input.MouseInputData;
import engine.grahpics.RenderableObject;

public interface Command {
    default void execute(RenderableObject target) {}
    default void execute(MouseInputData inputData) {}
}
