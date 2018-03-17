package teamk.xtrex;

import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONException;
import org.json.simple.parser.ParseException;

public class SpeechMain {
    public static void main(String[] args) throws JSONException, ParseException{
        WhereToController where = WhereToController.getInstance();

        Maps map = new Maps();
        MapModel mapModel = map.getMapModelInstance();
        Speech speech = Speech.getInstance();
        SpeechModel speechModel = speech.getSpeechModel();
        speechModel.setLanguage(1);
        String[] directions = mapModel.getDirections();
        speechModel.parseDirections(directions);
    }
}