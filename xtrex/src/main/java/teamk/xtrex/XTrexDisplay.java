package teamk.xtrex;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * The XTrexDisplay class displays a window on the screen in which the current screen is shown.
 * 
 * @author Daniel Gulliver
 * @author Laurence Jones
 */
public class XTrexDisplay extends JFrame {

	private static final long serialVersionUID = -2837748826675931508L;
	private static XTrexDisplay display = null;
	private XTrexFrame contentFrame = new XTrexFrame();
	
	private XTrexDisplay() {
		
		if (Style.undecorated) {
			this.setUndecorated(true);
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			this.setBackground(new Color(0,0,0,1));
			this.setLocation(((dim.width-(Style.DEVICE_SIZE.width))/2), ((dim.height-(Style.DEVICE_SIZE.height))/2));
			//this.setLocation(200,200);
		}


		this.setTitle("XTrex");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set a cross-platform look and feel.
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	
		this.add(contentFrame, BorderLayout.CENTER);
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
		return contentFrame.getCurrentScreen();
	}
	
	/**
	 * Set the screen to be displayed on the XTrex display.
	 * Removes the current screen from the display and then adds the specified screen.
	 */
	public void setScreen(Screen screen) {
		contentFrame.setScreen(screen);
	}
	
	public void refreshDisplay() {
		contentFrame.repaint();
	}

	public XTrexFrame getXTrexFrame(){
		return contentFrame;
	}	

}
