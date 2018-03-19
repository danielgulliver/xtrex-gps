package teamk.xtrex;

import java.util.ArrayList;

/**
 * Creates a PVT log to allow historic tracking
 * 
 * @author Connor Harris
 * @version Sprint 3 
 */


public class GPSutil {
    GPSparser gps =  GPSparser.getInstance();
    ArrayList<PositionVelocityTime> log = new ArrayList<PositionVelocityTime>();
    double latitude = 0.0d;
    double longitude= 0.0d;
    float gpsTime = 0;
    
    private GPSutil(){}
    /**
	 * Return the single instance of GPSparser held by this class
     * in a thread safe manner.
	 * @return the single instance of GPSparser
	 */
    private static class Loader {
        static final GPSutil instance = new GPSutil();
    }
	public static GPSutil getInstance() {
        return Loader.instance;
	}

    public void update(){
        latitude = gps.Latitude();
        longitude = gps.Longitude();
        gpsTime = gps.GPStime();
        log.add(new PositionVelocityTime(gpsTime, latitude, longitude));
        if (log.size() < 200){
            log.remove(log.size() - 1);
        }
    }

}
