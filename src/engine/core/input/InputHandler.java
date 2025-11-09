package engine.core.input;

import App_constants.Constants;
import engine.core.SceneManager;
import engine.grahpics.RenderableObject;
import scene.Scene;

import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class InputHandler implements KeyListener, MouseListener, MouseMotionListener {

    private final Map<Integer, Runnable> globalKeyBindings = new HashMap<>();
    private final SceneManager sceneManager;
    private int canvasWidth, canvasHeight;

    public InputHandler(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public void bindGlobalKey(int keyCode, Runnable runnable) {
        globalKeyBindings.put(keyCode, runnable);
    }

    private Scene getCurrentScene() {
        return sceneManager.getCurrentScene();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        Runnable globalCommand = globalKeyBindings.get(key);
        if (globalCommand != null) {
            globalCommand.run();
            return;
        }

        Scene scene = getCurrentScene();
        if (scene != null && scene.getInputContext() != null) {
            scene.getInputContext().handleKeyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Scene scene = getCurrentScene();
        double[] coords = convertToGL(e.getX(), e.getY());
        System.out.println(scene == null);
        if (scene != null && scene.getInputContext() != null) {
            scene.getInputContext().handleMousePressed(e, coords[0], coords[1]);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Scene scene = getCurrentScene();
        if (scene != null && scene.getInputContext() != null) {
            double[] coords = convertToGL(e.getX(), e.getY());
            scene.getInputContext().handleMouseMoved(coords[0], coords[1]);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    public void setCanvasSize(int width, int height) {
        this.canvasWidth = width;
        this.canvasHeight = height;
    }

    private double[] convertToGL(int mouseX, int mouseY) {
        double glX = (mouseX / (double) canvasWidth) * (Constants.World.WIDTH) + Constants.World.MIN_X;
        double glY = Constants.World.MAX_Y - (mouseY / (double) canvasHeight) * (Constants.World.HEIGHT);
        return new double[]{glX, glY};
    }
}
