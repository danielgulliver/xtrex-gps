package aprogrammerisneverlate.xtrex;

/**
 * Contains functions for calculating the distance travelled, the current speed of travel, and the total moving time of the XTrex device.
 * 
 * @author Daniel Gulliver
 */
public class Odometer {

    private GPSparser gps = GPSparser.getInstance(false);

    private static float distanceTravelled;
    private static float currentSpeed;
    private static float movingTime;

    /**
     * Calculate the distance travelled by the XTrex device since booting.
     * Must be called before <code>getDistanceTravelled()</code> in order to return the latest value.
     */
    public static void calculateDistanceTravelled() {
        // TODO: Calculate distance travelled
    }

    /**
     * Calculate the current speed at which the XTrex device is travelling.
     * Must be called before <code>getCurrentSpeed()</code> in order to return the latest value.
     */
    public static void calculateCurrentSpeed() {
        // TODO: Calculate current speed
    }

    /**
     * Calculate the amount of time for which the XTrex device has been moving since booting.
     * Must be called before <code>getMovingTime()</code> in order to return the latest value.
     */
    public static void calculateMovingTime() {
        // TODO: Calculate moving time
    }

    /**
     * Return the distance travelled by the XTrex device since booting.
     * Remember to call <code>calculateDistanceTravelled()</code> first to get the latest value.
     * @return the distance travelled by the XTrex device
     */
    public static float getDistanceTravelled() {
        return distanceTravelled;
    }

    /**
     * Return the current speed at which the XTrex device is travelling.
     * Remember to call <code>calculateCurrentSpeed()</code> first to get the latest value.
     * @return the current speed of the XTrex device
     */
    public static float getCurrentSpeed() {
        return currentSpeed;
    }

    /**
     * Return the amount of time for which the XTrex device has been moving since booting.
     * Remember to call <code>calculateMovingTime()</code> first to get the latest value.
     * @return the moving time of the XTrex device
     */
    public static float getMovingTime() {
        return movingTime;
    }
}