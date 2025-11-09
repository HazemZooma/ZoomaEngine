package base_shapes;

import com.jogamp.opengl.GL2;

public class Triangle extends Shape {
    private int base;
    private int height;
    private TriangleType type;

    public enum TriangleType {
        EQUILATERAL,
        ISOSCELES,
        RIGHT,
        SCALENE
    }

    public Triangle() {}

    public Triangle(float posX, float posY, int base, int height, float[] color) {
        super(posX, posY, color);
        this.base = base;
        this.height = height;
        this.type = TriangleType.ISOSCELES;
    }

    private Triangle(float posX, float posY, int base, int height, float[] color, TriangleType type) {
        super(posX, posY, color);
        this.base = base;
        this.height = height;
        this.type = type;
    }

    public static Triangle createEquilateral(float posX, float posY, int side, float[] color) {
        int height = (int) (Math.sqrt(3) / 2 * side);
        return new Triangle(posX, posY, side, height, color, TriangleType.EQUILATERAL);
    }

    public static Triangle createIsosceles(float posX, float posY, int base, int height, float[] color) {
        return new Triangle(posX, posY, base, height, color, TriangleType.ISOSCELES);
    }

    public static Triangle createRight(float posX, float posY, int base, int height, float[] color) {
        return new Triangle(posX, posY, base, height, color, TriangleType.RIGHT);
    }

    public static Triangle createScalene(float posX, float posY, int base, int height, float[] color) {
        return new Triangle(posX, posY, base, height, color, TriangleType.SCALENE);
    }

    public int getBase() { return base; }
    public void setBase(int base) { this.base = base; }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    public TriangleType getType() { return type; }
    public void setType(TriangleType type) { this.type = type; }

    @Override
    protected void onDraw(GL2 gl) {
        applyColor(gl);
        gl.glBegin(GL2.GL_POLYGON);

        switch (type) {
            case EQUILATERAL -> drawEquilateral(gl);
            case ISOSCELES -> drawIsosceles(gl);
            case RIGHT -> drawRight(gl);
            case SCALENE -> drawScalene(gl);
        }

        gl.glEnd();
    }

    private void drawEquilateral(GL2 gl) {
        double halfBase = base / 2.0;
        double h = height / 2.0;
        gl.glVertex2d(-halfBase, -h);
        gl.glVertex2d(halfBase, -h);
        gl.glVertex2d(0, h);
    }

    private void drawIsosceles(GL2 gl) {
        double halfBase = base / 2.0;
        double h = height / 2.0;
        gl.glVertex2d(-halfBase, -h);
        gl.glVertex2d(halfBase, -h);
        gl.glVertex2d(0, h);
    }

    private void drawRight(GL2 gl) {
        double halfBase = base / 2.0;
        double h = height / 2.0;
        gl.glVertex2d(-halfBase, -h);
        gl.glVertex2d(halfBase, -h);
        gl.glVertex2d(-halfBase, h);
    }

    private void drawScalene(GL2 gl) {
        double halfBase = base / 2.0;
        double h = height / 2.0;
        gl.glVertex2d(-halfBase, -h);
        gl.glVertex2d(halfBase, -h);
        gl.glVertex2d(halfBase * 0.3, h);
    }
}