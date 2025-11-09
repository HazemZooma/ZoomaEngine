package scene;

import base_shapes.Rectangle;
import com.jogamp.opengl.GL2;
import engine.grahpics.Colors;

public class PauseMenuScene extends Scene {

    public PauseMenuScene(){

        int rectWidth = 15;
        int rectHeight = 60;

        Rectangle leftBar = new Rectangle(-20, 0, rectWidth, rectHeight, Colors.BLUE);
        Rectangle rightBar = new Rectangle(20, 0, rectWidth, rectHeight, Colors.BLUE);

        addAll(leftBar, rightBar);
    }


    @Override
    public void update(GL2 gl) {
        super.update(gl);
    }


}
