package teamk.xtrex;

import java.util.Arrays;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author Adam Griffiths
 * @author Conor Spilsbury
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
    private static MapModel mapModel;
    
    public MapModel() {
        this.gps = GPSparser.getInstance();
        this.whereTo = WhereToController.getInstance();
        this.speech = Speech.getInstance();
    }

    public static MapModel getInstance() {
        if (mapModel == null) {
            mapModel = new MapModel();
        }
        return mapModel;
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
       
        byte[] response = HttpConnect.httpConnect(method, url, headers, body);
        return response;
        
    }
    
    public String[] getDirections() throws ParseException {
        String destination = whereTo.getDestination();
        String latStr  = Double.toString(gps.Latitude());
        String longStr = Double.toString(gps.Longitude());
        
        final String method = "GET";
        final String url
          = ( MapModel.DIRECTIONS_API_BASE
            + "?origin=" + latStr + "," + longStr
            + "&destination=" + "Harrison+Building,+Exeter"
            + "&mode=walking"
            + "&language=" + speech.getLanguageCode()
            + "&key=" + MapModel.DIRECTIONS_API_KEY );

        System.out.println(url);
        
        final byte[] body = {};
        final String[][] headers = {};
        
        byte[] response = HttpConnect.httpConnect(method, url, headers, body);
    
        //JSONObject jsonObject = new JSONObject(response);

        String s = new String(response);

        JSONObject array1;
        JSONArray routesArray;
        JSONParser parser = new JSONParser();


            JSONObject obj = (JSONObject) parser.parse(s);

            System.out.println(obj.toString());

           

             
            routesArray =  (JSONArray) obj.get("routes");

            JSONObject route = (JSONObject) routesArray.get(0);
            JSONArray legs = (JSONArray) route.get("legs");
            JSONObject step = (JSONObject) legs.get(0);
            JSONArray steps = (JSONArray) step.get("steps");


            this.directionLats = new double[steps.size()];
            this.directionLongs = new double[steps.size()];
            String[] directions = new String[steps.size()];
            
            for (int i = 0; i < directions.length; i++) {
                JSONObject startLoc = (JSONObject) steps.get(i);
                startLoc = (JSONObject) startLoc.get("start_location");
                this.directionLats[i]  = (Double) startLoc.get("lat");
                this.directionLongs[i] = (Double) startLoc.get("lng");
                JSONObject tmp = (JSONObject) steps.get(i);
                directions[i]          = (String) tmp.get("html_instructions");
                System.out.println(directions[i]);
            }
            return directions;        
    }
    
}