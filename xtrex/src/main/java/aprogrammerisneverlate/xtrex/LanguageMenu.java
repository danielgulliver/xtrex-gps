package aprogrammerisneverlate.xtrex;

import java.awt.GridLayout;

public class LanguageMenu extends Screen {

    private static LanguageMenu languageMenu = null;

	@Override
	public void onMinusButtonPressed() {
		
	}

	@Override
	public void onPlusButtonPressed() {
		
	}

	@Override
	public void onSelectButtonPressed() {
		
    }
    
    private LanguageMenu() {
        setLayout(new GridLayout(5,1));
    }

    public static LanguageMenu getInstance() {
        if (languageMenu == null) {
            languageMenu = new LanguageMenu();
        }
        return languageMenu;
    }

}