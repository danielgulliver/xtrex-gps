package aprogrammerisneverlate.xtrex;
/*
* Win7 Ublox7 reader.
*/   
public class xtrex {
    final static String PORT_NAME = "COM6";
    public static void main( String[] argv ) {
        Win7Ublox7 GPS = new Win7Ublox7();
        GPS.listPorts();
        GPS.reader( PORT_NAME );

        //XTrexDisplay disp = XTrexDisplay.getInstance();
        //disp.setScreen(new WhereTo());

    	//MapScreen map = new MapScreen();
    	//disp.setScreen(map);
    	//map.drawMap();
    }
}