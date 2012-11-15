/** Nicola Hetrick
 * Kira Combs
 * 10/26/12
 */
package main;

import gui.ClueGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Set;

import main.Card.CardType;

public class ComputerPlayer extends Player {
	private char lastRoomVisited;	//for use with GUI
	private ArrayList<String> cardsSeen;
	
	public ComputerPlayer() {
		ArrayList<String> seen = new ArrayList<String>();
		setCardsSeen(seen);
		lastRoomVisited = 'X';
	}
	

	public BoardCell pickLocation(Set<BoardCell> targets) {
		//if it's room, you must enter unless previously entered room otherwise pick random room
		ArrayList<BoardCell> nonRooms = new ArrayList<BoardCell>();
		Random rand = new Random();
		for(BoardCell key : targets) {
			if (key.isRoom() && (key.getCellType() != getLastRoomVisited())) {
				return key;
			} else if (key.isRoom() && (key.getCellType() == getLastRoomVisited())) {
			} else {
				if (key.isRoom() == false)
					nonRooms.add(key);
			}
		}
		if (nonRooms.size() > 0) {
			int randInt = rand.nextInt(Math.abs(nonRooms.size()));
			return nonRooms.get(randInt);
		} else {
			return ClueGame.getBoard().getCellAt(getCurrentLocation());
		}
	}
	
	public ArrayList<String> createSuggestion(String room) {
		boolean havePerson = false;
		boolean haveWeapon = false;
		String weapon = "";
		String person = "";
		
		ArrayList<String> selection = new ArrayList<String>();
		selection.add(room);
		ArrayList<Card> cards = ClueGame.getBoard().getAllCards();
		Collections.shuffle(cards);
		for (int i = 0; i < cards.size(); i++) {
			if (havePerson == false && cards.get(i).getCardType() == CardType.PERSON && !cardsSeen.contains(cards.get(i).getName())) {
				person = cards.get(i).getName();
				havePerson = true;
			}
			if (haveWeapon == false && cards.get(i).getCardType() == CardType.WEAPON && !cardsSeen.contains(cards.get(i).getName())) {
				weapon = cards.get(i).getName();
				haveWeapon = true;
			}
		}
		selection.add(weapon);
		selection.add(person);
		
		return selection;
	}
	public void updateSeen(String seen) {
		
		
		cardsSeen.add(seen);
	}
	
	//for use with GUI
	public char getLastRoomVisited() {
		return lastRoomVisited;
	}
	//for use with GUI
	public void setLastRoomVisited(char lastRoomVisited) {
		this.lastRoomVisited = lastRoomVisited;
	}
	public ArrayList<String> getCardsSeen() {
		return cardsSeen;
	}
	public void setCardsSeen(ArrayList<String> cardsSeen) {
		this.cardsSeen = cardsSeen;
	}
	

}
