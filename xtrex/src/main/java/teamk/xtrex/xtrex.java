package teamk.xtrex;
/*
* Win7 Ublox7 reader.
*/   
public class xtrex {
    public static Boolean gpsEnabled = true;
    private static Thread gpsThread = null;
    public static String gpsDevice = "";
    public static String os = GPSutil.getInstance().getOS();


    public static void main( String[] argv ) {
        
        if (argv.length > 0){
            gpsDevice = argv[0];
        } else { 
            if (os.startsWith("Windows")) {
                gpsDevice = "COM6";
            } else if (os.startsWith("Linux")) {
                gpsDevice = "/dev/ttyACM0";
            } else if (os.startsWith("Mac")) {
                gpsDevice = "/dev/cu.usbmodem1421";
            }
        }
        
        XTrexDisplay disp = XTrexDisplay.getInstance();
        disp.setScreen(MainMenu.getInstance());

        GPSparser GPS = GPSparser.getInstance();
        xtrex.gpsThread = new Thread(GPS, "GPS thread");

        // Start threads.
        new Thread(UpdateThread.getInstance()).start();
        xtrex.gpsThread.run();
    }

	public static Thread getGpsThread() {
        return gpsThread;
	}

}