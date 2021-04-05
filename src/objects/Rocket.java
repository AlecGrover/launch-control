package objects;

import display.drawables.IDrawable;
import display.drawables.RocketDrawable;
import physics.intefaces.IPhysicsObject;
import util.vectors.Vector2D;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;

import static objects.Constants.ROCKET_THRUST;
import static physics.constants.Constants.EARTH_RADIUS;
import static util.constants.actions.Actions.*;
import static util.constants.timer.TimerConstants.DELTA_TIME;

public class Rocket extends DynamicPhysicsObject implements IDrawable {

    private final Thruster thruster = new Thruster(ROCKET_THRUST, this);
    private final Vector2D position;
    private static final Earth earth = new Earth() {};
    private final RocketDrawable sprite = new RocketDrawable();
    private final float mass = 100;

    public Rocket() {
        this.position = new Vector2D(0, -EARTH_RADIUS);
        forces.add(thruster);
        forces.add(earth.getGravity());
    }

    public void setMaps(ActionMap actionMap, InputMap inputMap) {

        Action enableThrustAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thruster.setThrust(true);
            }
        };

        Action disableThrustAction = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
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

        System.out.printf("Rocket Velocity [%f, %f] with magnitude %f\n", velocity.x, velocity.y, velocity.getMagnitude());

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
        sprite.draw(g2);
        g2.setTransform(transform);
    }

    @Override
    public float getMass() {
        return mass;
    }

}
