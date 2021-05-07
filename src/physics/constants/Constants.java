package physics.constants;

/*
* Some general physics constants, while normally magic numbers
* should be avoided, these are the actual values required to
* create a 25 m/s^2 gravity on a 200m radius object in real life.
* In particular, G is the real world gravitational constant.
* */

public abstract class Constants {
    public static final double G = 6.67430e-11;
    // EARTH VALUES CANNOT BE CHANGED WITHOUT CHANGING ALL OF THEM
    // THE INVOLVED CALCULATIONS ARE TOO EXPENSIVE FOR RUNTIME, THIS
    // MUST BE MANUALLY ALTERED IN THE CONSTANTS
    public static final float EARTH_MASS = 14982844642883898L;
    public static final int EARTH_RADIUS = 200;
}
