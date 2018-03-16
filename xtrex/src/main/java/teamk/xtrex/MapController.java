package teamk.xtrex;

/**
 * @author Conor Spilsbury
 * @author Adam Griffiths
 */

public class MapController {
		
    private MapModel mapModel;
    private MapView mapView;
    
    public MapController(MapView mapView, MapModel mapModel) {
        this.mapModel = mapModel;
        this.mapView = mapView;
    }
    
    public void updateMap() {
        this.mapView.setMapData(this.mapModel.getMapData());
    }
    
    public void increaseZoom() {
        this.mapModel.setZoom(this.mapModel.getZoom() + 1);
        this.updateMap();
    }
    
    public void decreaseZoom() {
        this.mapModel.setZoom(this.mapModel.getZoom() - 1);
        this.updateMap();
    }
    
    
}