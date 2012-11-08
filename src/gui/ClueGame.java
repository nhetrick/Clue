
package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import main.Board;

public class ClueGame extends JFrame {
	DetectiveNotes notes = new DetectiveNotes();
	
	public ClueGame () {
		setSize(new Dimension(600, 800));
		setTitle("CLUE");
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
		Board board = new Board("roomLegend.txt", "craigAndLarsConfig.txt", "players.csv", "cards.csv");
		add(board, BorderLayout.CENTER);
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
	}

}
