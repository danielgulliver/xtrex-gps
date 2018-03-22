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

/**
 * @author Adam Griffiths
 * @author Conor Spilsbury
 * 
 * The view part of the map MVC, deals with displaying the map, cursor and button presses
 */
public class MapView extends Screen {

    private static final long serialVersionUID = 3769993700857781403L;
    
	private byte mapData[] = null;		
    private MapController mapController;
    private GPSparser gps;
    private BufferedImage cursorImg = null;
    private static MapView mapView;
    
    /**
     *  When we construct we read the cursor image to save multiple reads (it needs to be drawn
     * each time the map is refreshed and multiple reads would be ineffcent);
     */
    public MapView() {
        this.gps = GPSparser.getInstance();
        try {
            this.cursorImg = ImageIO.read(new File("cursor.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * set the controller for this view
     * 
     * @param mapController an instance of the MapController class
     */
    public void setController(MapController mapController) {
        this.mapController = mapController;
    }
    
    /**
     * Sets the data to the passed byte array for the map and draws it if the screen is active.
     * 
     * @param mapData the byte array of image data for the map
     */
    public void setMapData(byte mapData[]) {
        
        this.mapData = mapData;
        
        /* 
         * We check if the currently active screen is an instance of mapview and if so we need
         * to repaint the screen
         */
        if (XTrexDisplay.getInstance().getCurrentScreen() instanceof MapView)
            this.repaint();
        
    }
    
    /**
     * method for drawing the map on the screen
     * 
     * @param graphics which is an instance of Graphics
     */
    @Override
    public void paint(Graphics graphics) {
        
        Graphics2D graphics2d = (Graphics2D) graphics;
        
        // Converting the byte array for the map into a buffered image object
        ByteArrayInputStream bais = new ByteArrayInputStream(this.mapData);
        BufferedImage image = null;
        try {
            image = ImageIO.read(bais);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        /* 
         * If the image was read successfuly we rotate it to the current orientation, position
         * it and draw the cursor
         */
        if (image != null) {
            /* 
             * Rotation has to be 360 - angle since the bearing is clockwise but rotation is done
             * anti-clockwise
             */
            double rotation = Math.toRadians(360 - (double) gps.TrueTrackAngle());
            double locationX = image.getWidth() / 2;
            double locationY = image.getHeight() / 2;
            AffineTransform tx = AffineTransform.getRotateInstance(rotation, locationX, locationY);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
            
            /* 
             * We draw the image with an offset (since it is bigger than it needs to be so it 
             * can be rotated) and then draw the cursor image over it in the center of the frame
             */
            graphics2d.drawImage(op.filter(image, null), -99, -61, null);
            graphics2d.drawImage(cursorImg, 156, 194, null);   
        }
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
        // The select button is disabled for the map screen so we simply return
        return;
    }

    /**
     * get the instance of this MapView 
     * 
     * @return instance of MapVuew
     */
    public static MapView getInstance() {
        if (mapView == null) {
            mapView = new MapView();
        }
        return mapView;
    }
}