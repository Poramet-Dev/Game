package Models;

public class Card {
	private Rank rank;
	private Suit suit;
	
	//สร้าง 
	public Card(Rank rank, Suit suit) { //สร้าง Card 1 ใบ ใช้ rank 1 ตัว + Suit 1 ตัว
		this.rank = rank;
		this.suit = suit;
	}
	
	// Get ค่า
	public int getCardValue() {
		return rank.getValue();
	}
	
	// Get String ที่จะนำไปแสดง
	@Override
	public String toString() {
		return rank + " of " + suit;
	}
	
}
