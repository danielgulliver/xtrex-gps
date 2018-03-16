package teamk.xtrex;

import org.json.JSONException;

public class SpeechMain {
    public static void main(String[] args) throws JSONException {
        WhereToController where = WhereToController.getInstance();
        where.getDestination();
        Maps map = Maps.getInstance();
        MapModel mapModel = map.getMapModel();
        String[] directions = mapModel.getDirections();
        Speech speech = Speech.getInstance();
        SpeechModel speechModel = speech.getSpeehModel();
        speechModel.parseDirections(directions);
    }
}