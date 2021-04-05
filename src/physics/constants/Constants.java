package physics.constants;

public abstract class Constants {
    public static final double G = 6.67430e-11;
    // EARTH VALUES CANNOT BE CHANGED WITHOUT CHANGING ALL OF THEM
    // THE INVOLVED CALCULATIONS ARE TOO EXPENSIVE FOR RUNTIME, THIS
    // MUST BE MANUALLY ALTERED IN THE CONSTANTS
    public static final float EARTH_MASS = 14982844642883898L;
    public static final int EARTH_RADIUS = 200;
}
