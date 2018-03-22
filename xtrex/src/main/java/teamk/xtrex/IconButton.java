package teamk.xtrex;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class IconButton extends PrefabButton{

    private static final long serialVersionUID = 1468506541761036708L;
    
	private ImageIcon icon;
    private ImageIcon selectedIcon;
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

        public int getVerticalPosition(){
            return verticalTextPosition;
        }

        public int getHorizontalPosition(){
            return horizontalTextPosition;
        }
    }

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

    public IconButton(String displayString, int iconScale, iconPosition iconAlignment, String iconName){
        this(displayString, iconScale, iconAlignment, iconName, iconName);
    }

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