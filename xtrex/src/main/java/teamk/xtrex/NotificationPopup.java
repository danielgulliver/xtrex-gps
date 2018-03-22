package teamk.xtrex;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class NotificationPopup extends JPanel{

    private JLabel notificationContent;
    private String message;
    private final String html1 = "<html><body style='width: ";
    private final String html2 = "px'";
    private final int px = Style.SCREEN_SIZE.width - 200;

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

    public void setText(String inputMessage){
        notificationContent.setText(inputMessage);
    }
    
}