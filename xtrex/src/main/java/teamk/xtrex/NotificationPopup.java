package teamk.xtrex;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Custom Jpanel used as a notification popup. Message is configurable.
 * 
 * @author Laurence Jones
 */
public class NotificationPopup extends JPanel{

    private static final long serialVersionUID = -7356540919824947036L;
	private JLabel notificationContent;


    /**
     * Constructor for NotificationPopup, configures a panel for use in the device UI.
     * 
     * @param inputMessage String use to set the initial message for the popup.
     */
    NotificationPopup(String inputMessage){
        notificationContent = new JLabel();
        notificationContent.setText(inputMessage);
        notificationContent.setBackground(Style.ColorScheme.ACCENT);
        notificationContent.setForeground(Style.ColorScheme.SELECTED_FONT);
        setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(5,5,5,5),BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Style.ColorScheme.CONTENT_BORDER, 2), BorderFactory.createLineBorder(notificationContent.getBackground(), 3))));
        //setBorder(new LineBorder(Style.ColorScheme.CONTENT_BORDER, 5, true));
        
        notificationContent.setOpaque(true);
        setOpaque(false);
        notificationContent.setFont(Style.uiFont);
        notificationContent.setHorizontalAlignment(JLabel.CENTER);
        setSize(new Dimension(Style.SCREEN_SIZE.width, 20));
        setLayout(new BorderLayout());
        add(notificationContent, BorderLayout.CENTER);
    }

    /**
     * Sets the text of the popup.
     * 
     * @param inputMessage String taken in to use for the popup message.
     */
    public void setText(String inputMessage){
        notificationContent.setText(inputMessage);
    }
    
}