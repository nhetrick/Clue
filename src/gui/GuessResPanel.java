package gui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import main.Board;

public class GuessResPanel extends JPanel {
	Board board;
	
	public GuessResPanel(Board b) {
		board = b;
		setLayout(new GridLayout(0, 1));
		setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
		if (board.getSuggestions().size() != 0) {
			JLabel response = new JLabel("Response: " + board.disproveSuggestion(board.getSuggestions(), board.getCurrentPlayer()));
			add(response);
		}
	}
}
