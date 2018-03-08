package aprogrammerisneverlate.xtrex;
/*
* Win7 Ublox7 reader.
*/   
public class xtrex {

    public static void main( String[] argv ) {
        GPSparser GPS = GPSparser.getInstance();
        //GPS.Start();
        XTrexDisplay disp = XTrexDisplay.getInstance();
        disp.setScreen(MainMenu.getInstance());

        // Start threads.
        // TODO: Create and start GPS thread.
        UpdateThread.getInstance().start();
    }

}