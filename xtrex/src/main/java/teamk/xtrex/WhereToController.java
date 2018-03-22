package teamk.xtrex;

/**
 * The WhereToController is the go-between for the view and controller for the Where To screen.
 */
public class WhereToController {

    private static WhereToController whereToController = null;
    private WhereToView view;

    private WhereToModel.Keyboard currentKeyboard, alphaKeyboard, numKeyboard;

    /**
     * Construct a WhereToController. Cannot be instantiated outside of this class as it is a singleton.
     * @param view the view object for the Where To screen
     * @param model the model object for the Where To screen
     */
    private WhereToController(WhereToView view, WhereToModel model) {
        this.view = view;
        this.alphaKeyboard = model.constructAlphabeticKeyboard();
        this.numKeyboard = model.constructNumericKeyboard();

        // Set the alphabetic keyboard as the initial keyboard.
        this.currentKeyboard = this.alphaKeyboard;
        this.view.setKeyboard(this.alphaKeyboard);
    }

    /**
     * Return the single instance of WhereToController held by this class.
     * @return the single instance of WhereToController held by this class
     */
    public static WhereToController getInstance() {
        if (whereToController == null) {
            whereToController = new WhereToController(WhereToView.getInstance(), WhereToModel.getInstance());
        }
        return whereToController;
    }

    /**
     * Switch between the alphabetic keyboard and the numeric keyboard.
     */
    public void toggleKeyboard() {
        if (this.currentKeyboard.equals(alphaKeyboard)) {
            view.setKeyboard(numKeyboard);
            this.currentKeyboard = numKeyboard;
        } else {
            view.setKeyboard(alphaKeyboard);
            this.currentKeyboard = alphaKeyboard;
        }
    }

    /**
     * Set the destination in the view.
     * @param destination the destination to set in the view
     */
    public void setDestination(String destination) {
        view.setDestination(destination);
    }

    /**
     * Get the destination from the view.
     * @return the destination from the view
     */
    public String getDestination() {
        return view.getDestination();
    }

    /**
     * Reset the state of the Where To screen.
     */
    public void reset() {
        setDestination("");
    }
}