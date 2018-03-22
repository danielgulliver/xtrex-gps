package teamk.xtrex;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.MappedByteBuffer;

import javax.imageio.ImageIO;
import javax.swing.*;

public class XTrexFrame extends JLayeredPane {

    private static final long serialVersionUID = 913883001958811801L;
    
	private Screen currentScreen;
    private JLabel label = new JLabel();
    private JPanel paletteOverlayPane;
    private JPanel mapScreenOverlayPane;
    private JPanel popupOverlayPane;
    private JPanel notificationOverlayPane;
    private JPanel mapOverlayPane;
    private DirectionPane dirPane;
    private StatusPane status;
    GridBagConstraints constraints = new GridBagConstraints();
    private JPanel statusContainer;

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

            label.setIcon(new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(460, 886, Image.SCALE_SMOOTH)));
            label.setSize(Style.DEVICE_SIZE);
            add(label, JLayeredPane.DEFAULT_LAYER);
            label.setOpaque(false);
            //label.setLayout(new OverlayLayout(label));
            label.setLayout(new GridBagLayout());
            //label.setAlignmentX(0.5f);

        } catch (IOException e) {
            e.printStackTrace();
        }

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
        notificationOverlayPane.setVisible(false);
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

        mapOverlayPane.setVisible(directionsEnabled && (currentScreen == Maps.getMapViewInstance()));

        currentScreen.setVisible(true);
        this.revalidate();
        this.repaint();
    }

}