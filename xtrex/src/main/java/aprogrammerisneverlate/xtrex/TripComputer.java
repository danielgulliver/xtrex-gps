package aprogrammerisneverlate.xtrex;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class TripComputer extends Screen {

    private static final long serialVersionUID = 1L;

    private static TripComputer tripComputer = null;

    private TripComputer() {
        repaint();
    }

    public static TripComputer getInstance() {
        if (tripComputer == null) {
            tripComputer = new TripComputer();
        }
        return tripComputer;
    }

	@Override
	public void onMinusButtonPressed() {
		
	}

	@Override
	public void onPlusButtonPressed() {
		
	}

	@Override
	public void onSelectButtonPressed() {
		
    }
    
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.clearRect(0, 0, Screen.WIDTH, Screen.HEIGHT);
        g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        g2d.setColor(Color.BLACK);
        g2d.drawString("trip odem", 50, 50);
        g2d.drawString("speed", 50, 100);
        g2d.drawString("moving time", 50, 150);
    }
}