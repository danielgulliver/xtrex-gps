package teamk.xtrex;

import java.io.File;

import teamk.xtrex.SpeechModel.LanguageEnum;

public class SpeechMain {
    public static void main(String[] args) {
        WhereToController where = WhereToController.getInstance();

        Maps map = new Maps();
       MapModel mapModel = map.getMapModelInstance();
        Speech speech = Speech.getSpeechInstance();
        SpeechModel speechModel = speech.getSpeechModelInstance();
        speechModel.setLanguage(LanguageEnum.ENGLISH);
        //String[] directions = mapModel.getDirections();
        Speech.parseDirections(new String[] {"Turn left on southampton Rd."});
        Speech.playAudio(new File("0.wav"));
    }
}