package engine.core.input;

import command.Command;

import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class SceneInputContext {

    private final Map<Integer, Command> keyBindings = new HashMap<>();
    private final Map<Integer, Command> mouseBindings = new HashMap<>();

    private final MouseInputData mouse = new MouseInputData();

    public void bindKey(int keyCode, Command command) {
        keyBindings.put(keyCode, command);
    }

    public void bindMouse(int button, Command command) {
        mouseBindings.put(button, command);
    }

    public void onKeyPressed(int keyCode) {
        Command cmd = keyBindings.get(keyCode);
        if (cmd != null) cmd.execute();
    }

    public void onMousePressed(double x, double y, int button) {
//        System.out.println("width : " + Math.abs(lastMouseX - x));
//        System.out.println("height : " + Math.abs(lastMouseY - y));

        mouse.updatePosition(x, y);
        mouse.setPressed(true);
        mouse.setReleased(false);
        mouse.setButton(button);

        Command cmd = mouseBindings.get(button);
        if (cmd != null) cmd.execute();
    }

    public void onMouseReleased(double x, double y) {
        mouse.updatePosition(x, y);
        mouse.setPressed(false);
        mouse.setReleased(true);

        Command cmd = mouseBindings.get(mouse.getButton());
        if (cmd != null) cmd.execute();

        mouse.setButton(-1);
    }

    public void onMouseMoved(double x, double y) {
        mouse.updatePosition(x, y);

        Command cmd = mouseBindings.get(MouseEvent.NOBUTTON);
        if (cmd != null) cmd.execute();
    }

    public MouseInputData getMouseState() {
        return mouse;
    }
}
