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
	

	/**
	 * constructor for maps
	 * 
	 * get instances of each part of the MVC structure and link them together
	 */
	public Maps() {
		mapModel = MapModel.getInstance();
        mapView = MapView.getInstance();
		mapController = MapController.getInstance();
		mapView.setController(mapController);
		mapController.updateMap();
	}


	/**
	 * get the instance of the MapController
	 * 
	 * @return instance of MapController
	 */
	public static MapController getMapController() {
		if (mapController == null) {
			maps = new Maps();
		}
		return mapController;
	}

	/**
	 * get the instance of the MapView
	 * 
	 * @return instance of MapView
	 */
	public static MapView getMapViewInstance() {
		if (mapView == null) {
			maps = new Maps();
		}
		return mapView;
	}

	/**
	 * get the instance of MapModel
	 * 
	 * @return instance of MapModel
	 */
	public static MapModel getMapModelInstance() {
		if (mapModel == null) {
			maps = new Maps();
		}
		return mapModel;
	}
}