package aprogrammerisneverlate.xtrex;

/**
 * Used to instantiate all the elements of the Speech MVC and provide access
 * to the SpeechView, for the system front end.
 * 
 * @author Conor Spilsbury, 2018.
 * @version Sprint 2.
 */
public class Speech {
    private static Speech speech = null;
    private static SpeechModel model;
    private static SpeechController controller;
    private static SpeechView view;

    /**
     * Instantiate the Speech class which in turn instantiates each part of the 
     * MVC designed speech system. 
     */
    private Speech() {
        model = new SpeechModel();
        controller = new SpeechController(model);
        view = new SpeechView(controller);
    }

    /**
     * Lazy instantiation of the Speech class.
     * 
     * @return instance of the Speech class.
     */
    public static Speech getInstance() {
        if (speech == null) {
            speech = new Speech();
        }
        return speech;
    }

    /**
     * Lazy instantiation of the Speech class.
     * 
     * @return instance of the SpeechView class.
     */
    public static SpeechView getSpeechViewInstance() {
        if (speech == null) {
            speech = new Speech();
        }
        return view;
    }
}