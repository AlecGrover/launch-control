package physics.mechanics;

import physics.intefaces.IPhysicsObject;
import util.vectors.Vector2D;

import static util.constants.timer.TimerConstants.DELTA_TIME;

public class NormalForce extends Force{

    Gravity counteredForce;

    public NormalForce(Gravity counteredForce) {
        super(counteredForce.quantity, counteredForce.direction);
        this.counteredForce = counteredForce;
    }

    @Override
    public Vector2D getAcceleration(IPhysicsObject other) {
        return Vector2D.multiply(-10, counteredForce.getAcceleration(other));
    }

    @Override
    public String getName() {
        return "Normal Force";
    }
}
