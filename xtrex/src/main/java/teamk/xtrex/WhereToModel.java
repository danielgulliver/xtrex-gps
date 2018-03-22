package teamk.xtrex;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * @author Daniel Gulliver
 */
public class WhereToModel {

    private static WhereToModel whereToModel = null;

	private WhereToController controller = null;
	
	private ControllerNotPairedException notPairedException = new ControllerNotPairedException("The model does not have a paired controller");

    private WhereToModel() {
        
    }

    /**
     * Return the single instance of WhereToModel held by this class.
     * @return the single instance of WhereToModel held by this class 
     */
    public static WhereToModel getInstance() {
        if (whereToModel == null) {
            whereToModel = new WhereToModel();
        }
        return whereToModel;
    }

    /**
     * Set the controller to be used by the model. The controller must be set before its methods can be called.
     * It is recommended to set the controller straight after initialising the model.
     * It cannot be initialised in the constructor as the WhereToController constructor takes a WhereToModel.
     */
    public void setController(WhereToController controller) {
        this.controller = controller;
    }

    /**
     * Construct and return an alphabetic keyboard.
     * An alphabetic keyboard contains the letters A-Z, a space button, and a button to switch to a numeric keyboard.
     * @return an alphabetic keyboard
     */
    public Keyboard constructAlphabeticKeyboard() {
        return new AlphabeticKeyboard();
    }

    /**
     * Construct and return a numeric keyboard.
     * A numeric keyboard contains the digits 0-9, a delete button, and a button to switch to an alphabetic keyboard.
     * @return a numeric keyboard
     */
    public Keyboard constructNumericKeyboard() {
        return new NumericKeyboard();
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
			try {
				// Add the relevant character to the display.
				if (controller == null) throw notPairedException;
				controller.setDestination(controller.getDestination() + keyValue);
			} catch (ControllerNotPairedException e) {
				e.printStackTrace();
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
			try {
				// Remove the last character from the display.
				if (controller == null) throw notPairedException;
				String destination = controller.getDestination();
				if (destination.length() >= 1) {
					// Remove the last character from the destination string.
					String newDestination = controller.getDestination().substring(0, destination.length() - 1);
					controller.setDestination(newDestination);
				}
			} catch (ControllerNotPairedException e) {
				e.printStackTrace();
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
			try {
				if (controller == null) throw notPairedException;
				// Toggle the current keyboard.
				controller.toggleKeyboard();
			} catch (ControllerNotPairedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	* A keyboard is a collection of buttons which enables the user to enter text into the destination field.
	* Buttons are added to each keyboard during construction and cannot be added on the fly.
	*
	* @author Daniel Gulliver
	*/
	public class Keyboard extends JPanel implements ActionListener {
		private static final long serialVersionUID = 6403469078457676699L;
		
		public SelectionController sc;
		public ArrayList<PrefabButton> buttons;

		public void actionPerformed(ActionEvent e) {
			// Perform a button's associated action when it is clicked.
			if (e.getSource() instanceof PrefabButton) {
				PrefabButton sourceButton = (PrefabButton) e.getSource();
				sourceButton.action();
			}
		}
	}

	class AlphabeticKeyboard extends Keyboard {
		private static final long serialVersionUID = 1L;

		AlphabeticKeyboard() {
			buttons = new ArrayList<PrefabButton>();
			
			setLayout(new GridLayout(7, 4));

			// Create character buttons.
			char[] letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ_".toCharArray();
			for (char letter : letters) {
				buttons.add(new CharacterButton(letter));
			}

			// Create button to switch to second keyboard.
			buttons.add(new SwitchKeyboardButton("=>"));

			// Add an action listener to each of the buttons and add all of the buttons to the keyboard.
			for (PrefabButton button : buttons) {
				button.addActionListener(this);
				add(button);
			}
			
			// Add all of the buttons to the selection controller.
			sc = new SelectionController(buttons);
		}
	}

	class NumericKeyboard extends Keyboard {
		private static final long serialVersionUID = 1L;

		NumericKeyboard() {
			buttons = new ArrayList<PrefabButton>();
			
			setLayout(new GridBagLayout());

			GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.BOTH;
			c.gridwidth = 1;
			c.gridheight = 1;
			c.weightx = 0.5;
			c.weighty = 0.5;

			CharacterButton oneBtn = new CharacterButton('1');
			buttons.add(oneBtn);
			c.gridx = 0;
			c.gridy = 0;
			add(oneBtn, c);

			CharacterButton twoBtn = new CharacterButton('2');
			buttons.add(twoBtn);
			c.gridx = 1;
			c.gridy = 0;
			add(twoBtn, c);

			CharacterButton threeBtn = new CharacterButton('3');
			buttons.add(threeBtn);
			c.gridx = 2;
			c.gridy = 0;
			add(threeBtn, c);

			CharacterButton fourBtn = new CharacterButton('4');
			buttons.add(fourBtn);
			c.gridx = 0;
			c.gridy = 1;
			add(fourBtn, c);

			CharacterButton fiveBtn = new CharacterButton('5');
			buttons.add(fiveBtn);
			c.gridx = 1;
			c.gridy = 1;
			add(fiveBtn, c);

			CharacterButton sixBtn = new CharacterButton('6');
			buttons.add(sixBtn);
			c.gridx = 2;
			c.gridy = 1;
			add(sixBtn, c);
			
			CharacterButton sevenBtn = new CharacterButton('7');
			buttons.add(sevenBtn);
			c.gridx = 0;
			c.gridy = 2;
			add(sevenBtn, c);

			CharacterButton eightBtn = new CharacterButton('8');
			buttons.add(eightBtn);
			c.gridx = 1;
			c.gridy = 2;
			add(eightBtn, c);

			CharacterButton nineBtn = new CharacterButton('9');
			buttons.add(nineBtn);
			c.gridx = 2;
			c.gridy = 2;
			add(nineBtn, c);

			// Create zero button.
			CharacterButton zeroBtn = new CharacterButton('0');
			buttons.add(zeroBtn);
			c.gridx = 0;
			c.gridy = 3;
			add(zeroBtn, c);

			// Create button to switch to first keyboard.
			SwitchKeyboardButton switchBtn = new SwitchKeyboardButton("<=");
			buttons.add(switchBtn);
			c.gridx = 0;
			c.gridy = 4;
			add(switchBtn, c);

			// Create delete button.
			DeleteButton deleteBtn = new DeleteButton();
			buttons.add(deleteBtn);
			c.gridheight = 2;
			c.gridwidth = 2;
			c.gridx = 1;
			c.gridy = 3;
			add(deleteBtn, c);

			// Add action listeners to all buttons.
			for (PrefabButton button : buttons) {
				button.addActionListener(this);
			}

			// Add all of the buttons to the selection controller.
			sc = new SelectionController(buttons);
		}
	}

}

