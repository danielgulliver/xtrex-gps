package aprogrammerisneverlate.xtrex;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.imageio.ImageIO;

/**
 * Deals with the map screen and google maps API calls
 * 
 * @author Adam Griffiths
 * 
 * @version 0.2
 */

public class MapScreen extends Screen {
	
	private final static String API_BASE = "https://maps.googleapis.com/maps/api/staticmap";
	private final static String API_KEY = "AIzaSyDgW3X4z9hxnIAMRjl5ZAbeWgh0ylL68NQ";
	private final static String DEFAULT_LAT = "50.737730";
	private final static String DEFAULT_LONG = "-3.532626";
	private final static String IMG_SIZE = "490x600";
	
	private int zoom = 10;
	private byte[] imgData = null;
	
	private static MapScreen instance = null;
	//private static GPS gpsInstance = null;
	
	private MapScreen() {
		super();
	}
	
	public static MapScreen getInstance() {
		
		if (MapScreen.instance == null)
			MapScreen.instance = new MapScreen();
		
		return MapScreen.instance;
		
	}
	
	@Override
	public void onMinusButtonPressed() {
		
		if (zoom > 1) {
			this.zoom--;
			this.getMap();
		}
		
	}
	
	@Override
	public void onPlusButtonPressed() {
		
		if (zoom < 21) {
			this.zoom++;
			this.getMap();
		}
		
	}
	
	@Override 
	public void onSelectButtonPressed() {
		return;
	}
	
	@Override
	 public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		if (this.imgData == null) {
			this.getMap();
			return;
		}
        
        ByteArrayInputStream bais = new ByteArrayInputStream(this.imgData);
        BufferedImage image = null;
        try {
            image = ImageIO.read(bais);
        } catch (IOException e) {
        	e.printStackTrace();
        }
        
        if (image != null)
        	g2d.drawImage(image, 0, 0, null);
	}
        
	
	public void getMap() {

		/* float lat = gpsInstance.Latitude();
		float long = gpsInstance.Longitude(); */
		
		// The below block was modified from Maps.java by David Wakeling
		final String method = "GET";
	    final String url
	      = ( MapScreen.API_BASE
	        + "?center=" + MapScreen.DEFAULT_LAT + "," + MapScreen.DEFAULT_LONG
	        + "&zoom=" + zoom
	        + "&size=" + MapScreen.IMG_SIZE
	        + "&key=" + MapScreen.API_KEY );
	    
	    final byte[] body = {};
	    final String[][] headers = {};
	   
	    this.imgData = HttpConnect.httpConnect( method, url, headers, body );
	    
	}

	/*public static void setGpsInstance(GPS instance) {
		MapScreen.gpsInstance = instance;
	}*/
	
}