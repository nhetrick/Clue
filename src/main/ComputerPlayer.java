/** Nicola Hetrick
 * Kira Combs
 * 10/26/12
 */
package main;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

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
			} else {
				if (key.isRoom() == false)
					nonRooms.add(key);
			}
		}
		int randInt = rand.nextInt(nonRooms.size());
		return nonRooms.get(randInt);
	}
	
	public ArrayList<String> createSuggestion(String person, String room, String weapon) {
		//computer logic for creating a suggestion
		char r = 'Z';
		//converts given room to a char 
		switch (room) {
			case "Library": r = 'L';
							break;
			case "Conservatory": r = 'C';
							break;
			case "Kitchen": r = 'K';
							break;
			case "Ballroom": r = 'B';
							break;
			case "Billiard Room": r = 'R';
							break;
			case "Study": r = 'S';
							break;
			case "Dining Room": r = 'D';
							break;
			case "Lounge": r = 'O';
							break;
			case "Hall": r = 'H';
							break;
		}
		//must be currentRoom 
		if (r == getLastRoomVisited()) {
			if (!(cardsSeen.contains(person)) && !(cardsSeen.contains(weapon))) {
				ArrayList<String> selection = new ArrayList<String>();
				selection.add(person);
				selection.add(room);
				selection.add(weapon);
				return selection;
			} else {
				return null;
			}
		} else {
			return null;
		}
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
