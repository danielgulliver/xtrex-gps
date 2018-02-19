package aprogrammerisneverlate.xtrex;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;


public class XTrexButtons extends JPanel implements ActionListener {
	
	JButton powerBtn = new JButton("On/Off");
	JButton menuBtn = new JButton("Menu");
	JButton minusBtn = new JButton("-");
	JButton plusBtn = new JButton("+");
	JButton selectBtn = new JButton("Select");

	XTrexDisplay display;
	
	XTrexButtons(XTrexDisplay display) {
		super(new BorderLayout());
		
		this.display = display;

		JToolBar xTrekButtons = new JToolBar("XTrex Buttons");
		
		powerBtn.setToolTipText("Turn the XTrex on or off");
		menuBtn.setToolTipText("Return to the main menu");
		
		powerBtn.addActionListener(this);
		menuBtn.addActionListener(this);
		minusBtn.addActionListener(this);
		plusBtn.addActionListener(this);
		selectBtn.addActionListener(this);
		
		xTrekButtons.add(powerBtn);
		xTrekButtons.add(menuBtn);
		xTrekButtons.add(minusBtn);
		xTrekButtons.add(plusBtn);
		xTrekButtons.add(selectBtn);
		
		add(xTrekButtons, BorderLayout.PAGE_START);
	}

	public void actionPerformed(ActionEvent e) {
		Screen currentScreen = display.getCurrentScreen();

		JButton sourceBtn = (JButton) e.getSource();
		if (sourceBtn.equals(powerBtn)) {
			currentScreen.onPowerButtonPressed();
		} else if (sourceBtn.equals(menuBtn)) {
			currentScreen.onMenuButtonPressed();
		} else if (sourceBtn.equals(minusBtn)) {
			currentScreen.onMinusButtonPressed();
		} else if (sourceBtn.equals(plusBtn)) {
			currentScreen.onPlusButtonPressed();
		} else if (sourceBtn.equals(selectBtn)) {
			currentScreen.onSelectButtonPressed();
		}
	}
}
