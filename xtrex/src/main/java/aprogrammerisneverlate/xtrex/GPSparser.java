package aprogrammerisneverlate.xtrex;

import java.time.LocalTime;

public class GPSparser {  
    final static String GLLpre = "$GPGLL,";
    final static String GSVpre = "$GPGSV,";
    private static LocalTime localTime;
    private static GPSparser GPS = null;
    private static String OS = null;
    static FileWritter Logs = new FileWritter();
    static boolean aGPS = false;
    static float GPStime = 0.0f;
    static float latitude = 50.737730f;
    static float longitude = -3.532626f;

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
        Logs.Logging(true);
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
        Logs.Logging(false);
    }

    public boolean Tracking() {
        return aGPS;
    }

    public float Latitude() {
        return latitude;
    }

    public float Longitude() {
        return longitude;
    }

    public float GPStime() {
        return GPStime;
    }

    public static void processGPS( String input ) {
        String noPre;
        String noPreSat;
        String[] tokens;
        String[] tokenSat;
        int nGSV;

        if ( input.contains(GSVpre) ) {
            noPreSat = input.substring(input.indexOf(GSVpre) + GSVpre.length());
            tokenSat = noPreSat.split(",");
            nGSV = Integer.parseInt(tokenSat[0]);
            localTime = LocalTime.now();
            Logs.Logger("-- Number of GSV messages: " + tokenSat[0] + "  --" );
            System.out.println("-- Number of GSV messages: " + tokenSat[0] + "  --");
        }
        
        if ( input.contains(GLLpre) ) {
          noPre = input.substring(input.indexOf(GLLpre) + GLLpre.length());
          tokens = noPre.split(",");
          if (tokens.length >= 6) {
            if ( tokens[5].contains("V") && tokens[0].length() == 0 ){ 
                aGPS = false;
                Logs.Logger( "--  NO GPS ACQUIRED  --" + "  at time: " + localTime );
            } else { 
                GPStime = Float.parseFloat(tokens[4]);
                aGPS = true;
                System.out.println("-----   GPS ACQUIRED   -----");
                Logs.Logger("GPS LOCATION: ");
                Logs.Logger( "    GPS aquired at: " + tokens[4]  );
                            
                if ( tokens[1].contains("N") || tokens[1].contains("S") ){ 
                    latitude = Float.parseFloat(tokens[0])/100;
                    Logs.Logger( "    Latitude: " + Float.toString(latitude) );
                }
                if ( tokens[3].contains("E") || tokens[3].contains("W") ){ 
                    longitude = Float.parseFloat(tokens[2])/100;
                    Logs.Logger( "    Longitude: " + Float.toString(longitude) );
                }
            }
          }
        }
    }
}