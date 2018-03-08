package aprogrammerisneverlate.xtrex;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The controller class that is part of the MVC design behind the speech elements of the GPS. 
 * 
 * @author ConorSpilsbury, 2018.
 * @version Sprint 2.
 */
public class SpeechController {
    SpeechModel model;
    SpeechView view;

    /**
     * Create a new instance of the SpeechController.
     * 
     * @param model for the speech generation and playing of audio.
     */
    public SpeechController(SpeechModel model) {
        this.model = model;
    }

    /**
     * Update the language of the speech. Play audio letting to user know that 
     * their choice of language has been selected.
     * 
     * @param language as an integer corresponding to the position of the language in the default list:
     * <Off, English, French, German, Italian, Spanish>.
     * @throws IOException
     */
    public void selected(Integer language) {
        model.setLanguage(language);
        String lang = model.getLanguage();
        if (lang == null) return;
        File openFile = new File(lang + "Selected.wav");
        SpeechModel.playAudio(openFile);
    }
}