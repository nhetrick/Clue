/** Nicola Hetrick
 * Kira Combs
 * 10/26/12
 */
package main;

import java.util.ArrayList;

public abstract class Player {
	private String name;
	private ArrayList<Card> cards = new ArrayList<Card>();
	private String color;
	private int startRow;
	private int startCol;
	private int targetLocation;
	private int currentLocation;
	
	public int getTargetLocation() {
		return targetLocation;
	}
	public void setTargetLocation(int targetLocation) {
		this.targetLocation = targetLocation;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public int getStartRow() {
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public int getStartCol() {
		return startCol;
	}
	public void setStartCol(int startCol) {
		this.startCol = startCol;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Card> getCards() {
		return cards;
	}
	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}
	public void addCards(Card card) {
		cards.add(card);
	}
	public int getCurrentLocation() {
		return currentLocation;
	}
	public void setCurrentLocation(int currentLocation) {
		this.currentLocation = currentLocation;
	}
	
}
