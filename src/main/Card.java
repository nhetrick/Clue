/** Nicola Hetrick
 * Kira Combs
 * 10/26/12
 */
package main;

public class Card {
	private String name; 
	private int timesDealt; 				//will track if the card has been dealt to a player

	public enum CardType {
		PERSON, WEAPON, ROOM, NONE;
	}
	private CardType cardType;
	
	public Card(String name, CardType cardType) {
		super();
		this.name = name;
		this.cardType = cardType;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public CardType getCardType() {
		return cardType;
	}
	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}
	public boolean equals() {
		return false;
	}
	public int getTimesDealt() {
		return timesDealt;
	}
	public void incTimesDealt() {
		timesDealt++;
	}
	

}
