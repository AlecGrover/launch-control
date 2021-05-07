package objects;

import display.drawables.IDrawable;
import display.drawables.RocketDrawable;
import display.drawables.RocketUnderThrust;
import util.vectors.Vector2D;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;

import static objects.Constants.ROCKET_THRUST;
import static physics.constants.Constants.EARTH_RADIUS;
import static util.constants.actions.Actions.*;
import static util.constants.timer.TimerConstants.DELTA_TIME;

/*
* The rocket is the only DynamicPhysicsObject in the current implementation
* and is the only object directly manipulable by the user.
*
* The rocket is one of the primary classes, but due to encapsulation the
* direct functionalities of this class are fairly simple.
*
* Rocket contains a method to bind the control map to the correct keys,
* the draw method required by its interface, some query functions
* regarding its fuel status, some overrides for its physics content,
* and an update function.
* */

public class Rocket extends DynamicPhysicsObject implements IDrawable {

    private final Thruster thruster = new Thruster(ROCKET_THRUST, this);
    private final Vector2D position;
    private static final Earth earth = new Earth() {};
    private RocketDrawable sprite = new RocketDrawable();
    private final float mass = 100;

    public Rocket() {
        this.position = new Vector2D(0, -EARTH_RADIUS);
        forces.add(thruster);
        forces.add(earth.getGravity());
    }

    public boolean isEmpty() {
        return thruster.isEmpty();
    }

    public int getFuel() {
        return thruster.getFuel();
    }

    // Binds the keymaps for the left/right arrow keys and space
    // to the corresponding actions. These actions control rotation
    // and thrust, and assign the correct RocketDrawable to drawable.
    public void setMaps(ActionMap actionMap, InputMap inputMap) {

        Action enableThrustAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sprite = new RocketUnderThrust();
                thruster.setThrust(true);
            }
        };

        Action disableThrustAction = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                sprite = new RocketDrawable();
                thruster.setThrust(false);
            }
        };

        Action leftRotationAction = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                direction -= ROTATION_SPEED * DELTA_TIME;
            }
        };

        Action rightRotationAction = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                direction += ROTATION_SPEED * DELTA_TIME;
            }
        };

        actionMap.put(ENABLE_THRUST, enableThrustAction);
        actionMap.put(DISABLE_THRUST, disableThrustAction);
        actionMap.put(LEFT, leftRotationAction);
        actionMap.put(RIGHT, rightRotationAction);

        inputMap.put(KeyStroke.getKeyStroke("SPACE"), ENABLE_THRUST);
        inputMap.put(KeyStroke.getKeyStroke("released SPACE"), DISABLE_THRUST);
        inputMap.put(KeyStroke.getKeyStroke("LEFT"), LEFT);
        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), RIGHT);

    }

    // Utilizes the base update method to get the velocity change
    // and updates the position with it. Then determines if the rocket
    // is being pulled under the earth's surface. If so, normal force is
    // added, if not, normal force is removed.
    @Override
    public void update() {
        super.update();
        position.add(Vector2D.multiply(DELTA_TIME, velocity));
        if (Vector2D.subtract(position, earth.getPosition()).getMagnitude() <= EARTH_RADIUS) {
            if (!forces.contains(earth.getNormalForce())) forces.add(earth.getNormalForce());
        }
        else {
            forces.remove(earth.getNormalForce());
        }

        // System.out.printf("Rocket Velocity [%f, %f] with magnitude %f\n", velocity.x, velocity.y, velocity.getMagnitude());

        // System.out.printf("Current Rocket Position: [%f, %f]\n", position.x,position.y);
    }

    @Override
    public Vector2D getPosition() {
        return this.position;
    }

    @Override
    public void draw(Graphics2D g2) {
        update();
        AffineTransform transform = g2.getTransform();
        g2.translate(position.x, position.y);
        sprite.setRotation(direction);
        Stroke old = g2.getStroke();
        g2.setStroke(new BasicStroke(4));
        sprite.draw(g2);
        g2.setStroke(old);
        g2.setTransform(transform);
    }

    @Override
    public float getMass() {
        return mass;
    }

}
