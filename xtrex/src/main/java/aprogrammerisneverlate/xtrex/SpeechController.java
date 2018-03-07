package aprogrammerisneverlate.xtrex;

public class SpeechController {
    SpeechModel model;
    SpeechView view;

    public SpeechController(SpeechModel model) {
        this.model = model;
    }

	public void update(Integer lang) {
        model.setLanguage(lang);
	}
}