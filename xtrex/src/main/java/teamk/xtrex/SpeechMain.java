package teamk.xtrex;

import java.io.File;

import teamk.xtrex.SpeechModel.LanguageEnum;

public class SpeechMain {
    public static void main(String[] args) {
		String directions = "Turn <b>left</b> onto <b>Paragon St</b>";
        //"|Turn left onto Paragon St|"
        System.out.println(directions);
            directions = TextProcessor.removeHTMLTags(directions);
            directions = TextProcessor.expandAbbreviations(directions);
            System.out.println("|" + directions + "|");
        

    }
}