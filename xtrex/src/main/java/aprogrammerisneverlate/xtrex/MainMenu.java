package aprogrammerisneverlate.xtrex;

public class MainMenu extends Screen {
    
    private SelectionController Selector = new SelectionController();

    public void onPowerButtonPressed() {
        
    }

    public void onMenuButtonPressed() {

    }

    public void onMinusButtonPressed() {
        Selector.back();
    }

    public void onPlusButtonPressed() {
        Selector.forward();
    }

    public void onSelectButtonPressed() {
        Selector.click();
    }

    

}