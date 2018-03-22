package teamk.xtrex;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

/**
 * The Trip Computer screen displays the distance travelled, current speed, and time of the journey on the screen.
 * @author Daniel Gulliver
 */
public class TripComputerView extends CardScreen {

    private static final long serialVersionUID = 1L;

    private static TripComputerView tripComputerView = null;

    private double speed = 0.0d;
    private long millis = 0;
    private int distance = 0;


    private TripComputerView() {
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

    /**
     * Convert the distance travelled to kilometres and format it as a decimal with two decimal places.
     * @return the formatted distance as a string
     */
    public String getFormattedDistance() {
        String formattedDistance = new DecimalFormat("#.##").format((float) this.distance / 1000.0) + " km";
        return formattedDistance;
    }

    /**
     * Format the current speed as a decimal with two decimal places.
     * @return the formatted speed as a string
     */
    public String getFormattedSpeed() {
        String formattedSpeed = new DecimalFormat("#.##").format(this.speed) + " km/h";
        return formattedSpeed;
    }

    /**
     * Format the travel time as hh:mm:ss.
     * @return the formatted time as a string
     */
    public String getFormattedTime() {
        String formattedTime = String.format(
            "%02d:%02d:%02d",
            TimeUnit.MILLISECONDS.toHours(this.millis),
            TimeUnit.MILLISECONDS.toMinutes(this.millis) % TimeUnit.HOURS.toMinutes(1),
            TimeUnit.MILLISECONDS.toSeconds(this.millis) % TimeUnit.MINUTES.toSeconds(1)
        );
        return formattedTime;
    }

    @Override
    public void updateCards() {
        super.updateCards();
        addCard("Distance Travelled", getFormattedDistance());
        addCard("Current Speed", getFormattedSpeed());
        addCard("Moving Time", getFormattedTime());
    }
    
}