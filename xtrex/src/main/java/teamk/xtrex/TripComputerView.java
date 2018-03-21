package teamk.xtrex;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
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
        Graphics2D g2d = (Graphics2D) g;

        final int textHeight = g2d.getFontMetrics(Style.uiFont).getAscent(); // Height of the font from the baseline to the ascender line.
        final int lineSpacing = (int) textHeight / 2;
        final int numCards = 3;
        final int cardMargin = 5;
        final int cardHeight = (Style.SCREEN_SIZE.height - (cardMargin * (numCards + 1))) / numCards;
        final int cardWidth = Style.SCREEN_SIZE.width - 2 * cardMargin;
        final int textMarginLeft = 50;
        final int cardOutlineThickness = 2;

        // Set the rendering hint to improve text rendering.
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Clear the screen.
        g2d.clearRect(0, 0, Style.SCREEN_SIZE.width, Style.SCREEN_SIZE.height);

        // Draw three containing boxes.
        final int cardOneStartingHeight = cardMargin;
        final int cardTwoStartingHeight = cardOneStartingHeight + cardHeight + cardMargin;
        final int cardThreeStartingHeight = cardTwoStartingHeight + cardHeight + cardMargin;

        g2d.setColor(Style.ColorScheme.CONTENT_BACK);
        g2d.fillRect(cardMargin, cardOneStartingHeight, cardWidth, cardHeight);
        g2d.setColor(Style.ColorScheme.CONTENT_BORDER);
        g2d.setStroke(new BasicStroke(cardOutlineThickness));
        g2d.drawRect(cardMargin, cardOneStartingHeight, cardWidth, cardHeight);

        g2d.setColor(Style.ColorScheme.CONTENT_BACK);
        g2d.fillRect(cardMargin, cardTwoStartingHeight, cardWidth, cardHeight);
        g2d.setColor(Style.ColorScheme.CONTENT_BORDER);
        g2d.setStroke(new BasicStroke(cardOutlineThickness));
        g2d.drawRect(cardMargin, cardTwoStartingHeight, cardWidth, cardHeight);

        g2d.setColor(Style.ColorScheme.CONTENT_BACK);
        g2d.fillRect(cardMargin, cardThreeStartingHeight, cardWidth, cardHeight);
        g2d.setColor(Style.ColorScheme.CONTENT_BORDER);
        g2d.setStroke(new BasicStroke(cardOutlineThickness));
        g2d.drawRect(cardMargin, cardThreeStartingHeight, cardWidth, cardHeight);

        // Set the font and colour per the colour scheme.
        g2d.setFont(Style.uiFont);
        g2d.setColor(Style.ColorScheme.FONT);

        // Define label strings.
        final String distanceLabel = "Distance Travelled";
        final String speedLabel = "Current Speed";
        final String timeLabel = "Moving Time";

        final int firstTextLineMarginTop = ((cardHeight - (2*textHeight + lineSpacing)) / 2) + textHeight;
        final int distanceLabelMarginTop = cardOneStartingHeight + firstTextLineMarginTop;
        final int distanceMarginTop = distanceLabelMarginTop + textHeight + lineSpacing;
        final int speedLabelMarginTop = cardTwoStartingHeight + firstTextLineMarginTop - cardMargin;
        final int speedMarginTop = speedLabelMarginTop + textHeight + lineSpacing;
        final int timeLabelMarginTop = cardThreeStartingHeight + firstTextLineMarginTop - cardMargin;
        final int timeMarginTop = timeLabelMarginTop + textHeight + lineSpacing;

        // Create formatted time and speed strings.
        String formattedSpeed = new DecimalFormat("#.##").format(this.speed);
        String formattedTime = String.format(
            "%02d:%02d:%02d",
            TimeUnit.MILLISECONDS.toHours(this.millis),
            TimeUnit.MILLISECONDS.toMinutes(this.millis) % TimeUnit.HOURS.toMinutes(1),
            TimeUnit.MILLISECONDS.toSeconds(this.millis) % TimeUnit.MINUTES.toSeconds(1)
        );

        // Draw the strings on the screen.
        g2d.drawString(distanceLabel, textMarginLeft, distanceLabelMarginTop);
        g2d.drawString(this.distance + " km", textMarginLeft, distanceMarginTop);
        g2d.drawString(speedLabel, textMarginLeft, speedLabelMarginTop);
        g2d.drawString(formattedSpeed + " km/h", textMarginLeft, speedMarginTop);
        g2d.drawString(timeLabel, textMarginLeft, timeLabelMarginTop);
        g2d.drawString(formattedTime, textMarginLeft, timeMarginTop);
    }
}