package aprogrammerisneverlate.xtrex;

  /* 
   * Connor Harris - work package 5
   * Modified from:
   *    Win7 Ublox7 reader.
   *    by: 
   *       David Wakeling, 2018.
   */

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import java.io.InputStream;

public class Win7Ublox7 {
  /* original variables */
  final static String PORT_NAME = "COM6"; /* found via Computer->Devices */
  final static int    BAUD_RATE =  9600;  /* bps */
  final static int    TIMEOUT   =  2000;  /* ms  */
  final static int    BUFF_SIZE =  1024;  
  /* my variables */
  static GPSparser GPS = GPSparser.getInstance();
  

  /*
   * Reader Provided in Win7Ublox7.
   */
  public static void reader( String portName ) {
    
    System.out.println(  "--- OPEN PORT ---\n" );
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
        int    n;
        
        while ( ( n = in.read( buffer ) ) > -1 ) {
          s = new String( buffer, 0, n );  
          GPS.processGPS(s);     /* Redirrect output to proccessing */
          // System.out.print( s );
        
		}
      } else {
        System.out.println( "not a serial port" ); System.exit( 1 );
      }
    } catch ( Exception ex ) {
      System.out.println( ex ); System.exit( 1 );
    }
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