package teamk.xtrex;

import javax.swing.JButton;

import javax.swing.BorderFactory;

public class PrefabButton extends JButton {

    private static final long serialVersionUID = -5302854073581656523L;
    
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
    	
    	setBackground(Style.ColorScheme.CONTENT_BACK);
        setForeground(Style.ColorScheme.FONT);
        setFont(Style.uiFont);
        setBorder(BorderFactory.createLineBorder(Style.ColorScheme.BACKGROUND, 5));
        setBorder(BorderFactory.createCompoundBorder(getBorder(), BorderFactory.createLineBorder(Style.ColorScheme.CONTENT_BORDER, 2)));
    }

    public void selected(boolean isSelected) {
        if (isSelected) {
            setBackground(Style.ColorScheme.ACCENT);
            setForeground(Style.ColorScheme.SELECTED_FONT);
        } else {
            setBackground(Style.ColorScheme.CONTENT_BACK);
            setForeground(Style.ColorScheme.FONT);
        }
    }

    public void action() {
        
    }
    
}