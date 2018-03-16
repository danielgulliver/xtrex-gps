public class TripComputerController {
    
    private static TripComputerController tripComputerController = null;

    private TripComputerView view = null;
    private TripComputerModel model = null;

    private TripComputerController(TripComputerView view, TripComputerModel model) {
        this.view = view;
        this.model = model;
    }

    /**
     * Return the single instance of TripComputerController held by this class.
     * @return the single instance of TripComputerController held by this class
     */
    public static TripComputerController getInstance() {
        if (tripComputerController == null) {
            tripComputerController = new TripComputerController();
        }
        return tripComputerController;
    }

    public void updateSpeed() {
        model.setCurrentSpeed();
        view.setSpeed(model.getCurrentSpeed());
    }

    public void updateDistance() {
        model.setDistanceTravelledInTimeSlice();
        view.setDistance(model.getDistanceTravelledInTimeSlice());
    }

    public void updateTime() {
        model.setMovingTime();
        view.setTime(model.getMovingTime());
    }

}