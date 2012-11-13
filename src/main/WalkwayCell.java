package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.Map;

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
	
	public void draw(Graphics g, int r, int c, Map<Character, String> rooms) {
		row = r;
		col = c;
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.magenta);
		g2.fillRect(r*size, c*size, size, size);
		g2.setColor(Color.black);
		float thickness = 1;
		Stroke oldStroke = g2.getStroke();
		g2.setStroke(new BasicStroke(thickness));
		g2.drawRect(r*size, c*size, size, size);
		g2.setStroke(oldStroke);
	}
}
