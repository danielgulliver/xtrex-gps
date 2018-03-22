package teamk.xtrex;

/**
 * The controller for the map screen
 * 
 * @author Adam Griffiths
 * 
 * @version Sprint 3 - Final
 */
public class MapController {
		
    private static MapModel mapModel;
    private static MapView mapView;
    private static MapController mapController;
    
    public MapController() { 
        MapController.mapModel = MapModel.getInstance();
        MapController.mapView = MapView.getInstance();
    }
    
    /**
     * Gets the singelton instance of this class
     * 
     * @return The singelton instance of this class
     */
    public static MapController getInstance() {
        if (mapController == null) {
            mapController = new MapController();
        }
        return mapController;
    }

    /**
     * Redraws the map screen
     */
    public void updateMap() {
        byte[] mapData = mapModel.getMapData();

        if (mapData != null)
            mapView.setMapData(mapData);
    }

    /**
     * Checks the current location to see if anything needs to be done (see mapModel for more info)
     */
    public void checkLocation() {
        mapModel.checkLocation();
    }
    
    /**
     * Increases the zoom level of the map and redraws it
     */
    public void increaseZoom() {
        mapModel.setZoom(mapModel.getZoom() + 1);
        this.updateMap();
    }
    
    /**
     * Decreases the zoom level of the map and redraws it
     */
    public void decreaseZoom() {
        mapModel.setZoom(mapModel.getZoom() - 1);
        this.updateMap();
    }   

    /**
     * Gets the directions from the currnet location to the provided destination (see mapModel for more info)
     */
    public void getDirections(String location) {
        mapModel.getDirections(location);
    }

    /**
     * Resets the map MVC to its startup state for on/off functionality
     */
    public void reset() {
        mapModel.reset();
        mapView.setMapData(null);
    }
}