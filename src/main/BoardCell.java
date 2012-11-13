package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Map;

public abstract class BoardCell {
	protected char cellType;
	protected int row;
	protected int col;
	final static public int size = 38;
	private boolean target = false;
	private boolean person = false;
	
	public boolean isPerson() {
		return person;
	}

	public void setPerson(boolean person) {
		this.person = person;
	}

	public boolean isTarget() {
		return target;
	}

	public void setTarget(boolean target) {
		this.target = target;
	}

	public boolean isWalkway() {
		if( cellType == 'W' ) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isRoom() {
		if( cellType == 'W' ) {
			return false;
		} else {
			return true;
		}
	}
	
	public abstract boolean isDoorway();
	
	//Overwritten in RoomCell and WalkwayCell
	abstract void draw(Graphics g, int r, int c, Map<Character, String> rooms);
	
	public void highlight(Graphics g, int r, int c) {
		g.setColor(Color.orange);
		g.fillRect(r*size, c*size, size, size);
	}
	
	public boolean containsClick(int mouseX, int mouseY) {
		Rectangle rect = new Rectangle(row*size, col*size, size, size);
		if (rect.contains(new Point(mouseX, mouseY))) 
			return true;
		return false;
	}
	
	/*
	 * Getters
	 */
	public char getCellType() {
		return cellType;
	}
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BoardCell other = (BoardCell) obj;
		if (cellType != other.cellType)
			return false;
		if (col != other.col)
			return false;
		if (row != other.row)
			return false;
		return true;
	}


}
