package main;
import gui.ClueGame;
//import gui.GameControlPanel;
import gui.MakeAGuessPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.HashSet;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.html.HTMLDocument.Iterator;

import main.Card.CardType;
/**ORIGINAL
 * @author: Craig Carlson
 * @author: Lars Walen
 */
/**CLUE SWAP
 * @author: Nicola Hetrick
 * @author: Kira Combs
 */
/**CLUE BOARD GUI
 * @author: Anastasia Shpurik
 * @author: Nicola Hetrick
 */

public class Board extends JPanel implements MouseListener {

	private ArrayList<BoardCell> cells = new ArrayList<BoardCell>();
	private Map<Character, String> rooms = new HashMap<Character, String>();
	private Map<String, Character> iRooms = new HashMap<String, Character>();		//stores the room initial
	private ArrayList<Card> allCards = new ArrayList<Card>();						//ArrayList of all possible cards in the game...Note their awesomeness
	private ArrayList<ComputerPlayer> compPlayers = new ArrayList<ComputerPlayer>();//Stores a list of all computerPlayers
	private ArrayList<String> answers = new ArrayList<String>();					//stores 3 strings that will be the game answer
	private ArrayList<String> accusations = new ArrayList<String>();				//stores 3 strings that will be a person's accusation 
	private ArrayList<String> suggestions = new ArrayList<String>();				//stores 3 strings that will be a person's suggestion
	private ArrayList<Player> allPlayers = new ArrayList<Player>();
	private boolean isInRoom = false;
	boolean submittionComplete = true;
	
	private int diceNum;
	private HumanPlayer self = new HumanPlayer();
	
	private String cardShown = "";
	
	private int playerIter = -1;
	private boolean playerSelectedTarget = true;
	private boolean playerEnteredRoom = false;
	private boolean turnComplete = true;

	private int numRows;

	private int numColumns;
	// The following will be used to check card configurations
	private int numPlayers = 0;
	private int numRooms = 0;
	private int numWeapons = 0;
	//The following will be used to check cards dealt
	private int numDealt;				//will track number of total cards dealt
	//the following will be used to track winning status
	private boolean won = false;

	// Adjacencies and targets related members
	private Map<Integer, LinkedList<Integer>> adjMatrix;
	public HashSet<Integer> targets;
	private boolean[] visited;

	//Who's Turn is it?? hmmmmm
	private Player currentPlayer = self;	//human should start the game. default


	/**
	 * Creates board given filenames of legend file and board config file
	 * @param legendFilename
	 * @param boardFilename
	 */
	public Board(String legendFilename, String boardFilename, String playersFilename, String cardsFilename) {
		try {
			loadConfigFiles(legendFilename, boardFilename, playersFilename, cardsFilename);
		} catch (BadConfigFormatException e) {
			System.out.println(e);
		}
		visited = new boolean[numRows * numColumns];
		adjMatrix = new HashMap<Integer, LinkedList<Integer>>();
		targets = new HashSet<Integer>();
		dealCards();  //Shuffles cards and causes loadCards to fail. Use in GUI for actual gameplay
		calcAdjacencies();
		makeArrayOfAllPlayers();
		addMouseListener(this);
	}

	//USED TO DRAW BOARD. Use objected-oriented approach. 
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int k = 0;
		while (k < cells.size()) {
			for (int i = 0; i < numRows; i++) {
				for (int j = 0; j < numColumns; j++) {
					cells.get(k).draw(g, j, i, rooms);
					k++;
				}
			}
		}
		self.draw(g);
		for (int i = 0; i < compPlayers.size(); i++) {
			compPlayers.get(i).draw(g);
		}

		java.util.Iterator<Integer> it = targets.iterator();
		int value;
		int coord[];
		while (it.hasNext()) {
			value = it.next();
			coord = calcCoords(value);
			cells.get(value).highlight(g, coord[1], coord[0]);
		}

	}

	public void makeMove() {	
			playerSelectedTarget = false;
			playerEnteredRoom = false;
			setNextPlayer();
			rollDice();
			calcTargets(getCurrentPlayer().getCurrentLocation(), getDiceNum());
			if (currentPlayer == self) {
				repaint();
			} else {
				BoardCell newLoc = ((ComputerPlayer) currentPlayer).pickLocation(getTargets());
				currentPlayer.setRow(newLoc.getRow());
				currentPlayer.setColumn(newLoc.getCol());
				currentPlayer.setCurrentLocation(calcIndex(newLoc.getCol(), newLoc.getRow()));
				targets.clear();
				playerSelectedTarget = true;
				if (getCellAt(currentPlayer.getCurrentLocation()).isRoom()) {
					BoardCell cell = getCellAt(getCurrentPlayer().getCurrentLocation());
					char initial = cell.getCellType();
					String roomSel = findMapValue(initial);
					setSuggestions(((ComputerPlayer) currentPlayer).createSuggestion(roomSel));
					setCardShown(disproveSuggestion(getSuggestions(), currentPlayer));
					if (cardShown == null) {
						setAccusations(getSuggestions());
						setWon();
					}
				}
				repaint();
			}
	}
	
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mousePressed(MouseEvent e)  {
		//turnComplete = false;
		setSubmittionComplete(false);
		finishTurn(e);
		
	}  
	
	public void finishTurn(MouseEvent e) {
		java.util.Iterator<BoardCell> it = getTargets().iterator();
		BoardCell value;
		while (it.hasNext()) {
			value = it.next();
			if (value.containsClick(e.getX(), e.getY())) {
				getCurrentPlayer().setCurrentLocation(calcIndex(value.getCol(), value.getRow()));
				getCurrentPlayer().setRow(value.getRow());
				getCurrentPlayer().setColumn(value.getCol());
				playerSelectedTarget = true;
				targets.clear();
				BoardCell cell = getCellAt(self.getCurrentLocation());
				if (cell.isRoom()) {
					int row = getCurrentPlayer().getRow();
					int col = getCurrentPlayer().getColumn();
					char initial = cell.getCellType();
					String roomSel = findMapValue(initial);
					MakeAGuessPanel magpanel = new MakeAGuessPanel(ClueGame.getBoard(), roomSel);
					magpanel.setVisible(true);
				} else {
					setSubmittionComplete(true);
				}
				repaint();
				return;
			}
		}
		if (getCurrentPlayer() == getSelf() && playerSelectedTarget == false) {
			JOptionPane.showMessageDialog(null, "That is not a target...");
		}
	}
	
	public boolean isInRoomm() {
		return isInRoom;
	}
	
	public void setNextPlayer() {
		playerIter++;
		if (playerIter > 5) {
			playerIter = 0;
		}
		currentPlayer = allPlayers.get(playerIter);
	}
	
	public void makeArrayOfAllPlayers() {
		allPlayers.add(self);
		for (int i = 0; i < compPlayers.size(); i++) {
			allPlayers.add(compPlayers.get(i));
		}
	}

	public void rollDice() {
		Random rand = new Random();
		int roll = rand.nextInt(6);
		setDiceNum(roll + 1);
	}

	public String findMapValue(char Initial) {
		for (Map.Entry<Character, String> entry: rooms.entrySet()) {
			if (entry.getKey().equals(Initial)) {
				return entry.getValue();
			}
		}
		return null;
	}

	/**
	 * Calls helper functions to load data from legend and and board config files
	 */
	public void loadConfigFiles(String legendFilename, String boardFilename, String playersFilename, String cardsFilename) throws BadConfigFormatException {

		try {
			readLegend(legendFilename);
		} catch (FileNotFoundException e) {
			System.out.println("could not read legend file");
		}

		try {
			readBoard(boardFilename);
		} catch (FileNotFoundException e) {
			System.out.println("could not read board file");
		}
		try {
			readPlayers(playersFilename);
		} catch (FileNotFoundException e) {
			System.out.println("could not read players file");
		}
		try {
			readCards(cardsFilename);
		} catch (FileNotFoundException e) {
			System.out.println("could not read cards file");
		}
	}

	/**
	 * Iterates through lines in legend file, splits each line at ", ", and adds initial and name to rooms map.
	 * Also adds to inverted map, to allow getting initials from names
	 */
	public void readLegend(String legendFilename) throws FileNotFoundException, BadConfigFormatException {
		String legendLine; 
		FileReader legendFile = new FileReader(legendFilename);
		Scanner scan = new Scanner(legendFile);

		while( scan.hasNextLine() ) {
			legendLine = scan.nextLine();
			String[] line;
			line = legendLine.split(", ");
			if( line.length > 2 ) throw new BadConfigFormatException("Legend file has more than two items per line");
			rooms.put(line[0].charAt(0), line[1]);
			iRooms.put( line[1], line[0].charAt(0));
		}
		scan.close();
	} 

	/**
	 * Iterates through lines in board config file, splitting by "," and adding each symbol to cells as the appropriate cell type
	 */
	public void readBoard(String boardFilename) throws FileNotFoundException, BadConfigFormatException {
		FileReader boardFile = new FileReader(boardFilename);
		Scanner scan = new Scanner(boardFile);
		char walkwayKey = getInitial("Walkway");

		LinkedList<Integer> rowSize = new LinkedList<Integer>();		//stores number of "cells" per line for EVERY LINE
		numColumns = 0;
		numRows = 0;
		while( scan.hasNext() ) {
			String line = scan.next();
			String cellInits[] = line.split(",");

			numColumns = cellInits.length;

			for( int i = 0; i < cellInits.length; i++ ) {
				String cellInit = cellInits[i];

				BoardCell cell;
				if( cellInit.charAt(0) == walkwayKey ) {
					cell = new WalkwayCell();
				} else {
					cell = new RoomCell(cellInit);
				}
				cells.add(cell);
			}

			rowSize.add(numColumns);
			numRows++;
		}
		scan.close();
		for( int i = 1; i < rowSize.size(); i++ ) {
			if( rowSize.get(i) != rowSize.get(i-1) ) throw new BadConfigFormatException("Not all rows the same length");
		}

	}

	// Reads in Players
	public void readPlayers(String playersFilename) throws FileNotFoundException, BadConfigFormatException {
		String playersLine; 
		FileReader playersFile = new FileReader(playersFilename);
		Scanner scan = new Scanner(playersFile);
		playersLine = scan.nextLine();
		String[] line = playersLine.split(", ");
		self.setName(line[0]);
		self.setCol(line[1]);
		self.setRow(Integer.parseInt(line[2]));
		self.setColumn(Integer.parseInt(line[3]));
		self.setCurrentLocation(calcIndex(Integer.parseInt(line[3]), Integer.parseInt(line[2])));
		while( scan.hasNextLine() ) {
			playersLine = scan.nextLine();
			String[] l;
			l = playersLine.split(", ");
			ComputerPlayer comp = new ComputerPlayer();
			comp.setName(l[0]);
			comp.setCol(l[1]);
			comp.setRow(Integer.parseInt(l[2]));
			comp.setColumn(Integer.parseInt(l[3]));
			comp.setCurrentLocation(calcIndex(Integer.parseInt(l[3]), Integer.parseInt(l[2])));
			compPlayers.add(comp);
		}
		scan.close();
	} 

	// Reads in Cards
	public void readCards(String cardsFilename) throws FileNotFoundException, BadConfigFormatException {
		String cardsLine; 
		FileReader cardsFile = new FileReader(cardsFilename);
		Scanner scan = new Scanner(cardsFile);
		while( scan.hasNextLine() ) {
			cardsLine = scan.nextLine();
			String[] card;
			card = cardsLine.split(", ");
			CardType type = CardType.NONE;
			switch (card[1]) {
			case "P": 	type = CardType.PERSON;
			numPlayers++;
			break;
			case "R":   type = CardType.ROOM;
			numRooms++;
			break;
			case "W":   type = CardType.WEAPON;
			numWeapons++;
			break;
			}
			Card c = new Card(card[0], type);
			allCards.add(c);
		}
		scan.close();
	}

	public void dealCards() {
		boolean person = false;
		boolean room = false;
		boolean weapon = false;

		//takes the list of all cards and shuffles them into a new array for random distribution
		Collections.shuffle(allCards);
		ArrayList<Card> c = new ArrayList<Card>();
		ArrayList<String> a = new ArrayList<String>();
		for (int j = 0; j < allCards.size(); j++) {
			if (allCards.get(j).getCardType() == CardType.WEAPON && weapon == false) {
				a.add(allCards.get(j).getName());
				weapon = true;
			} else if (allCards.get(j).getCardType() == CardType.PERSON && person == false) {
				a.add(allCards.get(j).getName());
				person = true;
			} else if (allCards.get(j).getCardType() == CardType.ROOM && room == false) {
				a.add(allCards.get(j).getName());
				room = true;
			} else {
				c.add(allCards.get(j));
			}
		}
		setAnswers(a);

		Collections.shuffle(c);

		for (int i = 0; i < c.size();) {
			for (int j = 0; j < compPlayers.size(); j++) {
				compPlayers.get(j).addCards(c.get(i));
				c.get(i).incTimesDealt();
				compPlayers.get(j).updateSeen(c.get(i).getName());
				numDealt++;
				i++;
			}
			self.addCards(c.get(i));
			c.get(i).incTimesDealt();
			numDealt++;
			i++;
		}
	}

	/**
	 * Calculates adjacencies list for all points on board
	 */
	public void calcAdjacencies() {

		for( int row = 0; row < numRows; row++ ) {
			for( int col = 0; col < numColumns; col++ ) {

				LinkedList<Integer> list = new LinkedList<Integer>();

				if( getCellAt(calcIndex(row, col)).isWalkway() || getCellAt(calcIndex(row, col)).isDoorway() ) {

					// Up
					if( row-1 >= 0 && ( getCellAt(calcIndex(row-1, col)).isDoorway() || getCellAt(calcIndex(row-1, col)).isWalkway() ) ) {
						if( getCellAt(calcIndex(row-1, col)).isWalkway() || getRoomCellAt(row-1, col).getDoorDirection() == RoomCell.DoorDirection.DOWN) {
							list.add(calcIndex(row-1, col));
						}
					}
					// Down
					if( row+1 < numRows && ( getCellAt(calcIndex(row+1, col)).isDoorway() || getCellAt(calcIndex(row+1, col)).isWalkway() ) ) {
						if( getCellAt(calcIndex(row+1, col)).isWalkway() || getRoomCellAt(row+1, col).getDoorDirection() == RoomCell.DoorDirection.UP ) {
							list.add(calcIndex(row+1, col));
						}
					}
					// Left
					if( col-1 >= 0 && ( getCellAt(calcIndex(row, col-1)).isDoorway() || getCellAt(calcIndex(row, col-1)).isWalkway() ) ) {
						if( getCellAt(calcIndex(row, col-1)).isWalkway() || getRoomCellAt(row, col-1).getDoorDirection() == RoomCell.DoorDirection.RIGHT ) {
							list.add(calcIndex(row, col-1));
						}
					}
					// Right
					if( col+1 < numColumns && ( getCellAt(calcIndex(row, col+1)).isDoorway() || getCellAt(calcIndex(row, col+1)).isWalkway() ) ) {
						if( getCellAt(calcIndex(row, col+1)).isWalkway() || getRoomCellAt(row, col+1).getDoorDirection() == RoomCell.DoorDirection.LEFT ) {
							list.add(calcIndex(row, col+1));
						}
					}
				}
				adjMatrix.put(calcIndex(row, col), list);
			}
		}
	}

	/**
	 * 
	 * @return HashSet<BoardCell>
	 */
	public HashSet<BoardCell> getTargets() {

		// maybe put this in calcTargets
		for( int cell = 0; cell < numRows*numColumns; cell++ ) {
			visited[cell] = false;
		}

		HashSet<BoardCell> targetCells = new HashSet<BoardCell>();
		for( int i : targets ) {
			targetCells.add(getCellAt(i));
		}
		return targetCells;	
	}

	public void calcTargets(int startLocation, int numberOfSteps) {
		targets.clear();
		if (getCellAt(startLocation).isDoorway()) {
			visited[startLocation] = true;
			targets = calcTargetsRecursively(getAdjList(startLocation).getLast(), numberOfSteps-1);
		} else {
			targets = calcTargetsRecursively(startLocation, numberOfSteps);
		}
		for (int i = 0; i < allPlayers.size(); i++) {
			if (targets.contains(allPlayers.get(i).getCurrentLocation()) && !getCellAt(allPlayers.get(i).getCurrentLocation()).isRoom()) {
				targets.remove(allPlayers.get(i).getCurrentLocation());
			}
		}
	}

	public HashSet<Integer> calcTargetsRecursively(int startLocation, int numberOfSteps) {
		visited[startLocation] = true;
		HashSet<Integer> set = new HashSet<Integer>();
		if( numberOfSteps == 0 || getCellAt(startLocation).isDoorway()) {
			set.add(startLocation);
			visited[startLocation] = false;
			return set;
		}

		for( int i : getAdjList(startLocation) ) {
			if( !visited[i] ) {
				visited[i] = true;		
				set.addAll(calcTargetsRecursively(i, numberOfSteps-1));
				visited[i] = false;
			}
		}		
		return set;
	}
	public String disproveSuggestion(ArrayList<String> suggestion, Player current) {
		Random p_rand = new Random();		//for random player
		Random c_rand = new Random();		//for random card
		int playerInt;						
		int cardInt;	
		ArrayList<Player> players = new ArrayList<Player>();	//all players in one list
		players.add(self);
		for (int j = 0; j < compPlayers.size(); j++) {
			players.add(compPlayers.get(j));
		}
		for (int i = 0; i < players.size(); i++) {
			if (suggestion.contains(players.get(i).getName())) {
				players.get(i).setCurrentLocation(current.getCurrentLocation());
				players.get(i).setRow(current.getRow());
				players.get(i).setColumn(current.getColumn());
				repaint();
			}
		}
		playerInt = p_rand.nextInt(players.size());
		ArrayList<String> playerCards = new ArrayList<String>();		//copy list of the cards each player has
		int counter = players.size();									// iteration through each player only once
		ArrayList<String> matches = new ArrayList<String>();			//holds which cards would match the suggestion
		while (counter > 0) {
			if (!(current.getName().equals(players.get(playerInt).getName()))) {
				for (int i = 0; i < players.get(playerInt).getCards().size(); ++i ) {
					playerCards.add(players.get(playerInt).getCards().get(i).getName());
				}
				if (playerCards.contains(suggestion.get(0)))
					matches.add(suggestion.get(0));
				if(playerCards.contains(suggestion.get(1)))
					matches.add(suggestion.get(1));
				if(playerCards.contains(suggestion.get(2)))
					matches.add(suggestion.get(2));
				if (matches.size() > 0 ) {
					cardInt = c_rand.nextInt(matches.size());
					for (int f = 0; f < compPlayers.size(); f++) {
						compPlayers.get(f).updateSeen(matches.get(cardInt));
					}
					//randomly returns match
					return matches.get(cardInt);						
				}
			}
			//if the random playerInt reaches end of the array, restarts at the beginning
			if (playerInt < players.size() - 1) {		
				playerInt++;
			} else {
				playerInt = 0;
			}
			counter--;
		}
		return null;
	}
	

	/**
	 * Convert row and column coordinates to cell index
	 */
	public int calcIndex(int row, int col) {
		return col + (row * numColumns);
	}

	public int[] calcCoords(int index) {
		int[] coords = { index/numColumns, index%numColumns };
		return coords;
	}

	/**
	 * Get RoomCell at row and column coordinates 
	 */
	public RoomCell getRoomCellAt(int row, int col) {
		return (RoomCell)cells.get(calcIndex(row, col));
	}

	/*
	 *  Getters
	 */
	public LinkedList<Integer> getAdjList(int index) {
		return adjMatrix.get(index);
	}
	public char getInitial(String roomName) {
		return iRooms.get(roomName);
	}
	public Map<Character, String> getRooms() {
		return rooms;
	}
	public ArrayList<BoardCell> getCells() {
		return cells;
	}
	public int getNumRows() {
		return numRows;
	}
	public int getNumColumns() {
		return numColumns;
	}
	public ArrayList<ComputerPlayer> getCompPlayers() {
		return compPlayers;
	}
	public void setCompPlayers(ArrayList<ComputerPlayer> compPlayers) {
		this.compPlayers = compPlayers;
	}
	public void addCompPlayers(ComputerPlayer compPlayer) {
		compPlayers.add(compPlayer);
	}
	public HumanPlayer getSelf() {
		return self;
	}
	public ArrayList<Card> getAllCards() {
		return allCards;
	}
	public void setAllCards(ArrayList<Card> allCards) {
		this.allCards = allCards;
	}
	public String getCardShown() {
		return cardShown;
	}
	public void setCardShown(String c) {
		cardShown = c;
	}
	public int getNumPlayers() {
		return numPlayers;
	}
	public int getNumRooms() {
		return numRooms;
	}
	public int getNumWeapons() {
		return numWeapons;
	}
	public int getNumDealt() {
		return numDealt;
	}
	public boolean isWon() {
		return won;
	}
	public boolean isPlayerSelectedTarget() {
		return playerSelectedTarget;
	}
	public boolean isPlayerEnteredRoom() {
		return playerEnteredRoom;
	}
	public ArrayList<String> getAnswers() {
		return answers;
	}
	public void setAnswers(ArrayList<String> answers) {
		this.answers = answers;
	}
	public ArrayList<String> getAccusations() {
		return accusations;
	}
	public int getDiceNum() {
		return diceNum;
	}
	public void setDiceNum(int diceNum) {
		this.diceNum = diceNum;
	}
	public void setAccusations(ArrayList<String> newAccusations) {
		accusations = newAccusations;
	}

	public void setWon() {
		//determines if a winner has occured by comparing accusation to answers
		if (getAccusations().contains(getAnswers().get(0)) && getAccusations().contains(getAnswers().get(1)) && getAccusations().contains(getAnswers().get(2))) {
			won = true;
			JOptionPane.showMessageDialog(null, getCurrentPlayer().getName() + " won!!!\n" + accusations);
			setVisible(false);
		} else {
			won = false;
			JOptionPane.showMessageDialog(null, getCurrentPlayer().getName() + " was wrong!\n" + accusations);
		}
	}
	public boolean getSubmittionComplete() {
		return submittionComplete;
	}
	public void setSubmittionComplete(boolean status) {
		submittionComplete = status;
	}
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	public boolean isTurnComplete() {
		return turnComplete;
	}
	public void setTurnComplete(boolean turn) {
		turnComplete = turn;
	}
	public ArrayList<String> getSuggestions() {
		return suggestions;
	}
	public void setSuggestions(ArrayList<String> suggestions) {
		this.suggestions = suggestions;
	}
	public BoardCell getCellAt(int i) {
		return cells.get(i);
	}

}
