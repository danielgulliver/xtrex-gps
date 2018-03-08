package aprogrammerisneverlate.xtrex;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class XTrexFrame extends JPanel {

    private Screen currentScreen;
    JLabel label = null;

    public XTrexFrame(){
        //this.setBackground(new Color(1,1,1,0));
        this.setPreferredSize(new Dimension(662, 1275));

        try{
            BufferedImage img = ImageIO.read(new File("bg.png"));
            label = new JLabel(new ImageIcon(img));
            add(label);
            label.setOpaque(false);
            label.setLayout(new OverlayLayout(label));
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
        currentScreen.setMaximumSize(new Dimension(490,600));
        currentScreen.setAlignmentX(0.49f);
        currentScreen.setAlignmentY(0.56f);
        currentScreen.setBackground(Color.gray);
        label.add(currentScreen);
        
        currentScreen.setVisible(true);
		this.revalidate();
		this.repaint();
	}

}