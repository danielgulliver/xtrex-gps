package teamk.xtrex;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextPane;

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
public class WhereToView extends Screen {
	
	private static final long serialVersionUID = 2063996011118360800L;

	private static WhereToView whereTo = null;

	private static final int DESTINATION_FIELD_CHARACTER_LIMIT = 20;

	private String prevDestination = "";
	private JTextPaneLimit destinationField;
	private String destination = "";
	private WhereToModel.Keyboard currentKeyboard = null;
	
	private WhereToView() {
		super();
		setLayout(new BorderLayout());

		// Create destination field.
		destinationField = new JTextPaneLimit(DESTINATION_FIELD_CHARACTER_LIMIT);
		//destinationField.setEnabled(false);
		destinationField.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
		destinationField.setForeground(Color.BLACK);
		add(destinationField, BorderLayout.PAGE_START);
	}

	/**
	 * Return the single instance of WhereTo.
	 * @return the single instance of WhereTo
	 */
	public static WhereToView getInstance() {
		if (whereTo == null)
			whereTo = new WhereToView();
		return whereTo;
	}
	
	/**
	 * Changes the keyboard on the screen. Removes the old keyboard from the screen and shows the new keyboard on the screen.
	 * 
	 * @param Keyboard kbd -- The keyboard to display on the screen
	 */
	public void setKeyboard(WhereToModel.Keyboard kbd) {
		if (this.currentKeyboard != null) {
			remove(this.currentKeyboard);
		}
		this.currentKeyboard = kbd;
		add(this.currentKeyboard, BorderLayout.CENTER);
		revalidate();
		repaint();
	}

	/**
	 * Set the destination text in the input field.
	 */
	public void setDestination(String destination) {
		this.destination = destination;
		destinationField.setText(destination);
	}

	/**
	 * Return the keyboard currently being shown on the screen.
	 * @return the current keyboard
	 */
	public WhereToModel.Keyboard getKeyboard() {
		return currentKeyboard;
	}

	/**
	 * Return the destination entered by the user in the destination field.
	 * @return the destination entered by the user
	 */
	public String getDestination() {
		return this.destination;
	}


	@Override
	public void onMenuButtonPressed() {
		super.onMenuButtonPressed();
		// Update the directions upon leaving the Where To screen, as long as the destination has changed.
		if (!this.getDestination().equals("") && !this.getDestination().equals(this.prevDestination)) {
			MapController.getInstance().getDirections(destination);
			this.prevDestination = getDestination();

			// Tell the trip computer that the destination has been entered.
			TripComputer.getInstance().reset();
			TripComputer.getInstance().setDestinationEntered();
		}
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

	/**
	 * A text field with a character limit.
	 */
	class JTextPaneLimit extends JTextPane {
		private static final long serialVersionUID = 1L;
		private int maxChars;

		/**
		 * Construct a text field with a text limit.
		 * @param maxChars the maximum number of characters to allow in the field
		 */
		JTextPaneLimit(int maxChars) {
			this.maxChars = maxChars;
		}
		
		@Override
		public void setText(String text) {
			if (text.length() <= maxChars) {
				super.setText(text);
			}
		}
	}
}