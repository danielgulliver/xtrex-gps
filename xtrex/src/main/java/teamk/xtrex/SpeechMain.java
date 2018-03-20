package teamk.xtrex;

import java.io.File;

import teamk.xtrex.SpeechModel.LanguageEnum;

public class SpeechMain {
    public static void main(String[] args) {
		SpeechModel speechModel = Speech.getSpeechModelInstance();
        speechModel.setLanguage(LanguageEnum.ENGLISH);
        Speech.parseDirections(new String[] {"Internet Connection Offline", "Internet Connection Re established", "G P S Connection Acquired", "G P S Connection Lost", "Invalid Destination"});
        Speech.playAudio(new File("0.wav"));
    }

}