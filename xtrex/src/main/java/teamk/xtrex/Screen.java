package teamk.xtrex;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * The Screen class provides the behaviour and functionality required by each of the screens to show on the XTrex display.
 * Each screen must extend this class. Each of the methods corresponding to the behaviour to be performed when each of the
 * buttons on the XTrex device are pressed must be filled out.
 * <p>
 * Components can be added to a Screen and graphics can be painted to a Screen in the same way as a JPanel. Indeed, a Screen
 * is just a generalisation of a {@link JPanel}
 * 
 * @author Daniel Gulliver
 */
public abstract class Screen extends JPanel {
	public static final int SCREEN_HEIGHT = 600;
	public static final int SCREEN_WIDTH = 490;
	
	Screen() {
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
	}

	/**
	 * Specifies the behaviour to be performed when the power button on the XTrex device is pressed.
	 * If the device is on, it is turned off; if the device is off, it is turned on.
	 */
	public void onPowerButtonPressed() {
		// TODO: Turn screen on or off when the power button is pressed.
	}

	/**
	 * Specifies the behaviour to be performed when the menu button on the XTrex device is pressed.
	 * If the user is on any screen besides the main menu, they will be taken to the main menu.
	 */
	public void onMenuButtonPressed() {
		XTrexDisplay disp = XTrexDisplay.getInstance();
		disp.setScreen(MainMenu.getInstance());
	}

	/**
	 * Specifies the behaviour to be performed when the minus button on the XTrex device is pressed.
	 */
	public abstract void onMinusButtonPressed();

	/**
	 * Specifies the behaviour to be performed when the plus button on the XTrex device is pressed.
	 */
	public abstract void onPlusButtonPressed();

	/**
	 * Specifies the behaviour to be performed when the select button on the XTrex device is pressed.
	 */
	public abstract void onSelectButtonPressed();
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

}
