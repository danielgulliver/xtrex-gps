package teamk.xtrex;

public class TripComputer {

    private static TripComputer tripComputer = null;

    private TripComputerController controller = null;
    private TripComputerView view = null;
    private TripComputerModel model = null;

    public TripComputer() {
        this.controller = TripComputerController.getInstance();
        this.view = TripComputerView.getInstance();
        this.model = TripComputerModel.getInstance();
    }

    public void update() {
        this.controller.updateSpeed();
        this.controller.updateDistance();
        this.controller.updateTime();
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