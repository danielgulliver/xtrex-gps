package teamk.xtrex;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

public class Style {

    public static final int FONT_SIZE = 20;
    public static final Font uiFont = new Font("Arial", Font.PLAIN, FONT_SIZE);
    public static final Dimension DEVICE_SIZE = new Dimension(460,886);
    public static final int SCREEN_WIDTH = 342;
    public static final Dimension SCREEN_SIZE = new Dimension(SCREEN_WIDTH, 418);

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

    /**
     * The Card class is used to draw a label and a value on top of a card, which is part of a CardScreen. Cards are
     * used to show information on the screen.
     * @author Daniel Gulliver
     */
    public class Card {
        
        private String label;
        private String value;

        public static final int MARGIN_BOTTOM = 5;
        public static final int CARD_WIDTH = SCREEN_WIDTH - 2 * MARGIN_BOTTOM;
        public static final int PADDING_LEFT = 50;
        public static final int OUTLINE_THICKNESS = 2;

        Card(String label, String value) {
            this.label = label;
            this.value = value;
        }

        /**
         * Return the label on the card.
         * @return the label on the card
         */
        public String getLabel() {
            return this.label;
        }

        /**
         * Return the value on the card.
         * @return the value on the card
         */
        public String getValue() {
            return this.value;
        }
    }

}