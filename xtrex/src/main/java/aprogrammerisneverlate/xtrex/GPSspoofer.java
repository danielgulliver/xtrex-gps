package aprogrammerisneverlate.xtrex;

import java.time.LocalTime;
import java.util.Random;

/**
 * Spoofs GPS input for testing application 
 * without the Ublox 7 gps reciver
 * 
 * @author Connor Harris
 * @version Sprint 2 
 */

public class GPSspoofer {
    private static Random rand = new Random();
    private Boolean spoofing = true;
    int aGPS = rand.nextInt(5) +1;
    int nGPS = rand.nextInt(36) +1;
    float gpsTime = Float.parseFloat((LocalTime.now()).toString().replaceAll(":",""));
    float latitude = 50.73f + rand.nextFloat()/100;
    float longitude = -3.53f + rand.nextFloat()/100;
    float altitude = 120.0f + rand.nextFloat()/500;
    float velocity = 0.0f + rand.nextFloat();
    float trueTrackAngle = rand.nextFloat()*360;


    private GPSspoofer(){}
    /**
	 * Return the single instance of GPSparser held by this class
     * in a thread safe manner.
	 * @return the single instance of GPSparser
	 */
    private static class Loader {
        static final GPSspoofer instance = new GPSspoofer();
    }
	public static GPSspoofer getInstance() {
        return Loader.instance;
    }     
    public void update(){
        aGPS = rand.nextInt(5) +1;
        nGPS = rand.nextInt(36) +1;
        gpsTime = Float.parseFloat((LocalTime.now()).toString().replaceAll(":",""));
        latitude = 50.73f + rand.nextFloat()/100;
        longitude = -3.53f + rand.nextFloat()/100;
        altitude = 120.0f + rand.nextFloat()/500;
        velocity = 0.0f + rand.nextFloat();
        trueTrackAngle = rand.nextFloat()*360;
    }
}
