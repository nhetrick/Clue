package gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import main.Board;

public class GameControlPanel extends JPanel {
	static Board board;
	int diceNum;
	JTextArea turn;
	JTextArea diceRoll;
	static JTextArea guessResult;
	static JTextArea guess;
	MakeAGuessPanel magpanel;

	public GameControlPanel(Board b) {
		board = b;
		diceNum = 0;
		setLayout(new GridLayout(2,3));
		turn = new JTextArea(2, 20);
		turn.setLineWrap(true); // uses multiple lines
		turn.setWrapStyleWord(true); // breaks on word boundaries
		writeTurn();
		Border borderTurn = BorderFactory.createTitledBorder("Who's Turn?");
		turn.setBorder(borderTurn);
		add(turn);
		JButton nextPlayer = new JButton("Next player");
		add(nextPlayer);
		JButton accusation = new JButton("Make an accusation");
		add(accusation);
		diceRoll = new JTextArea(2, 20);
		diceRoll.setLineWrap(true); // uses multiple lines
		diceRoll.setWrapStyleWord(true); // breaks on word boundaries
		writeDice();
		Border borderDice = BorderFactory.createTitledBorder("Dice Roll");
		diceRoll.setBorder(borderDice);
		add(diceRoll);
		guess = new JTextArea(2, 20);
		guess.setLineWrap(true); // uses multiple lines
		guess.setWrapStyleWord(true); // breaks on word boundaries
		writeGuess();
		Border borderGuess = BorderFactory.createTitledBorder("Guess");
		guess.setBorder(borderGuess);
		add(guess);
		guessResult = new JTextArea(2, 20);
		guessResult.setLineWrap(true); // uses multiple lines
		guessResult.setWrapStyleWord(true); // breaks on word boundaries
		writeGuessResult();
		Border borderRes = BorderFactory.createTitledBorder("Response");
		guessResult.setBorder(borderRes);
		add(guessResult);
		accusation.addActionListener(new AccusationListener());
		nextPlayer.addActionListener(new NextPlayerListener());
	}

	public void setDiceNum() {
		diceNum = board.getDiceNum();
	}

	public void writeDice() {
		setDiceNum();
		diceRoll.setText(" " + diceNum);
	}

	public void writeTurn() {
		turn.setText("It is " + board.getCurrentPlayer().getName() + "'s turn");
	}

	public static void writeGuess() {
		if (board.getSuggestions().size() > 0) {
			guess.setText(board.getSuggestions().get(0) + ", " + board.getSuggestions().get(1) + ", " + board.getSuggestions().get(2));
		}
	}

	public static void writeGuessResult() {
		guessResult.setText(board.getCardShown());
		if (board.getCardShown() == null) {
			guessResult.setText("No new clue");
		}
	}

	private class AccusationListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (board.getCurrentPlayer() == board.getSelf()) {
				MakeAccusationPanel accusation1 = new MakeAccusationPanel(board);
				accusation1.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(null, "It is not your turn...");
			}
		}
	}

	private class NextPlayerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (board.getSubmittionComplete()) {
				if (board.isPlayerSelectedTarget()) {
					board.makeMove();
					writeDice();
					writeTurn();
					writeGuess();
					writeGuessResult();
					repaint();
				} else {
					JOptionPane.showMessageDialog(null, "Finish your turn!");
				}
			} 
		}
	}
}
