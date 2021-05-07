package display.drawables;

import betterAWT.Circle;
import util.vectors.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Random;

import static util.constants.window.Dimensions.HEIGHT;
import static util.constants.window.Dimensions.WIDTH;

/*
* EarthDrawable is the visual representation of the Earth object
* For the most part, it's just a circle, but it has some added
* decoration produced by generating a number of "lake" locations
* and Guassian distributing a few hundred blue dots in the area.
* The effect isn't perfect, but it provides a bit of interesting
* scenery.
* */

public class EarthDrawable implements IDrawable {

    private static final int RADIUS = 200;
    private static final int NOISE = 150;
    private static final int SCALE = 15;
    private static final int LAKES = 20;
    private final Circle mainCircle = new Circle(0, 0, RADIUS);
    private static Vector2D[][] noise = new Vector2D[LAKES][NOISE];
    private static final Color OCEANBLUE = new Color(0x1da2d8);

    public EarthDrawable() {

        float sliceAngles = (float) (2f * Math.PI / NOISE);

        Random r = new Random();
        for (int j = 0; j < LAKES; j++) {
            r.setSeed(System.currentTimeMillis() * j);
            Vector2D lake = Vector2D.fromPolar(r.nextFloat() * (RADIUS - 50), (float) (r.nextFloat() * Math.PI * 2));
            for (int i = 0; i < NOISE; i++) {
                noise[j][i] = new Vector2D(r.nextGaussian() * SCALE + lake.x, r.nextGaussian() * SCALE + lake.y);
            }
        }

    }

    @Override
    public void draw(Graphics2D g2) {
        AffineTransform transform = g2.getTransform();
        g2.setTransform(new AffineTransform());
        g2.translate(WIDTH / 2, HEIGHT /2);
        g2.setColor(new Color(0x228B22));
        g2.fill(mainCircle);
        g2.setStroke(new BasicStroke(8));
        g2.setColor(OCEANBLUE);
        Random r = new Random();
        for (Vector2D[] l : noise) for (Vector2D v: l) {
            g2.drawOval((int) v.x - 4, (int) v.y - 4, 8, 8);
        }
        g2.setColor(Color.BLACK);
        g2.draw(mainCircle);
    }
}
