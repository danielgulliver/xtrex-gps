package teamk.xtrex;

/**
 * @author Conor Spilsbury
 * @author Adam Griffiths
 */

public class MapController {
		
    private MapModel mapModel;
    private MapView mapView;
    
    public MapController() {
        this.mapModel = new MapModel();
        this.mapView = new MapView(this);
    }
    
    public MapView getScreen() {
        return this.mapView;
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