/** Nicola Hetrick
 * Kira Combs
 * 10/26/12
 */
package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.lang.reflect.Field;
import java.util.ArrayList;

public abstract class Player {
	private String name;
	private ArrayList<Card> cards = new ArrayList<Card>();
	private String color;
	private int startRow;
	private int startCol;
	private int targetLocation;
	private int currentLocation;
	final static public int size = 40;
	final static public int diameter = 39;
	
	//Draw the gui
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(convertColor(color));
		g2.fillOval(startRow*size, startCol*size, diameter, diameter);
	}
	
	public Color convertColor(String strColor) {
		Color color; 
		try {     
			// We can use reflection to convert the string to a color
			Field field = Class.forName("java.awt.Color").getField(strColor.trim());     
			color = (Color)field.get(null); } 
		catch (Exception e) {  
			color = null; // Not defined } 
		}
		return color;
	}
	
	public int getTargetLocation() {
		return targetLocation;
	}
	public void setTargetLocation(int targetLocation) {
		this.targetLocation = targetLocation;
	}
	public String getCol() {
		return color;
	}
	public void setCol(String color) {
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
