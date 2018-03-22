package teamk.xtrex;

/**
 * Contains functions for calculating the distance travelled, the current speed of travel, and the total moving time of the XTrex device.
 * 
 * @author Daniel Gulliver
 */
public class TripComputerModel {

    private static TripComputerModel tripComputerModel = null;

    private double distanceTravelled = 0.0;
    private double currentSpeed = 0.0;
    private long movingTime = 0;

    private GPSparser gps = GPSparser.getInstance();
    private double prevLat = 0.0D;
    private double prevLong;

    private long startTime = System.currentTimeMillis();

    private TripComputerModel() {

    }

    /**
     * Return the single instance of TripComputerModel held by this class.
     * @return the single instance of TripComputerModel held by this class
     */
    public static TripComputerModel getInstance() {
        if (tripComputerModel == null) {
            tripComputerModel = new TripComputerModel();
        }
        return tripComputerModel;
    }

    /**
     * Calculate the distance travelled by the GPS device since the last GPS poll. This function is to be called after each GPS poll.
     * @return the distance travelled by the GPS device since the last GPS poll
     * 
     * @author Connor Harris
     *          Fixed it - now updated bassed on GPS data properly
     */
    private int distanceTravelledInTimeSlice() {
        double currLat = gps.Latitude();
        double currLong = gps.Longitude();
        if (prevLat == 0.0D){
            prevLat = currLat;
            prevLong = currLong; 
        }
        int dist = GPSutil.latLongToDistance(prevLat, prevLong, currLat, currLong);
        prevLat = currLat;
        prevLong = currLong;

        return dist;
    }

    /**
     * Increase the distance travelled by the amount moved in the last time slice.
     */
    public void setDistanceTravelled() {
        distanceTravelled += distanceTravelledInTimeSlice();
    }

    /**
     * Calculate the current speed at which the XTrex device is travelling.
     * Must be called before <code>getCurrentSpeed()</code> in order to return the latest value.
     */
    public void setCurrentSpeed() {
        currentSpeed = gps.Velocity();
    }

    /**
     * Calculate the amount of time for which the XTrex device has been moving since booting in milliseconds.
     * Must be called before <code>getMovingTime()</code> in order to return the latest value.
     */
    public void setMovingTime() {
        movingTime = System.currentTimeMillis() - startTime;
    }

    /**
     * Return the distance travelled by the XTrex device since booting.
     * Remember to call <code>calculateDistanceTravelled()</code> first to get the latest value.
     * @return the distance travelled by the XTrex device
     */
    public int getDistanceTravelled() {
        return (int) distanceTravelled;
    }

    /**
     * Return the current speed at which the XTrex device is travelling.
     * Remember to call <code>calculateCurrentSpeed()</code> first to get the latest value.
     * @return the current speed of the XTrex device
     */
    public double getCurrentSpeed() {
        return currentSpeed;
    }

    /**
     * Return the amount of time for which the XTrex device has been moving since booting.
     * Remember to call <code>calculateMovingTime()</code> first to get the latest value.
     * @return the moving time of the XTrex device
     */
    public long getMovingTime() {
        return movingTime;
    }

    /**
     * Reset the state of the trip computer back to the default.
     */
    public void resetTripComputer() {
        this.distanceTravelled = 0.0;
        this.currentSpeed = 0.0;
        this.movingTime = 0;
        this.startTime = System.currentTimeMillis();
    }

}