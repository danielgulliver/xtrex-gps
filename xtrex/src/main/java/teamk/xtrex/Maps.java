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
		mapController.updateMap();
		mapView.setController(mapController);
		mapController.updateMap();
	}

	public static MapController getMapController() {
		if (mapController == null) {
			maps = new Maps();
		}
		return mapController;
	}

	public static MapView getMapViewInstance() {
		if (mapView == null) {
			mapView = MapView.getInstance();
			mapController = MapController.getInstance();
			mapController.updateMap();
			mapView.setController(mapController);
			mapController.updateMap();
		}
		return mapView;
	}

	public static MapModel getMapModelInstance() {
		if (mapModel == null) {
			maps = new Maps();
		}
		return mapModel;
	}
}