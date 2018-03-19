package teamk.xtrex;

public class TripComputer {

    private static TripComputer tripComputer = null;

    private TripComputerController controller = null;
    private TripComputerView view = null;
    // Note: We do not need a reference to the model.

    public TripComputer() {
        this.controller = TripComputerController.getInstance();
        this.view = TripComputerView.getInstance();
    }

    public void update() {
        this.controller.updateSpeed();
        this.controller.updateDistance();
        this.controller.updateTime();
        this.view.repaint(); // Update the display
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

    public TripComputerView getView() {
        return this.view;
    }
}