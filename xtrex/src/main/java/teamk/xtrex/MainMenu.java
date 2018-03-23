package teamk.xtrex;

import java.awt.GridLayout;
import java.util.Arrays;
import java.util.ArrayList;

/**
 * Main menu screen, facilitates navigation between screens inside the device.
 * 
 * @author Laurence Jones
 */
public class MainMenu extends Screen {

    private static final long serialVersionUID = -562687649523447264L;
    
	private static MainMenu mainMenu = null;
    private final int ICON_SCALE = 50;
    
    XTrexDisplay ScreenController = XTrexDisplay.getInstance();

    /**
     * Custom IconButton type, implements funcitonality to switch screens when "clicked".
     */
    class MenuButton extends IconButton {
        private static final long serialVersionUID = -4866035506036719922L;
        
		private Screen targetScreen;

        /**
         * Constructor, takes in a screen object which it will then switch the screen to when "clicked".
         * 
         * @param displayString String the button will display.
         * @param inputScreen the screen object used for switching.
         * @param iconName String of the icon filename.
         * @param selectedIconName String of the "selected" icon filename.
         */
        public MenuButton(String displayString, Screen inputScreen, String iconName, String selectedIconName){
            super(displayString, ICON_SCALE, IconButton.iconPosition.ICON_TOP, iconName, selectedIconName);
            targetScreen = inputScreen;
        }

        /**
         * Overrides the action() method in PrefabButton to enable the screen switching.
         */
        @Override
        public void action() {
            super.action();

            ScreenController.setScreen(targetScreen);
        }
    }

    private MenuButton WhereToButton;
    private MenuButton TripComputerButton;
    private MenuButton MapButton;
    private MenuButton SpeechButton;
    private MenuButton SatelliteButton;
    private MenuButton AboutButton;

    private SelectionController Selector;

    /**
     * Constructor for the Main Menu. Configures the menu buttons into the correct layout for the screen.
     */
    private MainMenu(){
        setLayout(new GridLayout(3,2));
        WhereToButton = new MenuButton("Where To?", WhereTo.getInstance().getView(), "icons/whereTo.png", "icons/whereToSelected.png");
        TripComputerButton = new MenuButton("Trip Computer", TripComputer.getInstance().getView(), "icons/odometer.png", "icons/odometerSelected.png");
        MapView mapView = MapView.getInstance();
        mapView.setMapController(MapController.getInstance());
        MapButton = new MenuButton("Map", MapView.getInstance(), "icons/map.png", "icons/mapSelected.png");
        SpeechButton = new MenuButton("Speech", Speech.getSpeechViewInstance(), "icons/speech.png", "icons/speechSelected.png");
        SatelliteButton = new MenuButton("Satellite", SatelliteView.getInstance(), "icons/satellite.png", "icons/satelliteSelected.png");
        AboutButton = new MenuButton("About", AboutView.getInstance(), "icons/about.png", "icons/aboutSelected.png");
        
        ArrayList<PrefabButton> UIButtons = new ArrayList<PrefabButton>(Arrays.asList(WhereToButton, TripComputerButton, MapButton, SpeechButton, SatelliteButton, AboutButton));
    
        Selector = new SelectionController(UIButtons);

        // WhereToButton
        // TripComputerButton
        // MapButton
        // SpeechButton
        // SatelliteButton
        // AboutButton

        this.add(WhereToButton);
        this.add(TripComputerButton);
        this.add(MapButton);
        this.add(SpeechButton);
        this.add(SatelliteButton);
        this.add(AboutButton);
    }

    /**
     * Returns MainMenu instance for lazy instantiation.
     * Instantiates MainMenu instance if it is not already set.
     * 
     * @return mainMenu object reference.
     */
    public static MainMenu getInstance() {
        if (mainMenu == null) {
            mainMenu = new MainMenu();
        }
        return mainMenu;
    }
    
    /**
     * Functionality methods to specify the behaviour of the on-device buttons.
     */
    public void onMinusButtonPressed() {
        Selector.back();
    }

    public void onPlusButtonPressed() {
        Selector.forward();
    }

    public void onSelectButtonPressed() {
        Selector.click();
    }

    /**
     * Reset method, reverts all stored information in the MainMenu to defaults.
     */
    public void reset() {
        Selector.reset();
    }

}