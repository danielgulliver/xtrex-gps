package teamk.xtrex;

import java.time.LocalTime;
import java.io.File;
import java.lang.Math;

/**
 * Selects the version of Ublox 7 appropriate for the Clients OS
 * Parses GPS input to variables and output log.
 * 
 * @author Connor Harris
 * @version Sprint 3 
 */

public class GPSparser implements Runnable {  
    
    final String GLL_PRE = "$GPGLL,";
    final String POSITION_PRE = "$GPGGA,";
    final String VELOCITY_PRE = "$GPRMC,";
    final String GSV_PRE = "$GPGSV,";
    final float CONVERT_RATE = 1.852f; // Convertion factor for Knots to Km/h
    private LocalTime localTime;
    private static Boolean gpsEnabled = false;
    private GPSspoofer spoof = GPSspoofer.getInstance();
    private StatusPane status;
    static LogWriter logs = new LogWriter();
    private Boolean gpsLost = true;
    private Boolean gpsAquired = false;
    private int gpsTimeOut = 5;
    private int aGPS = 0;
    private int nGPS = 0;
    private float gPStime = 0.0f;
    private double latitude = 50.737730d;
    private double longitude = -3.532626d;
    private float altitude = 0.0f;
    private float velocity = 0.0f;
    private float trueTrackAngle = 0.0f;

    private GPSparser(){
        this.status = XTrexDisplay.getInstance().getXTrexFrame().getStatusPane();
    }
    
    /**
	 * Return the single instance of GPSparser held by this class
     * in a thread safe manner.
     * Loader Class ensures that a thread safe singleton pattern is adheared to.
	 * @return the single instance of GPSparser
	 */
    private static class Loader {
        static final GPSparser instance = new GPSparser();
    }
    public static GPSparser getInstance() {
        return Loader.instance;
    }
    
    /**
	 * Starts the Logging and selects the apropriate version of Ublox7
	 */
    public void Start() {
        logs.Logging(true, "log.txt");
        System.out.println(xtrex.os + "  GPS STARTED");
        logs.Logger(xtrex.os);
        if (gpsEnabled == true) {
            if (xtrex.os.startsWith("Windows")) {
                System.err.close();
                Win7Ublox7 Ublox = new Win7Ublox7();
                // Ublox.listPorts();
                System.out.println("\nStarting GPS Read \n");
                Ublox.reader(xtrex.gpsDevice);
            } else if (xtrex.os.startsWith("Linux")) {
                LinuxUblox7 Ublox = new LinuxUblox7();
                System.out.println("\nStarting GPS Read \n");
                Ublox.reader(xtrex.gpsDevice);
            } else if (xtrex.os.startsWith("Mac")) {
                OSXUblox7 Ublox = new OSXUblox7();
                System.out.println("\nStarting GPS Read \n");
                Ublox.reader(xtrex.gpsDevice);
            }      
        } 
        else {
            System.out.println("Demo Mode Active - spoofing GPS reading");
            logs.Logger("Demo Mode Active - spoofing GPS reading");
        }
        logs.Logging(false, "");
    }

    /**
	 * Returns the tracking acuracy provided by the gps receiver
	 * @return GPS track accuracy
	 */
    public int Tracking() {
        if (gpsEnabled == true) return aGPS;
        else return spoof.aGPS;
    }

    /**
	 * Returns the number of satellites in view provided by the gps receiver
	 * @return number of GPS satellites
	 */
    public int numSatellites() {
        if (gpsEnabled == true) return nGPS;
        else return spoof.nGPS;
    }

    /**
	 * Returns the latitude provided by the gps receiver
	 * @return latitude
	 */
    public double Latitude() {
        if (gpsEnabled == true) return latitude;
        else return spoof.latitude;
    }

    /**
	 * Returns the longitude provided by the gps receiver
	 * @return longitude
	 */
    public double Longitude() {
        if (gpsEnabled == true) return longitude;
        else return spoof.longitude;
    }

    /**
	 * Returns the current Time provided by the gps receiver
	 * @return GPS time
	 */
    public float GPStime() {
        if (gpsEnabled == true) return gPStime;
        else return spoof.gpsTime;
    }
    
    /**
	 * Returns the altitude provided by the gps receiver
	 * @return GPS altitude
	 */
    public float Altitude() {
        if (gpsEnabled == true) return altitude;
        else return spoof.altitude;
    }

    /**
	 * Returns the velocity provided by the gps receiver
	 * @return GPS velocity
	 */
    public float Velocity() {
        if (gpsEnabled == true) return velocity;
        else return spoof.velocity;
    }

    /**
	 * Returns the heading from true north provided by the gps receiver
	 * @return GPS Heading
	 */
    public float TrueTrackAngle() {
        if (gpsEnabled == true) return trueTrackAngle;
        else return spoof.trueTrackAngle;
    }


    /**
	 * Converts the NMEA unique lat/long format to Decimal Degrees
     * taken by the google maps API
	 * @return Decimal Degrees
	 */
    public double SexagesimalToDecimal( String coordinate ) {
        String[] hourMinuteSecond = coordinate.split("[.]");
        double hour = Integer.parseInt(hourMinuteSecond[0].substring(0, hourMinuteSecond[0].length()-2));
        double minute = Integer.parseInt(hourMinuteSecond[0].substring(hourMinuteSecond[0].length()-2, hourMinuteSecond[0].length()))/60.0d;
        double divMod = Math.pow(10, hourMinuteSecond[1].length());
        double second = (Integer.parseInt(hourMinuteSecond[1])*60)/divMod/3600.0d; // connverts from decimal time to seconds then convets to deciamal degrees.
        return hour+minute+second;
    } 

    /**
	 * Processes the NMEA Strings to Variables and the Log file.
	 */
    public void processGPS( String input ) {
        String noPre;
        String noPreV;
        String noPreSat;
        String[] tokens;
        String[] tokenV;
        String[] tokenSat;
        int nGSV;
        localTime = LocalTime.now();
        float lTime = Float.parseFloat(localTime.toString().replaceAll("[:]", ""));

		if (lTime - gPStime > gpsTimeOut && gpsLost == false){
            gpsLost = true;
            gpsAquired = false;
            status.satelliteAvailable(false);
            Speech.playAudioNotification(Speech.NotificationsEnum.GPSConnectionLost);
        }
        if (gpsLost == false && gpsAquired == false){
            gpsAquired = true;
            status.satelliteAvailable(true);
            Speech.playAudioNotification(Speech.NotificationsEnum.GPSAcquired);
        }
        

        if ( input.contains(GSV_PRE) ) {
            noPreSat = input.substring(input.indexOf(GSV_PRE) + GSV_PRE.length());
            tokenSat = noPreSat.split(",");
            nGSV = Integer.parseInt(tokenSat[0]);
            if (Integer.parseInt(tokenSat[1])  == 1){
                logs.Logger("-- Number of GSV messages: " + tokenSat[0] + "   Number of Satalites in view: " + tokenSat[2] + "  --" );
            // System.out.println("-- Number of GSV messages: " + tokenSat[0] + "  --");
            }
        }
        
        if ( input.contains(POSITION_PRE) ) {
          noPre = input.substring(input.indexOf(POSITION_PRE) + POSITION_PRE.length());
          tokens = noPre.split(",");
          if (tokens.length >= 5 ){
            if ( /* Integer.parseInt(tokens[5]) == 0 || */ tokens[1].length() == 0 ){ 
                aGPS = Integer.parseInt(tokens[5]);
                logs.Logger( "--  NO GPS ACQUIRED  --" + "  at time: " + localTime );
            } else { 
                gpsLost = false;
                gPStime = Float.parseFloat(tokens[0]);
                System.out.println("-----   GPS GGA ACQUIRED " + aGPS + "   -----");
                logs.Logger("GPS GGA LOCATION: ");
                logs.Logger( "    GPS aquired at: " + tokens[0] );
                if (tokens[1].length() > 0 && tokens[3].length() > 0){            
                    if ( tokens[2].contains("N") ){ 
                        latitude = SexagesimalToDecimal(tokens[1]);
                        logs.Logger( "    Latitude: " + Double.toString(latitude) );
                    } else if ( tokens[2].contains("S") ){
                        latitude = -(SexagesimalToDecimal(tokens[1]));
                        logs.Logger( "    Latitude: " + Double.toString(latitude) );
                    }
                    if ( tokens[4].contains("E") ){ 
                        longitude = SexagesimalToDecimal(tokens[3]);
                        logs.Logger( "    Longitude: " + Double.toString(longitude) );
                    } else if ( tokens[4].contains("W") ){ 
                        longitude = -(SexagesimalToDecimal(tokens[3]));
                        logs.Logger( "    Longitude: " + Double.toString(longitude) );
                    }
                }
                if (tokens.length >= 8 ){
                    if (tokens[5].length() > 0) {
                        aGPS = Integer.parseInt(tokens[5]);
                    }
                    if (tokens[6].length() > 0) {
                        nGPS = Integer.parseInt(tokens[6]);
                    }
                    if (tokens[7].length() > 0) {
                        altitude = Float.parseFloat(tokens[7]);
                        logs.Logger( "    Altitude: " + Float.toString(altitude) );
                    }
                }
                // synchronized(UpdateThread.getInstance()){
                //     UpdateThread.getInstance().notify(); // Notifys the update thread new Data is availiable 
                // }
            }
          }
        }
        if ( input.contains(VELOCITY_PRE) ) {
            noPreV = input.substring(input.indexOf(VELOCITY_PRE) + VELOCITY_PRE.length());
            tokenV = noPreV.split(",");
            if ( tokenV.length >= 8 && tokenV[1].contains("A") ){
                gpsLost = false;
                gPStime = Float.parseFloat(tokenV[0]);
                System.out.println("-----   GPS RMC ACQUIRED " + aGPS + "   -----");
                logs.Logger("GPS RMC LOCATION: ");
                logs.Logger( "    GPS aquired at: " + tokenV[0] );
                            
                if ( tokenV[3].contains("N") ){ 
                    latitude = SexagesimalToDecimal(tokenV[2]);
                    logs.Logger( "    Latitude: " + Double.toString(latitude) );
                } else if ( tokenV[3].contains("S") ){
                    latitude = -(SexagesimalToDecimal(tokenV[2]));
                    logs.Logger( "    Latitude: " + Double.toString(latitude) );
                }
                if ( tokenV[5].contains("E") ){ 
                    longitude = SexagesimalToDecimal(tokenV[4]);
                    logs.Logger( "    Longitude: " + Double.toString(longitude) );
                } else if ( tokenV[5].contains("W") ){ 
                    longitude = -(SexagesimalToDecimal(tokenV[4]));
                    logs.Logger( "    Longitude: " + Double.toString(longitude) );
                }
                if (tokenV[6].length() > 0) {
                    velocity = CONVERT_RATE * Float.parseFloat(tokenV[6]);
                    logs.Logger( "    Velocity: " + Float.toString(velocity) );
                }
                if (tokenV[7].length() > 0) {
                    trueTrackAngle = Float.parseFloat(tokenV[7]);
                    logs.Logger( "    Track Angle: " + Float.toString(trueTrackAngle) );
                }

                synchronized(UpdateThread.getInstance()){
                    UpdateThread.getInstance().notify(); // Notifys the update thread new Data is availiable 
                }
            }
        }
    }
    
    public void run() {
        gpsEnabled = xtrex.gpsEnabled;
        Start();
    }
}