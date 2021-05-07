package display.panels;

import betterAWT.Circle;
import display.drawables.Comet;
import display.drawables.RocketTracker;
import objects.Debris;
import objects.Earth;
import objects.Rocket;
import util.constants.window.Dimensions;
import util.enums.DebrisType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import static util.constants.timer.TimerConstants.FRAME_DURATION;

/*
* SimulationPanel is one of the larger classes as it controls the
* central process of the simulation itself. It functions primarily
* to call the draw functions of all the objects and draw the UI,
* but it also serves as the nexus for most root object references.
* */

public class SimulationPanel extends JPanel implements ActionListener {

    private final Rocket rocket = new Rocket();
    private final RocketTracker tracker = new RocketTracker(rocket);
    private final ArrayList<Debris> debris = new ArrayList<>();
    private static final Random random = new Random();

    private static final int STARS = 1000;
    private final Circle[] stars = new Circle[STARS];

    // Creates and starts the timer, passes the action and input map
    // information to the rocket, spawns an object pool of debris,
    // sets the background color, and creates all the Circles used as
    // stars
    public SimulationPanel() {
        Timer timer = new Timer(FRAME_DURATION, this);
        timer.start();
        
        Random r = new Random();

        rocket.setMaps(this.getActionMap(), this.getInputMap());
        for (int i = 0; i < 20; i++) {
            if (r.nextInt(4) == 1) debris.add(new Comet());
            else debris.add(new Debris());
        }
        this.setBackground(new Color(0x4c408e));


        for(int i = 0; i < STARS; i++) {
            stars[i] = new Circle(r.nextInt(Dimensions.WIDTH), r.nextInt(Dimensions.HEIGHT), 1);
        }

        
    }

    // Draws all scene objects in the following order:
    // Stars, Background Debris, UI, Earth, RocketTracker
    // bread crumbs, the rocket, Foreground Debris
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.WHITE);
        for (Circle c : stars) { g2.draw(c); }

        drawLowerDebris(g2);

        AffineTransform transform = g2.getTransform();
        drawInfoText(g, g2);
        g2.translate(Dimensions.WIDTH / 2, Dimensions.HEIGHT / 2);
        Earth.draw(g2);
        tracker.draw(g2, rocket.getPosition());
        rocket.draw(g2);
        drawUpperDebris(g2);
        g2.setTransform(transform);

        drawEmptyMessage(g);
    }

    // Gets telemetry data from the rocket and prints it
    // in a box in the upper left corner for the user to
    // reference
    private void drawInfoText(Graphics g, Graphics2D g2) {
        String velocityText = String.format("Rocket Velocity Vector: [%f, %f]",
                rocket.getVelocity().x, rocket.getVelocity().y);
        String speedText = String.format("Rocket Speed: %f", rocket.getSpeed());
        String angleText = String.format("Rocket Angle: %f", rocket.getDirection());
        String altitudeText = String.format("Rocket Altitude: %d", Math.round(rocket.getPosition().getMagnitude()) - 200);
        String fuelText = String.format("Remaining Fuel: %d", rocket.getFuel());
        g2.setColor(new Color(0xafafafaf, true));
        g2.fillRect(10, 5, 300, 135);
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(4));
        g2.drawRect(10, 5, 300, 135);
        g.setColor(Color.WHITE);
        g.setFont(Font.getFont("Arial"));
        g.drawString(velocityText, 20, 25);
        g.drawString(speedText, 20, 50);
        g.drawString(angleText, 20, 75);
        g.drawString(altitudeText, 20, 100);
        g.drawString(fuelText, 20, 125);
    }

    // Checks the rocket's fuel level and displays an out
    // of fuel message indicating that the simulation has
    // progressed to its final state, this message takes
    // the place of a conclusion screen as although it
    // marks the point at which the user can no longer
    // alter the rocket's trajectory, there is still reason
    // to continue the simulation.
    private void drawEmptyMessage(Graphics g) {
        if (rocket.isEmpty()) {
            g.setFont(new Font("Arial", Font.BOLD, 40));
            String emptyText = "FUEL EMPTY";
            g.setColor(Color.RED);
            g.drawString(emptyText, (int) (Dimensions.WIDTH / 2f - g.getFontMetrics().stringWidth(emptyText) / 2), 48);
        }
    }

    // Finds all active lower debris objects and calls their
    // draw functions. Then it runs a probability equation to
    // see if it should initialize a new piece of debris. Debris
    // has a chance to be either upper or lower debris on
    // initialization. The equation has around a 33% chance of
    // initializing a piece of debris every 200 frames (1 second)
    private void drawLowerDebris(Graphics2D g2) {
        for (Debris debrisObject : getActiveDebris(false)) debrisObject.draw(g2);
        if (random.nextFloat() < 0.002f) {
            ArrayList<Debris> inactiveDebris = getInactiveDebris();
            if (inactiveDebris.size() == 0) return;
            inactiveDebris.get(random.nextInt(inactiveDebris.size())).init(DebrisType.ASTEROID);
        }
    }

    // Draws upper debris, does not spawn new debris
    private void drawUpperDebris(Graphics2D g2) {
        for (Debris debrisObject : getActiveDebris(true)) debrisObject.draw(g2);
    }

    // Iterator that returns all currently inactive Debris
    // objects in the Debris object pool
    private ArrayList<Debris> getInactiveDebris() {
        Iterator<Debris> debrisIterator = this.debris.iterator();
        ArrayList<Debris> inactiveDebris = new ArrayList<>();
        while (debrisIterator.hasNext()) {
            Debris nextDebris = debrisIterator.next();
            if (!nextDebris.getActive()) inactiveDebris.add(nextDebris);
        }
        return inactiveDebris;
    }

    // Iterator that returns all currently active Debris
    // objects *with* the specified level in the Debris
    // object pool
    private ArrayList<Debris> getActiveDebris(boolean upper) {
        Iterator<Debris> debrisIterator = this.debris.iterator();
        ArrayList<Debris> activeDebris = new ArrayList<>();
        while (debrisIterator.hasNext()) {
            Debris nextDebris = debrisIterator.next();
            if (nextDebris.getActive() && nextDebris.getUpper() == upper) activeDebris.add(nextDebris);
        }
        return activeDebris;
    }

    // Just calls repaint
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
