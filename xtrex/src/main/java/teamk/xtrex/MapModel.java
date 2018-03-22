package teamk.xtrex;

import java.io.File;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import teamk.xtrex.SpeechModel.LanguageEnum;

/**
 * The model part of the map screen. Contains the data for the map and tracks state
 * 
 * @author Adam Griffiths
 * @author Conor Spilsbury
 * 
 * @version Sprint 3 - Final
 */
public class MapModel {
		
    private final static String STATIC_API_BASE = "https://maps.googleapis.com/maps/api/staticmap";
    private final static String DIRECTIONS_API_BASE = "https://maps.googleapis.com/maps/api/directions/json";
    private final static String STATIC_API_KEY = "AIzaSyDv8abU01v40-krRiApS1w-zr5Kkcxb0zI";
    private final static String DIRECTIONS_API_KEY = "AIzaSyB_Xb021JnC9W3SwpD8tqD2ZBcAdxAuP9M";
    private final static String IMG_SIZE = "540x540";
    
    private GPSparser gps;
    private Speech speech;
    private int zoom = 18; // default zoom 

    // Stores the lats, longs and current point in the list of points for the current jorney
    private double[] directionLats = null;
    private double[] directionLongs = null;
    private int directionIndex = 0;

    private static MapModel mapModel;
    
    /**
     * constructor of the MapModel.
     * gets the current instances of GPSparser, Speech, GPSutil, and WhereTo classes.
     */
    public MapModel() {
        this.gps     = GPSparser.getInstance();
        this.speech  = Speech.getSpeechInstance();
        GPSutil.getInstance();
        WhereTo.getInstance();
    }

    /**
     * gets the instance of the MapModel
     * 
     * @return MapModel instance
     */
    public static MapModel getInstance() {
        if (mapModel == null) {
            mapModel = new MapModel();
        }
        return mapModel;
    }
    
    /**
     * Gets the current zoom level of the map
     * 
     * @return An int between 1-21 for the current zoom level of the map
     */
    public int getZoom() {
        return this.zoom;
    }
    
    /**
     * Sets the zoom level for the next download of the map image
     * 
     * @param zoom An integer between 1-21 for the required zoom level
     */
    public void setZoom(int zoom) { 
        if (zoom > 0 && zoom < 22)
            this.zoom = zoom;
    }
    
    /**
     * Gets the byte array, to be coverted to a buffered image for the map
     * 
     * @return A byte array for the data of the map image
     */
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

        // If the response is null there is no internet and we need to display an error popup
        if (response == null) {
            Speech.playAudio(new File("InternetOffline.wav"));
            return null;
        }
            
        return response;
        
    }

    /**
     * Checks the current location to see if we are off course (and need to recalculate the directions) or if
     * we are close to the coordinate for the next step of the journey (and need to play the audio for it)
     */
    public void checkLocation() {
        System.out.println("running check location");

        // If the journey points are unitnitalised we don't need to check the location
        if (this.directionLats == null || this.directionIndex >= this.directionLats.length) {
            return;
        }

        System.out.println("Next audio play: "+Double.toString(directionLats[directionIndex])+","+Double.toString(directionLongs[directionIndex]));
        System.out.println("Distance to next audio play: "+new Integer(GPSutil.latLongToDistance(this.directionLats[directionIndex], this.directionLongs[directionIndex], gps.Latitude(), gps.Longitude())).toString());
        //If we are moving away from the next point in the journey we are lost and need to recalculate the journey
        /* if (!gpsUtil.approaching(directionLats[directionIndex], directionLongs[directionIndex])) {
            //this.getDirections(whereTo.getDestination());
            System.out.println("Recalculation route");
        } */

        //If the distance to the next point is less than 10 meters its time to play the audio for the next direction
        if (GPSutil.latLongToDistance(this.directionLats[directionIndex], this.directionLongs[directionIndex], gps.Latitude(), gps.Longitude()) < 15) {
            if (speech.getLanguage() != LanguageEnum.OFF) {
                Speech.playAudio(new File(Integer.toString(this.directionIndex).toString()+".wav"));
                this.directionIndex++;
            }

        }
    
    }
    
    /**
     * Downloads the directions to the provided destination from the current location
     * 
     * @param destination A string of the destination to navigate to
     */
    public void getDirections(String destination) {

        //If the language is off then there's no need to download the directions
        if (speech.getLanguage() == LanguageEnum.OFF) {
            return;
        }

        // format the string to send to google maps
        destination        = destination.replaceAll(" ", "+");

        // get current lat and long coordinates
        String latStr      = Double.toString(gps.Latitude());
        String longStr     = Double.toString(gps.Longitude());
        
        final String method = "GET";
        final String url
          = ( MapModel.DIRECTIONS_API_BASE
            + "?origin=" + latStr + "," + longStr
            + "&destination=" + destination
            + "&mode=walking"
            + "&language=" + speech.getCountryCode()
            + "&key=" + MapModel.DIRECTIONS_API_KEY );
        
        final byte[] body = {};
        final String[][] headers = {};
        
        byte[] response = HttpConnect.httpConnect(method, url, headers, body);

        //I the response is null we have no internet so need to display an error popup
        if (response == null) {
            Speech.playAudio(new File("audio/InternetOffline.wav"));
            return;
        }

        String s = new String(response);

        // parse the json output to get the directions
        String[] directions = parseDirections(s);

        if (directions != null) {
            //Get the audio for the array of directions
            Speech.parseDirections(directions);
        }
    }

    /**
     * parse the json output from google maps to get just the step 
     * by step directions
     * 
     * @return array containing each direction
     */
    public String[] parseDirections(String json) {
        JSONArray routesArray;
        JSONParser parser = new JSONParser();
        JSONObject obj;
        
        try {
            obj = (JSONObject) parser.parse(json);
        } catch (Exception e) {
            // speech offline
            Speech.playAudio(new File("audio/SpeechUnavailable"));
            Speech.setSpeechAvailability(false);
            return null;
        }

        String status = (String) obj.get("status");

        // This means the destination entered doesn't exist so we need to create an error popup
        if (status.equals("ZERO_RESULTS") || status.equals("NOT_FOUND")) {
            Speech.playAudio(new File("audio/InvalidDestination.wav"));
            return null;
        }

        // parse the JSON to get the step by step directions
        routesArray =  (JSONArray) obj.get("routes");
        JSONObject route = (JSONObject) routesArray.get(0);
        JSONArray legs   = (JSONArray) route.get("legs");
        JSONObject step  = (JSONObject) legs.get(0);
        JSONArray steps  = (JSONArray) step.get("steps");


        // initialise arrays for storing latitudes, longitudes, and directions
        this.directionIndex = 0;
        this.directionLats  = new double[steps.size()];
        this.directionLongs = new double[steps.size()];
        String[] directions = new String[steps.size()];
        
        // populate the arrays with the json values
        for (int i = 0; i < directions.length; i++) {
            JSONObject startLoc    = (JSONObject) steps.get(i);
            startLoc               = (JSONObject) startLoc.get("start_location");
            this.directionLats[i]  = (Double) startLoc.get("lat");
            this.directionLongs[i] = (Double) startLoc.get("lng");
            JSONObject tmp         = (JSONObject) steps.get(i);
            directions[i]          = (String) tmp.get("html_instructions");

        }
        
        // sanitise the directions
        for (int i = 0; i < directions.length; i++) {
            directions[i] = TextProcessor.removeHTMLTags(directions[i]);
            directions[i] = TextProcessor.expandAbbreviations(directions[i]);
            directions[i] = TextProcessor.replaceCodes(directions[i]);
        }

        return directions;
    }
    
}