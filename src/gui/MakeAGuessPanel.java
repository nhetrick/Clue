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

public class MakeAGuessPanel extends JFrame {
	Board board;
	private JComboBox personSelection;
	private JComboBox weaponSelection;
	private String roomSel, personSel, weaponSel = "";
	
	public MakeAGuessPanel(Board b, String roomSel) {
		this.roomSel = roomSel;
		board = b;
		setSize(new Dimension(300, 300));
		setTitle("Make An Guess");
		setLayout(new GridLayout(4, 2));
		JLabel room = new JLabel("Your Room:");
		add(room);
		JLabel roomSelection = new JLabel(roomSel);
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
		personSelection.addActionListener(comboListener);
		weaponSelection.addActionListener(comboListener);
		cancel.addActionListener(new CancelListener());
		submit.addActionListener(new SubmitListener());
	}
	
	private class CancelListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
			board.setSubmittionComplete(true);
		}
	}
	
	private class ComboListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
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
				a.add(weaponSel);
				a.add(personSel);
				a.add(roomSel);
				board.setSuggestions(a);
				board.setCardShown(board.disproveSuggestion(board.getSuggestions(), board.getCurrentPlayer()));
				GameControlPanel.writeGuess();
				GameControlPanel.writeGuessResult();
				board.setSubmittionComplete(true);
				repaint();
			} else {
				JOptionPane.showMessageDialog(null, "Incomplete Suggestion.");
				board.setSubmittionComplete(false);
			}
			checkDone();
		}
	}
	
	private void checkDone() {
		if (board.getSubmittionComplete()) {
			setVisible(false);
		} 
	}
		
}
