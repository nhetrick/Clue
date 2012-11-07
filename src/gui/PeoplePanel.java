package gui;

import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class PeoplePanel extends JPanel{
	private JCheckBox gandalf, rose, donna, daniel, jack, malcolm;
	
	public PeoplePanel () {
		gandalf = new JCheckBox("Gandalf");
		rose = new JCheckBox("Rose Tyler");
		donna = new JCheckBox("Donna Noble");
		daniel = new JCheckBox("Daniel Jackson");
		jack = new JCheckBox("Jack O'Neill");
		malcolm = new JCheckBox("Malcolm Reynolds");
		add(gandalf);
		add(rose);
		add(donna);
		add(daniel);
		add(jack);
		add(malcolm);
		setBorder(new TitledBorder (new EtchedBorder(), "People"));
		setLayout(new GridLayout(3, 2));
	}

}

