package teamk.xtrex;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;


/**
 * Custom panel, displays three icons to provide connectivity to GPS, Speech and Map services.
 * Provides ability to change the icons between green and red (available and unavailable).
 * 
 * @author Laurence Jones
 */
public class StatusPane extends JPanel{

    private static final long serialVersionUID = 5046182138777298365L;

	private final int ICON_SIZE = 20;

    /**
     * Custom JLabel, stores two icons and includes a custom method to switch between them.
     */
    private class StatusIcon extends JLabel{

        private static final long serialVersionUID = -5098623875356784838L;
		private ImageIcon icon;
        private ImageIcon errorIcon;

        /**
         * Constructor for StatusIcon, takes in the two icon names and a 
         * resolution scale at which it will display them in the panel.
         * 
         * @param iconName primary "active" icon filename.
         * @param altIconName secondary "disabled" icon filename.
         * @param iconScale integer value used for height and width of the icon.
         */
        public StatusIcon(String iconName, String altIconName, int iconScale){
            try{

                ImageIcon img = new ImageIcon(new ImageIcon(ImageIO.read(new File(iconName))).getImage().getScaledInstance(iconScale, iconScale, Image.SCALE_SMOOTH));
                ImageIcon altImg = new ImageIcon(new ImageIcon(ImageIO.read(new File(altIconName))).getImage().getScaledInstance(iconScale, iconScale, Image.SCALE_SMOOTH));
                
                icon = img;
                errorIcon = altImg;
                setIcon(icon);
                setMinimumSize(new Dimension(iconScale, iconScale));
                setPreferredSize(new Dimension(iconScale, iconScale));
                setMaximumSize(new Dimension(iconScale, iconScale));

            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * Switching function, used to switch between the two icons.
         * 
         * @param status boolean value, true sets it to the "active" icon, false sets it to "disabled".
         */
        public void online(boolean status){
            if (status){
                setIcon(icon);
            } else {
                setIcon(errorIcon);
            }
        }

    }

    private StatusIcon satellite;
    private StatusIcon speech;
    private StatusIcon maps;

    private JPanel container; //Positioning container for the icons.

    /**
     * Constructor for the StatusPane, configures the panel to display the icons correctly.
     */
    public StatusPane(){
        container = new JPanel();
        container.setLayout(new GridLayout(1,0,5,5));
        setPreferredSize(new Dimension(100, 30));
        setOpaque(false);
        setBorder(new EmptyBorder(5,5,5,5));

        satellite = new StatusIcon("icons/satelliteOnline.png", "icons/satelliteOffline.png", ICON_SIZE);
        speech = new StatusIcon("icons/speechOnline.png", "icons/speechOffline.png", ICON_SIZE);
        maps = new StatusIcon("icons/mapOnline.png", "icons/mapOffline.png", ICON_SIZE);


        add(satellite);
        add(speech);
        add(maps);

    }

    /**
     * Switcher method for the satellite icon.
     * 
     * @param status boolean value to control the icon.
     */
    public void satelliteAvailable(boolean status){
        satellite.online(status);
    }

    /**
     * Switcher method for the maps icon.
     * 
     * @param status boolean value to control the icon.
     */
    public void mapsAvailable(boolean status){
        maps.online(status);
    }

    /**
     * Switcher method for the speech icon.
     * 
     * @param status boolean value to control the icon.
     */
    public void speechAvailable(boolean status){
        speech.online(status);
    }

}