package aprogrammerisneverlate.xtrex;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class XTrexFrame extends JFrame {
    public XTrexFrame(){
        setResizable(false);
        setContentPane(new JLabel(new ImageIcon("img/background.png")));
    }
}