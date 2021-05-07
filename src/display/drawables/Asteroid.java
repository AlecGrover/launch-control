package display.drawables;

import util.vectors.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

/*
* Asteroid is a recursively drawn Fractal design to create a rocky
* and irregular circular shape for background debris. The details
* of the methodology is described above varianceRecursion().
* */

public class Asteroid implements IDrawable {

    private final Shape asteroidShape;
    private final float variance, radius;

    public Asteroid(float radius, float variance, int slices) {

        this.variance = variance;
        this.radius = radius;
        Float[] variances = varianceRecursion(new Float[]{0f}, new Float[]{0f}, slices);

        Path2D path = new Path2D.Float();

        float angleSlices = (float) (2 * Math.PI / variances.length);
        Vector2D next = Vector2D.fromPolar(radius, 0);
        path.moveTo(next.x, next.y);
        for (int i = 1; i < variances.length - 1; i++) {
            next = Vector2D.fromPolar(radius + variances[i], i * angleSlices);
            path.lineTo(next.x, next.y);
        }

        path.closePath();


        this.asteroidShape = path.createTransformedShape(new AffineTransform());

    }

    /*
    * varianceRecursion is the heart of the Asteroid class. It follows a simple
    * recursive algorithm that aims to solve the problem of how to create a
    * random vaguely circular object. To do so, the circle is simplified to a
    * series of radiis at even intervals. To make it more chaotic, one simply
    * has to vary the length of each radius. But, this runs the risk of causing
    * massive radius differences between neighboring points, so I built it
    * recursively. At each level of recursion the method takes the array of
    * radii to its left and to its right, then adds a new radius between its
    * immediate neighbors that varies in length by +/- an amount within a
    * predefined maximum. These radii can then be combined with their known
    * angles in my Vector2D.fromPolar method to return Cartesian coordinates
    * for a path.
    * */
    private Float[] varianceRecursion(Float[] left, Float[] right, int slices) {

        // varianceRecursion is one of many places where I've used Java's stream functionality
        // I realize that this might come across as concerningly outside the course's coverage,
        // but it's a methodology I use extensively when working with multidimensional arrays
        // for image manipulation in CMPT 365 and CMPT 461. I don't think I use anything too
        // wild, but I figured I should probably address it.

        if (slices == 0 || left.length == 0 || right.length == 0) return Stream.concat(Arrays.stream(left),
                                                                                       Arrays.stream(right))
                                                                               .toArray(Float[]::new);
        Float[] variant = {(right[0] - left[left.length - 1])/2 + left[left.length - 1] + ((new Random()).nextFloat() * 2 * this.variance - this.variance)};
        Float[] leftVariant = varianceRecursion(left, variant, (int) Math.floor(slices/2f));
        Float[] rightVariant = varianceRecursion(variant, right, (int) Math.floor(slices/2f));
        return Stream.concat(
                Arrays.stream(leftVariant, 0, leftVariant.length - 1),
                Arrays.stream(rightVariant))
                .toArray(Float[]::new);
    }


    @Override
    public void draw(Graphics2D g2) {
        AffineTransform transform = g2.getTransform();
        g2.setStroke(new BasicStroke(2.5f));
        g2.setColor(new Color(0x8b6449));
        g2.fill(this.asteroidShape);
        g2.setColor(Color.BLACK);
        g2.draw(this.asteroidShape);
        g2.setTransform(transform);
    }
}
