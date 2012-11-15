package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.Board;

public class MakeAccusationPanel extends JFrame{
	
	Board board;
	private JComboBox personSelection;
	private JComboBox roomSelection;
	private JComboBox weaponSelection;
	private String roomSel, personSel, weaponSel = "";
	
	public MakeAccusationPanel(Board b) {
		board = b;
		setSize(new Dimension(300, 300));
		setTitle("Make An Accusation");
		setLayout(new GridLayout(4, 2));
		JLabel room = new JLabel("Room:");
		add(room);
		roomSelection = new JComboBox();
		roomSelection.addItem("");
		roomSelection.addItem("Conservatory");
		roomSelection.addItem("Kitchen");
		roomSelection.addItem("Ballroom");
		roomSelection.addItem("Billiard Room");
		roomSelection.addItem("Library");
		roomSelection.addItem("Study");
		roomSelection.addItem("Dining Room");
		roomSelection.addItem("Lounge");
		roomSelection.addItem("Hall");
		add(roomSelection);
		JLabel person = new JLabel("Person:");
		add(person);
		personSelection = new JComboBox();
		personSelection.addItem("");
		personSelection.addItem("Gandalf");
		personSelection.addItem("Rose Tyler");
		personSelection.addItem("Donna Noble");
		personSelection.addItem("Daniel Jackson");
		personSelection.addItem("Jack O'Neill");
		personSelection.addItem("Malcolm Reynolds");
		add(personSelection);
		JLabel weapon = new JLabel("Weapon:");
		add(weapon);
		weaponSelection = new JComboBox();
		weaponSelection.addItem("");
		weaponSelection.addItem("ZAT'NI'KATEL");
		weaponSelection.addItem("Glitter Gun");
		weaponSelection.addItem("Anti-Plastic");
		weaponSelection.addItem("Ronan Dex");
		weaponSelection.addItem("Ancient Drone");
		weaponSelection.addItem("Mjolnir");
		add(weaponSelection);
		JButton submit = new JButton("Submit");
		add(submit);
		JButton cancel = new JButton("Cancel");
		add(cancel);
		ComboListener comboListener = new ComboListener();
		roomSelection.addActionListener(comboListener);
		personSelection.addActionListener(comboListener);
		weaponSelection.addActionListener(comboListener);
		cancel.addActionListener(new CancelListener());
		submit.addActionListener(new SubmitListener());
	}
	
	private class CancelListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
		}
	}
	
	private class ComboListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == roomSelection) {
				roomSel = roomSelection.getSelectedItem().toString();
			} 
			if (e.getSource() == personSelection) {
				personSel = personSelection.getSelectedItem().toString();
			} 
			if (e.getSource() == weaponSelection) {
				weaponSel = weaponSelection.getSelectedItem().toString();
			}
		}
		
	}
	
	private class SubmitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (!(roomSel.equals("")) && !(weaponSel.equals("")) && !(personSel.equals(""))) {
				ArrayList<String> a = new ArrayList<String>();
				a.add(roomSel);
				a.add(personSel);
				a.add(weaponSel);
				board.setAccusations(a);
				board.setWon();
				setVisible(false);
			} else {
				JOptionPane.showMessageDialog(null, "Incomplete Accusation.");
			}
		}
	}
}
