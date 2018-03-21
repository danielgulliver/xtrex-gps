package teamk.xtrex;

/**
 * Deals with the map screen and google maps API calls
 * 
 * @author Adam Griffiths
 * @author Conor Spilsbury
 * 
 * @version Sprint 2
 */
public class Maps {
	private static Maps maps = null;
	private static MapController mapController;
	private static MapModel mapModel;
	private static MapView mapView;
	

	public Maps() {
		mapModel = MapModel.getInstance();
        mapView = MapView.getInstance();
		mapController = MapController.getInstance();
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
			maps = new Maps();
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