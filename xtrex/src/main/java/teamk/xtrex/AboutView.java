package teamk.xtrex;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Selects the version of Ublox 7 appropriate for the Clients OS
 * Parses GPS input to variables and output log.
 * 
 * @author Connor Harris
 * @version Sprint 2 
 */

public class AboutView extends Screen{

    private static final long serialVersionUID = -6580960841880839449L;
    
	// private JLabel logoImg = new JLabel();
    // private GridBagConstraints constraints = new GridBagConstraints();
    // private PrefabButton version = new PrefabButton("Xtrex Sprint 2");
    // private PrefabButton compName = new PrefabButton("Team K");

    // private AboutView(){
    //     setLayout(new GridBagLayout());
    //     try{
    //         BufferedImage img = ImageIO.read(new File("img/exlogo.png"));
    //         logoImg.setIcon(new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
    //         constraints.ipady = 10;
    //         constraints.gridx = 0;
    //         constraints.gridy = 0;
    //         add(logoImg, constraints);
    //         logoImg.setOpaque(false);
    //         constraints.gridy = 1;
    //         constraints.fill = GridBagConstraints.HORIZONTAL;
    //         add(version,constraints);
    //         constraints.gridy = 2;
    //         add(compName,constraints);
    //         //label.setLayout(new OverlayLayout(label));
    //         //label.setAlignmentX(0.5f);
            
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }


    // }
    // /**
	//  * Return the single instance of AboutView held by this class
    //  * in a thread safe manner.
	//  * @return the single instance of AboutView
    //  */

    private JLabel content;

    private AboutView(){

        setLayout(new BorderLayout());
        content = new JLabel();
        try {
            content.setIcon(new ImageIcon(new ImageIcon(ImageIO.read(new File("img/about.png"))).getImage().getScaledInstance(Style.SCREEN_SIZE.width, Style.SCREEN_SIZE.height, Image.SCALE_SMOOTH)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        content.setPreferredSize(Style.SCREEN_SIZE);
        add(content, BorderLayout.CENTER);
        
    }
    
    private static class Loader {
        static final AboutView instance = new AboutView();
    }

    public static AboutView getInstance() {
        return Loader.instance;
	}

	public void onMinusButtonPressed() {}

	public void onPlusButtonPressed() {}

	public void onSelectButtonPressed() {}


} 
