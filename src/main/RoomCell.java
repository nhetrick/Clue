package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class RoomCell extends BoardCell {
	private DoorDirection doorDirection;
	
	public RoomCell(String cellType) {
		super();
		this.cellType = cellType.charAt(0);
		if( cellType.length() > 1 ) {
			switch(cellType.charAt(1)) {
			case 'U': this.doorDirection = DoorDirection.UP;
				break;
			case 'D': this.doorDirection = DoorDirection.DOWN;
				break;
			case 'L': this.doorDirection = DoorDirection.LEFT;
				break;
			case 'R': this.doorDirection = DoorDirection.RIGHT;
				break;			
			}
		} else {
			this.doorDirection = DoorDirection.NONE;
		}

	}
	
	public boolean isRoom() {
		return true;
	}
	
	public boolean isDoorway() {
		if( doorDirection == DoorDirection.NONE ) return false;
		else return true;
	}

	public enum DoorDirection {
		UP('U'), DOWN('D'), LEFT('L'), RIGHT('R'), NONE(' ');
		char symbol;
		DoorDirection(char symbol) {
			this.symbol = symbol;
		}
	}
	
	//Draw the gui
	public void draw(Graphics g, int r, int c) {
		Graphics2D g2 = (Graphics2D) g;
		if (getInitial() == 'X') {
			g2.setColor(Color.lightGray);
			g2.fillRect(r*size, c*size, size, size);
		} else {
			g2.setColor(Color.darkGray);
			g2.fillRect(r*size, c*size, size, size);
		}
		g2.setColor(Color.black);
		switch (doorDirection) {
		case UP: 
			g2.fillRect(r*size, c*size, size, 4);
			break;
		case DOWN:
			g2.fillRect(r*size, c*size + size - 4, size, 4);
			break;
		case LEFT:
			g2.fillRect(r*size, c*size, 4, size);
			break;
		case RIGHT:
			g2.fillRect(r*size + size - 4, c*size, 4, size);
			break;
		}
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public char getInitial() {
		return cellType;
	}
}

