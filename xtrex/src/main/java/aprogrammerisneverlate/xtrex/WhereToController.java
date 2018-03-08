package aprogrammerisneverlate.xtrex;

public class WhereToController {

    private static WhereToController whereToController = null;
    private WhereToView view;
    private WhereToModel model;

    private Keyboard currentKeyboard, alphaKeyboard, numKeyboard;

    private WhereToController(WhereToView view, WhereToModel model) {
        this.view = view;
        this.model = model;
        this.alphaKeyboard = model.constructAlphabeticKeyboard();
        this.numKeyboard = model.constructNumericKeyboard();
        this.currentKeyboard = this.alphaKeyboard;
        this.view.setKeyboard(this.alphaKeyboard);
    }

    public static WhereToController getInstance() {
        if (whereToController == null) {
            whereToController = new WhereToController(WhereToView.getInstance(), WhereToModel.getInstance());
        }
        return whereToController;
    }

    public void toggleKeyboard() {
        if (this.currentKeyboard.equals(alphaKeyboard)) {
            view.setKeyboard(numKeyboard);
            this.currentKeyboard = numKeyboard;

        } else {
            view.setKeyboard(alphaKeyboard);
            this.currentKeyboard = alphaKeyboard;
        }
    }

    public void setDestination(String destination) {
        view.setDestination(destination);
    }

    public String getDestination() {
        return view.getDestination();
    }
}