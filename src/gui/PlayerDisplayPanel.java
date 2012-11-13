package gui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import main.Board;

public class PlayerDisplayPanel extends JPanel{
	Board board;
	
	public PlayerDisplayPanel(Board b) {
		board = b;
		setLayout(new GridLayout(6, 1));
		JLabel title = new JLabel("My Cards");
		add(title);
		PeopleCardsPanel pcpanel = new PeopleCardsPanel(board);
		add(pcpanel);
		RoomCardsPanel rcpanel = new RoomCardsPanel(board);
		add(rcpanel);
		WeaponCardsPanel wcpanel = new WeaponCardsPanel(board);
		add(wcpanel);
	}
}
