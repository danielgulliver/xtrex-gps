package teamk.xtrex;
/*
* Win7 Ublox7 reader.
*/   
public class xtrex {
    public static Boolean gpsEnabled;
    private static Thread gpsThread = null;
    public static String gpsDevice = "";
    public static String os = GPSutil.getInstance().getOS();


    public static void main( String[] argv ) {
        if (argv.length > 0){
            if (argv[0] == "false"){
                gpsEnabled = false;
            } else {
                gpsEnabled = true;
            }
        } else { gpsEnabled = true; }

        if (argv.length > 1){
            gpsDevice = argv[1];
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
        UpdateThread.startThread();
        xtrex.gpsThread.run();
    }

	public static Thread getGpsThread() {
        return gpsThread;
	}

}