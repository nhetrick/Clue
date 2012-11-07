package gui;

import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class PeopleGuessPanel extends JPanel{
	private JComboBox peopleGuess;
	
	public PeopleGuessPanel () {
		peopleGuess = new JComboBox();
		peopleGuess.addItem("Unsure");
		peopleGuess.addItem("Gandalf");
		peopleGuess.addItem("Rose Tyler");
		peopleGuess.addItem("Donna Noble");
		peopleGuess.addItem("Daniel Jackson");
		peopleGuess.addItem("Jack O'Neill");
		peopleGuess.addItem("Malcolm Reynolds");		
		add(peopleGuess);
		setBorder(new TitledBorder (new EtchedBorder(), "Suspected Person"));
		setLayout(new GridLayout(1, 1));
	}

}