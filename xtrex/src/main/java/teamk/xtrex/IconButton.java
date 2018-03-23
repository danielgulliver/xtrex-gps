package teamk.xtrex;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Custom extension on PrefabButtons, implements icons which can be justified to any side of the button.
 * 
 * @author Laurence Jones
 */
public class IconButton extends PrefabButton{

    private static final long serialVersionUID = 1468506541761036708L;
    
	private ImageIcon icon;
    private ImageIcon selectedIcon;

    /**
     * Custom enum for defining the position of the icon.
     */
    public enum iconPosition{

        ICON_TOP(CENTER, BOTTOM),
        ICON_RIGHT(LEFT, CENTER),
        ICON_BOTTOM(CENTER, TOP),
        ICON_LEFT(RIGHT, CENTER);

        private int verticalTextPosition;
        private int horizontalTextPosition;

        private iconPosition(int horizontalTextPosition, int verticalTextPosition){
            this.horizontalTextPosition = horizontalTextPosition;
            this.verticalTextPosition = verticalTextPosition;
        }

        /**
         * Returns the vertical position from the corresponding enum.
         * 
         * @return int verticalTextPosition
         */
        public int getVerticalPosition(){
            return verticalTextPosition;
        }

        /**
         * Returns the horizontal position from the corresponding enum.
         * 
         * @return int horizontalTextPosition
         */
        public int getHorizontalPosition(){
            return horizontalTextPosition;
        }
    }

    /**
     * Constructor for the IconButton. Configures the button using the 
     * alignments provided by the enum, and imports the icon.
     * 
     * @param displayString String that will display on the button.
     * @param iconScale integer value used to scale the icon.
     * @param iconPosition enum determining the position of the icon.
     * @param iconName string filename of the icon.
     * @param selectedIconName string filename of the icon to be displayed when the button is selected.
     */
    public IconButton(String displayString, int iconScale, iconPosition iconAlignment, String iconName, String selectedIconName){
        super(displayString);

        try {
            BufferedImage img = ImageIO.read(new File(iconName));
            icon = new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(iconScale, iconScale, Image.SCALE_SMOOTH));
            BufferedImage selectedImg = ImageIO.read(new File(selectedIconName));
            selectedIcon = new ImageIcon(new ImageIcon(selectedImg).getImage().getScaledInstance(iconScale, iconScale, Image.SCALE_SMOOTH));
        } catch(IOException e) {
            e.printStackTrace();
        }

        setIcon(icon);



        setHorizontalTextPosition(iconAlignment.getHorizontalPosition());
        setVerticalTextPosition(iconAlignment.getVerticalPosition());

    }

    /**
     * Alternative constructor for IconButton, allows the user to specify only 
     * one icon which will be used for both selected and un-selected buttons.
     * 
     * @param displayString String that will display on the button.
     * @param iconScale integer value used to scale the icon.
     * @param iconPosition enum determining the position of the icon.
     * @param iconName string filename of the icon.
     */
    public IconButton(String displayString, int iconScale, iconPosition iconAlignment, String iconName){
        this(displayString, iconScale, iconAlignment, iconName, iconName);
    }

    /**
     * Modification to the PrefabButton selection to enable the 
     * switching of icons as well as the button's colour scheme.
     * 
     * @param isSelected boolean to define it's "selected" status.
     */
    @Override
    public void selected(boolean isSelected) {
        super.selected(isSelected);
        if (isSelected) {
            setIcon(selectedIcon);
        } else {
            setIcon(icon);
        }
    }

}