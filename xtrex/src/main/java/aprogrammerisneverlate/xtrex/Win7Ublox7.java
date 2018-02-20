package aprogrammerisneverlate.xtrex;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import java.io.InputStream;
import java.time.LocalTime;

//import aProgrammerIsNeverLate.xTrex.FileWritter;

/* 
 * Win7 Ublox7 reader.
 * 
 * David Wakeling, 2018.
 *
 * From rxtx-2.2pre2-bins.zip, extract RXTXcomm.jar and
 * the win64 version of rxtxSerial.dll.
 *
 * RXTXcomm.jar should be added to the CLASSPATH and 
 * rxtxSerial.dll should be placed in current directory.
 */
public class Win7Ublox7 implements GPSinterface {
  final static String PORT_NAME = "COM6"; /* found via Computer->Devices */
  final static int    BAUD_RATE =  9600;  /* bps */
  final static int    TIMEOUT   =  2000;  /* ms  */
  final static int    BUFF_SIZE =  1024;
  final static String GLLpre = "$GPGLL,";
  final static String GSVpre = "$GPGSV,";
  private static LocalTime localTime;
  static FileWritter Logs = new FileWritter();
  static boolean aGPS = false;
  static float GPStime;
  static float latitude;
  static float longitude;

    public boolean GPS() {
        return aGPS;
    }
    public float Latitude() {
        return latitude;
    }
    public float Longitude() {
        return longitude;
    }
    public float GPStime() {
        return GPStime;
    }

  /*
   * Reader.
   */
  public static void reader( String portName ) {
    Logs.Logging(true);
    System.out.println(  "--- OPEN PORT ---" );
    try {
      CommPortIdentifier portId =
        CommPortIdentifier.getPortIdentifier( portName );
    
      if ( portId.isCurrentlyOwned() ) {
        System.out.println( "port in use" ); System.exit( 1 );
      }
        
      CommPort commPort = portId.open( "whatever", TIMEOUT );
            
      if ( commPort instanceof SerialPort ) {
        SerialPort serialPort = (SerialPort) commPort;
        serialPort.setSerialPortParams( BAUD_RATE
                                      , SerialPort.DATABITS_8
                                      , SerialPort.STOPBITS_1
                                      , SerialPort.PARITY_NONE
                                      );
                
        serialPort.setFlowControlMode( SerialPort.FLOWCONTROL_RTSCTS_IN );
        serialPort.setRTS( true );

        InputStream in = serialPort.getInputStream();
	    byte[] buffer  = new byte[ BUFF_SIZE ];
        String s;
        String noPre;
        String noPreSat;
        String[] tokens;
        String[] tokenSat;
        int nGSV;
        int    n;

        System.out.println(  "--- READ START ---" );        
        while ( ( n = in.read( buffer ) ) > -1 ) {
          s = new String( buffer, 0, n ); 
          if ( s.contains(GSVpre) ) {
              noPreSat = s.substring(s.indexOf(GSVpre) + GSVpre.length());
              tokenSat = noPreSat.split(",");
              nGSV = Integer.parseInt(tokenSat[0]);
              localTime = LocalTime.now();
              Logs.Logger("-- Number of GSV messages: " + tokenSat[0] + "  --" );
          }
          
          if ( s.contains(GLLpre) ) {
            noPre = s.substring(s.indexOf(GLLpre) + GLLpre.length());
            tokens = noPre.split(",");
            if ( tokens[5].contains("V") ){ 
                aGPS = false;
                Logs.Logger( "--  NO GPS ACQUIRED  --" + "  at time: " + localTime );
            } 
            else if ( tokens[5].contains("A") ){ 
              GPStime = Float.parseFloat(tokens[4]);
              aGPS = true;
              System.out.println("-----   GPS ACQUIRED   -----");
              Logs.Logger("GPS LOCATION: ");
              Logs.Logger( "    GPS aquired at: " + tokens[4]  );
                          
              if ( tokens[1].contains("N") || tokens[1].contains("S") ){ 
                  latitude = Float.parseFloat(tokens[0]);
                  Logs.Logger( "    Latitude: " + tokens[0] );
              }
              if ( tokens[3].contains("E") || tokens[3].contains("W") ){ 
                  longitude = Float.parseFloat(tokens[2]);
                  Logs.Logger( "    Longitude: " + tokens[2] );
              } 
            }
          }          
          
          //System.out.print( s );
        
		}
      } else {
        System.out.println( "not a serial port" ); System.exit( 1 );
      }
    } catch ( Exception ex ) {
      System.out.println( ex ); System.exit( 1 );
    }
    Logs.Logging(false);
  }

  static void listPorts() {
        java.util.Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
        while ( portEnum.hasMoreElements() ) 
        {
            CommPortIdentifier portIdentifier = portEnum.nextElement();
            System.out.println(portIdentifier.getName()  +  " - " +  getPortTypeName(portIdentifier.getPortType()) );
        }        
    }
    
    static String getPortTypeName ( int portType )
    {
        switch ( portType )
        {
            case CommPortIdentifier.PORT_I2C:
                return "I2C";
            case CommPortIdentifier.PORT_PARALLEL:
                return "Parallel";
            case CommPortIdentifier.PORT_RAW:
                return "Raw";
            case CommPortIdentifier.PORT_RS485:
                return "RS485";
            case CommPortIdentifier.PORT_SERIAL:
                return "Serial";
            default:
                return "unknown type";
        }
    }


}