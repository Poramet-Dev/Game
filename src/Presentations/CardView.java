package Presentations;

import Models.Card;

import javax.swing.*;
import java.awt.*;

public class CardView extends JPanel {

	private static final long serialVersionUID = 1;
	private Card card;

    public CardView(Card card) {
        this.card = card;
        setPreferredSize(new Dimension(100, 150));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (card != null) {
            g.drawRect(10, 10, 80, 130);
            g.drawString(card.toString(), 20, 75);
        }
    }
    
}
