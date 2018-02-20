package aprogrammerisneverlate.xtrex;

import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.util.Arrays;
import java.util.List;

public class MainMenu extends Screen {
    
    XTrexDisplay ScreenController = new XTrexDisplay();

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

    MainMenu(){
        setLayout(new GridLayout(3,2));
        WhereToButton = new MenuButton("Where To?", new WhereTo());
        TripComputerButton = new MenuButton("Trip Computer", new MainMenu());
        MapButton = new MenuButton("Map", new MainMenu());
        SpeechButton = new MenuButton("Trip Computer", new MainMenu());
        SatelliteButton = new MenuButton("Trip Computer", new MainMenu());
        AboutButton = new MenuButton("Trip Computer", new MainMenu());

        Selector = new SelectionController(Arrays.asList(WhereToButton, TripComputerButton, MapButton, SpeechButton, SatelliteButton, AboutButton));

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