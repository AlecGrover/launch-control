package display.drawables;

import objects.Rocket;
import util.vectors.Vector2D;

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

import static objects.Constants.*;

/*
* The RocketTracker class checks in on a given rocket at each
* drawing call and leaves a trail of bread crumb dots that trace
* the rocket's path. The bread crumbs are held in a queue and once
* the queue reaches a set length the oldest is despawned once a new
* one appears. Bread crumbs appear each time the rocket moves 50
* units from the previous crumb.
* */

public class RocketTracker {

    private final Queue<TrackerCrumb> trackerCrumbs = new LinkedList<>();
    private Vector2D last;
    Rocket rocket;

    public RocketTracker(Rocket trackedRocket) {
        rocket = trackedRocket;
        last = new Vector2D(rocket.getPosition().x, rocket.getPosition().y) ;
        trackerCrumbs.add(new TrackerCrumb(last));
    }

    public void draw(Graphics2D g2, Vector2D currentPosition) {
        if (Vector2D.subtract(currentPosition, last).getMagnitude() >= DISTANCE_BETWEEN_CRUMBS) {
            if (trackerCrumbs.size() > MAX_CRUMBS) trackerCrumbs.remove();
            last = new Vector2D(currentPosition.x, currentPosition.y);
            trackerCrumbs.add(new TrackerCrumb(currentPosition));
        }

        for (TrackerCrumb tc : trackerCrumbs) {
            tc.draw(g2);
        }

    }
}
