package aprogrammerisneverlate.xtrex;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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

	private JTextPane destinationField;
	private String destination = "";
	private Keyboard currentKeyboard = null;
	
	private WhereToView() {
		super();
		setLayout(new BorderLayout());

		// Create destination field.
		JTextPane destinationField = new JTextPaneLimit(25);
		destinationField.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
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
	public void setKeyboard(Keyboard kbd) {
		if (currentKeyboard != null) {
			remove(currentKeyboard);
		}
		currentKeyboard = kbd;
		add(currentKeyboard, BorderLayout.CENTER);
		revalidate();
		repaint();
	}

	public void setDestination(String destination) {
		this.destination = destination;
		destinationField.setText(destination);
	}

	/**
	 * Return the keyboard currently being shown on the screen.
	 * @return the current keyboard
	 */
	public Keyboard getKeyboard() {
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
	class JTextPaneLimit extends JTextPane implements KeyListener {
		private static final long serialVersionUID = 1L;
		private int maxChars;

		/**
		 * Construct a text field with a text limit.
		 * @param maxChars the maximum number of characters to allow in the field
		 */
		JTextPaneLimit(int maxChars) {
			this.maxChars = maxChars;
			this.addKeyListener(this);
		}
		
		@Override
		public void setText(String text) {
			if (text.length() <= maxChars) {
				super.setText(text);
			}
		}

		public void keyPressed(KeyEvent e) {
			
		}

		public void keyReleased(KeyEvent e) {
			
		}

		public void keyTyped(KeyEvent e) {
			// TODO: Remove magic numbers.
			int keyCode = e.getKeyCode();
			if (keyCode == 32 || (keyCode >= 48 && keyCode <= 90)) {
				e.consume();
				if (this.getText().length() < maxChars) {
					this.setText(this.getText() + ("" + e.getKeyChar()).toUpperCase());
				}
			}
		}
	}
}