package objects;

import betterAWT.Circle;
import display.drawables.IDrawable;
import physics.constants.Constants;
import physics.intefaces.IPhysicsObject;
import physics.mechanics.Gravity;
import physics.mechanics.NormalForce;
import util.vectors.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import display.drawables.*;

import static physics.constants.Constants.EARTH_RADIUS;

/*
* Abstract class containing the physics information for the Earth object.
* This is effectively a constant, thus the abstract class implementation.
*
* Earth has the unique property of producing forces, namely Gravity and
* the Normal Force. It also contains the rendering information for the
* scene to use.
* */

public abstract class Earth implements IPhysicsObject {

    private static final Vector2D position = new Vector2D(0, 0);
    private static final float mass = Constants.EARTH_MASS;
    private static final Circle outline = new Circle(position, EARTH_RADIUS);
    private final Gravity earthGravity = new Gravity(this);
    private final NormalForce normalForce = new NormalForce(earthGravity);
    private static final IDrawable drawable = new EarthDrawable();

    @Override
    public float getMass() {
        return mass;
    }

    @Override
    public Vector2D getPosition() {
        return position;
    }

    public static void draw(Graphics2D g2) {
        AffineTransform transform = g2.getTransform();
        drawable.draw(g2);
        g2.setTransform(transform);
    }

    public Gravity getGravity() { return earthGravity; }
    public NormalForce getNormalForce() { return normalForce; }
    public Shape getShape() { return outline; }

}
