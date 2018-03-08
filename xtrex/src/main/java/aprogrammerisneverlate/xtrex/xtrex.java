package aprogrammerisneverlate.xtrex;
/*
* Win7 Ublox7 reader.
*/   
public class xtrex {
    private static Thread gpsThread = null;

    public static void main( String[] argv ) {
        GPSparser gps =  GPSparser.getInstance(true);
        gpsThread = new Thread(gps, "GPS Thread");
        XTrexDisplay disp = XTrexDisplay.getInstance();
        disp.setScreen(MainMenu.getInstance());

        // Start threads.
        // TODO: Create and start GPS thread.
        gpsThread.start();
        UpdateThread.getInstance().start();
    }

	public Thread gpsInstance() {
        return gpsThread;
	}

}