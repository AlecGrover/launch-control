package display.drawables;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public class RocketDrawable implements IDrawable {

    private Shape body;
    private float rotation = 0;

    public RocketDrawable() {
        Path2D path = new Path2D.Double();
        path.moveTo(-5, 0);
        path.curveTo(-8, -4, -8, -8, 0, -15);
        path.curveTo(8, -8, 8, -4, 5, 0);
        path.closePath();

        body = path.createTransformedShape(new AffineTransform());
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    @Override
    public void draw(Graphics2D g2) {
        AffineTransform transform = g2.getTransform();
        g2.translate(0, -7.5);
        g2.rotate(rotation);
        g2.translate(0, 7.5);
        g2.setColor(Color.RED);
        g2.fill(body);
        g2.setColor(Color.BLACK);
        g2.draw(body);
        g2.setTransform(transform);
    }

}
