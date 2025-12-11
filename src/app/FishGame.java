package app;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import engine.core.GameController;
import engine.core.SceneManager;
import engine.core.input.InputHandler;

import javax.swing.*;
import java.awt.*;

public class FishGame extends JFrame {


    public FishGame() {
        super("The Fish Game");

//        JLayeredPane layeredPane = new JLayeredPane();
//        layeredPane.setPreferredSize(new Dimension(800, 600));
//        this.setContentPane(layeredPane);

        GLCapabilities caps = new GLCapabilities(GLProfile.getDefault());
        GLCanvas canvas = new GLCanvas(caps);

        FPSAnimator animator = new FPSAnimator(canvas, 60);


        SceneManager sceneManager = SceneManager.getInstance();
        InputHandler inputHandler = new InputHandler(sceneManager);
        GameController gameController = new GameController(sceneManager , inputHandler);

        canvas.addGLEventListener(gameController);
        canvas.addKeyListener(inputHandler);
        canvas.addMouseListener(inputHandler);
        canvas.addMouseMotionListener(inputHandler);

//        layeredPane.add(canvas, JLayeredPane.DEFAULT_LAYER);
//
//        JLabel winMessageLabel = new JLabel("YOU WIN!", JLabel.CENTER);
//        winMessageLabel.setFont(new FontType("Arial", FontType.BOLD, 36));
//        winMessageLabel.setForeground(Color.RED);
//        winMessageLabel.setBounds(0, 0, 800, 600);
//        winMessageLabel.setVisible(false);
//        layeredPane.add(winMessageLabel, JLayeredPane.POPUP_LAYER);

        this.getContentPane().add(canvas, BorderLayout.CENTER);
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.CenterWindow();
        this.setVisible(true);

        animator.start();

        this.setFocusable(true);
        canvas.requestFocus();
    }

    public static void main(String[] args) {
        new FishGame();
    }

    public void CenterWindow() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();

        if (frameSize.width > screenSize.width) frameSize.width = screenSize.width;
        if (frameSize.height > screenSize.height) frameSize.height = screenSize.height;

        this.setLocation(
                (screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2
        );
    }
}
