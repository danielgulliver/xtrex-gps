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
 * 
 * @author Daniel Gulliver
 */
public class XTrexDisplay extends JFrame implements ActionListener {

	private static final long serialVersionUID = -2837748826675931508L;
	private static XTrexDisplay display = null;
	private Screen currentScreen;

	// Device buttons
	JButton powerBtn = new JButton("On/Off");
	JButton menuBtn = new JButton("Menu");
	JButton minusBtn = new JButton("-");
	JButton plusBtn = new JButton("+");
	JButton selectBtn = new JButton("Select");

	JToolBar xTrekButtons = new JToolBar("XTrex Buttons");
	
	private XTrexDisplay() {
		this.setTitle("XTrex");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(Screen.SCREEN_WIDTH, Screen.SCREEN_HEIGHT));
		
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
		this.pack();
		this.setVisible(true);
	}

	/**
	 * Return the single instance of XTrexDisplay held by this class.
	 * @return the single instance of XTrexDisplay
	 */
	public static XTrexDisplay getInstance() {
		if (display == null)
			display = new XTrexDisplay();
		return display;
	}

	/**
	 * Return the Screen currently being shown on the display.
	 * @return the current Screen being shown on the display
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
		currentScreen.revalidate();
		currentScreen.repaint();
		this.revalidate();
		this.repaint();
	}

	public void actionPerformed(ActionEvent e) {
		Screen currentScreen = XTrexDisplay.getInstance().getCurrentScreen();

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
