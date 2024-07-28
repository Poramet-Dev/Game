package Models;
import java.util.*;

public abstract class CardContainer {
	public List<Card> cards;

    public CardContainer() {
        cards = new ArrayList<>();
    }

    public void addCard(List<Card> pileCards) {
    	this.cards.addAll(pileCards);
    }
    
    public void addCard(Card card) {
        this.cards.add(card);	
    }

    public void removeCard(int index) {
        if (index >= 0 && index < cards.size()) {
            cards.remove(index);
        }
    }
    public void clear() {
    	cards.clear();
    }
    public List<Card> getCards() {
        return cards;
    }
    
    public String showCards() {
        StringBuilder cardList = new StringBuilder();
        for (Card card : cards) {
            cardList.append(card.toString()).append(", ");
        }
        return cardList.toString();
    }
}
