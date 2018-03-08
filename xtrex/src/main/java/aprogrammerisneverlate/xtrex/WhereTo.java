package aprogrammerisneverlate.xtrex;

public class WhereTo {
    private static WhereTo whereTo = null;

    private WhereToModel model;
    private WhereToView view;
    private WhereToController controller;

    private WhereTo() {
        this.model = WhereToModel.getInstance();
        this.view = WhereToView.getInstance();
        this.controller = WhereToController.getInstance();
        this.model.setController(this.controller);
    }

    public static WhereTo getInstance() {
        if (whereTo == null) {
            whereTo = new WhereTo();
        }
        return whereTo;
    }

    public WhereToView getView() {
        return view;
    }
    
}