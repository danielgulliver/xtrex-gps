package teamk.xtrex;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class DirectionPane extends JPanel{
    private JPanel container;
    
    private class DirectionLabel extends JLabel{
        DirectionLabel(String label, int size){
            super(label);
            setFont(new Font(Style.uiFont.getFamily(), Style.uiFont.getStyle(), size));
            setForeground(Style.ColorScheme.FONT);
            setBorder(BorderFactory.createCompoundBorder(new LineBorder(Style.ColorScheme.CONTENT_BORDER, 2), new EmptyBorder(5,5,5,5)));
            setHorizontalAlignment(CENTER);
            setVerticalAlignment(CENTER);
        }

        public void setPhrase(String message){
            if (message.length() > 75){
                setVerticalAlignment(TOP);
            } else {
                setVerticalAlignment(CENTER);
            }
            message = ("<html>" + message + "</html>");
            setText(message);
            
        }

        public void resize(){
            setPreferredSize(new Dimension(Style.SCREEN_SIZE.width - distancePhrase.getPreferredSize().width - 10, 75));
            //setPreferredSize(distancePhrase.getPreferredSize());
        }

    }

    private DirectionLabel directionPhrase;
    private DirectionLabel distancePhrase;

    private JButton testbutton1 = new JButton("test1");
    private JButton testbutton2 = new JButton("test2");

    DirectionPane(){
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(5,5,5,5));
        setPreferredSize(new Dimension(Style.SCREEN_SIZE.width, 75));
        setOpaque(false);

        directionPhrase = new DirectionLabel("", 15);
        distancePhrase = new DirectionLabel("0m", 30);

        //container = new JPanel(new GridBagLayout());
        container = new JPanel(new BorderLayout());
        container.setBackground(Style.ColorScheme.CONTENT_BACK);
        GridBagConstraints c = new GridBagConstraints();
        // c.fill = GridBagConstraints.BOTH;
        // c.gridwidth = 1;
        // c.weighty = 1;
        // c.weightx = 0.5;
        // container.add(distancePhrase, c);
        // c.weightx = 1;
        //c.gridwidth = GridBagConstraints.REMAINDER;
        //container.add(directionPhrase,c);
        
        directionPhrase.resize();

        container.add(directionPhrase, BorderLayout.WEST);
        container.add(distancePhrase, BorderLayout.EAST);

        add(container);
    }

    public void setDirectionPhrase(String direction){
        directionPhrase.setPhrase(direction);
    }

    public void setDistance(int distance){
        distancePhrase.setPhrase(Integer.toString(distance)+"m");
        directionPhrase.resize();
    }

    public void visible(boolean enabled){
        setVisible(enabled);
    }

}