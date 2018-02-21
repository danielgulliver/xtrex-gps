package aprogrammerisneverlate.xtrex;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The XTrexDisplay class displays a window on the screen in which the current screen is shown.
 */
public class XTrexDisplay extends JFrame implements ActionListener {

	private Screen currentScreen;

	// Device buttons
	// TODO: Replace with customised button class
	JButton powerBtn = new JButton("On/Off");
	JButton menuBtn = new JButton("Menu");
	JButton minusBtn = new JButton("-");
	JButton plusBtn = new JButton("+");
	JButton selectBtn = new JButton("Select");

	JToolBar xTrekButtons = new JToolBar("XTrex Buttons");
	
	protected XTrexDisplay() {
		this.setTitle("XTrex");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.setPreferredSize(new Dimension(Screen.SCREEN_WIDTH, Screen.SCREEN_HEIGHT)); // replace in future
		
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
	
		this.add(xTrekButtons, BorderLayout.PAGE_START);


		// TODO: Replace with Menu screen when it has been created.
		setScreen(new WhereTo());
		
		this.pack();
		this.setVisible(true);
	}

	/**
	 * Return the Screen currently being shown on the display.
	 * @return the Screen 
	 */
	public Screen getCurrentScreen() {
		return currentScreen;
	}
	
	/**
	 * Set the screen to be displayed on the XTrex display.
	 * Removes the current screen from the display and then adds the specified screen.
	 */
	public void setScreen(Screen screen) {
		if (currentScreen != null) remove(currentScreen);
		currentScreen = screen;
		add(currentScreen, BorderLayout.CENTER);
		revalidate();
		repaint();
	}
	
	public void refreshDisplay() {
		repaint();
	}

	public void actionPerformed(ActionEvent e) {
		Screen currentScreen = this.getCurrentScreen();

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
