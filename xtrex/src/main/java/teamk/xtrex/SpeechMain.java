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
        Speech.parseDirections(new String[] {"Internet Connection Offline", "Internet Connection Re established", "G P S Connection Acquired", "G P S Connection Lost", "Invalid Destination"});
        Speech.playAudio(new File("0.wav"));
        Speech.playAudio(new File("1.wav"));
        Speech.playAudio(new File("2.wav"));
        Speech.playAudio(new File("3.wav"));
        Speech.playAudio(new File("4.wav"));
        Speech.playAudio(new File("5.wav"));
        Speech.playAudio(new File("6.wav"));
    }
}