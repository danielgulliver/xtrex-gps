package teamk.xtrex;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Deals with the map screen and google maps API calls
 * 
 * @author Adam Griffiths
 * 
 * @version Sprint 2
 */
public class Maps {
	private static Maps mapsInstance = null;
	
	private static MapController mapController = null;
	
	private Maps() {
		mapController = new MapController();
	}
	
	public static MapController getController() {
		
		if (mapsInstance == null)
			mapsInstance = new Maps();
		
		return mapController;
		
	}
	
}