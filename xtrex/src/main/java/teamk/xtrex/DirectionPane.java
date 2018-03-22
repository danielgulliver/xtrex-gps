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

        directionPhrase = new DirectionLabel("Go left around the corner and test", 15);
        distancePhrase = new DirectionLabel("40m", 30);

        container = new JPanel(new GridBagLayout());
        container.setBackground(Style.ColorScheme.CONTENT_BACK);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 1;
        c.weighty = 1;
        c.weightx = 0.5;
        container.add(directionPhrase, c);
        c.weightx = 1;
        //c.gridwidth = GridBagConstraints.REMAINDER;
        container.add(distancePhrase,c);
        add(container);
    }

    public void setDirectionPhrase(String direction){
        directionPhrase.setText(direction);
    }

    public void setDistance(int distance){
        distancePhrase.setText(Integer.toString(distance)+"m");
    }

    public void visible(boolean enabled){
        setVisible(enabled);
    }

}