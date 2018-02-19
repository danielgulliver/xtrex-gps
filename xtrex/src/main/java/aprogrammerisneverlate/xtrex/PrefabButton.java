package aprogrammerisneverlate.xtrex;

import javax.swing.JButton;

import java.awt.Color;

import javax.swing.ImageIcon;

public class PrefabButton extends JButton {
    public String displayedString = "";

    /*public PrefabButton(String displayString, int x, int y, int width, int height){
        displayString = displayString == null ? "" : displayString;
        width = width == 0 ? 40 : width;
        height = height == 0 ? 40 : height;

        setBounds(x,y,width,height);
        displayedString = displayString;
    }*/
    
    public PrefabButton(String displayString) {
    	this.displayedString = displayString;
    	setText(displayedString);
    	
    	setBackground(Color.white);
        setForeground(new Color(113,113,113));
    }

    public void Selected(boolean isSelected) {
        if (isSelected) {
            setBackground(new Color(255,153,0)); //Orange
            setForeground(Color.white);
        } else {
            setBackground(Color.white);
            setForeground(new Color(113,113,113)); //Mid-tone Gray
        }
    }

    public void Action(){
        
    }
    
}