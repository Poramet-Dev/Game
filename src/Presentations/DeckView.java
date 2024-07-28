package Presentations;

import Models.Deck;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class DeckView extends JPanel {

    private static final long serialVersionUID = 1L;
    private Deck deck;
    private JButton deckButton;
    private JButton shuffleButton;

    public DeckView(Deck deck, ActionListener drawCardListener, boolean canDrawCards) {
        this.deck = deck;
        setLayout(new FlowLayout(FlowLayout.LEFT));

        deckButton = new JButton("<html>Draw Card<br>Deck Size: " + deck.getSize() + "</html>");
        deckButton.setPreferredSize(new Dimension(150, 200));
        deckButton.addActionListener(drawCardListener);
        deckButton.setEnabled(canDrawCards);
        add(deckButton);

        shuffleButton = new JButton("Shuffle Deck");
        shuffleButton.setPreferredSize(new Dimension(150, 50));
        shuffleButton.addActionListener(e -> {
            deck.shuffle();
            updateDeck();
            JOptionPane.showMessageDialog(this, "Deck shuffled successfully!");
        });
        add(shuffleButton);
    }

    public void updateDeck() {
        deckButton.setText("<html>Draw Card<br>Deck Size: " + deck.getSize() + "</html>");
    }

    public void setDeck(Deck newDeck) {
        this.deck = newDeck;
        updateDeck();
    }

    public void setDrawCardButtonEnabled(boolean enabled) {
        deckButton.setEnabled(enabled);
        updateDeck();
    }
}
