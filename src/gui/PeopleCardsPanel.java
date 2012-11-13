package gui;

import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import main.Board;
import main.Card;
import main.Card.CardType;

public class PeopleCardsPanel extends JPanel {
	Board board;

	public PeopleCardsPanel(Board b) {
		board = b;
		ArrayList<Card> myCards = board.getSelf().getCards();
		int counter = 0;
		for (int i = 0; i < myCards.size(); i++) {
			if (myCards.get(i).getCardType() == CardType.PERSON) {
				add(addLabel(myCards.get(i).getName()));
				counter++;
			}
		}
		if (counter == 0) {
			add(addLabel("NO PEOPLE CARDS"));
		}
		setLayout(new GridLayout(0, 1));
		setBorder(new TitledBorder(new EtchedBorder(), "People"));
	}
	
	public JLabel addLabel(String cardName) {
		JLabel label = new JLabel(cardName);
		label.setFont(new Font("Arial", Font.ITALIC, 12));
		return label;
	}

}
