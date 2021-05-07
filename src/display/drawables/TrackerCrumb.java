package display.drawables;

import betterAWT.Circle;
import util.vectors.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;

/*
* Simple class holding the position and shape for each bread crumb
* drawn by RocketTracker.
* */

public class TrackerCrumb implements IDrawable{

    private Vector2D position;
    private Circle crumb;

    public TrackerCrumb(Vector2D position) {
        this.position = position;
        this.crumb = new Circle(position.x, position.y, 4);
    }

    @Override
    public void draw(Graphics2D g2) {
        AffineTransform transform = g2.getTransform();
        Color oldColor = g2.getColor();
        Stroke oldStroke = g2.getStroke();
        g2.setColor(Color.CYAN);
        g2.fill(this.crumb);
        g2.setColor(oldColor);
        g2.setStroke(oldStroke);
        g2.setTransform(transform);
    }

    public Vector2D getPosition() {
        return position;
    }
}
