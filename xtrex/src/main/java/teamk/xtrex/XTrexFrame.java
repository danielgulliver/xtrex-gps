package teamk.xtrex;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * The main wrapper for the device, creates all content layers for displaying the screen, notifications and direction overlays.
 * 
 * @author Laurence Jones
 * @version Sprint 3.0
 */
public class XTrexFrame extends JLayeredPane implements ActionListener {

    private static final long serialVersionUID = 913883001958811801L;
    
	private Screen currentScreen;
    private JLabel label = new JLabel();
    private JPanel buttonOverlayPane;
    private JPanel mapScreenOverlayPane;
    private JPanel popupOverlayPane;
    private JPanel notificationOverlayPane;
    private JPanel mapOverlayPane;
    private DirectionPane dirPane;
    private StatusPane status;
    GridBagConstraints constraints = new GridBagConstraints();
    private JPanel statusContainer;
    private final int buttonSize = 80;


    /**
     * Custom transparent buttons to position over the "virtual" xtrex background.
     * 
     * @author Laurence Jones
     */
    private class SystemButton extends JButton{
        private static final long serialVersionUID = -5694339216324835648L;

		public SystemButton(){
            setPreferredSize(new Dimension(buttonSize, buttonSize));
            setOpaque(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
        }
    }

    private SystemButton powerBtn;
    private SystemButton menuBtn;
    private SystemButton minusBtn;
    private SystemButton plusBtn;
    private SystemButton selectBtn;

    private NotificationPopup notification = new NotificationPopup("Alert: GPS Connection Lost");

    private boolean directionsEnabled = true;

    /**
     * Simple configuration function, used to create and configure a JPanel to serve as a positioning container,
     * the size of the entire device.
     * 
     * @return configured JPanel. 
     */
    private JPanel deviceOverlay(JPanel screenPositioner){
        JPanel devicePositioner;

        devicePositioner = new JPanel(new GridBagLayout());
        devicePositioner.setOpaque(false);
        devicePositioner.setSize(Style.DEVICE_SIZE);
        devicePositioner.add(screenPositioner, constraints, 0);

        return devicePositioner;
    }

    /**
     * Simple configuration function, used to create and configure a JPanel to serve as a positioning container,
     * the size of the on-device screen.
     * 
     * @return configured JPanel. 
     */
    private JPanel screenOverlay(){
        JPanel screenPositioner = new JPanel(new BorderLayout());
        screenPositioner.setPreferredSize(Style.SCREEN_SIZE);
        screenPositioner.setOpaque(false);

        return screenPositioner;
    }

    /**
     * Constructor, contains all initialisation and configuration of panels and UI elements.
     */
    public XTrexFrame() {
        this.setPreferredSize(Style.DEVICE_SIZE);
        constraints.insets = new Insets(40, 0, 0, 3); // Offsets to position the display correctly in the xtrex frame.
        try{
            BufferedImage img = ImageIO.read(new File("img/bg.png"));

            label.setIcon(new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(Style.DEVICE_SIZE.width, Style.DEVICE_SIZE.height, Image.SCALE_SMOOTH)));
            label.setSize(Style.DEVICE_SIZE);
            add(label, JLayeredPane.DEFAULT_LAYER);
            label.setOpaque(false);
            //label.setLayout(new OverlayLayout(label));
            label.setLayout(new GridBagLayout());
            //label.setAlignmentX(0.5f);

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Style.undecorated) {
            this.setOpaque(true);
            setBackground(new Color(0,0,0,1));
        }

        buttonOverlayPane = new JPanel();
        buttonOverlayPane.setSize(Style.DEVICE_SIZE);
        buttonOverlayPane.setLayout(null);
        buttonOverlayPane.setOpaque(false);

        //Large amounts of configuration for the five on-display buttons.

        powerBtn = new SystemButton();
        menuBtn = new SystemButton();
        minusBtn = new SystemButton();
        plusBtn = new SystemButton();
        selectBtn = new SystemButton();

        powerBtn.addActionListener(this);
        menuBtn.addActionListener(this);
        minusBtn.addActionListener(this);
        plusBtn.addActionListener(this);
        selectBtn.addActionListener(this);

        powerBtn.setBounds(312,150,buttonSize, buttonSize);
        menuBtn.setBounds(422,100,buttonSize-10, buttonSize+5);
        minusBtn.setBounds(0,170,buttonSize-10, buttonSize+5);
        plusBtn.setBounds(6,75,buttonSize-10, buttonSize+3);
        selectBtn.setBounds(0,285,buttonSize-10, buttonSize+5);

        buttonOverlayPane.add(powerBtn);
        buttonOverlayPane.add(menuBtn);
        buttonOverlayPane.add(minusBtn);
        buttonOverlayPane.add(plusBtn);
        buttonOverlayPane.add(selectBtn);

        add(buttonOverlayPane, JLayeredPane.MODAL_LAYER);

        //Configuring the containers and positioning panels for the screen overlays,
        //such as the notification popups, direction panel and status icons.

        statusContainer = new JPanel(new BorderLayout());
        statusContainer.setOpaque(false);
        status = new StatusPane();
        statusContainer.add(status, BorderLayout.WEST);

        dirPane = new DirectionPane();
        dirPane.visible(false);

        mapOverlayPane = screenOverlay();
        mapOverlayPane.setVisible(false);
        mapOverlayPane.add(statusContainer, BorderLayout.NORTH);
        mapOverlayPane.add(dirPane, BorderLayout.SOUTH);

        mapScreenOverlayPane = deviceOverlay(mapOverlayPane);
        add(mapScreenOverlayPane, JLayeredPane.PALETTE_LAYER);

        notificationOverlayPane = screenOverlay();
        notification.setVisible(false);
        notificationOverlayPane.add(notification, BorderLayout.NORTH);

        popupOverlayPane = deviceOverlay(notificationOverlayPane);
        add (popupOverlayPane, JLayeredPane.POPUP_LAYER);
        
    }

    /**
     * Used to simultaneously control the message displayed and the visibility of the notification popup.
     * 
     * @param enabled boolean used to control the visibility of the popup.
     * @param message the string that the popup will display.
     */
    public void notificationState(boolean enabled, String message) {
        notificationState(message);
        notificationState(enabled);
    }

    /**
     * Used to set the visibility of the popup.
     * 
     * @param enabled boolean used to control the visibility of the popup.
     */
    public void notificationState(boolean enabled) {
        notification.setVisible(enabled);
    }

    /**
     * Used to set the message of the notificatoin popup.
     * 
     * @param message string which will be displayed in the popup.
     */
    public void notificationState(String message) {
        notification.setText(message);
    }

    /**
     * Instance fetcher for the direction panel.
     *
     * @return the direction pane.
     */
    public DirectionPane getDirectionPane() {
        return dirPane;
    }

    /**
     * Instance fetcher for the status panel.
     *
     * @return the status pane.
     */
    public StatusPane getStatusPane(){
        return status;
    }

    
    /**
     * Instance fetcher for the currently displayed screen.
     *
     * @return the current Screen's instance.
     */
    public Screen getCurrentScreen() {
        return currentScreen;
    }


    
    /**
     * Repaint function for updating the UI.
     */
    public void refreshDisplay() {
        repaint();
    }

    /**
     * Switches current screen on display to provided screen.
     * Switches the overlay panes on and off to match the appropriate screens.
     * 
     * @param screen the screen object instance which will be displayed.
     */
    public void setScreen(Screen screen) {
        if (currentScreen != null)
            label.remove(currentScreen);
        currentScreen = screen;
        currentScreen.setMinimumSize(Style.SCREEN_SIZE);
        //currentScreen.setAlignmentX(0.49f);
        //currentScreen.setAlignmentY(0.56f);
        currentScreen.setBackground(Style.ColorScheme.BACKGROUND);
        label.add(currentScreen, constraints, 0);

        mapOverlayPane.setVisible(directionsEnabled && (currentScreen == MapView.getInstance()));
        notificationOverlayPane.setVisible(currentScreen != OffScreen.getInstance());

        currentScreen.setVisible(true);
        this.revalidate();
        this.repaint();
    }

    /**
     * Configures the behaviour when the on-device buttons are pressed.
     * 
     * @param e action event that triggers behaviour.
     */
    public void actionPerformed(ActionEvent e) {
		Screen currentScreen = XTrexDisplay.getInstance().getCurrentScreen();

		SystemButton sourceBtn = (SystemButton) e.getSource();
		if (sourceBtn.equals(powerBtn)) {
			currentScreen.onPowerButtonPressed();
		} else if (sourceBtn.equals(menuBtn)) {
			currentScreen.onMenuButtonPressed();
		} else if (sourceBtn.equals(minusBtn)) {
			currentScreen.onMinusButtonPressed();
		} else if (sourceBtn.equals(plusBtn)) {
			currentScreen.onPlusButtonPressed();
		} else if (sourceBtn.equals(selectBtn)) {
			currentScreen.onSelectButtonPressed();
		}
	}

}