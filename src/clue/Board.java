package clue;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class Board {
	
	private static final Color weaponcolor = new Color(200,255,255); 
	private static final Color Charactercolor = new Color(255,155,0);
	private static final Color roomcolor = new Color(0,255,155); 
	
	private static List<Player> players;
	private static Dice dice = new Dice();
	private static Tile[][] tiles;
	private static  List<Card> cards;
	private static List<Card> weapons;
	private static List<Card> roomCards;
	private static List<Card> characterCards;
	public static Solution solution;
	public static Suggestion suggestion;
	private static WeaponCard selectedWeapon;
	private static CharacterCard selectedCharacter;
	private static RoomCard selectedRoom;
	
	static boolean suggestionPhase;
	static boolean refutationPhase;
	static boolean AccusationPhase;
	
	public static Player refutee;
	
	public void draw(Graphics g, Dimension screen,Player currentTurn) {		
		Graphics2D g2 = (Graphics2D) g;
		
		for (int rows = 0 ; rows<getTiles().length; rows++) {
			for (int cols = 0 ; cols<getTiles()[0].length ; cols++) {
				Tile t = tiles[rows][cols];
				if(t.ishall()) {
					g2.setColor(Color.YELLOW);
				}else if(t.isRoom()) {
					g2.setColor(Color.GRAY);
				}
				t.draw(g2, screen);
				g2.setColor(Color.BLACK);
				t.drawWalls(g2, screen);

				
			}}
		for(Player p : players) {
			p.draw(g2, screen);
		}
		int xpos =50;
		if(!refutationPhase)
		for(Card c : currentTurn.hand) {
			if(c instanceof WeaponCard)g2.setColor(weaponcolor);
			if(c instanceof CharacterCard)g2.setColor(Charactercolor);
			if(c instanceof RoomCard)g2.setColor(roomcolor);
			c.draw(g2, screen, xpos,Tile.size*26+20);
			xpos+=90;
		}
		g.setColor(Color.WHITE);
		dice.draw(g2, screen);
		xpos = 50;
		int ypos = 50;
		int wcount =0;
		int ccount = 0;
		int rcount = 0;
		if(suggestionPhase) {
			for (Card c : currentTurn.suggestable) {
				if(c instanceof WeaponCard) {
						ypos=50;
						g2.setColor(weaponcolor);
						c.draw(g2, screen, xpos + wcount*80, ypos);
						wcount++;
					}else if(c instanceof CharacterCard) {
						ypos=180; 
						g2.setColor(Charactercolor);
						c.draw(g2, screen, xpos + ccount*80, ypos);
						ccount++;
					}

				
				if(c==getSelectedWeapon() || c==getSelectedCharacter()) {
					Stroke oldstroke = g2.getStroke();
					g2.setStroke(new BasicStroke(2));					
					c.drawOutline(g2, screen);
					g2.setStroke(oldstroke);
				}
				
			}
		}
		if(refutationPhase) {
			for (Card c : refutee.hand) {
				if(c.isRefutable(suggestion)) {
					if(c instanceof WeaponCard) {
						ypos=50;
						g2.setColor(weaponcolor);
						c.draw(g2, screen, xpos + wcount*80, ypos);
						wcount++;
					}else if(c instanceof CharacterCard) {
						ypos=180; 
						g2.setColor(Charactercolor);
						c.draw(g2, screen, xpos + ccount*80, ypos);
						ccount++;
					}else if(c instanceof RoomCard) {
						ypos = 310;
						g2.setColor(roomcolor);
						c.draw(g2, screen, xpos + rcount*80, ypos);
						rcount++;
					}
				}
			}
		}
		if(AccusationPhase) {
			for (Card c : currentTurn.suggestable) {
				if(c instanceof WeaponCard) {
					ypos=50;
					g2.setColor(weaponcolor);
					c.draw(g2, screen, xpos + wcount*80, ypos);
					wcount++;
				}else if(c instanceof CharacterCard) {
					ypos=180; 
					g2.setColor(Charactercolor);
					c.draw(g2, screen, xpos + ccount*80, ypos);
					ccount++;
				}else if(c instanceof RoomCard) {
					ypos = 310;
					g2.setColor(roomcolor);
					c.draw(g2, screen, xpos + rcount*80, ypos);
					rcount++;
				}
			}
		}
		
	}
	
	/**
	 * creates new board object and deals players cards
	 * @param players
	 */
	public Board(List<Player> players) {
		this.players=players;
		
		this.cards = new ArrayList<>();
		this.setTiles(new Tile[25][24]);
		
		roomCards = new ArrayList<>();
		weapons = new ArrayList<>();
		characterCards = new ArrayList<>();
		suggestionPhase=false;
		refutationPhase=false;
		
		fillBoard();
		
		
		//this part fills card lists
		for (WeaponCard.Name n : WeaponCard.Name.values()) {
			Card c = new WeaponCard(n);
			cards.add(c);
			weapons.add(c);
		}
		for (RoomCard.Name n : RoomCard.Name.values()) {
			Card c = new RoomCard(n);
			cards.add(c);
			roomCards.add(c);
		}
		for(CharacterCard.Name n : CharacterCard.Name.values()) {
			Card c = new CharacterCard(n);
			cards.add(c);
			characterCards.add(c);
		}
		
	
	}
	/**
	 * creates game solution then randomizes remaining 
	 * deck and deals out to players
	 * 
	 */
	public void deal() {
		WeaponCard murderWeapon;
		CharacterCard murderer;
		RoomCard murderRoom;
		
		List<Card> deck = new ArrayList<>(shuffle(getCards()));
		
		int random = (int)(Math.random()*weapons.size());
		murderWeapon = (WeaponCard)weapons.get(random);
		deck.remove(weapons.get(random));
		
		random = (int)(Math.random()*characterCards.size());
		murderer = (CharacterCard)characterCards.get(random);
		deck.remove(characterCards.get(random));
		
		random = (int)(Math.random()*roomCards.size());
		murderRoom = (RoomCard)roomCards.get(random);
		deck.remove(roomCards.get(random));
		
		this.setSolution(new Solution(murderWeapon,murderer,murderRoom));
		GUI.getTextOutputArea().setText(murderWeapon + " " + murderer + " " + murderRoom);
		System.out.println(random);
		while(!deck.isEmpty()) {
			for(Player p : players) {
				random = (int)(Math.random()*deck.size());
				if(!deck.isEmpty()) {
				p.hand.add(deck.get(random));
				deck.remove(random);
				}
			}
		}
	}
	
	
	/**
	 * method to initialize game solution
	 * @param solution
	 */
	private void setSolution(Solution solution) {
		Board.solution=solution;
		
	}

	
	/**
	 * Draws board object to console
	 */
	public String toString() {
		String s="\n \n \n \n######################################################################## \n";
		for (int rows = 0 ; rows<getTiles().length; rows++) {
			for (int cols = 0 ; cols<getTiles()[0].length ; cols++) {
				Player peice = null;
				
				for(Player p : players) {
					if(p.ypos==rows && p.xpos==cols) {
						peice = p;
					}
				}
				String tile = getTiles()[rows][cols].toString();
				//if player is on tile replaces center char with player char
				if(peice!=null) {
					tile = tile.substring(0,1) + peice.peice()+tile.substring(2);
				}
				s=s+tile;
			}
			s=s+"\n";
		}
		return s + "########################################################################";
	}
	
	/**
	 * moves player over board. returns true if player is eligible to make a 
	 * suggestion from newly moved to tile
	 * @param player
	 * @param direction
	 * @return 
	 */
	public static boolean move(Player p, Tile.Direction d) {		
		if(p.moves>0) {
		int prevX = p.xpos;
		int prevY = p.ypos;
		Tile adj = adjTile(getTiles()[p.ypos][p.xpos],d);
		if(getTiles()[p.ypos][p.xpos].walls.contains(d)) {
			System.out.println("there is a wall that you cannot move through");
		}else {
			if(adj!=null && !checkTile(adj)) {
				p.movePlayer(d);
				getTiles()[p.ypos][p.xpos].setHasPlayer(true);
				getTiles()[prevY][prevX].setHasPlayer(false);
			}else {
				System.out.println("you cannot move through other players");
			}
			if(getTiles()[p.ypos][p.xpos].isRoom() && !p.visitedRooms.contains(roomFromPos(p.xpos,p.ypos))) {
				return true;
			}			
		}
		}
		return false;

	}
	/**
	 * checks pile for player
	 * @param tile
	 * @return true if a player is on tile
	 */
	public static boolean checkTile(Tile t) {
		for(Player p : players) {
			if(t.getXpos()==p.xpos && t.getYpos()==p.ypos)return true;
		}
		return false;
	}
	
	/**
	 *originally made when the array was the only means of keeping track of tiles
	 *the tiles have been updated to contain x and y coordinates but the method still holds 
	 *returns the tile next adjacent in the direction of 
	 * @param tile
	 * @param direction
	 * @return adjacent Tile
	 */
	public static Tile adjTile(Tile t,Tile.Direction d) {
		int x = 0;
		int y = 0;
		for (int rows = 0 ; rows<getTiles().length ; rows++) {
			for (int cols = 0 ; cols < getTiles()[0].length ; cols++) {
				if (getTiles()[rows][cols]==t) {
					if(d==Tile.Direction.NORTH) {y=t.getYpos()-1; x = t.getXpos();}
					if(d==Tile.Direction.SOUTH) {y=t.getYpos()+1; x = t.getXpos();}
					if(d==Tile.Direction.EAST) {x=t.getXpos()+1; y = t.getYpos();}
					if(d==Tile.Direction.WEST) {x=t.getXpos()-1; y=t.getYpos();}
				}
			}
		}
		if(y>=getTiles().length || x>=getTiles()[0].length || x<0 || y<0) {
			System.out.println("you can't move off the board");
			return null;
		}
		
		return getTiles()[y][x];
		
	}


	/**
	 * method for randomizing cards
	 * @param deck
	 * @return
	 */
	public List<Card> shuffle(List<Card> deck){
		for(int i = 0 ; i<100 ; i++) {
			int random = (int)Math.random()*deck.size();
			Card c = deck.get(random);
			deck.remove(c);
			deck.add(0, c);
		}
		return deck;
	}

	/**
	 * 
	 * method for gaining room from tile position
	 * a little bit redundant now, was originally made when only way to find tile
	 * positions was from position in array 
	 * @param x
	 * @param y
	 * @return
	 */
	public static Card roomFromPos(int x , int y) {
		String s = "";
		
			if(y>=1 && y<6 && x<5 || x==5 && y>1 && y<7 || x>0 && x<6 && y==6) {
				s = "Kitchen";
			}else if(x>9 && x<13 && y==1 || y>1 && y<7 && x>7 && x<16  ) {
				 s = "Ballroom";
			}else if(x>=0 && x<8 && y>9 && y<16 || y==9 && x>=0 && x<5) {
				s = "Dining Room"; 
			}else if(x>=0 && x<8 && y>18) {
				s= "Lounge";
			}else if(x>8 && x<13 && y>17) {
				s="Hall";
			}else if(x>16 && y>20) {
				s="Study";
			} else if(x>17 && y>13 && y<19 || x>16 && y>14 && y<18) {
				s="Library";
			}else if(x>17 && y>7 && y<13 ) {
				s= "Billiard Room";
			}else if(x>17 && y<5 || x>18 && y==5) {
				s = "Dining Room";
			}
		
		for(Card c : roomCards) {
			if(c.isEqualTo(s))return c;
		}
		
		return null;
	}
	/**
	 * method for moving player to room being suggested
	 * places the player in 
	 * @param mover
	 * @param moved
	 */
	public static void movePlayerTo(Player mover, Player moved) {
		Tile adj = null;
		Tile target = null;
		for (int rows = 0 ; rows<getTiles().length ; rows++) {
			for (int cols = 0 ; cols < getTiles()[0].length ; cols++) {
				if(rows==mover.ypos && cols==mover.xpos) {
					adj = getTiles()[rows][cols];
				}
			}
		}
		for(int i = 0 ; i<Tile.Direction.values().length ; i++) {
			Tile.Direction d = Tile.Direction.values()[i];
				target=adjTile(adj, d);
				System.out.println(target.getData());
				if(target.isRoom()) {
				
				moved.xpos=target.getXpos();
				moved.ypos=target.getYpos();
			}
		}

		
	}
	/**
	 * begins player turn rolls dice and resets fields to clear gui
	 * 
	 * @param p
	 */	
	public static void startTurn(Player p) {
		suggestionPhase=false;
		refutationPhase=false;
		AccusationPhase=false;
		refutee = null;
		p.moves=dice.roll();
		for(Card c : cards) {
			c.reset();
		}
	}
	
	
	public void fillBoard() {
		int[][] tiles = new int[][] {{4,0,0,0,0,4,0,4,6,27,12,0,0,6,27,12,4,0,4,0,0,0,0,4},
				{41,32,32,32,34,15,14,25,21,22,41,32,32,35,28,21,19,14,41,32,32,32,32,35},
				{32,32,32,32,32,35,25,18,41,33,32,32,32,32,33,35,24,19,40,32,32,32,32,32},
				{40,32,32,32,32,34,24,18,40,32,32,32,32,32,32,34,24,18,40,32,32,32,32,32},
				{32,32,32,32,32,34,24,18,40,32,32,32,32,32,32,34,24,18,40,32,32,32,36,38},
				{44,32,32,32,32,34,24,16,32,32,32,32,32,32,32,32,16,16,18,44,36,38,15,13},
				{7,44,36,36,32,38,24,18,40,32,32,32,32,32,32,34,24,16,16,17,17,17,17,23},
				{29,17,17,17,16,17,16,18,44,32,36,36,36,36,32,38,24,16,20,20,20,20,22,13},
				{7,28,20,20,20,16,16,16,17,16,17,17,17,17,16,17,16,18,41,33,33,33,33,33},
				{33,33,33,33,35,28,20,20,16,16,20,20,20,20,20,16,16,16,32,32,32,32,32,32},
				{32,32,32,32,32,33,33,35,24,18,9,1,1,1,3,24,16,18,40,32,32,32,32,32},
				{32,32,32,32,32,32,32,34,24,18,8,0,0,0,2,24,16,18,40,32,32,32,32,32},
				{32,32,32,32,32,32,32,32,16,18,8,0,0,0,2,24,16,18,44,36,36,36,32,36},
				{32,32,32,32,32,32,32,34,24,18,8,0,0,0,2,24,16,16,21,21,17,21,22,9},
				{32,32,32,32,32,32,32,34,24,18,8,0,0,0,2,24,16,22,41,33,32,33,35,8},
				{36,36,36,36,36,36,32,38,24,18,8,0,0,0,2,24,18,41,32,32,32,32,32,32},
				{7,25,17,17,17,17,16,17,16,18,12,4,4,4,6,24,16,32,32,32,32,32,32,32},
				{29,16,16,16,16,16,16,16,16,20,21,17,17,21,21,16,18,44,32,32,32,32,32,32},
				{7,28,20,20,20,20,16,16,18,41,33,32,32,33,35,24,16,19,44,36,36,36,38,12},
				{15,41,33,33,33,33,34,24,18,40,32,32,32,32,34,24,16,16,17,17,17,17,17,23},
				{33,32,32,32,32,32,34,24,18,40,32,32,32,32,32,16,16,16,20,20,20,20,22,13},
				{32,32,32,32,32,32,34,24,18,40,32,32,32,32,34,24,18,40,33,33,33,33,35,15},
				{32,32,32,32,32,32,34,24,18,40,32,32,32,32,34,24,18,40,32,32,32,32,32,33},
				{32,32,32,32,32,32,38,24,22,40,32,32,32,32,34,28,18,44,32,32,32,32,32,32},
				{44,32,36,36,32,38,11,30,11,44,32,32,32,32,38,11,30,11,44,32,32,32,32,38}};
		for (int rows = 0 ; rows<tiles.length ; rows++) {
			for (int cols = 0 ; cols < tiles[0].length ; cols++) {
				this.getTiles()[rows][cols]=new Tile(tiles[rows][cols],cols,rows);
			}
		}		
	}


	public List<Player> getPlayers(){
		return this.players;
	}

	public Solution getSolution() {
		return solution;
	}

	public List<Card> getCards() {
		return this.cards;
	}


	public static Tile[][] getTiles() {
		return tiles;
	}
	public void setTiles(Tile[][] tiles) {
		this.tiles = tiles;
	}
	/**
	 * Begins suggestion phase and allows player to select cards for suggestion
	 * @param currentTurn
	 */
	public static void suggest(Player currentTurn) {
		if(Board.roomFromPos(currentTurn.xpos,currentTurn.ypos) instanceof RoomCard) {
		List<Card> suggestable = new ArrayList<>(); 
		for(Card c : cards) {
			if(!currentTurn.hand.contains(c) && !(c instanceof RoomCard) && !currentTurn.refuted.contains(c)) {
				suggestable.add(c);
			}
		}
		currentTurn.suggestable = suggestable;
		suggestionPhase = true;
		}
	}
	
	
	/**
	 * Mouse event triggers during suggestion and refutation phases
	 * for choosing cards to refutes / suggest with
	 * @param e
	 * @param currentTurn
	 */
	public static void onClick(MouseEvent e, Player currentTurn) {
		if(suggestionPhase) {
			for(Card c : currentTurn.suggestable) {
				if(c.xpos-c.size/2<e.getX() && c.xpos+c.size/2>e.getX() 
						&& c.ypos-c.size/2<e.getY() && c.ypos+c.size*(3/2)>e.getY()) {
					if(c instanceof WeaponCard) {
						setSelectedWeapon((WeaponCard) c);
					}
					else if(c instanceof CharacterCard) {
						setSelectedCharacter((CharacterCard)c);
					}
				}
			}
		}else if(refutationPhase) {
			if (refutee==currentTurn) {}
			else if(refutee.canRefute(suggestion)) {
				for(Card c : refutee.hand) {
					if(c.isRefutable(suggestion)) {
						if(c.xpos-c.size/2<e.getX() && c.xpos+c.size/2>e.getX() 
								&& c.ypos-c.size/2<e.getY() && c.ypos+c.size*(3/2)>e.getY()) {
							if(c instanceof WeaponCard) {
								setSelectedWeapon((WeaponCard) c);							
								}
							else if(c instanceof CharacterCard) {
								setSelectedCharacter((CharacterCard)c);
								}
							else if(c instanceof RoomCard) {
								setSelectedRoom((RoomCard)c);
								}
						}
					}
				}
			}
		}else if(AccusationPhase) {
			for(Card c : currentTurn.suggestable) {
				if(c.xpos-c.size/2<e.getX() && c.xpos+c.size/2>e.getX() 
						&& c.ypos-c.size/2<e.getY() && c.ypos+c.size*(3/2)>e.getY()) {
					if(c instanceof WeaponCard) {
						setSelectedWeapon((WeaponCard) c);
					}
					else if(c instanceof CharacterCard) {
						setSelectedCharacter((CharacterCard)c);
					}else if(c instanceof RoomCard) {
						setSelectedRoom((RoomCard)c);
					}
				}

			}
		}

	}



	public static void setSelectedRoom(RoomCard c) {
		if(refutationPhase) {
			selectedWeapon = null;
			selectedCharacter = null;
		}
		selectedRoom=c;

	}
	

	public static RoomCard getSelectedRoom() {
		return selectedRoom;
	}

	public static CharacterCard getSelectedCharacter() {
		return selectedCharacter;
	}

	public static void setSelectedCharacter(CharacterCard selectedCharacter) {
		Board.selectedCharacter = selectedCharacter;
		if(refutationPhase) {
			selectedRoom=null;
			selectedWeapon = null;
		}
	}

	public static WeaponCard getSelectedWeapon() {
		return selectedWeapon;
	}

	public static void setSelectedWeapon(WeaponCard selectedWeapon) {
		Board.selectedWeapon = selectedWeapon;
		if(refutationPhase) {
			selectedRoom = (null);
			selectedCharacter = (null);			
		}
	}
	/**
	 * moves player being suggested
	 * begins refutation phase
	 * sele 
	 * @param currentTurn
	 */
	public static void submitSuggest(Player currentTurn) {
		for(Player suggested : players) {
			if (suggested.name.equals(selectedCharacter.getName())) {
				movePlayerTo(currentTurn, suggested);
			}
		}
		suggestionPhase=false;
		refutationPhase = true;
		suggestion = new Suggestion((RoomCard)Board.roomFromPos(currentTurn.xpos,currentTurn.ypos),selectedCharacter,selectedWeapon);
		refutee = nextRefutee(currentTurn);
		setSelectedWeapon(null);
		setSelectedCharacter(null);
	}
	
	public static void submitRefute(Player currentTurn) {
		Card refute = null;
		if(selectedCharacter!=null) {
			refute = selectedCharacter;
		}else if(selectedRoom!=null) {
			refute = selectedRoom;
		}else if(selectedWeapon!=null){
			refute = selectedWeapon;
		}
		currentTurn.refuted.add(refute);
		
		selectedWeapon = null;
		selectedRoom = null;
		selectedCharacter = null;
		refutee = nextRefutee(refutee);
		if (refutee==currentTurn) {
			GUI.setCurrentTurn(GUI.nextPlayer(currentTurn));
			startTurn(GUI.nextPlayer(currentTurn));
		}		
	}
	
	//Problem here this method of selecting next refutee is defective
	public static Player nextRefutee(Player player) {
		Player temp = GUI.nextPlayer(player);
		while (!temp.canRefute(suggestion)) {			
			
			if(temp.canRefute(suggestion))return temp;
			if(temp==GUI.getCurrentTurn()) {
				return temp;
			}
			temp = GUI.nextPlayer(temp);
		}
		return temp;
		
	}
	
	public static void accuse(Player currentTurn) {
		List<Card> suggestable = new ArrayList<>(); 
		for(Card c : cards) {
			if(!currentTurn.hand.contains(c) && !currentTurn.refuted.contains(c)) {
				suggestable.add(c);
			}
		}
		currentTurn.suggestable = suggestable;
		AccusationPhase=true;
		selectedWeapon = null;
		selectedRoom = null;
		selectedCharacter = null;
	}
	


}