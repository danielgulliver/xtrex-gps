package aprogrammerisneverlate.xtrex;

import java.awt.GridLayout;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/**
 * The speech menu for the user to select the language of the speech.
 * 
 * @author Conor Spilsbury, 2018.
 * @version Sprint 2 
 */
public class SpeechView extends Screen {
    private static SpeechView speechView = null;
    private MenuButton Off;
    private MenuButton English;
    private MenuButton French;
    private MenuButton German;
    private MenuButton Italian;
    private MenuButton Spanish;
    private SpeechController controller;
    private SelectionController Selector;

    class MenuButton extends PrefabButton {
        private Integer language;

        /**
         * Instantiate a new MenuButton.
         * 
         * @param String to be displayed as the button's text.
         * @param Integer corresponding to the position of the specified language 
         * in the default list of languages.
         */

        public MenuButton(String displayString, Integer language){
            super(displayString);
            this.language = language;
        }

        /**
         * Define the action of a button press so that selecting a 
         * button will change the language of the speech.
         */
        @Override
        public void action() {
            super.action();
            controller.update(language);
        }
    }

    /**
     * Create a new instance of the SpeechView.
     * 
     * @param controller as part of the MVC pattern for Speech.
     */
    public SpeechView(SpeechController controller){
        this.controller = controller;
        setLayout(new GridLayout(6,1));
        
        // define all the buttons to be used as part of the menu.
        Off = new MenuButton("Off", null);
        English = new MenuButton("English", 1);
        French = new MenuButton("French", 2);
        German = new MenuButton("German", 3);
        Italian = new MenuButton("Italian", 4);
        Spanish = new MenuButton("Spanish", 5);

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