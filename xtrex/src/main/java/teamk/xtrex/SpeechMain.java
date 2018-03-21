package teamk.xtrex;

import java.io.File;

import teamk.xtrex.SpeechModel.LanguageEnum;

public class SpeechMain {
    public static void main(String[] args) {
		SpeechModel speechModel = Speech.getSpeechModelInstance();
        speechModel.setLanguage(LanguageEnum.FRENCH);
        MapModel maps = new MapModel();
        maps.getDirections("EX4 7AP");
    }
}