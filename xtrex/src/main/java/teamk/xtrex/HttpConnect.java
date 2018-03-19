package teamk.xtrex;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.net.URL;
import java.net.HttpURLConnection;
/*
 * Make an HTTP connection.
 *
 * David Wakeling, 2018.
 * Modified by Adam Grififths, 2018
 */
public class HttpConnect {
  final static int TIMEOUT  = 5000; /* ms  */
  final static int BUFFSIZE = 4096; /* 4KB */

  public static byte[] httpConnect
    ( String      method
    , String      url
    , String[][]  headers
    , byte[]      body
    ) {

    try {
      /*
       * Setup connection.
       */
      URL u = new URL( url );
      HttpURLConnection conn = (HttpURLConnection) u.openConnection();

      conn.setRequestMethod( method );
      conn.setDoInput ( true );
      conn.setDoOutput( true );
      conn.setConnectTimeout( TIMEOUT );
      conn.setReadTimeout   ( TIMEOUT );
      for ( int i = 0; i < headers.length; i++ ) {
        conn.setRequestProperty( headers[ i ][ 0 ], headers[ i ][ 1 ] );
      }
      conn.connect();

      /*
       * Send data.
       */
      DataOutputStream dos = new DataOutputStream( conn.getOutputStream() );
      dos.write( body );
      dos.flush();
      dos.close(); 

      /*
       * Receive data.
       */
      DataInputStream       dis = new DataInputStream( conn.getInputStream() );
      ByteArrayOutputStream bos = new ByteArrayOutputStream();

      byte[] buffer = new byte[ BUFFSIZE ];
      for (;;) {
        int n = dis.read( buffer );
        if ( n > 0 ) {
          bos.write( buffer, 0, n );
        } else { 
          break;
        }
      }

      byte response[] = bos.toByteArray();
      dis.close();

      /*
       * Teardown connection.
       */
      conn.disconnect();

      return response;
    } catch ( Exception ex ) {
      //We don't want to exit on failure and instead we will deal with it in the calling function
      System.out.println( ex );
      return null;
    }
  }
}
