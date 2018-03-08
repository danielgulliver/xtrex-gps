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
	
	private class MapController {
		
		private MapModel mapModel;
		private MapView mapView;
		
		public MapController() {
			
			this.mapModel = new MapModel();
			this.mapView = new MapView(this);
			
			this.mapView.setMapData(this.mapModel.getMapData(0.0d, 0.0d));
			
		}
		
		public MapView getMapView() {
			return this.mapView;
		}
		
		public void increaseZoom() {
			this.mapModel.setZoom(this.mapModel.getZoom() + 1);
			this.mapView.setMapData(this.mapModel.getMapData(0.0d, 0.0d));
		}
		
		public void decreaseZoom() {
			this.mapModel.setZoom(this.mapModel.getZoom() - 1);
			this.mapView.setMapData(this.mapModel.getMapData(0.0d, 0.0d));
		}
		
		
	}
	
	private class MapModel {
		
		private final static String API_BASE = "https://maps.googleapis.com/maps/api/staticmap";
		private final static String API_KEY = "AIzaSyDgW3X4z9hxnIAMRjl5ZAbeWgh0ylL68NQ";
		private final static String DEFAULT_LAT = "50.737730";
		private final static String DEFAULT_LONG = "-3.532626";
		private final static String IMG_SIZE = "342x418";
		
		private int zoom = 17;
		
		//Implicit no argument constructor here
		
		public int getZoom() {
			return this.zoom;
		}
		
		public void setZoom(int zoom) {
			
			if (zoom > 0 && zoom < 22)
				this.zoom = zoom;
			
		}
		
		public byte[] getMapData(double latitude, double longitude) {
			
			String latStr, longStr;
			
			if (latitude == 0.0d || longitude == 0.0d) {
				
				latStr = MapModel.DEFAULT_LAT;
				longStr = MapModel.DEFAULT_LONG;
				
			} else {
				
				latStr  = new Double(latitude).toString();
				longStr = new Double(longitude).toString();
 				
			}
			
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
	
	public static Maps getInstance() {
		
		if (Maps.mapsInstance == null)
			Maps.mapsInstance = new Maps();
		
		return Maps.mapsInstance;
		
	}
	
	public Screen getScreen() {
		
		return this.mapController.getMapView();
		
	}
	
}