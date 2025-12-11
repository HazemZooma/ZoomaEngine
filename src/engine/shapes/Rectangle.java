package engine.shapes;


import com.jogamp.opengl.GL2;


public class Rectangle extends Shape {
    private float width;
    private float height;
    private int topSloppingX;
    private int topSloppingY;
    private int bottomSloppingX;
    private int bottomSloppingY;
    private RectangleType type;

    public Rectangle() {
    }

    public Rectangle(float posX, float posY, float width, float height, float[] color) {
        super(posX, posY, color);
        this.width = width;
        this.height = height;
        this.type = RectangleType.RECTANGLE;
    }

    public Rectangle(float posX , float posY , float width , float height , float[] color , float opacity){
        super(posX, posY, color , opacity);
        this.width = width;
        this.height = height;
        this.type = RectangleType.RECTANGLE;
    }

    private Rectangle(float posX, float posY, float width, float height, float[] color,
                      int topSloppingX, int topSloppingY,
                      int bottomSloppingX, int bottomSloppingY,
                      RectangleType type) {
        super(posX, posY, color);
        this.width = width;
        this.height = height;
        this.topSloppingX = topSloppingX;
        this.topSloppingY = topSloppingY;
        this.bottomSloppingX = bottomSloppingX;
        this.bottomSloppingY = bottomSloppingY;
        this.type = type;
    }

    public static Rectangle createParallelogram(int posX , int posY ,int width, int height, int slopeX, float[] color) {
        return new Rectangle(posX , posY ,width, height, color,
                slopeX, 0, slopeX, 0, RectangleType.PARALLELOGRAM);
    }

    public static Rectangle createRhombus(int posX ,int posY , int width, int height, float[] color) {
        int slopeX = width / 4;
        return new Rectangle(posX , posY ,width, height, color,
                slopeX, 0, -slopeX, 0, RectangleType.RHOMBUS);
    }

    public static Rectangle createTrapezoid(int posX , int posY , int topWidth, int bottomWidth, int height, float[] color) {
        int slopeX = (bottomWidth - topWidth) / 2;
        return new Rectangle(posX , posY ,bottomWidth, height, color,
                slopeX, 0, 0, 0, RectangleType.TRAPEZOID);
    }

    public static Rectangle createGeneralQuad(int posX , int posY ,int width, int height,
                                              int topSlopeX, int topSlopeY,
                                              int bottomSlopeX, int bottomSlopeY,
                                              float[] color) {
        return new Rectangle(posX , posY , width, height, color,
                topSlopeX, topSlopeY, bottomSlopeX, bottomSlopeY, RectangleType.GENERAL_QUAD);
    }

    @Override
    protected void onDraw(GL2 gl) {
        applyColor(gl);
        gl.glBegin(GL2.GL_QUADS);
        switch (type) {
            case RECTANGLE -> drawRectangle(gl);
            case PARALLELOGRAM -> drawParallelogram(gl);
            case RHOMBUS -> drawRhombus(gl);
            case TRAPEZOID -> drawTrapezoid(gl);
            case GENERAL_QUAD -> drawGeneralQuad(gl);
        }
        gl.glEnd();
    }

    private void drawRectangle(GL2 gl) {
        double halfWidth = width / 2.0;
        double halfHeight = height / 2.0;

        gl.glVertex2d(-halfWidth, -halfHeight);
        gl.glVertex2d(halfWidth, -halfHeight);
        gl.glVertex2d(halfWidth, halfHeight);
        gl.glVertex2d(-halfWidth, halfHeight);
    }

    private void drawParallelogram(GL2 gl) {
        double halfWidth = width / 2.0;
        double halfHeight = height / 2.0;

        gl.glVertex2d(-halfWidth, -halfHeight);
        gl.glVertex2d(halfWidth, -halfHeight);
        gl.glVertex2d(halfWidth + topSloppingX, halfHeight);
        gl.glVertex2d(-halfWidth + topSloppingX, halfHeight);
    }

    private void drawRhombus(GL2 gl) {
        double halfWidth = width / 2.0;
        double halfHeight = height / 2.0;

        gl.glVertex2d(0, -halfHeight);
        gl.glVertex2d(halfWidth, 0);
        gl.glVertex2d(0, halfHeight);
        gl.glVertex2d(-halfWidth, 0);
    }

    private void drawTrapezoid(GL2 gl) {
        double halfWidth = width / 2.0;
        double halfHeight = height / 2.0;

        gl.glVertex2d(-halfWidth, -halfHeight);
        gl.glVertex2d(halfWidth, -halfHeight);
        gl.glVertex2d(halfWidth + topSloppingX, halfHeight);
        gl.glVertex2d(-halfWidth - topSloppingX, halfHeight);
    }

    private void drawGeneralQuad(GL2 gl) {
        double halfWidth = width / 2.0;
        double halfHeight = height / 2.0;

        gl.glVertex2d(-halfWidth + bottomSloppingX, -halfHeight + bottomSloppingY);
        gl.glVertex2d(halfWidth + bottomSloppingX, -halfHeight + bottomSloppingY);
        gl.glVertex2d(halfWidth + topSloppingX, halfHeight + topSloppingY);
        gl.glVertex2d(-halfWidth + topSloppingX, halfHeight + topSloppingY);
    }

    public void setTopSlope(int slopeX, int slopeY) {
        this.topSloppingX = slopeX;
        this.topSloppingY = slopeY;
        updateShapeType();
    }

    public void setBottomSlope(int slopeX, int slopeY) {
        this.bottomSloppingX = slopeX;
        this.bottomSloppingY = slopeY;
        updateShapeType();
    }

    private void updateShapeType() {
        if (topSloppingX == bottomSloppingX && topSloppingY == bottomSloppingY) {
            if (topSloppingX != 0 || topSloppingY != 0) {
                type = RectangleType.PARALLELOGRAM;
            } else {
                type = RectangleType.RECTANGLE;
            }
        } else if (topSloppingX != 0 || bottomSloppingX != 0) {
            type = RectangleType.TRAPEZOID;
        } else {
            type = RectangleType.GENERAL_QUAD;
        }
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public RectangleType getShapeType() {
        return type;
    }

    public void setShapeType(RectangleType rectangleType) {
        this.type = rectangleType;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public enum RectangleType {
        RECTANGLE,
        PARALLELOGRAM,
        RHOMBUS,
        TRAPEZOID,
        GENERAL_QUAD
    }
}
