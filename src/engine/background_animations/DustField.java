package engine.background_animations;

import com.jogamp.opengl.GL2;

import engine.graphics.RenderableObject;
import App_constants.Constants;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DustField extends RenderableObject {
    private static class DustParticle {
        float x, y;
        float xDrift;
        float ySpeed;
        float size;
        float alpha;
        float targetAlpha;
        float fadeSpeed;
        boolean fadingIn;
        float[] color;

        DustParticle(float x, float y, float xDrift, float ySpeed, float size,
                     float alpha, float[] color) {
            this.x = x;
            this.y = y;
            this.xDrift = xDrift;
            this.ySpeed = ySpeed;
            this.size = size;
            this.alpha = alpha;
            this.targetAlpha = 0.8f;
            this.fadeSpeed = 0.01f + new Random().nextFloat() * 0.02f;
            this.fadingIn = true;
            this.color = color;
        }
    }

    private final List<DustParticle> dustParticles = new ArrayList<>();
    private final int numParticles;
    private final float width = Constants.World.ORTHO_WIDTH;
    private final float height = Constants.World.ORTHO_HEIGHT;
    private final Random random = new Random();

    private static final float[][] DUST_COLORS = {
            {0.9f, 0.9f, 1.0f},
            {0.95f, 0.95f, 1.0f},
            {1.0f, 1.0f, 1.0f},
            {0.85f, 0.88f, 1.0f}
    };

    public DustField(int numParticles) {
        this.numParticles = numParticles;
        initParticles();
    }

    private void initParticles() {
        for (int i = 0; i < numParticles; i++) {
            dustParticles.add(randomParticle());
        }
    }

    private DustParticle randomParticle() {
        float x = random.nextFloat() * width - width / 2f;
        float y = random.nextFloat() * height - height / 2f;

        float xDrift = (random.nextFloat() - 0.5f) * 0.5f;

        float ySpeed = 0.1f + random.nextFloat() * 0.4f;

        float size = 3f + random.nextFloat() * 6f;

        float initialAlpha = random.nextFloat() * 0.8f;

        float[] color = DUST_COLORS[random.nextInt(DUST_COLORS.length)];

        return new DustParticle(x, y, xDrift, ySpeed, size, initialAlpha, color);
    }

    public void update() {
        for (DustParticle p : dustParticles) {
            p.y -= p.ySpeed;
            p.x += p.xDrift;

            if (p.fadingIn) {
                p.alpha += p.fadeSpeed;
                if (p.alpha >= p.targetAlpha) {
                    p.alpha = p.targetAlpha;
                    p.fadingIn = false;
                }
            } else {
                p.alpha -= p.fadeSpeed;
                if (p.alpha <= 0.1f) {
                    p.alpha = 0.1f;
                    p.fadingIn = true;
                }
            }

            if (p.y < -height / 2f ||
                    p.x < -width / 2f ||
                    p.x > width / 2f) {

                resetParticle(p);
            }
        }
    }

    private void resetParticle(DustParticle p) {
        p.x = random.nextFloat() * width - width / 2f;
        p.y = height / 2f + random.nextFloat() * 20f;

        p.xDrift = (random.nextFloat() - 0.5f) * 0.5f;
        p.ySpeed = 0.1f + random.nextFloat() * 0.4f;
        p.size = 3f + random.nextFloat() * 6f;

        p.alpha = 0.1f;
        p.fadingIn = true;
        p.fadeSpeed = 0.01f + random.nextFloat() * 0.02f;

        if (random.nextFloat() < 0.3f) {
            p.color = DUST_COLORS[random.nextInt(DUST_COLORS.length)];
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
        gl.glEnable(GL2.GL_BLEND);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);

        gl.glEnable(GL2.GL_POINT_SMOOTH);
        gl.glHint(GL2.GL_POINT_SMOOTH_HINT, GL2.GL_NICEST);

        gl.glBegin(GL2.GL_POINTS);
        for (DustParticle p : dustParticles) {
            gl.glColor4f(p.color[0], p.color[1], p.color[2], p.alpha);

            gl.glPointSize(p.size);

            gl.glVertex2f(p.x, p.y);
        }
        gl.glEnd();

        gl.glDisable(GL2.GL_BLEND);
        gl.glDisable(GL2.GL_POINT_SMOOTH);
    }

    public void setDensity(int newDensity) {
        if (newDensity > dustParticles.size()) {
            int toAdd = newDensity - dustParticles.size();
            for (int i = 0; i < toAdd; i++) {
                dustParticles.add(randomParticle());
            }
        } else if (newDensity < dustParticles.size()) {
            int toRemove = dustParticles.size() - newDensity;
            for (int i = 0; i < toRemove; i++) {
                dustParticles.removeLast();
            }
        }
    }
}
