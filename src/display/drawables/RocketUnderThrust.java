package display.drawables;

import util.vectors.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

/*
* Rocket under thrust is a decorator for the base RocketDrawable
* that produces an animated flame underneath the rocket when its
* engine is on. Each time the flame is drawn it uses a similar
* recursive method to Asteroid to create a randomly shaped flame,
* successive redraws cause the flame to flicker in a flame-like
* manner.
* */

public class RocketUnderThrust extends RocketDrawable {


    // Draw simply calls a flame generator and a smaller
    // flame generator then makes and draws flames from
    // those paths.
    @Override
    public void draw(Graphics2D g2) {
        g2.rotate(rotation);
        Path2D redPath = new Path2D.Double();
        Vector2D[] redFirePath = flameGenerator(28,
                new Vector2D[]{new Vector2D(-9, 0)},
                new Vector2D[]{new Vector2D(9, 0)},
                3);
        redPath.moveTo(redFirePath[0].x, redFirePath[0].y);
        for (int i = 1; i < redFirePath.length; i++) {
            redPath.lineTo(redFirePath[i].x, redFirePath[i].y);
        }
        Shape redFire = redPath.createTransformedShape(new AffineTransform());

        Path2D orangePath = new Path2D.Double();
        Vector2D[] orangeFirePath = flameGenerator(16,
                new Vector2D[]{new Vector2D(-9, 0)},
                new Vector2D[]{new Vector2D(9, 0)},
                3);
        orangePath.moveTo(orangeFirePath[0].x, orangeFirePath[0].y);
        for (int i = 1; i < orangeFirePath.length; i++) {
            orangePath.lineTo(orangeFirePath[i].x, orangeFirePath[i].y);
        }
        Shape orangeFire = orangePath.createTransformedShape(new AffineTransform());

        g2.setColor(Color.RED);
        g2.fill(redFire);
        g2.setColor(Color.ORANGE);
        g2.fill(orangeFire);
        g2.rotate(-rotation);
        super.draw(g2);
    }

    // flameGenerator works with a similar logic to varianceRecursion in Asteroid
    // however the randomness is done differently, and this time both the x and y
    // values of each vector are altered directly.
    private Vector2D[] flameGenerator(float maxHeight, Vector2D[] left, Vector2D[] right, int maxDepth) {
        if (maxDepth == 0) return Stream.concat(Arrays.stream(left), Arrays.stream(right)).toArray(Vector2D[]::new);
        Random r = new Random();
        Vector2D midpoint = Vector2D.add(left[left.length - 1], Vector2D.multiply(0.5f, Vector2D.subtract(right[0], left[left.length - 1])));
        midpoint.y += maxHeight * 0.5 * (r.nextFloat() + 1);
        midpoint.x += r.nextFloat() - 0.5f;

        Vector2D[] newMid = new Vector2D[]{ midpoint };
        Vector2D[] leftFlame = flameGenerator(maxHeight / 2, left, newMid, maxDepth - 1);
        Vector2D[] rightFlame = flameGenerator(maxHeight / 2, newMid, right, maxDepth - 1);
        return Stream.concat(
                Arrays.stream(leftFlame, 0, leftFlame.length - 1),
                Arrays.stream(rightFlame))
                .toArray(Vector2D[]::new);
    }

}
