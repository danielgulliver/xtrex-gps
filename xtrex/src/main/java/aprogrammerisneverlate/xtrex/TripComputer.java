package aprogrammerisneverlate.xtrex;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class TripComputer extends Screen {

    private static final long serialVersionUID = 1L;

    private static TripComputer tripComputer = null;

    private int distance = 0;
    private int speed = 0;
    private int minutes = 0, seconds = 0;

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
    
    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setTime(double millis) {
        int seconds = (int) millis / 1000;
        this.minutes = seconds / 60;
        this.seconds = seconds % 60;
    }

    public void paint(Graphics g) {
        int textSize = 24, textMargin = 10;
        Graphics2D g2d = (Graphics2D) g;
        g2d.clearRect(0, 0, Screen.SCREEN_WIDTH, Screen.SCREEN_HEIGHT);
        g2d.clearRect(0, 0, Screen.WIDTH, Screen.HEIGHT);
        g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, textSize));
        g2d.setColor(Color.BLACK);
        g2d.drawString("trip odem", 50, 100);
        g2d.drawString(this.distance + " KM", 50, 100 + textSize + textMargin);
        g2d.drawString("speed", 50, 200);
        g2d.drawString(this.speed + " KM/H", 50, 200 + textSize + textMargin);
        g2d.drawString("moving time", 50, 300);
        g2d.drawString(this.minutes + " min " + this.seconds + " sec", 50, 300 + textSize + textMargin);
    }
}