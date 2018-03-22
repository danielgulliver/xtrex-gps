package teamk.xtrex;

/**
 * This class provides an abstraction upon the Trip Computer controller.
 * @author Daniel Gulliver
 */
public class TripComputer {

    private static TripComputer tripComputer = null;

    private TripComputerController controller = null;
    private TripComputerView view = null;
    // Note: We do not need a reference to the model.

    private boolean destinationEntered = false;

    public TripComputer() {
        this.controller = TripComputerController.getInstance();
        this.view = TripComputerView.getInstance();
    }

    /**
     * Call when the destination has been entered. This prevents the trip computer from updating before the destination
     * has been set.
     */
    public void setDestinationEntered() {
        this.destinationEntered = true;
    }

    /**
     * Update the state of the trip computer so that all of the values are current.
     */
    public void update() {
        if (destinationEntered) {
            this.controller.updateSpeed();
            this.controller.updateDistance();
            this.controller.updateTime();
            this.view.repaint(); // Update the display
        }
    }

    /**
     * Reset the state of the trip computer to the default.
     */
    public void reset() {
        this.destinationEntered = false;
        this.controller.reset();
    }

    /**
     * Return the single instance of TripComputer held by this class.
     * @return the single instance of TripComputer held by this class
     */
    public static TripComputer getInstance() {
        if (tripComputer == null) {
            tripComputer = new TripComputer();
        }
        return tripComputer;
    }

    /**
     * Return the trip computer screen.
     * @return the trip computer screen
     */
    public TripComputerView getView() {
        return this.view;
    }
}