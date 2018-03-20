package teamk.xtrex;
/*
* Win7 Ublox7 reader.
*/   
public class xtrex {
    public static Boolean gpsEnabled = false;
    private static Thread gpsThread = null;


    public static void main( String[] argv ) {
        GPSparser GPS = GPSparser.getInstance();
        xtrex.gpsThread = new Thread(GPS, "GPS thread");
        
        XTrexDisplay disp = XTrexDisplay.getInstance();
        disp.setScreen(MainMenu.getInstance());

        // Start threads.
        new Thread(UpdateThread.getInstance()).start();
        xtrex.gpsThread.run();
    }

	public static Thread getGpsThread() {
        return gpsThread;
	}

}