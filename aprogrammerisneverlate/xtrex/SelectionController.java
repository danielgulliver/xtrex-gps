package aprogrammerisneverlate.xtrex;

import java.util.ArrayList;

public class SelectionController {
    private ArrayList<PrefabButton> UIElements;
    private int position;
    
    private void Selected(boolean isSelected){
        UIElements.get(position-1).Selected(isSelected);
    }

    public void Forward(){
        Selected(false);
        if (position == UIElements.size())
            position = 1;
        else
            position += 1;
        Selected(true);
    }

    public void Back(){
        Selected(false);
        if (position == 1)
            position = UIElements.size();
        else
            position -= 1;
        Selected(true);
    }

    public void Click(){
        UIElements.get(position-1).Action();
    }

}