package teamk.xtrex;

import java.util.ArrayList;
import java.util.List;

/**
 * Standardised button control system. Takes in a list of PrefabButtons 
 * which it will then utilise when on-device controls are used. Uses the selected() methods
 * inside the PrefabButton to visually change the buttons to show which is selected on screen.
 * 
 * Designed for ease of use, so that button controls can easily be implemented in screens
 * by using the forward(), back() and click() methods attached to the on-device buttons.
 * 
 * @author Laurence Jones
 */
public class SelectionController {
    private ArrayList<PrefabButton> UIElements;
    private int position;
    
    /**
     * Calls the selected() method in the button in the Array at the current position.
     * 
     * @param isSelected boolean to set it's selected state. 
     */
    private void selected(boolean isSelected) {
        UIElements.get(position-1).selected(isSelected);
    }

    /**
     * Constructor for the SelectionController. 
     * Initialises the list of buttons provided, sets 
     * position to 1 and selects the corresponding button.
     * 
     * @param buttons List of PrefabButtons, in the order they are to be iterated through.
     */
    public SelectionController(List<PrefabButton> buttons) {
        UIElements = new ArrayList<PrefabButton>();
        UIElements.addAll(buttons);
        position = 1;
        selected(true);
    }

    /**
     * Forward iteration, moves to the next button in the list.
     * Deselects previously selected button and selects new button.
     * Wraps around to beginning of the list if it reaches the end.
     */
    public void forward() {
        selected(false);
        if (position == UIElements.size())
            position = 1;
        else
            position += 1;
        selected(true);
    }

    /**
     * Backward iteration, moves to the previous button in the list.
     * Deselects previously selected button and selects new button.
     * Wraps around to end of the list if it reaches the beginning.
     */
    public void back() {
        selected(false);
        if (position == 1)
            position = UIElements.size();
        else
            position -= 1;
        selected(true);
    }

    /**
     * Calls the click() method in the currently selected button.
     */
    public void click() {
        UIElements.get(position-1).action();
    }

    /**
     * Resets the position of the controller to 1.
     */
    public void reset() {
        selected(false);
        position = 1;
        selected(true);
    }

}