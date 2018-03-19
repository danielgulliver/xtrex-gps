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
