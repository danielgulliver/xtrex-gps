package teamk.xtrex;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

/**
 * The Trip Computer screen displays the distance travelled, current speed, and time of the journey on the screen.
 * @author Daniel Gulliver
 */
public class TripComputerView extends Screen {

    private static final long serialVersionUID = 1L;

    private static TripComputerView tripComputerView = null;

    private double speed = 0.0d;
    private long millis = 0;
    private int distance = 0;

    private TripComputerView() {
        repaint();
    }

    /**
     * Return the single instance of TripComputer held by this class.
     * @return the single instance of TripComputer held by this class
     */
    public static TripComputerView getInstance() {
        if (tripComputerView == null) {
            tripComputerView = new TripComputerView();
        }
        return tripComputerView;
    }

	@Override
	public void onMinusButtonPressed() {
		// Do nothing.
	}

	@Override
	public void onPlusButtonPressed() {
		// Do nothing.
	}

	@Override
	public void onSelectButtonPressed() {
		// Do nothing.
    }
    
    /**
     * Set the distance travelled by the XTrex device.
     * @param distance the distance travelled by the XTrex device
     */
    public void setDistance(int distance) {
        this.distance = distance;
    }

    /**
     * Set the current speed of travel of the XTrex device.
     * @param speed the current speed of travel of the XTrex device
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Set the time of the journey of the XTrex device.
     * @param millis the time of the journey of the XTrex device
     */
    public void setTime(long millis) {
        this.millis = millis;
    }

    public void paint(Graphics g) {
        int textSize = 24, textMargin = 10;
        Graphics2D g2d = (Graphics2D) g;

        String formattedTime = String.format(
            "%02d:%02d:%02d",
            TimeUnit.MILLISECONDS.toHours(this.millis),
            TimeUnit.MILLISECONDS.toMinutes(this.millis) % TimeUnit.HOURS.toMinutes(1),
            TimeUnit.MILLISECONDS.toSeconds(this.millis) % TimeUnit.MINUTES.toSeconds(1)
        );
        String formattedSpeed = new DecimalFormat("#.##").format(this.speed);

        g2d.clearRect(0, 0, Screen.SCREEN_WIDTH, Screen.SCREEN_HEIGHT);
        g2d.clearRect(0, 0, Screen.WIDTH, Screen.HEIGHT);
        g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, textSize));
        g2d.setColor(Color.BLACK);
        g2d.drawString("trip odem", 50, 100);
        g2d.drawString(this.distance + " M", 50, 100 + textSize + textMargin);
        g2d.drawString("speed", 50, 200);
        g2d.drawString(formattedSpeed + " KM/H", 50, 200 + textSize + textMargin);
        g2d.drawString("moving time", 50, 300);
        g2d.drawString(formattedTime, 50, 300 + textSize + textMargin);
    }
}