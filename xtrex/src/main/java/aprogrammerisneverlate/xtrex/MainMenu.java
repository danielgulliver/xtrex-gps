package aprogrammerisneverlate.xtrex;

import java.awt.GridLayout;
import java.util.Arrays;
import java.util.ArrayList;

public class MainMenu extends Screen {

    private static MainMenu mainMenu = null;
    
    XTrexDisplay ScreenController = XTrexDisplay.getInstance();

    class MenuButton extends PrefabButton {
        private Screen targetScreen;

        public MenuButton(String displayString, Screen inputScreen){
            super(displayString);
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
        setLayout(new GridLayout(3,2,10,10));
        WhereToButton = new MenuButton("Where To?", WhereTo.getInstance());
        TripComputerButton = new MenuButton("Trip Computer", TripComputer.getInstance());
        MapButton = new MenuButton("Map", MapScreen.getInstance());
        SpeechButton = new MenuButton("Speech", Speech.getSpeechViewInstance());
        SatelliteButton = new MenuButton("Satellite", mainMenu);
        AboutButton = new MenuButton("About", mainMenu);
        
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

    

}