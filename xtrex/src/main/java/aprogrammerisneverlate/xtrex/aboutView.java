package aprogrammerisneverlate.xtrex;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class AboutView extends Screen{

    private JLabel logoImg = new JLabel();

    private AboutView(){
        setLayout(new GridBagLayout());


    }
    /**
	 * Return the single instance of AboutView held by this class
     * in a thread safe manner.
	 * @return the single instance of AboutView
	 */
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
