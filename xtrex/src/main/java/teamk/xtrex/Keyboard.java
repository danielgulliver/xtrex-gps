package teamk.xtrex;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
* A keyboard is a collection of buttons which enables the user to enter text into the destination field.
* Buttons are added to each keyboard during construction and cannot be added on the fly.
*/
public class Keyboard extends JPanel implements ActionListener {
    private static final long serialVersionUID = 6403469078457676699L;
    public SelectionController sc;
    public ArrayList<PrefabButton> buttons;

    /**
     * Construct a Keyboard.
     * @param buttons the ArrayList of PrefabButtons to add to the keyboard
     * @param rows the number of rows of buttons
     * @param cols the number of columns of buttons
     */
    Keyboard(ArrayList<PrefabButton> buttons, int rows, int cols) {
        setLayout(new GridLayout(rows, cols));
        buttons.addAll(buttons);
        sc = new SelectionController(buttons);

        // Add the buttons to the keyboard.
        for (PrefabButton button : buttons) {
            button.addActionListener(this);
            add(button);
        }
    }

    public void actionPerformed(ActionEvent e) {
        // Perform a button's associated action when it is clicked.
        if (e.getSource() instanceof PrefabButton) {
            PrefabButton sourceButton = (PrefabButton) e.getSource();
            sourceButton.action();
        }
    }
}