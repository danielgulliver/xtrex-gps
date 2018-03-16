package teamk.xtrex;

/**
 * Contains functions for calculating the distance travelled, the current speed of travel, and the total moving time of the XTrex device.
 * 
 * @author Daniel Gulliver
 */
public class Odometer {
    private static double distanceTravelled = 0.0;
    private static double currentSpeed = 0.0;
    private static double movingTime = 0.0;

    private static GPSparser gps = GPSparser.getInstance();
    private static double prevLat = gps.Latitude();
    private static double prevLong = gps.Longitude();

    private static final double startTime = System.currentTimeMillis();

    /**
     * Calculate the distance travelled by the GPS device since the last GPS poll. This function is to be called after each GPS poll.
     * @return the distance travelled by the GPS device since the last GPS poll
     */
    private static double distanceTravelledInTimeSlice() {
        double currLat = gps.Latitude();
        double currLong = gps.Longitude();

        final double R = 6371E3F;
        double phi1 = Math.toRadians(prevLat);
        double phi2 = Math.toRadians(currLat);
        double delta_phi = Math.toRadians(prevLat - currLat);
        double delta_lambda = Math.toRadians(prevLong - currLong);

        // Haversine formula for calculating the distance between two points on a sphere given their latitude and longitude.
        double a = Math.sin(delta_phi / 2) * Math.sin(delta_phi / 2) +
                   Math.cos(phi1) * Math.cos(phi2) *
                   Math.sin(delta_lambda / 2) * Math.sin(delta_lambda / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c;
        return d;
    }

    /**
     * Increase the distance travelled by the amount moved in the last time slice.
     */
    public static void setDistanceTravelled() {
        distanceTravelled += distanceTravelledInTimeSlice();
    }

    /**
     * Calculate the current speed at which the XTrex device is travelling.
     * Must be called before <code>getCurrentSpeed()</code> in order to return the latest value.
     */
    public static void setCurrentSpeed() {
        currentSpeed = gps.Velocity();
    }

    /**
     * Calculate the amount of time for which the XTrex device has been moving since booting in milliseconds.
     * Must be called before <code>getMovingTime()</code> in order to return the latest value.
     */
    public static void setMovingTime() {
        movingTime = System.currentTimeMillis() - startTime;
    }

    /**
     * Return the distance travelled by the XTrex device since booting.
     * Remember to call <code>calculateDistanceTravelled()</code> first to get the latest value.
     * @return the distance travelled by the XTrex device
     */
    public static double getDistanceTravelled() {
        return distanceTravelled;
    }

    /**
     * Return the current speed at which the XTrex device is travelling.
     * Remember to call <code>calculateCurrentSpeed()</code> first to get the latest value.
     * @return the current speed of the XTrex device
     */
    public static double getCurrentSpeed() {
        return currentSpeed;
    }

    /**
     * Return the amount of time for which the XTrex device has been moving since booting.
     * Remember to call <code>calculateMovingTime()</code> first to get the latest value.
     * @return the moving time of the XTrex device
     */
    public static double getMovingTime() {
        return movingTime;
    }

    /**
     * Update the status of the odometer by calculating new values for the distance travelled, moving time, and current speed.
     */
    public static void update() {
        setDistanceTravelled();
        setMovingTime();
        setCurrentSpeed();
    }

    public static void main(String[] args) {
        System.out.println("Distance travelled: " + getDistanceTravelled());
        setDistanceTravelled();
        System.out.println("Distance travelled: " + getDistanceTravelled());
    }
}