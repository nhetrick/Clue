package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class WalkwayCell extends BoardCell{
	
	public WalkwayCell() {
		super();
		cellType = 'W';
	}
	
	public boolean isWalkway() {
		return true;
	}
	
	public boolean isDoorway() {
		return false;
	}
	
	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.yellow);
		float thickness = 2;
		Stroke oldStroke = g2.getStroke();
		g2.setStroke(new BasicStroke(thickness));
		g2.drawRect(row*20, col*20, 20, 20);
		g2.setStroke(oldStroke);
	}
}
