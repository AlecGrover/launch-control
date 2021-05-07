package display.drawables;

import betterAWT.Circle;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

public class RocketDrawable implements IDrawable {

    private final Shape body;
    private final Shape leftFin;
    private final Shape middleFin;
    private final Shape rightFin;
    private final Shape window;
    protected float rotation = 0;

    /*
    * The base RocketDrawable doesn't have anything fancy, it's just a compound
    * shape consisting of two paths for the left and right fins, a rectangle for
    * the middle fin, a path for the rocket body, and a circle for the window.
    * */

    public RocketDrawable() {
        Path2D bodyPath = new Path2D.Double();
        bodyPath.moveTo(0, 0);
        bodyPath.lineTo(9, 0);
        bodyPath.curveTo(15, -13, 15, -26, 0, -40);
        bodyPath.curveTo(-15, -26, -15, -13, -9, 0);
        bodyPath.closePath();
        this.body = bodyPath.createTransformedShape(new AffineTransform());

        Path2D leftFinPath = new Path2D.Double();
        leftFinPath.moveTo(-9, 0);
        leftFinPath.lineTo(-18, 9);
        leftFinPath.lineTo(-15, -13);
        leftFinPath.closePath();
        this.leftFin = leftFinPath.createTransformedShape(new AffineTransform());

        Path2D rightFinPath = new Path2D.Double();
        rightFinPath.moveTo(9, 0);
        rightFinPath.lineTo(18, 9);
        rightFinPath.lineTo(15, -13);
        rightFinPath.closePath();
        this.rightFin = rightFinPath.createTransformedShape(new AffineTransform());

        this.middleFin = new Rectangle2D.Float(-3, -16, 6, 25);

        this.window = new Circle(0, -25, 4);

    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    @Override
    public void draw(Graphics2D g2) {
        AffineTransform transform = g2.getTransform();
        g2.translate(0, -10);
        g2.rotate(rotation);
        g2.translate(-Math.sin(-rotation) * 10, Math.cos(-rotation) * 10);
        g2.setStroke(new BasicStroke(4));

        g2.setColor(Color.WHITE);
        g2.fill(this.leftFin);
        g2.fill(this.rightFin);
        g2.setColor(Color.BLACK);
        g2.draw(this.leftFin);
        g2.draw(this.rightFin);

        g2.setColor(Color.RED);
        g2.fill(body);
        g2.setColor(Color.BLACK);
        g2.draw(body);

        g2.setColor(Color.DARK_GRAY);
        g2.fill(this.window);
        g2.setColor(Color.BLACK);
        g2.draw(this.window);

        g2.setColor(Color.WHITE);
        g2.fill(this.middleFin);
        g2.setColor(Color.BLACK);
        g2.draw(this.middleFin);
        g2.setTransform(transform);
    }

}
