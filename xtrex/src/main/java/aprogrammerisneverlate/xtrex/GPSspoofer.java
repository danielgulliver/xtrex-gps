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
    static int aGPS = rand.nextInt(5) +1;
    static int nGPS = rand.nextInt(36) +1;
    static float gpsTime = Float.parseFloat((LocalTime.now()).toString().replaceAll(":",""));
    static float latitude = 50.73f + rand.nextFloat()/100;
    static float longitude = -3.53f + rand.nextFloat()/100;
    static float altitude = 120.0f + rand.nextFloat()/500;
    static float velocity = 0.0f + rand.nextFloat();
    static float trueTrackAngle = rand.nextFloat()*360;


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
}
