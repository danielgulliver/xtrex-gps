package aprogrammerisneverlate.xtrex;

import java.time.LocalTime;
import java.lang.Math;

/**
 * Selects the version of Ublox 7 appropriate for the Clients OS
 * Parses GPS input to variables and output log.
 * 
 * @author Connor Harris
 * @version Sprint 2 
 */

public class GPSparser implements Runnable {  
    
    final String gLLpre = "$GPGLL,";
    final String positionPre = "$GPGGA,";
    final String velocityPre = "$GPRMC,";
    final String gSVpre = "$GPGSV,";
    final float convertRate = 1.852f; // Convertion factor for Knots to Km/h
    private LocalTime localTime;
    private String OS = null;
    private static Boolean gpsEnabled = false;
    private GPSspoofer spoof = GPSspoofer.getInstance();
    static LogWriter logs = new LogWriter();
    private int aGPS = 0;
    private int nGPS = 0;
    private float gPStime = 0.0f;
    private double latitude = 50.737730d;
    private double longitude = -3.532626d;
    private float altitude = 0.0f;
    private float velocity = 0.0f;
    private float trueTrackAngle = 0.0f;

    private GPSparser(){}
    
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
        if(OS == null) { OS = System.getProperty("os.name"); }
        System.out.println(OS);
        logs.Logger(OS);
        if (gpsEnabled == true) {
            spoof = null;
            gpsEnabled = true;
            if (OS.startsWith("Windows")) {
                System.err.close();
                Win7Ublox7 Ublox = new Win7Ublox7();
                Ublox.listPorts();
                System.out.println("\nStarting GPS Read \n");
                Ublox.reader("COM3");
            } else if (OS.startsWith("Linux")) {
                LinuxUblox7 Ublox = new LinuxUblox7();
                System.out.println("\nStarting GPS Read \n");
                Ublox.reader("/dev/ttyACM0");
            } else if (OS.startsWith("Mac")) {
                OSXUblox7 Ublox = new OSXUblox7();
                System.out.println("\nStarting GPS Read \n");
                Ublox.reader("/dev/cu.usbmodem1421");
            }      
        } 
        else {
            System.out.println("Demo Mode Active - spoofing GPS reading");
        }
        logs.Logging(false, "");
    }

    public int Tracking() {
        if (gpsEnabled == true) return aGPS;
        else return spoof.aGPS;
    }

    public int numSatalites() {
        if (gpsEnabled == true) return nGPS;
        else return spoof.nGPS;
    }

    public double Latitude() {
        if (gpsEnabled == true) return latitude;
        else return spoof.latitude;
    }

    public double Longitude() {
        if (gpsEnabled == true) return longitude;
        else return spoof.longitude;
    }

    public float GPStime() {
        if (gpsEnabled == true) return gPStime;
        else return spoof.gpsTime;
    }
    
    public float Altitude() {
        if (gpsEnabled == true) return altitude;
        else return spoof.altitude;
    }

    public float Velocity() {
        if (gpsEnabled == true) return velocity;
        else return spoof.velocity;
    }

    public float TrueTrackAngle() {
        if (gpsEnabled == true) return trueTrackAngle;
        else return spoof.trueTrackAngle;
    }

    public double SexagesimalToDecimal( String coordinate ) {
        String[] hourMinuteSecond = coordinate.split("[.]");
        double hour = Integer.parseInt(hourMinuteSecond[0].substring(0, hourMinuteSecond[0].length()-2));
        double minute = Integer.parseInt(hourMinuteSecond[0].substring(hourMinuteSecond[0].length()-2, hourMinuteSecond[0].length()))/60.0d;
        double divMod = Math.pow(10, hourMinuteSecond[1].length());
        double second = (Integer.parseInt(hourMinuteSecond[1])*60)/divMod/3600.0d; // connverts from decimal time to seconds then convets to deciamal degrees.
        return hour+minute+second;
    } 

    public void processGPS( String input ) {
        String noPre;
        String noPreV;
        String noPreSat;
        String[] tokens;
        String[] tokenV;
        String[] tokenSat;
        int nGSV;
        localTime = LocalTime.now();

        if ( input.contains(gSVpre) ) {
            noPreSat = input.substring(input.indexOf(gSVpre) + gSVpre.length());
            tokenSat = noPreSat.split(",");
            nGSV = Integer.parseInt(tokenSat[0]);
            if (Integer.parseInt(tokenSat[1])  == 1){
                logs.Logger("-- Number of GSV messages: " + tokenSat[0] + "   Number of Satalites in view: " + tokenSat[2] + "  --" );
            // System.out.println("-- Number of GSV messages: " + tokenSat[0] + "  --");
            }
        }
        
        if ( input.contains(positionPre) ) {
          noPre = input.substring(input.indexOf(positionPre) + positionPre.length());
          tokens = noPre.split(",");
          if (tokens.length >= 5 ){
            if ( /* Integer.parseInt(tokens[5]) == 0 || */ tokens[1].length() == 0 ){ 
                aGPS = Integer.parseInt(tokens[5]);
                logs.Logger( "--  NO GPS ACQUIRED  --" + "  at time: " + localTime );
            } else { 
                gPStime = Float.parseFloat(tokens[0]);
                System.out.println("-----   GPS ACQUIRED   -----");
                logs.Logger("GPS LOCATION: ");
                logs.Logger( "    GPS aquired at: " + tokens[0]  );
                            
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
                synchronized(this){
                    notify();
                }
            }
          }
        }
        if ( input.contains(velocityPre) ) {
            noPreV = input.substring(input.indexOf(velocityPre) + velocityPre.length());
            tokenV = noPreV.split(",");
            if (tokenV.length >= 8 ){
                //if (tokenV[1].contains("A")) {
                    if (tokenV[6].length() > 0) {
                        velocity = convertRate * Float.parseFloat(tokenV[6]);
                        logs.Logger( "    Velocity: " + Float.toString(velocity) );
                    }
                    if (tokenV[7].length() > 0) {
                        trueTrackAngle = Float.parseFloat(tokenV[7]);
                        logs.Logger( "    Track Angle: " + Float.toString(trueTrackAngle) );
                    }
                // }
            }

        }
    }
    
    public void run() {
        gpsEnabled = xtrex.gpsEnabled;
        Start();
        System.out.println("strart");
    }
}