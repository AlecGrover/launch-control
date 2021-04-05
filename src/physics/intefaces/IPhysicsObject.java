package physics.intefaces;

import physics.mechanics.Force;
import util.vectors.Vector2D;

public interface IPhysicsObject {
    public float getMass();
    public Vector2D getPosition();
}
