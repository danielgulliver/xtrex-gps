package aprogrammerisneverlate.xtrex;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MapScreen extends Screen {
	
	private final static String API_BASE = "https://maps.googleapis.com/maps/api/staticmap";
	private final static String API_KEY = "AIzaSyDgW3X4z9hxnIAMRjl5ZAbeWgh0ylL68NQ";
	private final static String DEFAULT_LAT = "50.737730";
	private final static String DEFAULT_LONG = "-3.532626";
	private final static String IMG_SIZE = "490x600";
	
	private Graphics g;
	
	private int zoom = 10;
	
	public MapScreen() {
		super();
		g = super.getGraphics();
		super.paintComponent(g);
	}
	
	@Override
	public void onMinusButtonPressed() {
		
		if (zoom > 1) {
			zoom--;
			drawMap();
		}
		
	}
	
	@Override
	public void onPlusButtonPressed() {
		
		if (zoom < 21) {
			zoom++;
			drawMap();
		}
		
	}
	
	@Override 
	public void onSelectButtonPressed() {
		return;
	}
	
	public void drawMap() {
		
		// The below block was modified from Maps.java by David Wakeling
		final String method = "GET";
	    final String url
	      = ( API_BASE
	        + "?center=" + DEFAULT_LAT + "," + DEFAULT_LONG
	        + "&zoom=" + zoom
	        + "&size=" + IMG_SIZE
	        + "&key=" + API_KEY );
	    
	    final byte[] body = {};
	    final String[][] headers = {};
	    
	    byte[] response = HttpConnect.httpConnect( method, url, headers, body );
	    
	    ByteArrayInputStream bais = new ByteArrayInputStream(response);
	    
	    BufferedImage img = null;
	    try {
			img = ImageIO.read(bais);
		} catch (IOException e) {
			System.out.println("IOException when parsing map image: "+e.getMessage());
			e.printStackTrace();
		}
	    
	    if (img != null)
	    	g.drawImage(img, 0, 0, this);
	    
	}
	
}