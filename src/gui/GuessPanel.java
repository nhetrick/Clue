package gui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import main.Board;

public class GuessPanel extends JPanel {
	Board board;
	
	public GuessPanel(Board b) {
		board = b;
		setLayout(new GridLayout(0, 1));
		setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		if (board.getSuggestions().size() != 0) {
			JLabel guess = new JLabel(board.getSuggestions().get(0) + ", " + board.getSuggestions().get(1) + ", " + board.getSuggestions().get(2));
			add(guess);
		}
	}
	
}
