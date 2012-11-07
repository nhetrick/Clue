/** Nicola Hetrick
 * Kira Combs
 * 10/26/12
 */
package clueTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import junit.framework.Assert;

import main.Board;
import main.BoardCell;
import main.Card;
import main.Card.CardType;
import main.ComputerPlayer;
import main.HumanPlayer;
import main.Player;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GameActionTests {
	private static Board board;
	private static ComputerPlayer player;
	private static Card card1;
	private static Card card2;
	private static Card card3;
	private static Card card4;
	String cardShown;
	
	@Before
	public void setup() {
		board = new Board("roomLegend.txt", "craigAndLarsConfig.txt", "players.csv", "cards.csv");
		player = board.getCompPlayers().get(0);
		//randomly generated cards
		card1 = new Card("Gandalf", CardType.PERSON);
		card2 = new Card("Daniel Jackson", CardType.PERSON);
		card3 = new Card("Library", CardType.ROOM);
		card4 = new Card("Dalek", CardType.WEAPON);		
	}
	
	@Test
	public void accusationTest() {
		ArrayList<String> a = new ArrayList<String>();
		a.add("Donna Noble");
		a.add("Conservatory");
		a.add("Mjolnir");
		board.setAccusations(a);
		a.clear();
		a.add("Donna Noble");
		a.add("Conservatory");
		a.add("Mjolnir");
		board.setAnswers(a);
		board.setWon();							//determines if a winner has occured
		//all three elements in Answers MUST equal the three elements in Accusations in order to be true
		Assert.assertTrue(board.isWon());
		
		ArrayList<String> b = new ArrayList<String>();
		b.add("Donna Noble");
		b.add("Library");
		b.add("Mjolnir");
		board.setAccusations(b);
		board.setWon();
		//This statement room is wrong
		Assert.assertFalse(board.isWon());		
		
		ArrayList<String> c = new ArrayList<String>();
		c.add("Donna Noble");
		c.add("Conservatory");
		c.add("ZAT'NI'KATEL");
		board.setAccusations(c);
		board.setWon();
		//This statement weapon is wrong
		Assert.assertFalse(board.isWon());					
		
		ArrayList<String> d = new ArrayList<String>();
		d.add("Daniel Jackson");
		d.add("Conservatory");
		d.add("Mjolnir");
		board.setAccusations(d);
		board.setWon();
		//This statement person is wrong
		Assert.assertFalse(board.isWon());				
		
		ArrayList<String> e = new ArrayList<String>();
		e.add("Daniel Jackson");
		e.add("Library");
		e.add("ZAT'NI'KATEL");
		board.setAccusations(e);
		board.setWon();
		//This statement has ALL elements wrong
		Assert.assertFalse(board.isWon());				
			
	}
	
	@Test
	public void testTargetRandomSelection() {
		//tests a random location
		// Pick a location with no rooms in target, just three targets
		board.calcTargets(3, 2);		//calcs the targets from index 3 with 2 steps
		int loc_2_3Tot = 0;
		int loc_1_4Tot = 0;
		// Run the test 100 times
		for (int i=0; i<100; i++) {
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(board.calcIndex(2,3)))
				loc_2_3Tot++;
			else if (selected == board.getCellAt(board.calcIndex(1,4)))
				loc_1_4Tot++;
			else
				fail("Invalid target selected");
		}
		// Ensure we have 100 total selections (fail should also ensure)
		
		assertEquals(100, loc_2_3Tot + loc_1_4Tot);
		// Ensure each target was selected more than once
		assertTrue(loc_2_3Tot > 10);
		assertTrue(loc_1_4Tot > 10);	

		//test ensures that the room is always selected if it isn't the last visited
		// Pick a location with a room in target, just three targets
		board.calcTargets(board.calcIndex(14, 11),2);		//calcs the targets from point (14,11) with 4 steps
		// Run the test 100 times to simulate randomness
		for (int i=0; i<100; i++) {
			BoardCell selected = player.pickLocation(board.getTargets());
			//this test should never fail if the room is always being chosen
			Assert.assertTrue(selected == board.getCellAt(board.calcIndex(14,13)));			
		}
		
		//test ensures that if the room was the last visited, a random choice is made
		//pick a room cell 
		board.calcTargets(board.calcIndex(8, 12), 3);					//random point with door to Lounge (O) three steps away
		player.setLastRoomVisited('O');									//simulate that computer has already visited this room
		// Run the test 100 times to simulate randomness
		for (int i=0; i<100; i++) {
			BoardCell selected = player.pickLocation(board.getTargets());
			//this test should never fail if because a room should never be chosen as it was last visited
			Assert.assertFalse(selected == board.getCellAt(board.calcIndex(6,13)));			
		}
	}
	
	@Test
	public void disproveSuggestion_SinglePlayer_Test() {
		//add all the cards to playerCards
		//changed to have an addCards function instead
		player.addCards(card1);
		player.addCards(card2);
		player.addCards(card3);
		player.addCards(card4);
		ArrayList<String> a = new ArrayList<String>();			//temp answer array
		a.add(card1.getName());
		a.add("Kitchen");
		a.add("Reaver");
		board.setSuggestions(a);
		
		//This is a single player with a single match (W, P, or R)
		cardShown = board.disproveSuggestion(board.getSuggestions(), board.getCurrentPlayer());
		//Assert.assertEquals(1, board.getCompPlayers().size());
		//Assert.assertEquals(4, board.getCompPlayers().get(0).getCards().size());
		Assert.assertEquals(card1.getName(), board.getCompPlayers().get(0).getCards().get(0).getName());
		
		//Reset Accusation
		a.clear();
		a.add("Jack O'Neill");
		a.add("Kitchen");
		a.add("Reaver");
		board.setSuggestions(a);
		
		// This is if it is null... (bad guess)
		cardShown = board.disproveSuggestion(board.getSuggestions(), board.getCurrentPlayer());
		Assert.assertEquals(null, cardShown);
		
		//Reset Accusation
		a.clear();
		a.add(card2.getName());
		a.add(card3.getName());
		a.add(card4.getName());
		board.setSuggestions(a);
		
		//This is a single player with multiple matches
		int str_1 = 0;
		int str_2 = 0;
		int str_3 = 0;
		// Run the test 100 times
		for (int i=0; i<100; i++) {
			cardShown = board.disproveSuggestion(board.getSuggestions(), board.getCurrentPlayer());
			Assert.assertTrue(a.contains(cardShown));			//makes sure that an irrelevant card is not shown, ie card shown is in the accusation
			if (cardShown.equals(player.getCards().get(1).getName()))
				str_1++;
			else if (cardShown.equals(player.getCards().get(2).getName()))
				str_2++;
			else if (cardShown.equals(player.getCards().get(3).getName()))
				str_3++;
			else
				fail("Invalid target selected");
		}
		// Ensure we have 100 total selections (fail should also ensure)
		assertEquals(100, str_1 + str_2 + str_3);
		// Ensure each card was selected
		assertTrue(str_1 > 10);
		assertTrue(str_2 > 10);
		assertTrue(str_3 > 10);
	}
	
	@Test
	public void disproveSuggestion_AllPlayer_Test() {
		board.getCompPlayers().get(0).addCards(card1);
		board.getCompPlayers().get(1).addCards(card2);
		board.getCompPlayers().get(2).addCards(card3);
		board.getSelf().addCards(card4);
		
		// ALL PLAYER, no card found
		ArrayList<String> a = new ArrayList<String>();
		a.add("Donna Noble");
		a.add("Conservatory");
		a.add("Mjolnir");
		board.setSuggestions(a);
		
		// This is if it is null... (bad guess)
		cardShown = board.disproveSuggestion(board.getSuggestions(), board.getCurrentPlayer());
		Assert.assertEquals(null, cardShown);

		//ALL PLAYER, only human has THE card
		Player current = board.getSelf();
		board.setCurrentPlayer(current);				//sets the currentPlayer to the human player
		//Reset Accusation
		a.clear();
		a.add("Donna Noble");
		a.add("Conservatory");
		a.add("Dalek");								//card exists 
		board.setSuggestions(a);

		//only player who made accusation can disprove (this case the player is human)
		cardShown = board.disproveSuggestion(board.getSuggestions(), board.getCurrentPlayer());
		Assert.assertTrue(board.getCurrentPlayer() == current);
		Assert.assertEquals(null, cardShown);
		
		//ALL PLAYER, only computer has THE card
		current = board.getCompPlayers().get(0);
		board.setCurrentPlayer(current);				//sets the currentPlayer to the computer player
		//Reset Accusation
		a.clear();
		a.add("Gandalf");							//card exists
		a.add("Conservatory");
		a.add("Mjolnir");
		board.setSuggestions(a);

		//only player who made accusation can disprove (this case the player is computer)
		cardShown = board.disproveSuggestion(board.getSuggestions(), board.getCurrentPlayer());
		Assert.assertEquals(null, cardShown);


		//ALL PLAYER, only human has THE card
		board.setCurrentPlayer(board.getCompPlayers().get(0));				//sets the currentPlayer to the computer player
		//Reset Accusation
		a.clear();
		a.add("Gandalf");							//card exists
		a.add("Conservatory");
		a.add("Dalek");								//card exists
		board.setSuggestions(a);

		//The player who made the accusation can disprove, but so can human, and the human's card should be returned
		cardShown = board.disproveSuggestion(board.getSuggestions(), board.getCurrentPlayer());
		Assert.assertEquals("Dalek", cardShown);
		
		//ALL PLAYER, 2 players have cards!
		board.setCurrentPlayer(board.getCompPlayers().get(1));				//sets the currentPlayer to the computer player
		//Reset Accusation
		a.clear();
		a.add("Gandalf");							//card exists
		a.add("Conservatory");
		a.add("Dalek");								//card exists
		board.setSuggestions(a);

		int player_1 = 0;
		int player_2 = 0;
		// Run the test 100 times
		for (int i=0; i<100; i++) {
			//Count time seen for each card, cause the function need to be RANDOM!!! 
			cardShown = board.disproveSuggestion(board.getSuggestions(), board.getCurrentPlayer());
			
			if (cardShown.equals(board.getCompPlayers().get(0).getCards().get(0).getName()))
				player_1++;
			else if (cardShown.equals(board.getSelf().getCards().get(0).getName()))
				player_2++;
			else
				fail("Invalid target selected");
		}
		// Ensure we have 100 total selections (fail should also ensure)
		assertEquals(100, player_1 + player_2);
		// Ensure each card was selected
		assertTrue(player_1 > 10); // MAY NOT PASS OCCASIONALLY DUE TO COMPUTER RANDOMNESS FAIL. 
		assertTrue(player_2 > 10);
	}
	
	@Test
	public void cardsSeenTest() {
		// This tests shows cards being updated in the "cardsSeen" ArrayList that each player has.
		board.setCurrentPlayer(board.getCompPlayers().get(0));
		board.getCompPlayers().get(0).addCards(card1);
		board.getCompPlayers().get(0).addCards(card2);
		board.getCompPlayers().get(0).addCards(card3);
		board.getCompPlayers().get(0).updateSeen(card1.getName());
		board.getCompPlayers().get(0).updateSeen(card2.getName());
		board.getCompPlayers().get(0).updateSeen(card3.getName());
		board.getCompPlayers().get(1).addCards(card4);
		ArrayList<String> a = new ArrayList<String>();
		a.add("Donna Noble");
		a.add("Conservatory");
		a.add("Dalek");									//card exists
		board.setSuggestions(a);
		Assert.assertEquals(3, board.getSuggestions().size());
		Assert.assertEquals(3, board.getCompPlayers().get(0).getCards().size());
		cardShown = board.disproveSuggestion(board.getSuggestions(), board.getCurrentPlayer());
		Assert.assertTrue(board.getCompPlayers().get(0).getCardsSeen().contains(cardShown));
	}
	
	@Test
	public void suggestionTest() {
		// Checks that a suggestion must always be made in the player's current room
		//Suggestion has incorrect room
		ComputerPlayer comp3 = new ComputerPlayer();
		board.setCurrentPlayer(comp3);
		ArrayList<Card> cd = new ArrayList<Card>();
		cd.add(card1);
		cd.add(card2);
		cd.add(card3);
		comp3.setCards(cd);
		comp3.updateSeen(card1.getName());
		comp3.updateSeen(card2.getName());
		comp3.updateSeen(card3.getName());
		comp3.setLastRoomVisited('L');
		board.setSuggestions(comp3.createSuggestion("Donna Noble", "Conservatory", "Mjolnir"));
		Assert.assertEquals(null, board.getSuggestions());										//may be changed later during implementation
		
		//Suggestion has a correct room testing random other values
		board.setSuggestions(comp3.createSuggestion("Donna Noble", card3.getName(), "Mjolnir"));
		Assert.assertEquals("Library", board.getSuggestions().get(1));
		
		//Suggestion does not include seen card
		board.setSuggestions(comp3.createSuggestion(card1.getName(), card3.getName(), "Mjolnir"));
		Assert.assertEquals(null, board.getSuggestions());
	}	
}
