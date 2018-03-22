package teamk.xtrex;

public class OffScreen extends Screen{

    private static OffScreen offScreen = null;

    @Override
    public void onMenuButtonPressed(){}
    @Override
    public void onPowerButtonPressed(){
        new Thread(UpdateThread.getInstance()).start();
        XTrexDisplay.getInstance().setScreen(MainMenu.getInstance());
    }

    private OffScreen() {
        setOpaque(false);
    }

    public static OffScreen getInstance() {
        if (offScreen == null) {
            offScreen = new OffScreen();
        }
        return offScreen;
    }

    public void onPlusButtonPressed(){}
    public void onMinusButtonPressed(){}
    public void onSelectButtonPressed(){}
}