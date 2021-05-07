package display.drawables;

import objects.Debris;
import util.vectors.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.Random;

import static util.constants.window.Dimensions.HEIGHT;
import static util.constants.window.Dimensions.WIDTH;

/*
* Comet is a decorator for Debris that adds a trail behind the
* debris object. It uses a nifty little bit of trigonometry to
* find the base points of an isosceles triangle formed with the
* inverse velocity vector and a perpendicular cross section of
* the unmodified radius of the debris.
* */

public class Comet extends Debris {
    private Vector2D tailEnd;
    private static final Color PALEYELLOW = new Color(0x5FFFEC41, true);

    @Override
    public void draw(Graphics2D g2) {
        Random r = new Random();
        Vector2D[] tailRoots = new Vector2D[2];
        double theta = Math.atan2(-velocity.x, -velocity.y);
        tailRoots[0] = Vector2D.fromPolar(Math.max(radius, 10), (float) (theta + Math.PI / 2));
        tailRoots[1] = Vector2D.fromPolar(Math.max(radius, 10), (float) (theta - Math.PI / 2));
        Path2D tail = new Path2D.Float();
        tail.moveTo(tailRoots[0].x, tailRoots[0].y);
        tail.lineTo(-velocity.x * 0.75, -velocity.y * 0.75);
        tail.lineTo(tailRoots[1].x, tailRoots[1].y);
        tail.closePath();
        AffineTransform transform = g2.getTransform();
        g2.setTransform(new AffineTransform());
        g2.translate(WIDTH / 2, HEIGHT / 2);
        g2.translate(position.x, position.y);
        g2.setColor(PALEYELLOW);
        g2.fill(tail.createTransformedShape(new AffineTransform()));
        g2.setTransform(transform);
        super.draw(g2);
    }
}
