package teamk.xtrex;

/**
 * Screen for use when the device is powered off. 
 * Halts and restarts update thread when powered off and on respectively.
 * Resets all stored information in the device on power off.
 * 
 * @author Laurence Jones
 */
public class OffScreen extends Screen{

    private static final long serialVersionUID = 6177220480648676449L;

	private static OffScreen offScreen = null;

    /**
     * Overrides to change functionality of the on-device buttons.
     * The power button is altered to restart the update thread and set the screen to the MainMenu.
     */
    @Override
    public void onMenuButtonPressed(){}
    @Override
    public void onPowerButtonPressed(){
        UpdateThread.startThread();
        XTrexDisplay.getInstance().setScreen(MainMenu.getInstance());
    }

    /**
     * Constructor, sets the OffScreen transparent to represent being "off".
     */
    private OffScreen() {
        setOpaque(false);
    }

    /**
     * Instance getter to enable the lazy-instantiation screen system.
     * 
     * @return offScreen screen object.
     */
    public static OffScreen getInstance() {
        if (offScreen == null) {
            offScreen = new OffScreen();
        }
        return offScreen;
    }

    /**
     * Empty functionality for on-device buttons to disable unwanted behaviour.
     */
    public void onPlusButtonPressed(){}
    public void onMinusButtonPressed(){}
    public void onSelectButtonPressed(){}
}