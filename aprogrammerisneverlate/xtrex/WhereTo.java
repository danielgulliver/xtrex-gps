import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;


public class WhereTo extends JPanel implements XTrexButtonsBehaviour {
	
	private JPanel keyboard1 = new Keyboard1(), keyboard2 = new Keyboard2(), currentKeyboard = keyboard1;
	private int buttonNumber = 1;
	
	protected WhereTo() {
		setLayout(new BorderLayout());
		add(new XTrexButtons(this), BorderLayout.NORTH);
		
		JTextPane destinationField = new JTextPane();
		add(destinationField, BorderLayout.CENTER);
		add(currentKeyboard, BorderLayout.SOUTH);
	}

	public void onPowerButtonPressed() {
		System.out.println("Power button clicked");
	}

	public void onMenuButtonPressed() {
		System.out.println("Menu button clicked");
	}

	public void onMinusButtonPressed() {
		if (buttonNumber > 0) buttonNumber--;
	}

	public void onPlusButtonPressed() {
		if (currentKeyboard.equals(keyboard1) && buttonNumber < 28) buttonNumber++;
	}

	public void onSelectButtonPressed() {
		System.out.println("Button number: " + buttonNumber);
	}
	
}

class Keyboard1 extends JPanel {
	Keyboard1() {
		setLayout(new GridLayout(7, 4));
		char[] letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ_".toCharArray();
		for (char letter : letters) {
			this.add(new JButton("" + letter));
		}
		this.add(new JButton("=>"));
	}
}

class Keyboard2 extends JPanel {
	Keyboard2() {
		// TODO: implement keyboard2
	}
}
