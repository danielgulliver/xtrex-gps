package teamk.xtrex;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class XTrexFrame extends JLayeredPane {

    private Screen currentScreen;
    private JLabel label = new JLabel();
    private JPanel deviceOverlayPane;
    private JPanel screenOverlayPane;
    private JPanel directionsPane;
    GridBagConstraints constraints = new GridBagConstraints();

    private PrefabButton directionPhrase = new PrefabButton("Route Guidance Inactive so I'll type some stuff here to see what ");
    private PrefabButton notification = new PrefabButton("Oh hey there");

    private boolean directionsEnabled = false;

    public XTrexFrame(){
        this.setPreferredSize(Style.DEVICE_SIZE);
        constraints.insets = new Insets(40,0,0,3); // Offsets to position the display correctly in the xtrex frame.

        try{
            BufferedImage img = ImageIO.read(new File("bg.png"));
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

        //GridBagConstraints directionConstraints = new GridBagConstraints();
        //directionConstraints.fill = GridBagConstraints.BOTH;

        directionPhrase.setFont(new Font(Style.uiFont.getFamily(), Font.BOLD, 15));

        directionsPane = new JPanel(new BorderLayout());
        directionsPane.setPreferredSize(new Dimension(Style.SCREEN_SIZE.width, 75));
        directionsPane.add(directionPhrase, BorderLayout.CENTER);
        directionsPane.setVisible(false);

        screenOverlayPane = new JPanel(new BorderLayout());
        screenOverlayPane.setPreferredSize(Style.SCREEN_SIZE);
        screenOverlayPane.setOpaque(false);
        screenOverlayPane.add(directionsPane, BorderLayout.SOUTH);
        notification.setVisible(false);
        screenOverlayPane.add(notification, BorderLayout.NORTH);
        
        deviceOverlayPane = new JPanel(new GridBagLayout());
        deviceOverlayPane.setOpaque(false);
        deviceOverlayPane.setSize(Style.DEVICE_SIZE);
        deviceOverlayPane.add(screenOverlayPane, constraints, 0);

        add(deviceOverlayPane, JLayeredPane.PALETTE_LAYER);


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

    public void directionState(boolean enabled, String message) {
        directionState(message);
        directionState(enabled);
    }

    public void directionState(boolean enabled) {
        directionsEnabled = enabled;
        directionsPane.setVisible(directionsEnabled && (currentScreen == Maps.getMapViewInstance()));
    }

    public void directionState(String message) {
        directionPhrase.setText(message);
    }

    public Screen getCurrentScreen() {
        return currentScreen;
    }

    public void refreshDisplay() {
        repaint();
    }

    public void setScreen(Screen screen) {
		if (currentScreen != null) label.remove(currentScreen);
        currentScreen = screen;
        currentScreen.setMinimumSize(Style.SCREEN_SIZE);
        //currentScreen.setAlignmentX(0.49f);
        //currentScreen.setAlignmentY(0.56f);
        currentScreen.setBackground(Style.ColorScheme.BACKGROUND);
        label.add(currentScreen, constraints, 0);
        
        directionsPane.setVisible(directionsEnabled && (currentScreen == Maps.getMapViewInstance()));

        currentScreen.setVisible(true);
		this.revalidate();
		this.repaint();
    }

}