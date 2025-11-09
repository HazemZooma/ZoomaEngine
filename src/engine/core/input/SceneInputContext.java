package engine.core.input;

import engine.command.Command;
import engine.grahpics.RenderableObject;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class SceneInputContext {
    private final Map<Integer, Command> keyCommands = new HashMap<>();
    private final Map<Integer, Command> mouseCommands = new HashMap<>();
    private final double[] mouseCoordinates = new double[2];

    public void bindKey(int keyCode, Command command) {
        keyCommands.put(keyCode, command);
    }

    public void bindMouse(int button, Command command) {
        mouseCommands.put(button, command);
    }

    public void handleKeyPressed(KeyEvent e) {
        Command command = keyCommands.get(e.getKeyCode());
        if (command != null) command.execute((RenderableObject) null);
    }

    public void handleMousePressed(MouseEvent e, double glX, double glY) {
        Command command = mouseCommands.get(e.getButton());
        if (command != null) {
            command.execute(new MouseInputData(glX, glY, e.getButton()));
        }
    }

    public void handleMouseMoved(double glX, double glY) {
        mouseCoordinates[0] = glX;
        mouseCoordinates[1] = glY;
        Command command = mouseCommands.get(MouseEvent.NOBUTTON);
        if (command != null) {
            command.execute(new MouseInputData(glX, glY, 0));
        }
    }

    public double[] getMouseCoordinates(){
        return mouseCoordinates;
    }
}
