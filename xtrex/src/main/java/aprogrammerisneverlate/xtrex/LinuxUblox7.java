package aprogrammerisneverlate.xtrex;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/* 
 * Linux Ublox7 reader.
 * 
 * David Wakeling, 2018.
 */
public class LinuxUblox7 {
  final String FILE_NAME = "/dev/ttyACM0";
  final int    BUFF_SIZE = 1024;
  GPSparser GPS = GPSparser.getInstance(true);

  /*
   * Reader.
   */
  public void reader( String fileName ) {
    try {
      FileInputStream in = new FileInputStream( new File( fileName ) );
      byte[] buffer      = new byte[ BUFF_SIZE ];
      String s;
      int    n;
                
      while ( ( n = in.read( buffer ) ) > -1 ) {
        s = new String( buffer, 0, n );   
        GPS.processGPS(s);     /* Redirrect output to proccessing */
        // System.out.print( s );
      }
    } catch ( Exception ex ) {
      System.out.println( ex ); System.exit( 1 );
    }
  }
   
  /*
   * Linux Ublox7 reader.
   */   
  public void main( String[] argv ) {
    reader( FILE_NAME );
  } 
}
