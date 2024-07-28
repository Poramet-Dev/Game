package Models;
import java.util.*;

public class Deck extends CardContainer {
	public Deck(List<Card> deck) {
		super();
		if(deck != null) {
			cards = deck;
		}else {
			//Create New Deck
			for (Suit suit : Suit.values()) {
	            for (Rank rank : Rank.values()) {
	                addCard(new Card(rank, suit));
	            }
	        }
		}
	}
	
	public void shuffle() { // Function สับการ์ด
        Collections.shuffle(cards);
        System.out.println("After shuffle : "+toString());
    }
	
	public Card cards() { //Function จั่วการ์ด
		if (!cards.isEmpty()) {
			return cards.remove(cards.size() - 1);
		}
		return null;
	}
	
	public Card drawCard() {
        if (!cards.isEmpty()) {
            return cards.remove(cards.size() - 1);
        }
        return null;
    }

	public int getSize() { // Function get จำนานไพ่ที่เหลือในกอง
        return cards.size();
    }
	
	public void clear() {
        cards.clear();
    }

	
	public List<Deck> splitDeckIntoPiles(int numberOfPiles) {
	    List<Deck> piles = new ArrayList<Deck>();
	    int pileSize = cards.size() / numberOfPiles;
	    int remainingCards = cards.size() % numberOfPiles;

	    int startIndex = 0;

	    for (int i = 0; i < numberOfPiles; i++) {
	        int endIndex = startIndex + pileSize + (remainingCards > 0 ? 1 : 0);
	        
	        List<Card> pileCards = new ArrayList<>(cards.subList(startIndex, endIndex));
	        piles.add(new Deck(pileCards));

	        startIndex = endIndex;
	        if (remainingCards > 0) {
	            remainingCards--;
	        }

	        System.out.println("Pile " + (i + 1) + " : " + pileCards.toString());
	    }
	    cards.clear();
	    return piles;
	}

	@Override
    public String toString() {
        return showCards();
    }
}
