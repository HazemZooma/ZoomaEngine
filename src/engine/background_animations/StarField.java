package engine.background_animations;

import App_constants.Constants;
import com.jogamp.opengl.GL2;
import engine.graphics.Colors;
import engine.graphics.RenderableObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StarField extends RenderableObject {
    private static class Star {
        float x, y;
        float speed;
        float size;
        float[] color;

        Star(float x, float y, float speed, float size, float[] color) {
            this.x = x;
            this.y = y;
            this.speed = speed;
            this.size = size;
            this.color = color;
        }
    }

    private final List<Star> stars = new ArrayList<>();
    private final int numStars;
    private final float width = Constants.World.ORTHO_WIDTH, height = Constants.World.ORTHO_HEIGHT;
    private final Random random = new Random();

    public StarField(int numStars) {
        this.numStars = numStars;
        initStars();
    }

    private void initStars() {
        for (int i = 0; i < numStars; i++) {
            stars.add(randomStar());
        }
    }

    private Star randomStar() {
        float x = random.nextFloat() * width - width / 2f;
        float y = random.nextFloat() * height - height / 2f;
        float speed = 0.5f + random.nextFloat() * 1.5f;
        float size = 1f + random.nextFloat() * 2f;
        return new Star(x, y, speed, size, Colors.GRAY);
    }

    public void update() {
        for (Star s : stars) {
            s.y -= s.speed;
            if (s.y < -height / 2f) {
                s.x = random.nextFloat() * width - width / 2f;
                s.y = height / 2f;
                s.speed = 0.5f + random.nextFloat() * 1.5f;
                s.size = 1f + random.nextFloat() * 2f;
                float gray = 0.7f + random.nextFloat() * 0.3f;
                s.color = new float[]{gray, gray, gray};
            }
        }
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    protected void onDraw(GL2 gl) {
        gl.glPointSize(2f);
        gl.glBegin(GL2.GL_POINTS);
        for (Star s : stars) {
            gl.glColor3f(s.color[0], s.color[1], s.color[2]);
            gl.glVertex2f(s.x, s.y);
        }
        gl.glEnd();
    }
}
