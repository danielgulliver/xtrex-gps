package aprogrammerisneverlate.xtrex;
/*
* Win7 Ublox7 reader.
*/   
public class xtrex {
    private static String OS = null;
    private static Thread gpsThread = null;

    public static void main( String[] argv ) {
        GPSparser GPS = GPSparser.getInstance();
        //GPS.Start();
        XTrexDisplay disp = XTrexDisplay.getInstance();
        disp.setScreen(MainMenu.getInstance());

    }

    public static Thread getGpsThread() {
        return xtrex.gpsThread;
    }
}