package objects;

import display.drawables.Asteroid;
import display.drawables.IDrawable;
import util.enums.DebrisType;
import util.vectors.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Random;

import static physics.constants.Constants.EARTH_RADIUS;
import static util.constants.timer.TimerConstants.FRAME_DURATION;
import static util.constants.window.Dimensions.WIDTH;
import static util.constants.window.Dimensions.HEIGHT;


/*
* The Debris object is a reinitializable decorative object that
* will start at a random location on the bounding circle of the
* screen and travel across the screen at a randomized angle and
* speed. Once it travels beyond the opposing bounding circle it
* will deactivate and wait for reinitialization.
* */

public class Debris {

    private IDrawable drawable;
    protected Vector2D position;
    protected Vector2D velocity;
    private float rotationSpeed;
    private float maxRadius;
    private float rotation;
    private boolean active = false;
    private boolean upper = false;
    protected float radius = 0;

    public Debris() {

    }

    // Randomly chooses size, layer, rotation speed, and speed
    // Sets the velocity vector based on randomly chosen start
    public void init(DebrisType type) {
        Random r = new Random();

        this.active = true;
        this.upper = r.nextInt(2) == 1;
        this.radius = EARTH_RADIUS * (r.nextFloat() * 0.05f + 0.05f);
        // This switch has only one statement, but it is included to facilitate easy expansion
        switch (type) {
            case ASTEROID:
                drawable = new Asteroid(this.radius, 3, 15);
                break;
        }

        this.maxRadius = Math.max(WIDTH / 1.5f, HEIGHT / 1.5f);
        float angle = (float) (r.nextFloat() * 2 * Math.PI);
        this.rotationSpeed = (float) (r.nextFloat() * 4 * Math.PI - 2 * Math.PI);
        float speed = r.nextFloat() * 500 + 100;
        this.position = Vector2D.fromPolar(this.maxRadius, angle);
        this.velocity = Vector2D.fromPolar(-speed, (float) (angle + r.nextFloat() * Math.PI/1.5f - Math.PI/3f));
        this.rotation = 0;
    }

    // Draws the debris object and updates its rotation and position values
    public void draw(Graphics2D g2) {

        if (!active) return;

        AffineTransform transform = g2.getTransform();
        g2.setTransform(new AffineTransform());
        g2.translate(WIDTH / 2, HEIGHT / 2);
        g2.translate(position.x, position.y);
        rotation = (float) ((this.rotation + this.rotationSpeed * FRAME_DURATION / 1000f) % (2 * Math.PI));
        g2.rotate(this.rotation);
        drawable.draw(g2);
        g2.setTransform(transform);

        position.add(Vector2D.multiply(FRAME_DURATION / 1000f, this.velocity));
        // System.out.printf("Current Asteroid Position: %f, %f\n", position.x, position.y);

        this.active = position.getMagnitude() < 1000;

    }

    // Public get functions used in the iterators in SimulationPanel
    public boolean getActive() {
        return this.active;
    }

    public boolean getUpper() {
        return upper;
    }
}
