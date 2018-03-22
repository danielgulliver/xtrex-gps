package teamk.xtrex;

/**
 * This exception should be thrown when trying to access an attribute or method of a controller which is null.
 * It occurs when a model needs to know about a controller and the controller needs to know about the model, but the
 * programmer has not paired a controller with the model.
 * 
 * @author Daniel Gulliver
 */
public class ControllerNotPairedException extends Exception {
    private static final long serialVersionUID = 2035720307708401220L;

	public ControllerNotPairedException(String message) {
        super(message);
    }
}