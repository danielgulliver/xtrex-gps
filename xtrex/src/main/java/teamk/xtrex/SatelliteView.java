package teamk.xtrex;

public class SatelliteView extends CardScreen {

    private static final long serialVersionUID = 7593535606056467998L;
    
	GPSparser gps =  GPSparser.getInstance();
    double latitude = 0.0d;
    double longitude= 0.0d;
    int nGPS = 0;
    
    private SatelliteView(){
        super.repaint();
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
        super.repaint();
    }

    @Override
    public void updateCards() {
        super.updateCards();
        addCard("Latitude", Double.toString(latitude));
        addCard("Longitude", Double.toString(longitude));
        addCard("Satellites in View", Integer.toString(nGPS));
    }
    
}