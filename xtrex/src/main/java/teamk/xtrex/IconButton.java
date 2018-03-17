package teamk.xtrex;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class IconButton extends PrefabButton{

    private ImageIcon icon;

    public IconButton(String displayString, String iconName, int iconScale){
        super(displayString);

        try {
            BufferedImage img = ImageIO.read(new File("exlogo.png"));
            icon = new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(iconScale, iconScale, Image.SCALE_SMOOTH));
        } catch(IOException e) {
            e.printStackTrace();
        }

        setIcon(icon);
        setHorizontalTextPosition(CENTER);
        setVerticalTextPosition(BOTTOM);

    }

}