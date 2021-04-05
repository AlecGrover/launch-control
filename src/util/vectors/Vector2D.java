package util.vectors;

public class Vector2D {

    public float x, y;

    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public Vector2D(double x, double y) {
        this.x = (float) x;
        this.y = (float) y;
    }

    public float dotProduct(Vector2D v2) {
        return this.x * v2.x + this.y * v2.y;
    }
    public static float dotProduct(Vector2D v1, Vector2D v2) {
        return v1.x * v2.x + v1.y * v2.y;
    }
    public static float dotProduct(float v1x, float v1y, float v2x, float v2y) { return v1x * v2x + v1y * v2y; }

    public static Matrix2D multiply(Vector2D v1, Vector2D v2) {
        return new Matrix2D(v1.x * v2.x, v1.x * v2.y, v1.y * v2.x, v1.y * v2.y);
    }
    public void multiply(float coefficient) {
        this.x *= coefficient;
        this.y *= coefficient;
    }
    public static Vector2D multiply(float coefficient, Vector2D v) {
        return new Vector2D(v.x * coefficient, v.y * coefficient);
    }

    public void add(Vector2D v2) {
        x += v2.x;
        y += v2.y;
    }
    public static Vector2D add(Vector2D v1, Vector2D v2) {
        return new Vector2D(v1.x + v2.x, v1.y + v2.y);
    }

    public void subtract(Vector2D v2) {
        x -= v2.x;
        y -= v2.y;
    }
    public static Vector2D subtract(Vector2D v1, Vector2D v2) {
        return new Vector2D(v1.x - v2.x, v1.y - v2.y);
    }

    public void normalize() {
        double magnitude = Math.sqrt(x * x + y * y);
        this.x /= magnitude;
        this.y /= magnitude;
    }
    public static Vector2D normalize(Vector2D v) {
        double magnitude = Math.sqrt(v.x * v.x + v.y * v.y);
        return new Vector2D(v.x / magnitude, v.y / magnitude);
    }

    public static Vector2D fromPolar(float r, float theta) {
        return new Vector2D(r * Math.cos(theta), r * Math.sin(theta));
    }

    public float getMagnitude() {
        return (float) Math.sqrt(x * x + y * y);
    }

}
