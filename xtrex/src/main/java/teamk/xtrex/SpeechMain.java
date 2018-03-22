package teamk.xtrex;

import java.io.File;

import teamk.xtrex.SpeechModel.LanguageEnum;

public class SpeechMain {
    public static void main(String[] args) {
        SpeechModel speechModel = Speech.getSpeechModelInstance();
        String[] directions = new String[] {"Internet Connection Offline", "Speech Is Unavailable Right Now", "Speech Is Back Online", "G P S Connection Lost", "Invalid Destination"};
        Speech.parseDirections(directions);
    }
}