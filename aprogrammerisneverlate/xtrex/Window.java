package aprogrammerisneverlate.xtrex;

import javax.swing.JFrame;

public class Window extends JFrame {

	Screen currentScreen;
	
	private Window() {
		this.setTitle("XTrex");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setScreen(new WhereTo());
		
		this.pack();
		this.setVisible(true);
	}
	
	public void setScreen(Screen screen) {
		if (currentScreen != null) remove(currentScreen);
		currentScreen = screen;
		add(currentScreen);
		revalidate();
		repaint();
	}
	
	
	public static void main(String[] args) {
		Window window = new Window();
	}

}
