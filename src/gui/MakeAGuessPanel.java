package gui;

import java.awt.GridLayout;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Board;

public class MakeAGuessPanel extends JPanel {
	Board board;
	
	public MakeAGuessPanel(Board b) {
		board = b;
		setLayout(new GridLayout(4, 2));
		JLabel room = new JLabel("Your Room:");
		add(room);
		Map<Character, String> roomMap = board.getRooms();
		char initial = board.getRoomCellAt(2, 1).getInitial();
		JLabel roomSelection = new JLabel(board.findMapValue(initial));
		add(roomSelection);
		JLabel person = new JLabel("Person:");
		add(person);
		JComboBox personSelection = new JComboBox();
		personSelection.addItem("Gandalf");
		personSelection.addItem("Rose Tyler");
		personSelection.addItem("Donna Noble");
		personSelection.addItem("Daniel Jackson");
		personSelection.addItem("Jack O'Neill");
		personSelection.addItem("Malcolm Reynolds");
		add(personSelection);
		JLabel weapon = new JLabel("Weapon:");
		JComboBox weaponSelection = new JComboBox();
		weaponSelection.addItem("ZAT'NI'KATEL");
		weaponSelection.addItem("Glitter Gun");
		weaponSelection.addItem("Anti-Plastic");
		weaponSelection.addItem("Reaver");
		weaponSelection.addItem("Ronan Dex");
		weaponSelection.addItem("Glamdring");
		weaponSelection.addItem("Dalek");
		weaponSelection.addItem("Ancient Drone");
		weaponSelection.addItem("Mjolnir");
		add(weaponSelection);
		JButton submit = new JButton("Submit");
		add(submit);
		JButton cancel = new JButton("Cancel");
		add(cancel);
	}
}
