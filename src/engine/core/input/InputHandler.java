package engine.core.input;

import App_constants.Constants;
import engine.core.EngineContext;
import engine.core.SceneManager;
import scene.Scene;

import java.awt.event.*;

public class InputHandler implements KeyListener, MouseListener, MouseMotionListener {

    private final SceneManager sceneManager;

    public InputHandler(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    private SceneInputContext getInputContext() {
        Scene scene = sceneManager.getCurrentScene();
        return (scene != null) ? scene.getInputContext() : null;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        SceneInputContext context = getInputContext();
        if (context != null) context.onKeyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        SceneInputContext context = getInputContext();
        if (context == null) return;

        double[] gl = convertToGL(e.getX(), e.getY());
        context.onMousePressed(gl[0], gl[1], e.getButton());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        SceneInputContext context = getInputContext();
        if (context == null) return;

        double[] gl = convertToGL(e.getX(), e.getY());
        context.onMouseReleased(gl[0], gl[1]);
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
        SceneInputContext c = getInputContext();
        if (c == null) return;

        double[] gl = convertToGL(e.getX(), e.getY());
        c.onMouseMoved(gl[0], gl[1]);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }

    private double[] convertToGL(int mouseX, int mouseY) {
        EngineContext engineContext = EngineContext.get();
        int pixelWidth = engineContext.getWorldPixelWidth();
        int pixelHeight = engineContext.getWorldPixelHeight();

        double glX = (mouseX / (double) pixelWidth) * Constants.World.ORTHO_WIDTH + Constants.World.ORTHO_LEFT;
        double glY = Constants.World.ORTHO_TOP - (mouseY / (double) pixelHeight) * Constants.World.ORTHO_HEIGHT;
        return new double[]{glX, glY};
    }
}
