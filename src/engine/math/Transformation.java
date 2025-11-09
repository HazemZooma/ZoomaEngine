package engine.math;

import com.jogamp.opengl.GL2;

public class Transformation {

    private float translateX = 0;
    private float translateY = 0;
    private float rotateAngle = 0;
    private float rotationPivotX = 0;
    private float rotationPivotY = 0;
    private float scaleX = 1;
    private float scaleY = 1;

    private float orbitAngle = 0;

    public Transformation() {
    }

    public void setRotation(float angle, float pivotX, float pivotY) {
        this.rotateAngle = angle;
        this.rotationPivotX = pivotX;
        this.rotationPivotY = pivotY;
    }

    public void setScale(float x, float y) {
        this.scaleX = x;
        this.scaleY = y;
    }

    public float getTranslateX() {
        return translateX;
    }

    public float getTranslateY() {
        return translateY;
    }

    public float getScaleX() {
        return scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public float getRotateAngle() {
        return rotateAngle;
    }

    public void apply(GL2 gl) {
        gl.glTranslatef(translateX, translateY, 0);

        gl.glTranslatef(rotationPivotX, rotationPivotY, 0);
        gl.glRotatef(rotateAngle, 0, 0, 1);
        gl.glTranslatef(-rotationPivotX, -rotationPivotY, 0);

        gl.glScalef(scaleX, scaleY, 1);
    }


    public void rotateAroundPoint(float angle, float px, float py) {
        this.rotateAngle = angle;
        this.rotationPivotX = px - translateX;
        this.rotationPivotY = py - translateY;
    }

    public void rotateAroundSelf(float angle) {
        this.rotateAngle = angle;
        this.rotationPivotX = 0;
        this.rotationPivotY = 0;
    }


    public void translateOnOrbit(float centerX, float centerY, float radiusX, float radiusY, float speed) {
        orbitAngle += speed / 100f;
        this.translateX = centerX + (float) (Math.cos(orbitAngle) * radiusX);
        this.translateY = centerY + (float) (Math.sin(orbitAngle) * radiusY);
    }

    public void translateBy(float dx, float dy) {
        this.translateX += dx;
        this.translateY += dy;
    }

    public void translateTo(float x, float y) {
        this.translateX = x;
        this.translateY = y;
    }

    public void scaleBy(float factorX, float factorY) {
        this.scaleX *= factorX;
        this.scaleY *= factorY;
    }

    public float[] getTranslation() { return new float[] {translateX, translateY}; }


}

