package teamk.xtrex;

import java.awt.GridLayout;
import java.util.Arrays;
import java.util.ArrayList;

public class MainMenu extends Screen {

    private static final long serialVersionUID = -562687649523447264L;
    
	private static MainMenu mainMenu = null;
    private final int ICON_SCALE = 50;
    
    XTrexDisplay ScreenController = XTrexDisplay.getInstance();

    class MenuButton extends IconButton {
        private static final long serialVersionUID = -4866035506036719922L;
        
		private Screen targetScreen;

        public MenuButton(String displayString, Screen inputScreen, String iconName, String selectedIconName){
            super(displayString, ICON_SCALE, IconButton.iconPosition.ICON_TOP, iconName, selectedIconName);
            targetScreen = inputScreen;
        }

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

    private MainMenu(){
        setLayout(new GridLayout(3,2));
        WhereToButton = new MenuButton("Where To?", WhereTo.getInstance().getView(), "icons/whereTo.png", "icons/whereToSelected.png");
        TripComputerButton = new MenuButton("Trip Computer", TripComputer.getInstance().getView(), "icons/odometer.png", "icons/odometerSelected.png");
        MapButton = new MenuButton("Map", Maps.getMapViewInstance(), "icons/map.png", "icons/mapSelected.png");
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

    public static MainMenu getInstance() {
        if (mainMenu == null) {
            mainMenu = new MainMenu();
        }
        return mainMenu;
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

    public void reset() {
        Selector.reset();
    }

}