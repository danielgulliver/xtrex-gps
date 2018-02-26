package aprogrammerisneverlate.xtrex;
/*
* Win7 Ublox7 reader.
*/   
public class xtrex {
    private static String OS = null;

    public static void main( String[] argv ) {
        if(OS == null) { OS = System.getProperty("os.name"); }
        System.out.println(OS);
        if (OS.startsWith("Windows")) {
            Win7Ublox7 GPS = new Win7Ublox7();
            System.out.println();
            System.out.println(GPS.Latitude());
            System.out.println(GPS.Longitude());
            System.out.println("\nStarting GPS Read \n");
            GPS.Start();
        } else { 
        XTrexDisplay disp = XTrexDisplay.getInstance();
        disp.setScreen(MainMenu.getInstance());
        }
    }
}