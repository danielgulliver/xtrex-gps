package teamk.xtrex;

public class MapController {
		
    private static MapModel mapModel;
    private static MapView mapView;
    private static MapController mapController;
    
    public MapController(MapModel mapModel, MapView mapView) { 
        this.mapModel = mapModel;
        this.mapView = mapView;
    }
    
    public static MapController getInstance() {
        if (mapController == null) {
            mapController = new MapController(MapModel.getInstance(), MapView.getInstance());
        }
        return mapController;
    }

    public void updateMap() {
        this.mapView.setMapData(this.mapModel.getMapData());
    }

    public void checkLocation() {
        //mapModel.checkLocation();
    }
    
    public void increaseZoom() {
        this.mapModel.setZoom(this.mapModel.getZoom() + 1);
        this.updateMap();
    }
    
    public void decreaseZoom() {
        this.mapModel.setZoom(this.mapModel.getZoom() - 1);
        this.updateMap();
    }   

    public void getDirections(String location) {
        this.mapModel.getDirections(location);
    }
}