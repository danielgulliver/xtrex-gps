package aprogrammerisneverlate.xtrex;

import javax.swing.JButton;
import javax.swing.ImageIcon;

public class PrefabButton extends JButton {
    public String displayedString = "";

    public PrefabButton(String displayString, int x, int y, int width, int height){
        displayString = displayString == null ? "" : displayString;
        width = width == 0 ? 40 : width;
        height = height == 0 ? 40 : height;

        setBounds(x,y,width,height);
        displayedString = displayString;
        setText(displayedString);
    }
    
}