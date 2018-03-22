package teamk.xtrex;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

public abstract class CardScreen extends Screen {
    private static final long serialVersionUID = 1738845911297955695L;

    private ArrayList<Style.Card> cards;

	public void updateCards() {
        cards = new ArrayList<Style.Card>();
        cards.clear();
    }

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

        final int textHeight = g2d.getFontMetrics(Style.uiFont).getAscent(); // Height of the font from the baseline to the ascender line.
        final int lineSpacing = (int) textHeight / 2;

        // Set the rendering hint to improve text rendering.
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Clear the screen.
        g2d.clearRect(0, 0, Style.SCREEN_SIZE.width, Style.SCREEN_SIZE.height);

        updateCards();

        int numCards = cards.size();
        int cardHeight = (Style.SCREEN_SIZE.height - (Style.Card.MARGIN_BOTTOM * (numCards + 1))) / numCards;

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