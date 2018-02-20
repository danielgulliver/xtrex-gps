package aprogrammerisneverlate.xtrex;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

/**
 * The Screen class provides the behaviour and functionality required by each of the screens to show on the XTrex display.
 * Each screen must extend this class. Each of the methods corresponding to the behaviour to be performed when each of the
 * buttons on the XTrex device are pressed must be filled out.
 * 
 * Components can be added to a Screen and graphics can be painted to a Screen in the same way as a JPanel. Indeed, a Screen
 * is just a generalisation of a {@link JPanel}
 */
public abstract class Screen extends JPanel {
	public static final int SCREEN_HEIGHT = 600;
	public static final int SCREEN_WIDTH = (int) (SCREEN_HEIGHT * 54.0/103.0);
	
	Screen() {
		setSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		setBackground(Color.BLACK);
	}

	public void onPowerButtonPressed() {

	}

	public void onMenuButtonPressed() {

	}

	public abstract void onMinusButtonPressed();

	public abstract void onPlusButtonPressed();

	public abstract void onSelectButtonPressed();

}
