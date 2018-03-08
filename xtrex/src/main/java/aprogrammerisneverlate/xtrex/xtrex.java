package aprogrammerisneverlate.xtrex;
/*
* Win7 Ublox7 reader.
*/   
public class xtrex {
<<<<<<< HEAD

    public static void main( String[] argv ) {
        GPSparser GPS = GPSparser.getInstance();
        //GPS.Start();
=======
    public static Thread gpsThread = null;

    public static void main( String[] argv ) {
        GPSparser GPS = GPSparser.getInstance(false);
        gpsThread = new Thread(GPS, "GPS thread");
        gpsThread.run();
>>>>>>> ca89fd9c747b2f6eb7b5dd3516665c081703324c
        XTrexDisplay disp = XTrexDisplay.getInstance();
        disp.setScreen(MainMenu.getInstance());

        // Start threads.
        // TODO: Create and start GPS thread.
        UpdateThread.getInstance().start();
    }

}