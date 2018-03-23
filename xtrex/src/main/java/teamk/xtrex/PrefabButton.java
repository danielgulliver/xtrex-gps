package teamk.xtrex;

import javax.swing.JButton;

import javax.swing.BorderFactory;

/**
 * Custom extension from JButton, adds custom styling to match the aesthetic of the device, 
 * adds the action() and selected() functions for use in the SelectionController system.
 */
public class PrefabButton extends JButton {

    private static final long serialVersionUID = -5302854073581656523L;
    
	public String displayedString = "";
    
    /**
     * Constructor for the PrefabButtons. Configures the aesthetic of the button and sets the
     * text to the provided string.
     * 
     * @param displayString the string the button label will be set to.
     */
    public PrefabButton(String displayString) {
    	this.displayedString = displayString;
    	setText(displayedString);
    	
    	setBackground(Style.ColorScheme.CONTENT_BACK);
        setForeground(Style.ColorScheme.FONT);
        setFont(Style.uiFont);
        setBorder(BorderFactory.createLineBorder(Style.ColorScheme.BACKGROUND, 5));
        setBorder(BorderFactory.createCompoundBorder(getBorder(), BorderFactory.createLineBorder(Style.ColorScheme.CONTENT_BORDER, 2)));
    }

    /**
     * Alters the aesthetic of the button when the button's "selected" value is true.
     * 
     * @param isSelected boolean used to chcoose the "selected" status.
     */
    public void selected(boolean isSelected) {
        if (isSelected) {
            setBackground(Style.ColorScheme.ACCENT);
            setForeground(Style.ColorScheme.SELECTED_FONT);
        } else {
            setBackground(Style.ColorScheme.CONTENT_BACK);
            setForeground(Style.ColorScheme.FONT);
        }
    }

    /**
     * Stub function, to be extended by all extensions of PrefabButtons to add their own functionality to it.
     * Called by the SelectionController system when a button is "clicked".
     */
    public void action() {
        
    }
    
}