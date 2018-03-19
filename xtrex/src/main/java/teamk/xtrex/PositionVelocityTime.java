package teamk.xtrex;

/**
 * Creates a PVT object to allow historic tracking
 * 
 * @author Connor Harris
 * @version Sprint 3 
 */

public class PositionVelocityTime {
    public float gpsTime;
    public double latitude;
    public double longitude;

    public PositionVelocityTime(float gpsTime, double latitude, double longitude){
        this.gpsTime = gpsTime;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}