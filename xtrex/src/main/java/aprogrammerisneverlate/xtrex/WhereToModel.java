package aprogrammerisneverlate.xtrex;

import java.util.ArrayList;

/**
 * @author Daniel Gulliver
 */
public class WhereToModel {

    private static WhereToModel whereToModel = null;

    private WhereToController controller = null;

    private WhereToModel() {
        
    }

    public static WhereToModel getInstance() {
        if (whereToModel == null) {
            whereToModel = new WhereToModel();
        }
        return whereToModel;
    }

    public void setController(WhereToController controller) {
        this.controller = controller;
    }

    public Keyboard constructAlphabeticKeyboard() {
        // Create first keyboard:
		ArrayList<PrefabButton> keyboard1Buttons = new ArrayList<PrefabButton>();
		
		// Create character buttons for keyboard 1.
		char[] letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ_".toCharArray();
		for (char letter : letters) {
			keyboard1Buttons.add(new CharacterButton(letter));
		}

		// Create button to switch to second keyboard for keyboard 1.
		keyboard1Buttons.add(new SwitchKeyboardButton("=>"));
		
		return new Keyboard(keyboard1Buttons, 7, 4);
    }

    public Keyboard constructNumericKeyboard() {
        // Create second keyboard:
		ArrayList<PrefabButton> keyboard2Buttons = new ArrayList<PrefabButton>();
		
		// Create number buttons (1-9).
		char[] numbers = "789456123".toCharArray();
		for (char number : numbers) {
			keyboard2Buttons.add(new CharacterButton(number));
		}

		// Create button to switch to first keyboard.
		keyboard2Buttons.add(new SwitchKeyboardButton("<="));

		// Create zero button.
		keyboard2Buttons.add(new CharacterButton('0'));

		// Create delete button.
		keyboard2Buttons.add(new DeleteButton());

		return new Keyboard(keyboard2Buttons, 5, 3);
    }

    /**
	 * A button designed for keyboards, which has a character on its face and inserts a character into the text field.
	 * Typically the character added to the text field is the same as the one on the face of the button, however this
	 * is not the case for the space character; an underscore is shown on the face of the button, but a normal space
	 * is inserted into the text field.
	 */
	class CharacterButton extends PrefabButton {
		private static final long serialVersionUID = 6752238440776770911L;
		private char keyValue;
		
		/**
		 * Construct a CharacterButton.
		 * @param keyValue the character to display on the face of the button, and to add to the display (except in the
		 * case of the space button)
		 */
		CharacterButton(char keyValue) {
			super("" + keyValue);
			if (keyValue == '_')
				this.keyValue = ' ';
			else
				this.keyValue = keyValue;
		}

		@Override
		public void action() {
            // Add the relevant character to the display.
            if (controller != null)
                controller.setDestination(controller.getDestination() + keyValue);
            else {
                System.out.println("The model does not have a paired controller");
                //throw new ControllerNotPairedException("The model does not have a paired controller");
            }
		}
	}

	/**
	 * A button designed for keyboards, which deletes the last character from the text field.
	 */
	class DeleteButton extends PrefabButton {
		private static final long serialVersionUID = -2056965874020110981L;

		DeleteButton() {
			super("DEL");
		}

		@Override
		public void action() {
            // Remove the last character from the display.
            if (controller != null) {
                String destination = controller.getDestination();
                if (destination.length() >= 1) {
                    String newDestination = controller.getDestination().substring(0, destination.length() - 1);
                    controller.setDestination(newDestination);
                }
            } else {
                System.out.println("The model does not have a paired controller");
                //throw new ControllerNotPairedException("The model does not have a paired controller");
            }
		}
	}

	/**
	 * A button designed for keyboards, which toggles between the two keyboards.
	 */
	class SwitchKeyboardButton extends PrefabButton {
		private static final long serialVersionUID = -7789589360533656032L;

		/**
		 * Construct a SwitchKeyboardButton.
		 * @param displayString the string to display on the button
		 */
		SwitchKeyboardButton (String displayString) {
			super(displayString);
		}

		@Override
		public void action() {
            if (controller != null) {
                // Toggle the current keyboard.
                controller.toggleKeyboard();
            } else {
                System.out.println("The model does not have a paired controller");
                //throw new ControllerNotPairedException("The model does not have a paired controller");
            }
		}
	}
}