package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;

public class DetectiveNotes extends JFrame {
	 public DetectiveNotes() {
		 setSize(new Dimension(600, 600));
		 setTitle("Detective Notes");
		 setLayout(new GridLayout(0, 2));
		 PeoplePanel people = new PeoplePanel();
		 add(people);
		 PeopleGuessPanel pguess = new PeopleGuessPanel();
		 add(pguess);
		 RoomPanel room = new RoomPanel();
		 add(room);
		 RoomGuessPanel rguess = new RoomGuessPanel();
		 add(rguess);
		 WeaponPanel weapon = new WeaponPanel();
		 add(weapon);
		 WeaponGuessPanel wguess = new WeaponGuessPanel();
		 add(wguess);
	}
	
	public static void main(String[] args) {
		DetectiveNotes notes = new DetectiveNotes();
		notes.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		notes.setVisible(true);
	}
}
