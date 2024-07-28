package Models;

public enum Rank {
	Ace(1),
	Two(2),
	Treee(3),
	Four(4),
	Five(5),
	Six(6),
	Seven(7),
	Eight(8),
	Nine(9),
	Ten(10),
	Jack(0),
	Queen(0),
	King(0);
	
	private int value;
	
	private Rank(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
        this.value = value;
    }
}
