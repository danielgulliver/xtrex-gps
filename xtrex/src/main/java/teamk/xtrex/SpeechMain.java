package teamk.xtrex;

import org.json.JSONException;

public class SpeechMain {
    public static void main(String[] args) throws JSONException {
        WhereToController where = WhereToController.getInstance();
        where.getDestination();
        Maps map = new Maps();
        MapModel mapModel = map.getMapModelInstance();
        String[] directions = mapModel.getDirections();
        Speech speech = Speech.getInstance();
        SpeechModel speechModel = speech.getSpeechModel();
        speechModel.parseDirections(directions);
    }
}