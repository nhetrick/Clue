package clueTests;

import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import main.Board;
import main.BoardCell;
import main.RoomCell;

public class BoardAdjTargetTestsCnL {
	private static Board board;
	@BeforeClass
	public static void setUp() {
		board = new Board("roomLegend.txt", "craigAndLarsConfig.txt", "players.csv", "cards.csv");
	}

	@Test
	public void testAdjacentWalkways() {
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(4, 1));
		//System.out.println(board.calcIndex(4, 1));
		//System.out.println(testList.get(0) + " => " + board.calcCoords(testList.get(0))[0] + ", " + board.calcCoords(testList.get(0))[1]);
		Assert.assertEquals(4, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(4, 0)));
		Assert.assertTrue(testList.contains(board.calcIndex(4, 2)));
		Assert.assertTrue(testList.contains(board.calcIndex(3, 1)));
		Assert.assertTrue(testList.contains(board.calcIndex(5, 1)));
	}
	
	@Test
	public void testEdge() {
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(0, 9));
		Assert.assertEquals(3, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(0, 8)));
		Assert.assertTrue(testList.contains(board.calcIndex(0, 10)));
		Assert.assertTrue(testList.contains(board.calcIndex(1, 9)));
		
		testList = board.getAdjList(board.calcIndex(4, 0));
		Assert.assertEquals(3, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(3, 0)));
		Assert.assertTrue(testList.contains(board.calcIndex(5, 0)));
		Assert.assertTrue(testList.contains(board.calcIndex(4, 1)));
		
		testList = board.getAdjList(board.calcIndex(7, 14));
		Assert.assertEquals(3, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(6, 14)));
		Assert.assertTrue(testList.contains(board.calcIndex(7, 13)));
		Assert.assertTrue(testList.contains(board.calcIndex(8, 14)));
	}

	@Test
	public void testByRoomNotDoorway() {
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(18, 9));
		Assert.assertEquals(3, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(17, 9)));
		Assert.assertTrue(testList.contains(board.calcIndex(18, 8)));
		Assert.assertTrue(testList.contains(board.calcIndex(18, 10)));
		
		testList = board.getAdjList(board.calcIndex(12, 8));
		Assert.assertEquals(2, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(11, 8)));
		Assert.assertTrue(testList.contains(board.calcIndex(12, 7)));
	}
	
	@Test
	public void testAdjacentToDoorway() {
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(11, 1));
		Assert.assertEquals(4, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(10, 1)));
		Assert.assertTrue(testList.contains(board.calcIndex(12, 1)));
		Assert.assertTrue(testList.contains(board.calcIndex(11, 0)));
		Assert.assertTrue(testList.contains(board.calcIndex(11, 2)));
		
		testList = board.getAdjList(board.calcIndex(6, 2));
		Assert.assertEquals(3, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(5, 2)));
		Assert.assertTrue(testList.contains(board.calcIndex(7, 2)));
		Assert.assertTrue(testList.contains(board.calcIndex(6, 3)));
	}
	
	@Test
	public void testTargetsAlongWalkways() {
		board.calcTargets(board.calcIndex(17, 5), 2);
		Set<BoardCell> testList = board.getTargets();
		Assert.assertEquals(7, testList.size());
		Assert.assertTrue(testList.contains(board.getCellAt(board.calcIndex(15, 5))));
		Assert.assertTrue(testList.contains(board.getCellAt(board.calcIndex(17, 3))));
		Assert.assertTrue(testList.contains(board.getCellAt(board.calcIndex(16, 4))));
		Assert.assertTrue(testList.contains(board.getCellAt(board.calcIndex(18, 4))));
		Assert.assertTrue(testList.contains(board.getCellAt(board.calcIndex(16, 6))));
		Assert.assertTrue(testList.contains(board.getCellAt(board.calcIndex(18, 6))));
		Assert.assertTrue(testList.contains(board.getCellAt(board.calcIndex(17, 7))));
		
		board.calcTargets(board.calcIndex(3, 14), 4);
		testList = board.getTargets();
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.getCellAt(board.calcIndex(7, 14))));
		
		board.calcTargets(board.calcIndex(9, 8), 3);
		testList = board.getTargets();
		Assert.assertEquals(3, testList.size());
		Assert.assertTrue(testList.contains(board.getCellAt(board.calcIndex(6, 8))));
		Assert.assertTrue(testList.contains(board.getCellAt(board.calcIndex(12, 8))));
		Assert.assertTrue(testList.contains(board.getCellAt(board.calcIndex(11, 7))));
		
		board.calcTargets(board.calcIndex(17, 12), 2);
		testList = board.getTargets();
		Assert.assertEquals(6, testList.size());
		Assert.assertTrue(testList.contains(board.getCellAt(board.calcIndex(15, 12))));
		Assert.assertTrue(testList.contains(board.getCellAt(board.calcIndex(16, 11))));
		Assert.assertTrue(testList.contains(board.getCellAt(board.calcIndex(17, 10))));
		Assert.assertTrue(testList.contains(board.getCellAt(board.calcIndex(18, 11))));
		Assert.assertTrue(testList.contains(board.getCellAt(board.calcIndex(18, 13))));
		Assert.assertTrue(testList.contains(board.getCellAt(board.calcIndex(17, 14))));
	}
	
	@Test
	public void testEnterRoomTargets() {
		board.calcTargets(board.calcIndex(1, 4), 2);
		Set<BoardCell> testList = board.getTargets();
		Assert.assertEquals(4, testList.size());
		Assert.assertTrue(testList.contains(board.getCellAt(board.calcIndex(2, 5))));
		Assert.assertTrue(testList.contains(board.getCellAt(board.calcIndex(3, 4))));
		Assert.assertTrue(testList.contains(board.getCellAt(board.calcIndex(2, 3))));
		Assert.assertTrue(testList.contains(board.getCellAt(board.calcIndex(0, 3))));
		
		board.calcTargets(board.calcIndex(17, 0), 3);
		testList = board.getTargets();
		Assert.assertEquals(5, testList.size());
		Assert.assertTrue(testList.contains(board.getCellAt(board.calcIndex(19, 0))));
		Assert.assertTrue(testList.contains(board.getCellAt(board.calcIndex(17, 3))));
		Assert.assertTrue(testList.contains(board.getCellAt(board.calcIndex(18, 2))));
		Assert.assertTrue(testList.contains(board.getCellAt(board.calcIndex(18, 0))));
		Assert.assertTrue(testList.contains(board.getCellAt(board.calcIndex(17, 1))));
	}
	
	@Test
	public void testExitRoomTargets() {
		board.calcTargets(board.calcIndex(14, 13), 1);
		Set<BoardCell> testList = board.getTargets();
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.getCellAt(board.calcIndex(14, 12))));
		
		board.calcTargets(board.calcIndex(12, 10), 2);
		testList = board.getTargets();
		Assert.assertEquals(3, testList.size());
		Assert.assertTrue(testList.contains(board.getCellAt(board.calcIndex(11, 11))));
		Assert.assertTrue(testList.contains(board.getCellAt(board.calcIndex(13, 11))));
		Assert.assertTrue(testList.contains(board.getCellAt(board.calcIndex(12, 12))));
	}
}
