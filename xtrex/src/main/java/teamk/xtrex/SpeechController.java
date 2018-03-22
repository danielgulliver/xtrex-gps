package teamk.xtrex;

import teamk.xtrex.SpeechModel.LanguageEnum;

/**
 * The controller class that is part of the MVC design behind the speech elements of the GPS. 
 * 
 * @author ConorSpilsbury, 2018.
 * @version Sprint 3.
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
     */
    public void selected(LanguageEnum language) {
        // use the integer representation of the language to set the language for the device.
        model.setLanguage(language);

        // get the name of the language as a string.
        SpeechModel.LanguageEnum lang = model.getLanguage();

        // check that a language has been set and it's not been turned off.
        if (lang == null) return;

        // play the audio to say the specific language has been selected.
        String fileName = new String("languageSelected/" + lang.getName() + "Selected");
        Speech.playAudio(fileName);
    }
}