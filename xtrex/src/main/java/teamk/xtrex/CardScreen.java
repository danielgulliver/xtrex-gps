package teamk.xtrex;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

/**
 * The CardScreen class provides functionality for drawing text on the screen, organised into neat boxes called
 * 'cards'. Each card has a label, which is a heading, and a value. The label and heading are centered in the box.
 * <p>
 * Simply call <code>addCard()</code> with a label and value for as many cards as you would like to add. The number of
 * cards to display on the screen is calculated dynamically and fills all available space on the screen.
 * <p>
 * Beware that while there is no strict limit on the number of cards that may be added to a screen, adding more than
 * four cards can cause the text to overflow the card.
 * 
 * @author Daniel Gulliver
 */
public abstract class CardScreen extends Screen {
    private static final long serialVersionUID = 1738845911297955695L;

    private ArrayList<Style.Card> cards;

    /**
     * Update the value of the cards in the list. It is expected that sub-classes will override this method and call
     * its super method. Cards should be added using the <code>addCard()</code> method inside this method.
     */
	public void updateCards() {
        cards = new ArrayList<Style.Card>();
        cards.clear();
    }

    /**
     * Add a card to the screen, with the given label and value.
     * @param String label -- the heading to display
     * @param String value -- the value to display
     */
    public void addCard(String label, String value) {
        Style.Card card = (new Style()).new Card(label, value);
        cards.add(card);
    }

	@Override
	public void onMinusButtonPressed() {
		
	}

	@Override
	public void onPlusButtonPressed() {
		
	}

	@Override
	public void onSelectButtonPressed() {
		
    }
    
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        updateCards();

        int numCards = cards.size();
        int cardHeight = (Style.SCREEN_SIZE.height - (Style.Card.MARGIN_BOTTOM * (numCards + 1))) / numCards;

        // Height of the font from the baseline to the ascender line.
        final int textHeight = g2d.getFontMetrics(Style.uiFont).getAscent();
        final int lineSpacing = (int) textHeight / 2;

        // Set the rendering hint to improve text rendering.
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Clear the screen.
        g2d.clearRect(0, 0, Style.SCREEN_SIZE.width, Style.SCREEN_SIZE.height);

        // Set the font and colour per the colour scheme.
        g2d.setFont(Style.uiFont);

        // Draw as many containing boxes as we need.
        for (int i = 0; i < numCards; i++) {
            int cardStartHeight = Style.Card.MARGIN_BOTTOM + i*cardHeight;
            if (i > 0) cardStartHeight += i*Style.Card.MARGIN_BOTTOM;
            
            int labelStartHeight = cardStartHeight + ((cardHeight - (2*textHeight + lineSpacing)) / 2) + textHeight;
            if (i > 0) labelStartHeight -= Style.Card.MARGIN_BOTTOM;

            int valueStartHeight = labelStartHeight + textHeight + lineSpacing;

            // Draw a card.
            g2d.setColor(Style.ColorScheme.CONTENT_BACK);
            g2d.fillRect(Style.Card.MARGIN_BOTTOM, cardStartHeight, Style.Card.CARD_WIDTH, cardHeight);
            g2d.setColor(Style.ColorScheme.CONTENT_BORDER);
            g2d.setStroke(new BasicStroke(Style.Card.OUTLINE_THICKNESS));
            g2d.drawRect(Style.Card.MARGIN_BOTTOM, cardStartHeight, Style.Card.CARD_WIDTH, cardHeight);

            // Draw the card text.
            g2d.setColor(Style.ColorScheme.FONT);
            g2d.drawString(cards.get(i).getLabel(), Style.Card.PADDING_LEFT, labelStartHeight);
            g2d.drawString(cards.get(i).getValue(), Style.Card.PADDING_LEFT, valueStartHeight);
        }
    }
}