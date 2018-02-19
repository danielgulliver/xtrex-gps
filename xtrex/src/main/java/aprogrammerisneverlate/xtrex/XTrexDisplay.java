package aprogrammerisneverlate.xtrex;

import javax.swing.JFrame;

/**
 * The XTrexDisplay class displays a window on the screen in which the current screen is shown.
 */
public class XTrexDisplay extends JFrame {

	private Screen currentScreen;
	
	private XTrexDisplay() {
		this.setTitle("XTrex");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// TODO: Replace with Menu screen when it has been created.
		setScreen(new WhereTo());
		
		this.pack();
		this.setVisible(true);
	}
	
	/**
	 * Set the screen to be displayed on the XTrex display.
	 * Removes the current screen from the display and then adds the specified screen.
	 */
	public void setScreen(Screen screen) {
		if (currentScreen != null) remove(currentScreen);
		currentScreen = screen;
		add(currentScreen);
		revalidate();
		repaint();
	}
	
	
	public static void main(String[] args) {
		XTrexDisplay display = new XTrexDisplay();
	}

}
