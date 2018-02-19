package aprogrammerisneverlate.xtrex;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public abstract class Screen extends JPanel implements XTrexButtonsBehaviour {
	public static final int SCREEN_HEIGHT = 600;
	public static final int SCREEN_WIDTH = (int) (SCREEN_HEIGHT * 54.0/103.0);
	
	Screen() {
		setSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		setBackground(Color.BLACK);
	}
}
