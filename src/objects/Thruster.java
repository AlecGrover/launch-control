package objects;

import physics.intefaces.IPhysicsObject;
import physics.mechanics.Force;
import util.vectors.Vector2D;

import static util.constants.timer.TimerConstants.DELTA_TIME;

public class Thruster extends Force {

    private final DynamicPhysicsObject object;
    private boolean underThrust = false;
    private float fuel = 100;
    private float fuelUsedPerSecond = 1;

    public Thruster(float quantity, DynamicPhysicsObject object) {
        super(quantity, Vector2D.fromPolar(1, (float) (object.direction + Math.PI / 2)));
        System.out.printf("Thruster Details:\n\tQuantity: %f\n\tDirection: [%f, %f]\n", this.quantity, this.direction.x, this.direction.y);
        this.object = object;
    }

    public Thruster(float quantity, DynamicPhysicsObject object, float startingFuel, float fuelUsedPerSecond) {
        super(quantity, Vector2D.fromPolar(1, (float) (object.direction + Math.PI / 2)));
        System.out.printf("Thruster Details:\n\tQuantity: %f\n\tDirection: [%f, %f]", this.quantity, this.direction.x, this.direction.y);
        this.object = object;
        this.fuel = startingFuel;
        this.fuelUsedPerSecond = fuelUsedPerSecond;
    }

    @Override
    public Vector2D getAcceleration(IPhysicsObject other) {
        update();
        if (underThrust && fuel > 0) {
            return super.getAcceleration(other);
        }
        return (new Vector2D(0, 0));
    }

    @Override
    public String getName() {
        return "Thruster";
    }

    public void update() {
        direction = Vector2D.fromPolar(1, (float) (object.direction - Math.PI / 2));
        if (fuel <= 0) return;
        if (underThrust) {
            fuel -= fuelUsedPerSecond * DELTA_TIME;
        }
    }

    public void setThrust(boolean thrust) { underThrust = thrust; }

}
