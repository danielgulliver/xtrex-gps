package teamk.xtrex;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

public class Style {

    public static final int FONT_SIZE = 20;
    public static final Font uiFont = new Font("Arial", Font.PLAIN, FONT_SIZE);
    public static final Dimension DEVICE_SIZE = new Dimension(460,886);
    public static final Dimension SCREEN_SIZE = new Dimension(342,418);

    public static class ColorScheme{
        public static final Color BACKGROUND = new Color(237,237,237); //Light grey background
        public static final Color CONTENT_BORDER = new Color(220,220,220); //Medium grey outline
        public static final Color CONTENT_BACK = Color.WHITE; //Titanium hwhite.
        public static final Color ACCENT = new Color(249,147,33); //Orange
        public static final Color FONT = new Color(113,113,113); //Mid-tone grey
        public static final Color SELECTED_FONT = Color.WHITE; //Titanium hwite.

    }

    public Style(){
    }

}