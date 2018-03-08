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
 * @version Sprint 2
 */
public class Maps {
	
	private class MapView extends Screen {
		
		private byte mapData[] = null;		
		private MapController mapController;
		
		public MapView(MapController mapController) {
			this.mapController = mapController;
		}
		
		public void setMapData(byte mapData[]) {
			
			this.mapData = mapData;
			
			if (XTrexDisplay.getInstance().getCurrentScreen() instanceof MapView)
				this.repaint();
			
		}
		
		@Override
		public void paint(Graphics g) {
			
			Graphics2D g2d = (Graphics2D) g;
	        
	        ByteArrayInputStream bais = new ByteArrayInputStream(this.mapData);
	        BufferedImage image = null;
	        try {
	            image = ImageIO.read(bais);
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }
	        
	        if (image != null)
	        	g2d.drawImage(image, 0, 0, null);
	        
		}
		
		@Override
		public void onMinusButtonPressed() {	
			mapController.decreaseZoom();	
		}
		
		@Override
		public void onPlusButtonPressed() {
			mapController.increaseZoom();
		}
		
		@Override 
		public void onSelectButtonPressed() {
			return;
		}
		
	}
	
	public class MapController {
		
		private MapModel mapModel;
		private MapView mapView;
		
		private MapController() {
			
			this.mapModel = new MapModel();
			this.mapView = new MapView(this);
			
		}
		
		public MapView getScreen() {
			return this.mapView;
		}
		
		public void updateMap() {
			this.mapView.setMapData(this.mapModel.getMapData());
		}
		
		public void increaseZoom() {
			this.mapModel.setZoom(this.mapModel.getZoom() + 1);
			this.updateMap();
		}
		
		public void decreaseZoom() {
			this.mapModel.setZoom(this.mapModel.getZoom() - 1);
			this.updateMap();
		}
		
		
	}
	
	private class MapModel {
		
		private final static String API_BASE = "https://maps.googleapis.com/maps/api/staticmap";
		private final static String API_KEY = "AIzaSyDgW3X4z9hxnIAMRjl5ZAbeWgh0ylL68NQ";
		private final static String DEFAULT_LAT = "50.737730";
		private final static String DEFAULT_LONG = "-3.532626";
		private final static String IMG_SIZE = "342x418";
		
		
		private GPSparser gps = GPSparser.getInstance(true);
		private int zoom = 17;
		
		//Implicit no argument constructor here
		
		public int getZoom() {
			return this.zoom;
		}
		
		public void setZoom(int zoom) {
			
			if (zoom > 0 && zoom < 22)
				this.zoom = zoom;
			
		}
		
		public byte[] getMapData() {
				
			String latStr  = Double.toString(gps.Latitude());
			String longStr = Double.toString(gps.Longitude());
 				
			
			final String method = "GET";
		    final String url
		      = ( MapModel.API_BASE
		        + "?center=" + latStr + "," + longStr
		        + "&zoom=" + zoom
		        + "&size=" + MapModel.IMG_SIZE
		        + "&key=" + MapModel.API_KEY );
		    
		    final byte[] body = {};
		    final String[][] headers = {};
		   
		    return HttpConnect.httpConnect( method, url, headers, body );
			
		}
		
	}
	
	private static Maps mapsInstance = null;
	
	private MapController mapController = null;
	
	private Maps() {
		
		this.mapController = new MapController();
		
	}
	
	public static MapController getController() {
		
		if (Maps.mapsInstance == null)
			Maps.mapsInstance = new Maps();
		
		return Maps.mapsInstance.mapController;
		
	}
	
}