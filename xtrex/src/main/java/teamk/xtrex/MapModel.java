package teamk.xtrex;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Conor Spilsbury
 * @author Adam Griffiths
 */
public class MapModel {
		
    private final static String STATIC_API_BASE = "https://maps.googleapis.com/maps/api/staticmap";
    private final static String DIRECTIONS_API_BASE = "https://maps.googleapis.com/maps/api/directions/json";
    private final static String STATIC_API_KEY = "AIzaSyA4kRpdpRTRbVsTJZTP4-CE0Gi4W67v--A";
    private final static String DIRECTIONS_API_KEY = "AIzaSyB_Xb021JnC9W3SwpD8tqD2ZBcAdxAuP9M";
    private final static String IMG_SIZE = "540x540";
    
    private GPSparser gps;
    private WhereToController whereTo;
    private Speech speech;
    private int zoom = 18;
    private double[] directionLats = null;
    private double[] directionLongs = null;
    
    public MapModel() {
        this.gps = GPSparser.getInstance();
        this.whereTo = WhereToController.getInstance();
        this.speech = Speech.getInstance();
    }
    
    public int getZoom() {
        return this.zoom;
    }
    
    public void setZoom(int zoom) { 
        if (zoom > 0 && zoom < 22)
            this.zoom = zoom;
    }
    
    public byte[] getMapData() {
            
        String latStr  = Double.toString(gps.Latitude());
        String longStr = Double.toString(gps.Longitude());
             
        
        final String method = "GET";
        final String url
          = ( MapModel.STATIC_API_BASE
            + "?center=" + latStr + "," + longStr
            + "&zoom=" + zoom
            + "&size=" + MapModel.IMG_SIZE
            + "&key=" + MapModel.STATIC_API_KEY );
        
        final byte[] body = {};
        final String[][] headers = {};
       
        return HttpConnect.httpConnect(method, url, headers, body);
        
    }
    
    public String[] getDirections() throws JSONException {
        String destination = whereTo.getDestination();
        String latStr  = Double.toString(gps.Latitude());
        String longStr = Double.toString(gps.Longitude());
        
        final String method = "GET";
        final String url
          = ( MapModel.DIRECTIONS_API_BASE
            + "?origin=" + latStr + "," + longStr
            + "&destination=" + destination
            + "&mode=walking"
            + "&language=" + speech.getLanguageCode()
            + "&key=" + MapModel.DIRECTIONS_API_KEY );
        
        final byte[] body = {};
        final String[][] headers = {};
        
        byte[] response = HttpConnect.httpConnect(method, url, headers, body);
    
        JSONObject jsonObject = new JSONObject(response);

        JSONArray routesArray;

            routesArray = jsonObject.getJSONArray("routes");
            JSONObject route = routesArray.getJSONObject(0);

            JSONArray legs = route.getJSONArray("legs");

            this.directionLats = new double[legs.length()];
            this.directionLongs = new double[legs.length()];
            String[] directions = new String[legs.length()];
            
            for (int i = 0; i < directions.length; i++) {
                JSONObject startLoc = legs.getJSONObject(0).getJSONObject("start_location");
                this.directionLats[i]  = startLoc.getDouble("lat");
                this.directionLongs[i] = startLoc.getDouble("lng");
                directions[i]          = legs.getJSONObject(0).getString("html_instructions");
            }
            return directions;        
    }
    
}