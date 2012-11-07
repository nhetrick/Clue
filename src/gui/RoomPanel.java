package gui;

import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class RoomPanel extends JPanel{
	private JCheckBox conservatory, kitchen, ballroom, billiard, library, study, dining, lounge, hall;
	
	public RoomPanel () {
		conservatory = new JCheckBox("Conservatory");
		kitchen = new JCheckBox("Kitchen");
		ballroom = new JCheckBox("Ballroom");
		billiard = new JCheckBox("Billiard Room");
		library = new JCheckBox("Library");
		study = new JCheckBox("Study");
		dining = new JCheckBox("Dining Room");
		lounge = new JCheckBox("Lounge");
		hall = new JCheckBox("Hall");
		add(conservatory);
		add(kitchen);
		add(ballroom);
		add(billiard);
		add(library);
		add(study);
		add(dining);
		add(lounge);
		add(hall);
		setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		setLayout(new GridLayout(0, 2));
	}

}