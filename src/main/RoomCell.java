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
	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.gray);
		float thickness = 2;
		Stroke oldStroke = g2.getStroke();
		g2.setStroke(new BasicStroke(thickness));
		g2.drawRect(row*20, col*20, 20, 20);
		g2.setStroke(oldStroke);
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public char getInitial() {
		return cellType;
	}
}

