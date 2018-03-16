package teamk.xtrex;

import javax.swing.JButton;

import javax.swing.BorderFactory;

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
    	
    	setBackground(Style.colour.CONTENT_BACK);
        setForeground(Style.colour.FONT);
        setFont(Style.uiFont);
        setBorder(BorderFactory.createLineBorder(Style.colour.BACKGROUND, 5));
        setBorder(BorderFactory.createCompoundBorder(getBorder(), BorderFactory.createLineBorder(Style.colour.CONTENT_BORDER, 2)));
    }

    public void selected(boolean isSelected) {
        if (isSelected) {
            setBackground(Style.colour.ACCENT);
            setForeground(Style.colour.SELECTED_FONT);
        } else {
            setBackground(Style.colour.CONTENT_BACK);
            setForeground(Style.colour.FONT);
        }
    }

    public void action() {
        
    }
    
}