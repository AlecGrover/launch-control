package physics.intefaces;

import physics.mechanics.Force;
import util.vectors.Vector2D;

/*
* The basic physics object interface
* */

public interface IPhysicsObject {
    float getMass();
    Vector2D getPosition();
}
