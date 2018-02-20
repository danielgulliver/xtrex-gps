package aprogrammerisneverlate.xtrex;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;


public class WhereTo extends Screen {
	
	private Keyboard keyboard1 = new Keyboard1(), keyboard2 = new Keyboard2(), currentKeyboard;
	private int buttonNumber = 0;
	private JTextPane destinationField;
	
	protected WhereTo() {
		super();
		setLayout(new BorderLayout());
		
		destinationField = new JTextPane();
		add(destinationField, BorderLayout.NORTH);
		
		setKeyboard(keyboard1);
	}
	
	public void addCharToDisplay(char ch) {
		destinationField.setText(destinationField.getText() + ch);
	}
	
	public void deleteCharFromDisplay() {
		try {
			destinationField.setText(destinationField.getText(0, destinationField.getText().length() - 1));
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	public void setKeyboard(Keyboard kbd) {
		if (currentKeyboard != null) {
			remove(currentKeyboard);
			currentKeyboard.buttons[buttonNumber].selected(false);
		}
		buttonNumber = 0;
		currentKeyboard = kbd;
		add(currentKeyboard, BorderLayout.SOUTH);
		currentKeyboard.buttons[0].selected(true);
		revalidate();
		repaint();
	}

	public void onPowerButtonPressed() {
		System.out.println("Power button clicked");
	}

	public void onMenuButtonPressed() {
		System.out.println("Menu button clicked");
	}

	public void onMinusButtonPressed() {
		if (buttonNumber > 0) {
			currentKeyboard.buttons[buttonNumber].selected(false); // deselect previously highlighted button
			buttonNumber--;
		}		
		currentKeyboard.buttons[buttonNumber].selected(true);
	}

	public void onPlusButtonPressed() {
		if (buttonNumber < currentKeyboard.buttons.length - 1) {
			currentKeyboard.buttons[buttonNumber].selected(false); // deselect previously highlighted button
			buttonNumber++;
		}
		
		currentKeyboard.buttons[buttonNumber].selected(true);
	}

	public void onSelectButtonPressed() {
		if (buttonNumber >= 0 && buttonNumber < currentKeyboard.buttons.length - 1) {
			String btnText = currentKeyboard.buttons[buttonNumber].getText();
			if (btnText == "=>") {
				setKeyboard(keyboard2);
			} else if (btnText == "<=") {
				setKeyboard(keyboard1);
			} else if (btnText == "DEL") {
				deleteCharFromDisplay();
			} else {
				char charToAdd = btnText.charAt(0);
				if (charToAdd == '_') charToAdd = ' ';
				addCharToDisplay(charToAdd);
			}
		}
	}

	class CharacterButton extends PrefabButton {
		private char keyValue;
		
		CharacterButton(char keyValue) {
			super("" + keyValue);
			if (keyValue == ' ') setText("_");
			this.keyValue = keyValue;
		}

		@Override
		public void action() {
			WhereTo.this.addCharToDisplay(this.keyValue);
		}
	}

	class DeleteButton extends PrefabButton {
		DeleteButton() {
			super("DEL");
		}

		@Override
		public void action() {
			WhereTo.this.deleteCharFromDisplay();
		}
	}

	class SwitchKeyboardButton extends PrefabButton {
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

	class Keyboard extends JPanel {
		public PrefabButton[] buttons;
	}

	class Keyboard1 extends Keyboard {
		
		Keyboard1() {
			setLayout(new GridLayout(7, 4));
			this.buttons = new PrefabButton[28];
			
			char[] letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
			int i = 0;
			for (final char letter : letters) {
				buttons[i] = new PrefabButton("" + letter);
				buttons[i].addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						addCharToDisplay(letter);
					}
					
				});
				this.add(buttons[i]);
				i++;
			}
			buttons[26] = new PrefabButton("_");
			buttons[26].addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					addCharToDisplay(' ');
				}
				
			});
			this.add(buttons[26]);
			
			buttons[27] = new PrefabButton("=>");
			buttons[27].addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					setKeyboard(keyboard2);
				}
				
			});
			this.add(buttons[27]);
		}
	}

	class Keyboard2 extends Keyboard {
		Keyboard2() {
			this.buttons = new PrefabButton[12];
			
			setLayout(new GridLayout(5, 3));
			
			char[] numbers = "1234567890".toCharArray();
			int i = 0;
			for (final char number : numbers) {
				buttons[i] = new PrefabButton("" + number);
				buttons[i].addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						addCharToDisplay(number);
					}
					
				});
				this.add(buttons[i]);
				i++;
			}
			buttons[10] = new PrefabButton("<=");
			buttons[10].addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					setKeyboard(keyboard1);
				}
				
			});
			this.add(buttons[10]);
			
			buttons[11] = new PrefabButton("DEL");
			buttons[11].addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					deleteCharFromDisplay();
				}
				
			});
			this.add(buttons[11]);
		}
	}
	
}
