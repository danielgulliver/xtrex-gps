package aprogrammerisneverlate.xtrex;
/*
* Win7 Ublox7 reader.
*/   
public class xtrex {
    final static String PORT_NAME = "COM6";
    public static void main( String[] argv ) {
        /* Win7Ublox7 GPS = new Win7Ublox7();
        GPS.listPorts();
        
        System.out.println();
        System.out.println(GPS.latitude);
        System.out.println(GPS.longitude);
        System.out.println("\n\n");

        GPS.reader( PORT_NAME ); */



        XTrexDisplay disp = XTrexDisplay.getInstance();
        disp.setScreen(MainMenu.getInstance());
    }
}