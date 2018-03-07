package aprogrammerisneverlate.xtrex;

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
     * Update the language of the speech.
     * 
     * @param language as an integer corresponding to the position of the language in the default list:
     * <Off, English, French, German, Italian, Spanish>.
     */
	public void update(Integer language) {
        model.setLanguage(language);
	}
}