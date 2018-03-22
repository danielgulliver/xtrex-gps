package teamk.xtrex;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class SatelliteView extends Screen {

    private static final long serialVersionUID = 7593535606056467998L;
    
	GPSparser gps =  GPSparser.getInstance();
    double latitude = 0.0d;
    double longitude= 0.0d;
    int nGPS = 0;
    
    private SatelliteView(){
        repaint();
    }
    /**
	 * Return the single instance of GPSparser held by this class
     * in a thread safe manner.
	 * @return the single instance of GPSparser
	 */
    private static class Loader {
        static final SatelliteView instance = new SatelliteView();
    }
	public static SatelliteView getInstance() {
        return Loader.instance;
	}

	@Override
	public void onMinusButtonPressed() {
		
	}

	@Override
	public void onPlusButtonPressed() {
		
	}

	@Override
	public void onSelectButtonPressed() {
		
    }

    /**
	 * updates the View with the latest availiable data.
	 */
    public void update(){
        latitude = gps.Latitude();
        longitude = gps.Longitude();
        nGPS = gps.numSatellites();
        repaint();
    }
    /**
	 * resets the View on 'power off'.
	 */
    public void reset(){
        latitude = 0.0D;
        longitude = 0.0D;
        nGPS = 0;
        repaint();
    }

    /**
	 * Draws the updateable Screen.
	 */

    public void paint(Graphics g) {
        int textSize = 24, textMargin = 10;
        Graphics2D g2d = (Graphics2D) g;        
        g2d.clearRect(0, 0, Style.SCREEN_SIZE.width, Style.SCREEN_SIZE.height);        
        g2d.clearRect(0, 0, Style.SCREEN_SIZE.width, Style.SCREEN_SIZE.height);        
        g2d.setFont(Style.uiFont);        
        g2d.setColor(Style.ColorScheme.FONT);        
        g2d.drawString("Latitude: ", 50, 100);        
        g2d.drawString(Double.toString(latitude), 50, 100 + textSize + textMargin);        
        g2d.drawString("Longitude: ", 50, 200);        
        g2d.drawString(Double.toString(longitude), 50, 200 + textSize + textMargin);        
        String satView = "Satellites in View: " + Integer.toString(nGPS);        
        g2d.drawString(satView, 50, 300);
    }    
}