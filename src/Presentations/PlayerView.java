package Presentations;


import Models.Card;
import Models.Player;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PlayerView extends JPanel {
	
	private static final long serialVersionUID = 1;
	
	private Player player;

    public PlayerView(Player player) {
        this.player = player;
        setLayout(new FlowLayout());
        updateCards();
    }

    public void updateCards() {
    	removeAll();
        List<Card> cards = player.getCards();
        for (Card card : cards) {
            add(new CardView(card));
        }
        revalidate();
        repaint();
    }
}
