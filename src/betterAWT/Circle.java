package betterAWT;

import util.vectors.Vector2D;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/*
* Circle is simply a reimplementation of the awt Ellipse2D that is defined based off its center
* and radius. It's just easier to work with, I grew a bit frustrated with top left objects that
* cannot be defined with negative width or height.
* */

public class Circle extends Ellipse2D.Float {

    public Circle(float x, float y, float radius) {
        super(x - radius, y - radius, 2 * radius, 2 * radius);
    }
    public Circle(Vector2D position, float radius) {super(position.x - radius, position.y - radius,
                                                          2 * radius, 2* radius);}

}
