package teamk.xtrex;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.FontMetrics;

/**
 * Selects the version of Ublox 7 appropriate for the Clients OS
 * Parses GPS input to variables and output log.
 * 
 * @author Connor Harris
 * @version Sprint 3 
 */

public class SatelliteView extends Screen {

    private static final long serialVersionUID = 7593535606056467998L;
    
    private GPSparser gps =  GPSparser.getInstance();
    private GPSutil gpsUtil = GPSutil.getInstance();
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

        // Draws the background pane
        g2d.setColor(Style.ColorScheme.BACKGROUND);        
        g2d.fillRect(0, 0, Style.SCREEN_SIZE.width, Style.SCREEN_SIZE.height);
        g2d.setColor(Style.ColorScheme.CONTENT_BORDER);        
        g2d.fillRect(5, 5, Style.SCREEN_SIZE.width - 10, Style.SCREEN_SIZE.height - 10);  
        g2d.setColor(Style.ColorScheme.CONTENT_BACK);        
        g2d.fillRect(7, 7, Style.SCREEN_SIZE.width - 14, Style.SCREEN_SIZE.height - 14);      

        // Writes Data to Screem
        g2d.setFont(Style.uiFont);        
        g2d.setColor(Style.ColorScheme.FONT);        
        g2d.drawString("Latitude and Longitude: ", 50, 100);

        g2d.setFont(new Font(Style.uiFont.getFamily(), Font.BOLD, 40));
        FontMetrics metrics = g.getFontMetrics(new Font(Style.uiFont.getFamily(), Font.BOLD, 40));   
        String input = Double.toString(gpsUtil.round(latitude, 5));
        int justify =  (Style.SCREEN_SIZE.width - metrics.stringWidth(input) -  metrics.stringWidth(" N")) / 2;
        if ( latitude > 0 ) {     
            g2d.drawString(input + " N", justify, 125 + textSize + textMargin);   
        } else { 
            g2d.drawString(input + " S", justify, 125 + textSize + textMargin); 
        } 
        input = Double.toString(gpsUtil.round(longitude, 5));
        justify =  (Style.SCREEN_SIZE.width - metrics.stringWidth(input) -  metrics.stringWidth(" N")) / 2;
        if ( longitude > 0 ) {     
            g2d.drawString(input + " E", justify, 200 + textSize + textMargin);   
        } else { 
            g2d.drawString(input + " W", justify, 200 + textSize + textMargin); 
        }                 
        g2d.setFont(Style.uiFont); 
        String satView = "Satellites in View: " + Integer.toString(nGPS);        
        g2d.drawString(satView, 50, 325);
    }    
}