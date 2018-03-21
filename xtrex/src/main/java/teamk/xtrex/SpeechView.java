package teamk.xtrex;

import java.awt.GridLayout;
import java.util.Arrays;
import java.util.List;
import teamk.xtrex.SpeechModel.LanguageEnum;

import java.util.ArrayList;

/**
 * The speech menu for the user to select the language of the speech.
 * 
 * @author Conor Spilsbury, 2018.
 * @version Sprint 3 
 */
public class SpeechView extends Screen {
    private MenuButton Off;
    private MenuButton English;
    private MenuButton French;
    private MenuButton German;
    private MenuButton Italian;
    private MenuButton Spanish;
    private SpeechController controller;
    private SelectionController Selector;
    private final int ICON_SCALE = 40;
    
    /**
     * Inner class extends the PrefabButton class, which extends the JButton class. 
     * Used to create each button in the menu for setting the language. 
     */
    class MenuButton extends IconButton {
        private LanguageEnum language;

        /**
         * Instantiate a new MenuButton.
         * 
         * @param displayString is the text to be displayed as the button's text.
         * @param language is the integer corresponding to the position of the specified 
         * language in the default list of languages.
         */
        public MenuButton(LanguageEnum language, String iconName, String selectedIconName){
            super(language.getName(), ICON_SCALE, IconButton.iconPosition.ICON_LEFT, iconName, selectedIconName);
            this.language = language;
        }

        /**
         * Define the action of a button press so that selecting a 
         * button will change the language of the speech.
         */
        @Override
        public void action() {
            super.action();
			controller.selected(language);
        }
    }

    /**
     * Create a new instance of the SpeechView.
     * 
     * @param controller is the controller part of the MVC pattern for Speech.
     */
    public SpeechView(SpeechController controller){
        this.controller = controller;
        setLayout(new GridLayout(6,1));
        
        // define all the buttons to be used as part of the menu.
        Off = new MenuButton(LanguageEnum.OFF, "icons/mute.png", "icons/muteSelected.png");
        English = new MenuButton(LanguageEnum.ENGLISH, "icons/english.png", "icons/englishSelected.png");
        French = new MenuButton(LanguageEnum.FRENCH, "icons/french.png", "icons/frenchSelected.png");
        German = new MenuButton(LanguageEnum.GERMAN, "icons/german.png", "icons/germanSelected.png");
        Italian = new MenuButton(LanguageEnum.ITALIAN, "icons/italian.png", "icons/italianSelected.png");
        Spanish = new MenuButton(LanguageEnum.SPANISH, "icons/spanish.png", "icons/spanishSelected.png");

        // add the buttons to a list used be the selector to cycle through the buttons using the
        // hardware buttons on the side of the device.
        List<PrefabButton> UIButtons = new ArrayList<PrefabButton>(Arrays.asList(Off, English, French, German, Italian, Spanish));
        Selector = new SelectionController(UIButtons);

        // add the buttons to the display.
        this.add(Off);
        this.add(English);
        this.add(French);
        this.add(German);
        this.add(Italian);
        this.add(Spanish);
    }

    /**
	 * Specifies the behaviour to be performed when the minus button on the XTrex device is pressed.
	 */
	public void onMinusButtonPressed() {
        Selector.back();
    }

    /**
	 * Specifies the behaviour to be performed when the plus button on the XTrex device is pressed.
	 */
    public void onPlusButtonPressed() {
        Selector.forward();
    }

    /**
	 * Specifies the behaviour to be performed when the select button on the XTrex device is pressed.
	 */
    public void onSelectButtonPressed() {
        Selector.click();
    }
}