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
		mapModel = MapModel.getInstance();
        mapView = MapView.getInstance();
		mapController = MapController.getInstance();
        mapView.setController(mapController);
	}

	public static MapController getMapController() {
		return mapController;
	}

	public static MapView getMapViewInstance() {
		if (mapView == null) {
			mapView = MapView.getInstance();
			mapController = MapController.getInstance();
        	mapView.setController(mapController);
		}
		return mapView;
	}
}