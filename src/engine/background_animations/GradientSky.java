package engine.background_animations;

import com.jogamp.opengl.GL2;
import engine.graphics.RenderableObject;
import App_constants.Constants;

public class GradientSky extends RenderableObject {
    private float[] topColor;
    private float[] bottomColor;
    private final float width;
    private final float height;

    public enum TimeOfDay {
        DAY(new float[]{0.2f, 0.4f, 0.8f},
                new float[]{0.6f, 0.8f, 1.0f}),

        SUNRISE(new float[]{0.8f, 0.3f, 0.1f},
                new float[]{1.0f, 0.8f, 0.4f}),

        SUNSET(new float[]{0.6f, 0.1f, 0.2f},
                new float[]{1.0f, 0.5f, 0.0f}),

        NIGHT(new float[]{0.0f, 0.0f, 0.1f},
                new float[]{0.1f, 0.1f, 0.3f}),

        TWILIGHT(new float[]{0.1f, 0.1f, 0.3f},
                new float[]{0.3f, 0.1f, 0.4f});

        private final float[] top;
        private final float[] bottom;

        TimeOfDay(float[] top, float[] bottom) {
            this.top = top;
            this.bottom = bottom;
        }

        public float[] getTopColor() { return top.clone(); }
        public float[] getBottomColor() { return bottom.clone(); }
    }

    private boolean isCycling = false;
    private float cycleSpeed = 0.001f;
    private TimeOfDay currentTime = TimeOfDay.DAY;
    private TimeOfDay targetTime = TimeOfDay.DAY;
    private float transitionProgress = 1.0f;

    public GradientSky() {
        this(TimeOfDay.DAY);
    }

    public GradientSky(TimeOfDay initialTime) {
        this.width = Constants.World.ORTHO_WIDTH;
        this.height = Constants.World.ORTHO_HEIGHT;
        setTimeOfDay(initialTime);
    }

    public GradientSky(float[] customTopColor, float[] customBottomColor) {
        this.width = Constants.World.ORTHO_WIDTH;
        this.height = Constants.World.ORTHO_HEIGHT;
        this.topColor = customTopColor.clone();
        this.bottomColor = customBottomColor.clone();
    }

    private void setTimeOfDay(TimeOfDay time) {
        this.currentTime = time;
        this.topColor = time.getTopColor();
        this.bottomColor = time.getBottomColor();
        this.transitionProgress = 1.0f;
    }

    public void startColorCycle(float speed) {
        this.isCycling = true;
        this.cycleSpeed = Math.max(0.0001f, speed);
        this.targetTime = getNextTimeOfDay(currentTime);
        this.transitionProgress = 0.0f;
    }

    public void stopColorCycle() {
        this.isCycling = false;
    }

    public void setColors(float[] top, float[] bottom) {
        this.topColor = top.clone();
        this.bottomColor = bottom.clone();
        this.isCycling = false;
    }

    public void update() {
        if (!isCycling || transitionProgress >= 1.0f) {
            return;
        }

        transitionProgress += cycleSpeed;

        if (transitionProgress >= 1.0f) {
            transitionProgress = 1.0f;
            currentTime = targetTime;
            topColor = targetTime.getTopColor();
            bottomColor = targetTime.getBottomColor();

            if (isCycling) {
                targetTime = getNextTimeOfDay(currentTime);
                transitionProgress = 0.0f;
            }
        } else {
            float[] currentTop = currentTime.getTopColor();
            float[] currentBottom = currentTime.getBottomColor();
            float[] targetTop = targetTime.getTopColor();
            float[] targetBottom = targetTime.getBottomColor();

            topColor = interpolateColors(currentTop, targetTop, transitionProgress);
            bottomColor = interpolateColors(currentBottom, targetBottom, transitionProgress);
        }
    }

    private TimeOfDay getNextTimeOfDay(TimeOfDay current) {
        TimeOfDay[] times = TimeOfDay.values();
        int nextIndex = (current.ordinal() + 1) % times.length;
        return times[nextIndex];
    }

    private float[] interpolateColors(float[] from, float[] to, float progress) {
        float[] result = new float[3];
        for (int i = 0; i < 3; i++) {
            result[i] = from[i] + (to[i] - from[i]) * progress;
        }
        return result;
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
        gl.glBegin(GL2.GL_QUADS);

        gl.glColor3f(topColor[0], topColor[1], topColor[2]);
        gl.glVertex2f(-width / 2, height / 2);

        gl.glColor3f(topColor[0], topColor[1], topColor[2]);
        gl.glVertex2f(width / 2, height / 2);

        gl.glColor3f(bottomColor[0], bottomColor[1], bottomColor[2]);
        gl.glVertex2f(width / 2, -height / 2);

        gl.glColor3f(bottomColor[0], bottomColor[1], bottomColor[2]);
        gl.glVertex2f(-width / 2, -height / 2);

        gl.glEnd();
    }

    public void setTopColor(float r, float g, float b) {
        this.topColor = new float[]{r, g, b};
        this.isCycling = false;
    }

    public void setBottomColor(float r, float g, float b) {
        this.bottomColor = new float[]{r, g, b};
        this.isCycling = false;
    }

    public void transitionToTimeOfDay(TimeOfDay target, float speed) {
        this.targetTime = target;
        this.transitionProgress = 0.0f;
        this.cycleSpeed = Math.max(0.0001f, speed);
        this.isCycling = false; // Not cycling, just one transition
    }

    public float[] getTopColor() {
        return topColor.clone();
    }

    public float[] getBottomColor() {
        return bottomColor.clone();
    }

    public boolean isTransitioning() {
        return transitionProgress < 1.0f;
    }
}