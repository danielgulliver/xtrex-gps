package teamk.xtrex;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Creates a PVT log to allow historic tracking
 * 
 * @author Connor Harris
 * @version Sprint 3 
 */


public class GPSutil {
    private ReentrantLock lock;
    private GPSparser gps =  GPSparser.getInstance();
    private ArrayList<PositionVelocityTime> log = new ArrayList<PositionVelocityTime>();
    String operatingSystem = null;    
    double latitude = 0.0d;
    double longitude= 0.0d;
    float gpsTime = 0;

    private GPSutil(){
        this.lock = new ReentrantLock();
    }
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
    
    public String getOS() {
        if(operatingSystem == null) { 
            operatingSystem = System.getProperty("os.name"); 
        }
        return operatingSystem;
    }

    public void update(){
        latitude = gps.Latitude();
        longitude = gps.Longitude();
        gpsTime = gps.GPStime();
        lock.lock();
        log.add(new PositionVelocityTime(gpsTime, latitude, longitude));
        if (log.size() > 200){
            log.remove(log.size() - 1);
        }
        lock.unlock();
    }

    /**
     * Given target coordinates, claculates if the device is apraoching the target
     * over the previous data points
     * 
     * @param double latitude -- latitude of target
     * @param double longitude -- longitude of target
     * 
     * @return true if getting closser, false if not.
     * 
     * @author Connor Harris
     */
    public Boolean approaching(double latitude, double longitude) {
        ArrayList<Integer> distLog = new ArrayList<Integer>();
        int count = 0;
        
        lock.lock();
        for (int i = 0; i < log.size(); i++ ){
            distLog.add( latLongToDistance( log.get(i).latitude, log.get(i).longitude, latitude, longitude) );
        }
        lock.unlock();
        if ( distLog.size() > 10 ) {
            for (int i = 1; i < distLog.size(); i++ ){
                if ((i-1 - i) < -10 && (i-1 - i) > 10 ){
                    count -= 1; 
                }
                else { count += 1; }
            }
        } else { count = 1; }

        if (count > 0){ return true; }
        else { return false; }
    }

    /**
     * Given the coordinates of two points on the globe, specified in terms of latitude and longitude, calculate the
     * distance between them using the Haversine formula.
     * 
     * @param double lat1 -- latitude of first point
     * @param double long1 -- longitude of first point
     * @param double lat2 -- latitude of second point
     * @param double long2 -- longitude of second point
     * 
     * @return the distance, in metres, between the two points
     * 
     * @author Daniel Gulliver
     */
    public static int latLongToDistance(double lat1, double long1, double lat2, double long2) {
        final double RADIUS_OF_EARTH = 6371E3D;
        double phi1 = Math.toRadians(lat1);
        double phi2 = Math.toRadians(lat2);
        double delta_phi = Math.toRadians(lat1 - lat2);
        double delta_lambda = Math.toRadians(long1 - long2);

        // Haversine formula for calculating the distance between two points on a sphere given their latitude and
        // longitude.
        double a = Math.sin(delta_phi / 2) * Math.sin(delta_phi / 2) +
                   Math.cos(phi1) * Math.cos(phi2) *
                   Math.sin(delta_lambda / 2) * Math.sin(delta_lambda / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = RADIUS_OF_EARTH * c;

        return (int) Math.round(d);
    }

    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
    
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

}
