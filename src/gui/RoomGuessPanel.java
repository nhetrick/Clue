package gui;

import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class RoomGuessPanel extends JPanel{
	private JComboBox roomGuess;
	
	public RoomGuessPanel () {
		roomGuess = new JComboBox();
		roomGuess.addItem("Unsure");
		roomGuess.addItem("Conservatory");
		roomGuess.addItem("Kitchen");
		roomGuess.addItem("Ballroom");
		roomGuess.addItem("Billiard Room");
		roomGuess.addItem("Library");
		roomGuess.addItem("Study");
		roomGuess.addItem("Dining Room");
		roomGuess.addItem("Lounge");
		roomGuess.addItem("Hall");
		add(roomGuess);
		setBorder(new TitledBorder (new EtchedBorder(), "Suspected Room"));
		setLayout(new GridLayout(1, 1));
	}

}