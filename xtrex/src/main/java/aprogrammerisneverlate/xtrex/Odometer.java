package aprogrammerisneverlate.xtrex;

/**
 * Contains functions for calculating the distance travelled, the current speed of travel, and the total moving time of the XTrex device.
 * 
 * @author Daniel Gulliver
 */
public class Odometer {

    private GPSparser gps = GPSparser.getInstance();

    private static float distanceTravelled;
    private static float currentSpeed;
    private static float movingTime;

    /**
     * Calculate the distance travelled by the XTrex device since booting.
     */
    public static void calculateDistanceTravelled() {
        // TODO: Calculate distance travelled
    }

    /**
     * Calculate the current speed at which the XTrex device is travelling.
     */
    public static void calculateCurrentSpeed() {
        // TODO: Calculate current speed
    }

    /**
     * Calculate the amount of time for which the XTrex device has been moving since booting.
     */
    public static void calculateMovingTime() {
        // TODO: Calculate moving time
    }

    /**
     * Return the distance travelled by the XTrex device since booting.
     * @return the distance travelled by the XTrex device
     */
    public static float getDistanceTravelled() {
        return distanceTravelled;
    }

    /**
     * Return the current speed at which the XTrex device is travelling.
     * @return the current speed of the XTrex device
     */
    public static float getCurrentSpeed() {
        return currentSpeed;
    }

    /**
     * Return the amount of time for which the XTrex device has been moving since booting.
     * @return the moving time of the XTrex device
     */
    public static float getMovingTime() {
        return movingTime;
    }
}