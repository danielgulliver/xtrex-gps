package aprogrammerisneverlate.xtrex;

import java.time.LocalTime;

public class GPSparser implements Runnable {  
    final static String gLLpre = "$GPGLL,";
    final static String positionPre = "$GPGGA,";
    final static String velocityPre = "$GPRMC,";
    final static String gSVpre = "$GPGSV,";
    final static float convertRate = 1.852f; // Convertion factor for Knots to Km/h
    private static LocalTime localTime;
    private static GPSparser GPS = null;
    private static String OS = null;
    static FileWritter logs = new FileWritter();
    static int aGPS = 0;
    static int nGPS = 0;
    static float gPStime = 0.0f;
    static float latitude = 50.737730f;
    static float longitude = -3.532626f;
    static float altitude = 0.0f;
    static float velocity = 0.0f;
    static float trueTrackAngle = 0.0f;

    private GPSparser(){}
    
    /**
	 * Return the single instance of GPSparser held by this class.
	 * @return the single instance of GPSparser
	 */
	public static GPSparser getInstance() {
		if (GPS == null)
			GPS = new GPSparser();
		return GPS;
	}

    public void Start() {
        logs.Logging(true);
        if(OS == null) { OS = System.getProperty("os.name"); }
        System.out.println(OS);
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
        logs.Logging(false);
    }

    public int Tracking() {
        return aGPS;
    }

    public float Latitude() {
        return latitude;
    }

    public float Longitude() {
        return longitude;
    }

    public float GPStime() {
        return gPStime;
    }

    private float SexagesimalToDecimal( String coordinate ) {
        String[] hourMinuteSecond = coordinate.split(".");
        float hour = Integer.parseInt(hourMinuteSecond[0].substring(0, lhourMinuteSecond[0])-2));
        float minute = Integer.parseInt(hourMinuteSecond[0].substring(length(hourMinuteSecond[0])-2, length(hourMinuteSecond[0]))/60;
        float second = Integer.parseInt(hourMinuteSecond[0])/3600;

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

        if ( input.contains(gSVpre) ) {
            noPreSat = input.substring(input.indexOf(gSVpre) + gSVpre.length());
            tokenSat = noPreSat.split(",");
            nGSV = Integer.parseInt(tokenSat[0]);
            localTime = LocalTime.now();
            logs.Logger("-- Number of GSV messages: " + tokenSat[0] + "  --" );
            79
            System.out.println("-- Number of GSV messages: " + tokenSat[0] + "  --");
        }
        
        if ( input.contains(positionPre) ) {
          noPre = input.substring(input.indexOf(positionPre) + positionPre.length());
          tokens = noPre.split(",");
          if (tokens.length >= 6) {
            if ( Integer.parseInt(tokens[5]) == 0 /* || tokens[1].length() == 0 */ ){ 
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
            noPreV = input.substring(input.indexOf(velocityPre) + velocityPre.length());
            tokenV = noPreV.split(",");
            if (tokenV[1].contains("A")) {
                if (tokenV[6].length() > 0) {
                    velocity = convertRate * Float.parseFloat(tokenV[6]);
                    logs.Logger( "    Velocity: " + Float.toString(velocity) );
                }
                if (tokenV[7].length() > 0) {
                    trueTrackAngle = Float.parseFloat(tokenV[7]);
                    logs.Logger( "    Velocity: " + Float.toString(trueTrackAngle) );
                }
            }

        }
    }

	public void run() {
		
	}
}