package aprogrammerisneverlate.xtrex;

import java.time.LocalTime;

/**
 * Selects the version of Ublox 7 appropriate for the Clients OS
 * Parses GPS input to variables and output log.
 * 
 * @author Connor Harris
 * @version Sprint 2 
 */

public class GPSparser implements Runnable {  
    
    final static String gLLpre = "$GPGLL,";
    final static String positionPre = "$GPGGA,";
    final static String velocityPre = "$GPRMC,";
    final static String gSVpre = "$GPGSV,";
    final static float convertRate = 1.852f; // Convertion factor for Knots to Km/h
    private static LocalTime localTime;
    private static String OS = null;
    private static Boolean gpsEnabled = false;
    private GPSspoofer spoof = GPSspoofer.getInstance();
    static LogWriter logs = new LogWriter();
    private static int aGPS = 0;
    private static int nGPS = 0;
    private static float gPStime = 0.0f;
    private static float latitude = 50.737730f;
    private static float longitude = -3.532626f;
    private static float altitude = 0.0f;
    private static float velocity = 0.0f;
    private static float trueTrackAngle = 0.0f;

    private GPSparser(){}
    
    /**
	 * Return the single instance of GPSparser held by this class
     * in a thread safe manner.
	 * @return the single instance of GPSparser
	 */
    private static class Loader {
        static final GPSparser instance = new GPSparser();
    }
	public static GPSparser getInstance(Boolean gpsEnable) {
        gpsEnabled = gpsEnable;
        return Loader.instance;
	}

    public void Start() {
        logs.Logging(true, "log.txt");
        if(OS == null) { OS = System.getProperty("os.name"); }
        System.out.println(OS);
        logs.Logger(OS);
        if (gpsEnabled == true) {
            if (OS.startsWith("Windows")) {
                Win7Ublox7 Ublox = new Win7Ublox7();
                Ublox.listPorts();
                System.out.println("\nStarting GPS Read \n");
                Ublox.reader("COM6");
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

    public float numSatalites() {
        if (gpsEnabled == true) return nGPS;
        else return spoof.nGPS;
    }

    public float Latitude() {
        if (gpsEnabled == true) return latitude;
        else return spoof.latitude;
    }

    public float Longitude() {
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

    private static float SexagesimalToDecimal( String coordinate ) {
        String[] hourMinuteSecond = coordinate.split(".");
        float hour = Integer.parseInt(hourMinuteSecond[0].substring(0, hourMinuteSecond[0].length()-2));
        float minute = Integer.parseInt(hourMinuteSecond[0].substring(hourMinuteSecond[0].length()-2, hourMinuteSecond[0].length()))/60;
        float second = Integer.parseInt(hourMinuteSecond[1])/3600;

        return hour+minute+second;
    } 

    public static void processGPS( String input ) {
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
            logs.Logger("-- Number of GSV messages: " + tokenSat[0] + "   Number of Satalites in view: " + tokenSat[2] + "  --" );
            // System.out.println("-- Number of GSV messages: " + tokenSat[0] + "  --");
        }
        
        if ( input.contains(positionPre) ) {
          System.out.println(input);
          noPre = input.substring(input.indexOf(positionPre) + positionPre.length());
          tokens = noPre.split(",");
          if (tokens.length >= 8 ){
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
                    logs.Logger( "    Latitude: " + Float.toString(latitude) );
                } else if ( tokens[2].contains("S") ){
                    latitude = -(SexagesimalToDecimal(tokens[1]));
                    logs.Logger( "    Latitude: " + Float.toString(latitude) );
                }
                if ( tokens[4].contains("E") ){ 
                    longitude = SexagesimalToDecimal(tokens[3]);
                    logs.Logger( "    Longitude: " + Float.toString(longitude) );
                } else if ( tokens[4].contains("W") ){ 
                    longitude = -(SexagesimalToDecimal(tokens[3]));
                    logs.Logger( "    Longitude: " + Float.toString(longitude) );
                }
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
          }
        }
        if ( input.contains(velocityPre) ) {
            System.out.println(input);
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
                        logs.Logger( "    Velocity: " + Float.toString(trueTrackAngle) );
                    }
                // }
            }

        }
    }
    public void run() {
        Start();
    }
}