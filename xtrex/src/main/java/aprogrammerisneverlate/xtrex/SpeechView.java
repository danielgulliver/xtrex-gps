package aprogrammerisneverlate.xtrex;

import java.awt.GridLayout;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/**
 * The speech menu for the user to select the language of the speech.
 * 
 * @author Conor Spilsbury
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

        public MenuButton(String displayString, Integer language){
            super(displayString);
            this.language = language;
        }

        @Override
        public void action() {
            super.action();
            controller.update(language);
        }
    }

    public SpeechView(SpeechController controller){
        this.controller = controller;
        setLayout(new GridLayout(6,1));
        Off = new MenuButton("Off", null);
        English = new MenuButton("English", 1);
        French = new MenuButton("French", 2);
        German = new MenuButton("German", 3);
        Italian = new MenuButton("Italian", 4);
        Spanish = new MenuButton("Spanish", 5);

        List<PrefabButton> UIButtons = new ArrayList<PrefabButton>(Arrays.asList(Off, English, French, German, Italian, Spanish));
        Selector = new SelectionController(UIButtons);

        this.add(Off);
        this.add(English);
        this.add(French);
        this.add(German);
        this.add(Italian);
        this.add(Spanish);
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