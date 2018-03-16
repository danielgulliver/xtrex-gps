package teamk.xtrex;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class SatelliteView extends Screen {
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
	 * Draws the updateable Screen.
	 */
    public void paint(Graphics g) {
        int textSize = 24, textMargin = 10;
        Graphics2D g2d = (Graphics2D) g;
        g2d.clearRect(0, 0, Screen.SCREEN_WIDTH, Screen.SCREEN_HEIGHT);
        g2d.clearRect(0, 0, Screen.WIDTH, Screen.HEIGHT);
        g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, textSize));
        g2d.setColor(Color.BLACK);
        g2d.drawString("Latitude: ", 50, 100);
        g2d.drawString(Double.toString(latitude), 50, 100 + textSize + textMargin);
        g2d.drawString("Longitude: ", 50, 200);
        g2d.drawString(Double.toString(longitude), 50, 200 + textSize + textMargin);
        g2d.drawString("Satellites in View:", 50, 300);
        g2d.drawString(Integer.toString(nGPS), 50, 300 + textSize + textMargin);
    }
}