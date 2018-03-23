package teamk.xtrex;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


/**
 * Custom panel, displays two content panes to show the upcoming direction phrase and the distance to the next node.
 * Provides ability to update the direction phrase and the distance to node.
 * 
 * @author Laurence Jones
 */
public class DirectionPane extends JPanel{
    private static final long serialVersionUID = 6313613211796159252L;
	private JPanel container;
    
    /**
     * Custom JLabel definition for use in the direction pane. Configures the labels to match the UI aesthetic and
     * implements logic to text-wrap the text contextually.
     * 
     * @param label String that will be displayed in the label
     * @param size Int the font size that the label will be displayed at.
     */
    private class DirectionLabel extends JLabel{
        private static final long serialVersionUID = -7173025080827683931L;

		DirectionLabel(String label, int size){
            super(label);
            setFont(new Font(Style.uiFont.getFamily(), Style.uiFont.getStyle(), size));
            setForeground(Style.ColorScheme.FONT);
            setBorder(BorderFactory.createCompoundBorder(new LineBorder(Style.ColorScheme.CONTENT_BORDER, 2), new EmptyBorder(5,5,5,5)));
            setHorizontalAlignment(CENTER);
            setVerticalAlignment(CENTER);
        }

        /**
         * Setter function used to set the label phrase. Includes logic to wrap the text to multiple lines,
         * automatically adjusts the alignment mode of the text box to accomodate.
         * 
         * @param message String to be used for the label.
         */
        public void setPhrase(String message){
            if (message.length() > 75){
                setVerticalAlignment(TOP);
            } else {
                setVerticalAlignment(CENTER);
            }
            message = ("<html>" + message + "</html>");
            setText(message);
            
        }

        /**
         * Used specifically for the direction phrase, sets the width to 
         * fit the remaining space left after the distance box resizes.
         */
        public void resize(){
            setPreferredSize(new Dimension(Style.SCREEN_SIZE.width - distancePhrase.getPreferredSize().width - 10, 75));
        }

    }

    private DirectionLabel directionPhrase;
    private DirectionLabel distancePhrase;

    /**
     * Constructor for the direction pane. Configures the labels and components for use in the UI.
     */
    DirectionPane(){
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(5,5,5,5));
        setPreferredSize(new Dimension(Style.SCREEN_SIZE.width, 75));
        setOpaque(false);

        directionPhrase = new DirectionLabel("", 15);
        distancePhrase = new DirectionLabel("0m", 30);

        container = new JPanel(new BorderLayout());
        container.setBackground(Style.ColorScheme.CONTENT_BACK);
        
        directionPhrase.resize();

        container.add(directionPhrase, BorderLayout.WEST);
        container.add(distancePhrase, BorderLayout.EAST);

        add(container);
    }

    /**
     * Sets the phrase of the direction component.
     * 
     * @param direction String used to display in the component.
     */
    public void setDirectionPhrase(String direction){
        directionPhrase.setPhrase(direction);
    }

    /**
     * Sets the distance in the panel, adds a meter unit "m" to the end of the int.
     * 
     * @param distance integer used to set the distance.
     */
    public void setDistance(int distance){
        distancePhrase.setPhrase(Integer.toString(distance)+"m");
        directionPhrase.resize();
    }

    /**
     * Sets the visibility of the direction panel.
     * 
     * @param enabled boolean used to specify visibility.
     */
    public void visible(boolean enabled){
        setVisible(enabled);
    }

}