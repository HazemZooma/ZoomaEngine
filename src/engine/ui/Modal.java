package engine.ui;

import com.jogamp.opengl.GL2;

import javax.swing.*;

public class Modal extends UIElement {

    private final String message;
    private final String title;
    private final String[] options;
    private final Runnable onOptionSelected;

    public Modal(String message, String title, String[] options, Runnable onOptionSelected) {
        this.message = message;
        this.title = title;
        this.options = options;
        this.onOptionSelected = onOptionSelected;
    }

    public void show() {
        SwingUtilities.invokeLater(() -> {
            int result = JOptionPane.showOptionDialog(null, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            if (result == JOptionPane.YES_OPTION || result == JOptionPane.NO_OPTION) {
                onOptionSelected.run();
            }
        });
    }

    @Override
    protected void onDraw(GL2 gl) {
        show();
    }

}