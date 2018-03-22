package teamk.xtrex;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import teamk.xtrex.Speech.NotificationsEnum;
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
    private final static int AUDIO_TOLERANCE = 10;
    
    private GPSparser gps;
    private Speech speech;
    private GPSutil gpsUtil;
    private WhereTo whereTo;
    private XTrexFrame frame;
    private DirectionPane dirPane;
    private StatusPane statPane;
    private int zoom = 18; // default zoom 

    // Stores the lats, longs and current point in the list of points for the current jorney
    private double[] directionLats = null;
    private double[] directionLongs = null;
    private String[] directions = null;
    private int directionIndex = 0;
    private static boolean internetAvailable = true;

    private static MapModel mapModel;
    
    private MapModel() {
        this.gps     = GPSparser.getInstance();
        this.speech  = Speech.getSpeechInstance();
        this.gpsUtil = GPSutil.getInstance();
        this.whereTo = WhereTo.getInstance();
        this.frame = XTrexDisplay.getInstance().getXTrexFrame();
        this.dirPane = frame.getDirectionPane();
        this.statPane = frame.getStatusPane();
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
     * Resets the variables associated with directions for a journey. Used at the end of the journey and when
     * on/off is used
     */
    public void resetDirections() {
        this.dirPane.setVisible(false);
        this.directionLats = null;
        this.directionLongs = null;
        this.directions = null;
        this.directionIndex = 0;
    }

    /**
     * Resets all state variables for use with the on/off button
     */
    public void reset() {
        this.resetDirections();
        this.zoom = 18;
        MapModel.internetAvailable = true;
    }

    /**
     * Sets the avaliability of the internet, but takes no action if the state you want to set is what it 
     * currently is. WIll play the associated audio file
     */
    public void setInternetAvailability(boolean available) {

        if (available != MapModel.internetAvailable) {

            MapModel.internetAvailable = available;

            if (MapModel.internetAvailable)
                Speech.playAudioNotification(NotificationsEnum.InternetEstablished);
            else 
                Speech.playAudioNotification(NotificationsEnum.InternetOffline);
            
        }
    }

    /**
     * Returns the curent avaliability of the internet
     */
    public static boolean getInternetAvailability() {
        return internetAvailable;
    }
    
    /**
     * Gets the byte array, to be coverted to a buffered image for the map
     * 
     * @return A byte array for the data of the map image
     */
    public byte[] getMapData() {

        // If we have no lat and long data then we return null
        if (gps.Latitude() == null && gps.Longitude() == null) {
            return null;
        }
            
        String latStr  = gps.Latitude().toString();
        String longStr = gps.Longitude().toString();
             
        
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

        // If the response is null there is no internet and we need to set the internet status icon to false
        if (response == null) {
            statPane.mapsAvailable(false);
            this.setInternetAvailability(false);
            return null;
        }
            
        //The internet must be avaliable so we ensure the icon is set to true
        this.setInternetAvailability(true);
        statPane.mapsAvailable(true);
        return response;
        
    }

    /**
     * Checks the current location to see if we are off course (and need to recalculate the directions) or if
     * we are close to the coordinate for the next step of the journey (and need to play the audio for it)
     */
    public void checkLocation() {

        // If the journey points are unitnitalised we don't need to check the location
        if (this.directionLats == null) {
            return;
        }

        //If we are moving away from the next point in the journey we are lost and need to recalculate the journey
        if (!gpsUtil.approaching(directionLats[directionIndex], directionLongs[directionIndex])) {
            this.getDirections(whereTo.getDestination());
            Speech.playAudioNotification(NotificationsEnum.Recalculating);
            return;
        } 


        //We offset the distance by 25 so that the audio will play at the right point despite GPS inaccuracy
        int offsetDistance = GPSutil.latLongToDistance(this.directionLats[directionIndex], this.directionLongs[directionIndex], gps.Latitude(), gps.Longitude()) - MapModel.AUDIO_TOLERANCE;
        
        //We display the distance to the next point
        dirPane.setDistance(offsetDistance + (MapModel.AUDIO_TOLERANCE/2));

        /* If the distance to the next point is less than 25 meters its time to play the audio for the next direction
           (Since the distance has already been offset by 25 we just check to see if it is 0 or less) */
        if (offsetDistance < 1) {
            if (speech.getLanguage() != LanguageEnum.OFF) {
                Speech.playAudio(Integer.toString(this.directionIndex).toString());
                this.directionIndex++;

                // If the index is the size of the array we've reached the end of the journey and need to reset everything
                if (this.directionIndex == this.directionLats.length) {
                    this.resetDirections();
                } else
                    dirPane.setDirectionPhrase(this.directions[directionIndex]);
                    
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

        // Formats the destiantion to be URL santitsed
        destination = destination.replaceAll(" ", "+");

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
        
        // We run the http request in a new thread to prevent blocking of the UI
        new Thread() {

            public void run() {

                byte[] response = HttpConnect.httpConnect(method, url, headers, body);

                //If the response is null we have no internet so need to display an error popup
                if (response == null) {
                    Speech.setSpeechAvailability(false);
                    return;
                }

                String s = new String(response);

                // parse the json output to get the directions
                String[] directions = parseDirections(s);

                // Gets the audio file from the parsed directions
                if (directions != null) {
                    Speech.setSpeechAvailability(true);
                    mapModel.directions = directions;
                    dirPane.setVisible(true);
                    dirPane.setDirectionPhrase(directions[0]);
                    Speech.parseDirections(directions);
                } else
                    Speech.setSpeechAvailability(false);

            }

        }.start();
    }

    /**
     * parse the json output from google maps to get just the step 
     * by step directions
     * 
     * @return array containing the directions
     */
    public String[] parseDirections(String json) {
        JSONArray routesArray;
        JSONParser parser = new JSONParser();
        JSONObject obj;
        
        // If the JSON is unable to be parsed the google maps API json is invalid (should never happen)
        try {
            obj = (JSONObject) parser.parse(json);
        } catch (Exception e) {
            // speech offline
            Speech.playAudioNotification(NotificationsEnum.SpeechUnavailable);
            Speech.setSpeechAvailability(false);
            return null;
        }

        String status = (String) obj.get("status");

        // This means the destination entered doesn't exist so we need to create an error popup
        if (status.equals("ZERO_RESULTS") || status.equals("NOT_FOUND")) {
            frame.notificationState(true, "Invalid Destination");
            new java.util.Timer().schedule( 
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            frame.notificationState(false);
                        }
                    }, 
                    5000 
            );
            Speech.playAudioNotification(NotificationsEnum.InvalidDestination);
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