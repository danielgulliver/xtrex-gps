
package aprogrammerisneverlate.xtrex;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/* 
 * OSX Ublox7 reader.
 * 
 * David Wakeling, 2018.
 */
public class OSXUblox7 {
  final static String FILE_NAME = "/dev/cu.usbmodem1421";
  /* final static String FILE_NAME = "/dev/cu.usbmodem1441"; */
  final static int    BUFF_SIZE = 1024;
  static GPSparser GPS = GPSparser.getInstance(true);

  /*
   * Reader.
   */
  public static void reader( String fileName ) {
    try {
      FileInputStream in = new FileInputStream( new File( fileName ) );
      byte[] buffer      = new byte[ BUFF_SIZE ];
      String s;
      int    n;
                
      while ( ( n = in.read( buffer ) ) > -1 ) {
        s = new String( buffer, 0, n );     
        GPS.processGPS(s);     /* Redirrect output to proccessing */
        //System.out.print( s );
      }
    } catch ( Exception ex ) {
      System.out.println( ex ); System.exit( 1 );
    }
  }
   
  /*
   * OSX Ublox7 reader.
   */   
  public static void main( String[] argv ) {
    reader( FILE_NAME );
  } 
}
