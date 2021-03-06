
package 
gui;

import java.awt.BorderLayout;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import main.Board;

public class ClueGame extends JFrame {
	static Board board;
	DetectiveNotes notes = new DetectiveNotes();
	
	
	public ClueGame () {
		setSize(new Dimension(750, 950));
		setTitle("CLUE");
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
		board = new Board("roomLegend.txt", "craigAndLarsConfig.txt", "players.csv", "cards.csv");
		add(board, BorderLayout.CENTER);
		PlayerDisplayPanel pdpanel = new PlayerDisplayPanel(board);
		add(pdpanel, BorderLayout.EAST);
		GameControlPanel gcpanel = new GameControlPanel(board);
		add(gcpanel, BorderLayout.SOUTH);

	}
	
	public static Board getBoard() {
		return board;
	}

	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File"); 
		JMenuItem item = new JMenuItem("Exit");
		item.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			}
		);
		JMenuItem item2 = new JMenuItem("Detective Notes");
		item2.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					notes.setVisible(true);
				}
			}
		);
		menu.add(item);
		menu.add(item2);
		return menu;
	}

	public DetectiveNotes getNotes() {
		return notes;
	}
	
	public static void main(String[] args) {
		ClueGame clue = new ClueGame();
		clue.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		clue.setVisible(true);
		String name = board.getSelf().getName();
		JOptionPane.showMessageDialog(clue, "You are " + name + ", press Next Player to begin play", "Welcome To Clue!", JOptionPane.INFORMATION_MESSAGE);
	}

}

