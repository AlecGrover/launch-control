package physics.mechanics;

import physics.intefaces.IPhysicsObject;
import util.vectors.Vector2D;

import static util.constants.timer.TimerConstants.DELTA_TIME;

/*
* The Normal Force is the summary concept of the counter force
* that prevents gravity from pulling you through the floor.
*
* Obviously, it's a bit more complex than that, but for the sake
* of this assignment I take a simplistic way of handling it. When
* the object falls below a zero elevation a force equal to ten times
* the inverse of Gravity is applied. This does cause an unintended
* mechanic where a rocket left unattended will begin to bounce in a
* positive feedback loop, but a full collision physics system here
* wasn't really all that important, as the simulation is of orbital
* mechanics and the goal is to never touch the ground again. Doing
* it this way also has the favorable outcome of causing the rocket
* to bounce when it hits the ground at speed, which can cause some
* fascinating simulation scenarios.
* */

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
