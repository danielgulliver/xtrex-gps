package teamk.xtrex;

/**
 * Deals with the map screen and google maps API calls
 * 
 * @author Conor Spilsbury
 * 
 * @version Sprint 2
 */
public class Maps {
	private static Maps maps = null;
	private static MapController mapController;
	private static MapModel mapModel;
	private static MapView mapView;
	
	/**
	 * Constructor
	 */
	public Maps() {
		mapModel = new MapModel();
		mapController = new MapController(mapView, mapModel);
		mapView = new MapView(mapController);
	}
	
	public static MapController getController() {
		if (mapController == null) {
			mapController = new MapController(mapView, mapModel);
		}
		return mapController;
	}
	
	public static MapView getView() {
		if (mapView == null) {
			mapView = new MapView(getController());
		}
        return mapView;
	}
	
	/**
     * Lazy instantiation of the Speech class.
     * 
     * @return instance of the Speech class.
     */
    public static Maps getInstance() {
        if (maps == null) {
            maps = new Maps();
        }
        return maps;
    }

    /**
     * Lazy instantiation of the Speech class.
     * 
     * @return instance of the SpeechView class.
     */
    public static MapView getMapViewInstance() {
        if (mapView == null) {
            mapView = new MapView(getController());
        }
        return mapView;
	}
	
	public static MapModel getMapModel() {
		if (mapModel == null) {
			mapModel = new MapModel();
		}
		return mapModel;
	}
}