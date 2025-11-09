package base_shapes;


import App_constants.Constants;
import com.jogamp.opengl.GL2;


public class Circle extends Shape {
    private float radiusX;
    private float radiusY;
    private float innerRadius = 0;
    private double startAngle;
    private double endAngle;
    private CircleType type;

    public Circle() {
    }

    public Circle(float posX , float posY ,float radiusX, float radiusY, float[] color, double startAngle, double endAngle, CircleType type) {
        super(posX , posY ,color);
        this.radiusX = radiusX;
        this.radiusY = radiusY;
        this.startAngle = startAngle;
        this.endAngle = endAngle;
        this.type = type;
    }

    public static Circle createCircle(float posX , float posY , float radius, float[] color) {
        return new Circle(posX , posY ,radius, radius, color, 0, Constants.Math.THREE_SIXTY, CircleType.CIRCLE);
    }

    public static Circle createEllipse(float posX , float posY ,float radiusX, float radiusY, float[] color) {
        return new Circle(posX , posY , radiusX, radiusY, color, 0, Constants.Math.THREE_SIXTY, CircleType.ELLIPSE);
    }

    public static Circle createArc(float posX , float posY ,float radius, double startAngle, double endAngle, float[] color) {
        return new Circle(posX , posY , radius, radius, color, startAngle, endAngle, CircleType.ARC);
    }

    public static Circle createRing(float posX , float posY ,float innerRadius, float outerRadius, float[] color) {
        Circle c = new Circle(posX , posY ,outerRadius, outerRadius, color, 0, Constants.Math.THREE_SIXTY, CircleType.RING);
        c.innerRadius = innerRadius;
        return c;
    }

    public float getRadiusX() {
        return radiusX;
    }

    public void setRadiusX(float r) {
        this.radiusX = r;
    }

    public float getRadiusY() {
        return radiusY;
    }

    public void setRadiusY(float r) {
        this.radiusY = r;
    }

    public double getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(double a) {
        this.startAngle = a;
    }

    public double getEndAngle() {
        return endAngle;
    }

    public void setEndAngle(double a) {
        this.endAngle = a;
    }

    public CircleType getShapeType() {
        return type;
    }

    public void setShapeType(CircleType circleType) {
        this.type = circleType;
    }

    @Override
    protected void onDraw(GL2 gl) {
        applyColor(gl);

        switch (type) {
            case CIRCLE, ELLIPSE, ARC -> drawEllipseOrArc(gl);
            case RING -> drawRing(gl);
        }
    }

    private void drawEllipseOrArc(GL2 gl) {
        gl.glBegin(GL2.GL_POLYGON);
        for (double a = startAngle; a <= endAngle; a += Constants.Math.ONE_DEGREE) {
            double x = radiusX * Math.cos(a);
            double y = radiusY * Math.sin(a);
            gl.glVertex2d(x, y);
        }
        gl.glEnd();
    }

    private void drawRing(GL2 gl) {
        int segments = 360;
        double angleStep = 2 * Math.PI / segments;

        gl.glBegin(GL2.GL_QUAD_STRIP);
        for (int i = 0; i <= segments; i++) {
            double angle = i * angleStep;
            double outerX = radiusX * Math.cos(angle);
            double outerY = radiusY * Math.sin(angle);
            double innerX = innerRadius * Math.cos(angle);
            double innerY = innerRadius * Math.sin(angle);
            gl.glVertex2d(outerX, outerY);
            gl.glVertex2d(innerX, innerY);
        }
        gl.glEnd();
    }

    public enum CircleType {
        CIRCLE,
        ELLIPSE,
        ARC,
        RING
    }
}
