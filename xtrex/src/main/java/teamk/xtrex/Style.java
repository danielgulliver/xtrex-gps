package teamk.xtrex;

import java.awt.Color;
import java.awt.Font;

public class Style {

	public static ColorScheme colour;
    public static final int FONT_SIZE = 20;
    public static final Font uiFont = new Font("Arial", Font.PLAIN, FONT_SIZE);

    public class ColorScheme{
        public final Color BACKGROUND = new Color(237,237,237); //Light grey background
        public final Color CONTENT_BORDER = new Color(220,220,220); //Medium grey outline
        public final Color CONTENT_BACK = Color.WHITE; //Titanium hwhite.
        public final Color ACCENT = new Color(249,147,33); //Orange
        public final Color FONT = new Color(113,113,113); //Mid-tone grey
        public final Color SELECTED_FONT = Color.WHITE; //Titanium hwite.
    }

    public Style(){
    }

}