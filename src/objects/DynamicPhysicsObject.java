package objects;

import physics.intefaces.IPhysicsObject;
import physics.mechanics.Force;
import util.constants.timer.TimerConstants;
import util.vectors.Vector2D;

import java.util.ArrayList;

/*
* Defines an object in the Physics model that changes over time
* This holds relevant forces, positional information, velocity,
* and contains the update methods to process all of these each
* frame.
* */

public abstract class DynamicPhysicsObject implements IPhysicsObject {

    protected final ArrayList<Force> forces = new ArrayList<>();
    protected Vector2D velocity = new Vector2D(0, 0);
    protected float direction = 0;


    // All dynamic objects have a base mass of 1 to avoid
    // divide by zero errors
    @Override
    public float getMass() {
        return 1;
    }

    @Override
    public Vector2D getPosition() {
        return null;
    }

    // Updates the object's velocity using the forces applied to it
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

    // Simply gets the magnitude of the velocity vector
    public float getSpeed() {
        return velocity.getMagnitude();
    }

    // Gets the current rotation
    public float getDirection() {
        return direction;
    }

    // Gets the velocity vector
    public Vector2D getVelocity() { return velocity; }


    // Methods for manipulating the force list without direct access
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
