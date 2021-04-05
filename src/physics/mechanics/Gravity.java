package physics.mechanics;

import physics.constants.Constants;
import physics.intefaces.IPhysicsObject;
import util.vectors.Vector2D;

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
