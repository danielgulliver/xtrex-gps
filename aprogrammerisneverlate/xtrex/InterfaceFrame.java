package aprogrammerisneverlate.xtrex;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class InterfaceFrame extends JFrame {
    public InterfaceFrame(){
        setResizable(false);
        setContentPane(new JLabel(new ImageIcon("img/background.png")));
    }
} 