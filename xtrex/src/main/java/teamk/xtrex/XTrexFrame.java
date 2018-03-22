package teamk.xtrex;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.IOException;
import java.nio.MappedByteBuffer;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * @author Laurence Jones
 * @version Sprint 3.0
 */


public class XTrexFrame extends JLayeredPane implements ActionListener {

    private static final long serialVersionUID = 913883001958811801L;
    
	private Screen currentScreen;
    private JLabel label = new JLabel();
    private JPanel buttonOverlayPane;
    private JPanel paletteOverlayPane;
    private JPanel mapScreenOverlayPane;
    private JPanel popupOverlayPane;
    private JPanel notificationOverlayPane;
    private JPanel mapOverlayPane;
    private DirectionPane dirPane;
    private StatusPane status;
    GridBagConstraints constraints = new GridBagConstraints();
    private JPanel statusContainer;
    private final int buttonSize = 80;

    private final Dimension powerBtnPos = new Dimension(0,0);

    private class SystemButton extends JButton{
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


    private JPanel deviceOverlay(JPanel screenPositioner){
        JPanel devicePositioner;

        devicePositioner = new JPanel(new GridBagLayout());
        devicePositioner.setOpaque(false);
        devicePositioner.setSize(Style.DEVICE_SIZE);
        devicePositioner.add(screenPositioner, constraints, 0);

        return devicePositioner;
    }

    private JPanel screenOverlay(){
        JPanel screenPositioner = new JPanel(new BorderLayout());
        screenPositioner.setPreferredSize(Style.SCREEN_SIZE);
        screenPositioner.setOpaque(false);

        return screenPositioner;
    }

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

        dirPane.setDirectionPhrase("This is a thing that I wans to owahdwioajdawd");

        
    }

    public void notificationState(boolean enabled, String message) {
        notificationState(message);
        notificationState(enabled);
    }

    public void notificationState(boolean enabled) {
        notification.setVisible(enabled);
    }

    public void notificationState(String message) {
        notification.setText(message);
    }

    public DirectionPane getDirectionPane() {
        return dirPane;
    }

    public StatusPane getStatusPane(){
        return status;
    }

    public Screen getCurrentScreen() {
        return currentScreen;
    }

    public void refreshDisplay() {
        repaint();
    }

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