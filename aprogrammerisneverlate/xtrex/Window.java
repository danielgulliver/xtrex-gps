import javax.swing.JFrame;

public class Window extends JFrame {

	private Window() {
		this.setTitle("XTrex");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.add(new WhereTo());
		
		this.pack();
		this.setVisible(true);
	}
	
	
	public static void main(String[] args) {
		Window window = new Window();
	}

}
