package teamk.xtrex;

/**
 * Provides an abstraction on top of the WhereToController.
 */
public class WhereTo {
    private static WhereTo whereTo = null;

    private WhereToModel model;
    private WhereToView view;
    private WhereToController controller;

    private WhereTo() {
        this.model = WhereToModel.getInstance();
        this.view = WhereToView.getInstance();
        this.controller = WhereToController.getInstance();
        // We cannot put the controller in the constructor so we must pair it manually.
        this.model.setController(this.controller);
    }

    /**
     * Reset the state of the Where To screen.
     */
    public void reset() {
        this.controller.reset();
    }

    /**
     * Return the single instance of WhereTo held by this class.
     * @return the single instance of WhereTo held by this class
     */
    public static WhereTo getInstance() {
        if (whereTo == null) {
            whereTo = new WhereTo();
        }
        return whereTo;
    }

    /**
     * Return the Where To screen (view object).
     * @return the Where To screen
     */
    public WhereToView getView() {
        return view;
    }

    /**
     * Return the destination typed in by the user to the destination field on the Where To screen.
     */
    public String getDestination() {
        return this.view.getDestination();
    }

}