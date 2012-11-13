package gui;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.html.HTMLDocument.Iterator;

import main.Board;
import main.BoardCell;

public class GameControlPanel extends JPanel {
	Board board;
	int diceNum;
	JTextArea diceRoll;
	
	public GameControlPanel(Board b) {
		board = b;
		diceNum = 0;
		setLayout(new GridLayout(2,3));
		JLabel turn = new JLabel("It is " + board.getCurrentPlayer().getName() + "'s turn");
		add(turn);
		JButton nextPlayer = new JButton("Next player");
		add(nextPlayer);
		JButton accusation = new JButton("Make an accusation");
		add(accusation);
		diceRoll = new JTextArea(2, 20);
		diceRoll.setLineWrap(true); // uses multiple lines
		diceRoll.setWrapStyleWord(true); // breaks on word boundaries
		writeDice();
		add(diceRoll);
		GuessPanel guess = new GuessPanel(board);
		add(guess);
		GuessResPanel guessResult = new GuessResPanel(board);
		add(guessResult);
		accusation.addActionListener(new AccusationListener());
		nextPlayer.addActionListener(new NextPlayerListener());
	}

	public void setDiceNum() {
		diceNum = board.getDiceNum();
	}

	public void writeDice() {
		setDiceNum();
		diceRoll.setText("Dice Roll: " + diceNum);
	}
	
	private class AccusationListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			MakeAccusationPanel accusation1 = new MakeAccusationPanel(board);
			accusation1.setVisible(true);
		}
	}
	
	private class NextPlayerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			board.makeMove();
			writeDice();
		}
	}
}
