package aprogrammerisneverlate.xtrex;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

/**
 * The 'Where To?' screen enables the user to type in their destination, identified either by name or by postcode.
 * The screen has a destination field and two keyboards which are used to enter text into the field.
 * <p>
 * True to the behaviour of the original device, the user may cycle through the keyboard's buttons using the plus
 * and minus buttons, and select a button using the Select button. However, in our simulation of the device, it is
 * also possible to click directly on the buttons on the keyboard to enter the destination, or even type the destination
 * directly into the destination field.
 * 
 * @author Daniel Gulliver
 */
public class WhereTo extends Screen {
	
	private static final long serialVersionUID = 2063996011118360800L;
	private Keyboard currentKeyboard, keyboard1, keyboard2;
	private JTextPane destinationField;
	
	protected WhereTo() {
		super();
		setLayout(new BorderLayout());

		// Create destination field.
		destinationField = new JTextPane();
		destinationField.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		add(destinationField, BorderLayout.PAGE_START);


		// Create first keyboard:
		ArrayList<PrefabButton> keyboard1Buttons = new ArrayList<PrefabButton>();
		
		// Create character buttons for keyboard 1.
		char[] letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ_".toCharArray();
		for (char letter : letters) {
			keyboard1Buttons.add(new CharacterButton(letter));
		}

		// Create button to switch to second keyboard for keyboard 1.
		//keyboard1Buttons.add(new SwitchKeyboardButton("=>", keyboard2));
		
		keyboard1 = new Keyboard(keyboard1Buttons, 7, 4);


		// Create second keyboard:
		ArrayList<PrefabButton> keyboard2Buttons = new ArrayList<PrefabButton>();
		
		// Create character buttons.
		char[] numbers = "1234567890".toCharArray();
		for (char number : numbers) {
			keyboard2Buttons.add(new CharacterButton(number));
		}

		// Create button to switch to first keyboard.
		//keyboard2Buttons.add(new SwitchKeyboardButton("<=", WhereTo.this.keyboard1));

		// Create delete button.
		keyboard2Buttons.add(new DeleteButton());

		keyboard2 = new Keyboard(keyboard2Buttons, 5, 3);

		setKeyboard(keyboard1);
			
	}
	
	/**
	 * Changes the keyboard on the screen. Removes the old keyboard from the screen and shows the new keyboard on the screen.
	 * 
	 * @param Keyboard kbd -- The keyboard to display on the screen
	 */
	public void setKeyboard(Keyboard kbd) {
		if (currentKeyboard != null) {
			remove(currentKeyboard);
		}
		currentKeyboard = kbd;
		add(currentKeyboard, BorderLayout.CENTER);
		revalidate();
		repaint();
	}

	public void onMinusButtonPressed() {
		// Highlight the previous button on the keyboard on the screen.
		currentKeyboard.sc.back();
	}

	public void onPlusButtonPressed() {
		// Highlight the next button on the keyboard on the screen.
		currentKeyboard.sc.forward();
	}

	public void onSelectButtonPressed() {
		// Click the highlighted button to perform its associated action.
		currentKeyboard.sc.click();
	}

	class CharacterButton extends PrefabButton {
		private static final long serialVersionUID = 6752238440776770911L;
		private char keyValue;
		
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
			WhereTo.this.destinationField.setText(destinationField.getText() + keyValue);
		}
	}

	class DeleteButton extends PrefabButton {
		private static final long serialVersionUID = -2056965874020110981L;

		DeleteButton() {
			super("DEL");
		}

		@Override
		public void action() {
			// Remove the last character from the display.
			try {
				String newDestinationText = destinationField.getText(0, destinationField.getText().length() - 1);
				WhereTo.this.destinationField.setText(newDestinationText);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
	}

	class SwitchKeyboardButton extends PrefabButton {
		private static final long serialVersionUID = -7789589360533656032L;
		Keyboard keyboardToSwitchTo;

		SwitchKeyboardButton (String displayString, Keyboard keyboardToSwitchTo) {
			super(displayString);
			this.keyboardToSwitchTo = keyboardToSwitchTo;
		}

		@Override
		public void action() {
			WhereTo.this.setKeyboard(keyboardToSwitchTo);
		}
	}

	/**
	 * A keyboard is a collection of buttons which enables the user to enter text into the destination field.
	 * Buttons are added to each keyboard during construction and cannot be added on the fly.
	 */
	class Keyboard extends JPanel implements ActionListener {
		private static final long serialVersionUID = 6403469078457676699L;
		public SelectionController sc;
		public ArrayList<PrefabButton> buttons;

		Keyboard(ArrayList<PrefabButton> buttons, int rows, int cols) {
			setLayout(new GridLayout(rows, cols));
			buttons.addAll(buttons);
			sc = new SelectionController(buttons);

			// Add the buttons to the keyboard.
			for (PrefabButton button : buttons) {
				add(button);
				button.addActionListener(this);
			}
		}

		public void actionPerformed(ActionEvent e) {
			((PrefabButton) e.getSource()).action();
		}
	}
}