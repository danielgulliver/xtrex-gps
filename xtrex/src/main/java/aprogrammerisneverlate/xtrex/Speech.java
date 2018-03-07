package aprogrammerisneverlate.xtrex;

public class Speech {
    private static Speech speech = null;
    private static SpeechModel model;
    private static SpeechController controller;
    private static SpeechView view;

    private Speech() {
        this.model = new SpeechModel();
        this.controller = new SpeechController(model);
        this.view = new SpeechView(controller);
    }

    public static Speech getInstance() {
        if (speech == null) {
            speech = new Speech();
        }
        return speech;
    }

    public static SpeechView getSpeechViewInstance() {
        if (speech == null) {
            speech = new Speech();
        }
        return view;
    }
}