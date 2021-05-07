package physics.mechanics;

import physics.constants.Constants;
import physics.intefaces.IPhysicsObject;
import util.vectors.Vector2D;

/*
* Gravity is constructed with a source object that is used
* to determine the direction and quantity.
*
* When producing acceleration, the source object's distance
* from the effected object is used in combination with the
* the direction between the two to produce gravitational
* acceleration via the Newtonian gravity equation:
*
* F = G(m_1 * m_2)/r^2
*
* As F = m_1 * A from the basic Force equation, the smaller
* masses cancel leaving acceleration to be determined by:
*
* G * EARTH_MASS/Distance^2
*
* */

public class Gravity extends Force {

    private final IPhysicsObject source;

    public Gravity(IPhysicsObject source) {
        super(0, source.getPosition());
        this.source = source;
    }

    @Override
    public Vector2D getAcceleration(IPhysicsObject other) {
        double distance = (Vector2D.subtract(source.getPosition(), other.getPosition())).getMagnitude();
        double force = (Constants.G * source.getMass()) / Math.pow(distance, 2);
        Vector2D direction = (Vector2D.subtract(source.getPosition(), other.getPosition()));
        direction.normalize();
        direction.multiply((float) force);
        return direction;
    }

    @Override
    public String getName() {
        return "Gravity";
    }

}
