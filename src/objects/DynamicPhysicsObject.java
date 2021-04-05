package objects;

import physics.intefaces.IPhysicsObject;
import physics.mechanics.Force;
import util.constants.timer.TimerConstants;
import util.vectors.Vector2D;

import java.util.ArrayList;

public abstract class DynamicPhysicsObject implements IPhysicsObject {

    protected final ArrayList<Force> forces = new ArrayList<>();
    protected Vector2D velocity = new Vector2D(0, 0);
    protected float direction = 0;


    @Override
    public float getMass() {
        return 1;
    }

    @Override
    public Vector2D getPosition() {
        return null;
    }

    public void update() {
        Vector2D acceleration = new Vector2D(0, 0);
//        System.out.println("Current Forces: ");
        for (Force force : forces) {
            acceleration.add(force.getAcceleration(this));
            Vector2D accelerationLabel = force.getAcceleration(this);
//            System.out.printf("\t%s\t\tcausing acceleration vector [%f, %f]\n", force.getName(), accelerationLabel.x, accelerationLabel.y);
        }
        velocity.add(Vector2D.multiply(TimerConstants.DELTA_TIME, acceleration));
    }

    public float getSpeed() {
        return velocity.getMagnitude();
    }

    public float getDirection() {
        return direction;
    }

    public void addForce(Force force) {
        forces.add(force);
    }

    public void removeForce(Force force) {
        forces.remove(force);
    }

    public boolean containsForce(Force force) {
        return forces.contains(force);
    }

}
