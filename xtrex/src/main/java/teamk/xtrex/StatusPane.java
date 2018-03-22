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

public class StatusPane extends JPanel{
    
    private boolean satCon;
    private boolean speechCon;
    private boolean mapCon;

    private final int ICON_SIZE = 20;

    private class StatusIcon extends JLabel{

        private ImageIcon icon;
        private ImageIcon errorIcon;

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

    private JPanel container;

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

    public void satelliteAvailable(boolean status){
        satellite.online(status);
    }

    public void mapsAvailable(boolean status){
        maps.online(status);
    }

    public void speechAvailable(boolean status){
        speech.online(status);
    }

}