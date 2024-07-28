package Models;


public class Player extends CardContainer {
	private String name;
	
	public Player(String name) {
		super();
		this.name = name;
	}
	
	public String getName() {
        return name;
    }

	
	public int getScore() {
        int score = 0;
        for (Card card : cards) {
            score += card.getCardValue();
        }
        return score;
    }
	
    @Override
    public String toString() {
        return "Player " + this.name + " has Cards: " + showCards();
    }
    
}
