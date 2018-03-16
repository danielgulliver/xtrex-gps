package teamk.xtrex;

import java.awt.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class XTrexFrame extends JPanel {

    private Screen currentScreen;
    JLabel label = new JLabel();
    GridBagConstraints constraints = new GridBagConstraints();

    public XTrexFrame(){
        //this.setBackground(new Color(1,1,1,0));
        //this.setPreferredSize(new Dimension(662, 1275));
        this.setPreferredSize(new Dimension(460, 886));
        constraints.insets = new Insets(40,0,0,3); // Offsets to position the display correctly in the xtrex frame.

        try{
            BufferedImage img = ImageIO.read(new File("bg.png"));
            label.setIcon(new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(460, 886, Image.SCALE_SMOOTH)));
            add(label);
            label.setOpaque(false);
            //label.setLayout(new OverlayLayout(label));
            label.setLayout(new GridBagLayout());
            //label.setAlignmentX(0.5f);
            
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        currentScreen.setMinimumSize(new Dimension(342,418));
        //currentScreen.setAlignmentX(0.49f);
        //currentScreen.setAlignmentY(0.56f);
        currentScreen.setBackground(Style.ColorScheme.BACKGROUND);
        label.add(currentScreen, constraints);
        
        currentScreen.setVisible(true);
		this.revalidate();
		this.repaint();
	}

}