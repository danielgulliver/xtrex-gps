package aprogrammerisneverlate.xtrex;
/*
* Win7 Ublox7 reader.
*/   
public class xtrex {
    public static Thread gpsThread = null;

    public static void main( String[] argv ) {
        GPSparser GPS = GPSparser.getInstance(false);
        gpsThread = new Thread(GPS, "GPS thread");
        gpsThread.run();
        XTrexDisplay disp = XTrexDisplay.getInstance();
        disp.setScreen(MainMenu.getInstance());
    }

    public static Thread getGpsThread() {
        return xtrex.gpsThread;
    }
}