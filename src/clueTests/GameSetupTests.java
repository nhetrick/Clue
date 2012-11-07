/** Nicola Hetrick
 * Kira Combs
 * 10/26/12
 */
package clueTests;

import static org.junit.Assert.*;
import junit.framework.Assert;

import main.Board;
import main.Card.CardType;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GameSetupTests {
	private static Board board;
	
	@BeforeClass
	public static void setup() {
		board = new Board("roomLegend.txt", "craigAndLarsConfig.txt", "players.csv", "cards.csv");
	}
	
	@Test
	public void loadPeopleTest() {
		//Test the human player		
		Assert.assertEquals("Gandalf", board.getSelf().getName());
		Assert.assertEquals("White", board.getSelf().getColor());
		Assert.assertEquals(6, board.getSelf().getStartRow());
		Assert.assertEquals(8, board.getSelf().getStartCol());
		//Test First Computer Player
		Assert.assertEquals("Rose Tyler", board.getCompPlayers().get(0).getName());
		Assert.assertEquals("Purple", board.getCompPlayers().get(0).getColor());
		Assert.assertEquals(3, board.getCompPlayers().get(0).getStartRow());
		Assert.assertEquals(8, board.getCompPlayers().get(0).getStartCol());
		//Test Second Computer Player
		Assert.assertEquals("Donna Noble", board.getCompPlayers().get(1).getName());
		Assert.assertEquals("Red", board.getCompPlayers().get(1).getColor());
		Assert.assertEquals(4, board.getCompPlayers().get(1).getStartRow());
		Assert.assertEquals(8, board.getCompPlayers().get(1).getStartCol());
		//Test Third Computer Player
		Assert.assertEquals("Daniel Jackson", board.getCompPlayers().get(2).getName());
		Assert.assertEquals("Green", board.getCompPlayers().get(2).getColor());
		Assert.assertEquals(5, board.getCompPlayers().get(2).getStartRow());
		Assert.assertEquals(8, board.getCompPlayers().get(2).getStartCol());
		//Test Fourth Computer Player --last in the file
		Assert.assertEquals("Jack O'Neill", board.getCompPlayers().get(3).getName());
		Assert.assertEquals("Blue", board.getCompPlayers().get(3).getColor());
		Assert.assertEquals(7, board.getCompPlayers().get(3).getStartRow());
		Assert.assertEquals(8, board.getCompPlayers().get(3).getStartCol());
		//Test Fifth Computer Player --last in the file
		Assert.assertEquals("Malcolm Reynolds", board.getCompPlayers().get(4).getName());
		Assert.assertEquals("Black", board.getCompPlayers().get(4).getColor());
		Assert.assertEquals(8, board.getCompPlayers().get(4).getStartRow());
		Assert.assertEquals(8, board.getCompPlayers().get(4).getStartCol());
	}

	@Test
	public void loadCardsTest() {
		//Cards file is listed with a card name and a cardtype initial The following tests that this initial is interpreted properly
		//Tests person cards; there are 6 people, so last card: index = 5
		Assert.assertEquals(CardType.PERSON, board.getAllCards().get(0).getCardType());
		Assert.assertEquals(CardType.PERSON, board.getAllCards().get(5).getCardType());
		Assert.assertEquals(CardType.ROOM, board.getAllCards().get(6).getCardType());			//checks that next card is a room
		//Tests room cards; there are 9 rooms, so (9 rooms+6 players)-1 index = 14
		Assert.assertEquals(CardType.ROOM, board.getAllCards().get(14).getCardType());
		Assert.assertEquals(CardType.WEAPON, board.getAllCards().get(15).getCardType());		//checks that next card is a weapon
		//Tests weapon cards; there are 8 weapons, so (9+6+9) - 1 index = 23
		Assert.assertEquals(CardType.WEAPON, board.getAllCards().get(23).getCardType());
		
		//Test total number of cards
		Assert.assertEquals(24, board.getAllCards().size());
		
		//Test that each card type is the correct number
		Assert.assertEquals(6, board.getNumPlayers());
		Assert.assertEquals(9, board.getNumRooms());
		Assert.assertEquals(9, board.getNumWeapons());
		
		//Test that the card names were interpreted properly
		//Test a person card
		Assert.assertEquals("Donna Noble", board.getAllCards().get(2).getName());
		//Test a room card
		Assert.assertEquals("Lounge", board.getAllCards().get(13).getName());
		//Test a weapon card
		Assert.assertEquals("Ronan Dex", board.getAllCards().get(19).getName());
	}
	
	@Test 
	public void dealTest() {
		board.dealCards();
		//Every player should have 4 cards (6 people + 9 rooms + 9 weapons/ 6)
		Assert.assertEquals(4, board.getSelf().getCards().size());		
		Assert.assertEquals(4, board.getCompPlayers().get(0).getCards().size());
		Assert.assertEquals(4, board.getCompPlayers().get(1).getCards().size());
		Assert.assertEquals(4, board.getCompPlayers().get(2).getCards().size());
		Assert.assertEquals(4, board.getCompPlayers().get(3).getCards().size());
		Assert.assertEquals(4, board.getCompPlayers().get(4).getCards().size());
		
		//checks total number of dealt cards
		Assert.assertEquals(24, board.getNumDealt());
		
		//checks that one card is not given to two different players
		//this loop will go through every card in the deck and check it's only been dealt once
		for(int i = 0; i < 24; ++i) {
			Assert.assertEquals(1, board.getAllCards().get(i).getTimesDealt());
		}
	}
}
