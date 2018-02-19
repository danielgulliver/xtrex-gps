package aprogrammerisneverlate.xtrex;

import java.util.ArrayList;

public class SelectionController {
    private ArrayList<PrefabButton> UIElements;
    private int position;
    
    private void selected(boolean isSelected) {
        UIElements.get(position-1).selected(isSelected);
    }

    public SelectionController() {
        
    }

    public void forward() {
        selected(false);
        if (position == UIElements.size())
            position = 1;
        else
            position += 1;
        selected(true);
    }

    public void back() {
        selected(false);
        if (position == 1)
            position = UIElements.size();
        else
            position -= 1;
        selected(true);
    }

    public void click() {
        UIElements.get(position-1).action();
    }

}