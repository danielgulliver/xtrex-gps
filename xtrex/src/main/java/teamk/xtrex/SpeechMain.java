package teamk.xtrex;

import org.json.JSONException;
import org.json.simple.parser.ParseException;
import teamk.xtrex.SpeechModel.LanguageEnum;

public class SpeechMain {
    public static void main(String[] args) throws JSONException, ParseException{
        WhereToController where = WhereToController.getInstance();

        Maps map = new Maps();
        MapModel mapModel = map.getMapModelInstance();
        Speech speech = Speech.getSpeechInstance();
        SpeechModel speechModel = speech.getSpeechModel();
        speechModel.setLanguage(LanguageEnum.ENGLISH);
        String[] directions = mapModel.getDirections();
        speechModel.parseDirections(directions);
    }
}