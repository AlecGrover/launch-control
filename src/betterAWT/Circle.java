package betterAWT;

import util.vectors.Vector2D;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Circle extends Ellipse2D.Float {

    public Circle(float x, float y, float radius) {
        super(x - radius, y - radius, 2 * radius, 2 * radius);
    }
    public Circle(Vector2D position, float radius) {super(position.x - radius, position.y - radius,
                                                          2 * radius, 2* radius);}

}
