package physics.mechanics;

import physics.intefaces.IPhysicsObject;
import util.vectors.Vector2D;

public class Force {
    protected float quantity;
    protected Vector2D direction;

    public Force(float quantity, Vector2D direction) {
        this.quantity = quantity;
        this.direction = direction;
    }

    public Vector2D getAcceleration(IPhysicsObject other) {
        Vector2D normal = Vector2D.normalize(direction);
        float a = quantity/other.getMass();
//        System.out.printf("%s accelerating at magnitude %f\n", getName(), a);
//        Vector2D aVec = Vector2D.multiply(a, normal);
//        System.out.printf("\tReturning acceleration vector [%f, %f]\n", aVec.x, aVec.y);
        return Vector2D.multiply(a, normal);
    }

    public String getName() {
        return "Force";
    }

}
