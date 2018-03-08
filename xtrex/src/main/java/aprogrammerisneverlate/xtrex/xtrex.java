package aprogrammerisneverlate.xtrex;
/*
* Win7 Ublox7 reader.
*/   
public class xtrex {
    public static Boolean gpsEnabled = false;
    private static Thread gpsThread = null;


    public static void main( String[] argv ) {
        GPSparser GPS = GPSparser.getInstance();
        xtrex.gpsThread = new Thread(GPS, "GPS thread");
        xtrex.gpsThread.run();
        XTrexDisplay disp = XTrexDisplay.getInstance();
        disp.setScreen(MainMenu.getInstance());

        // Start threads.
        // TODO: Create and start GPS thread.
        gpsThread.start();
        new Thread(UpdateThread.getInstance()).start();
    }

	public static Thread getGpsThread() {
        return gpsThread;
	}

}