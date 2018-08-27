package clue;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;



public class Player {
	
	CharacterCard.Name name;
	Boolean inRoom;
	Boolean hasAccused;
	public List<Card> visitedRooms;
	public List<Card> hand;
	public List<Card> suggestable;
	public List<Card> refuted;
	public int xpos; 
	public int ypos;
	public int moves;
	
	/**
	 * this method constructs player and sets player position according to their 
	 * name
	 * @param name
	 */
	public Player(CharacterCard.Name name) {
		this.name=name;
		this.inRoom=false;
		this.hasAccused=false;
		suggestable = new ArrayList<Card>();
		hand = new ArrayList<Card>();
		visitedRooms = new ArrayList<Card>();
		refuted = new ArrayList<Card>();
		if(name==CharacterCard.Name.MISS_SCARLET) {xpos = 7; ypos = 24;}
		if(name==CharacterCard.Name.COLONEL_MUSTARD) {xpos = 0; ypos = 17;}
		if(name==CharacterCard.Name.MRS_WHITE) {xpos = 9; ypos = 0;}
		if(name==CharacterCard.Name.MR_GREEN) {xpos = 14; ypos = 0;}
		if(name==CharacterCard.Name.MRS_PEACOCK){xpos = 23; ypos = 6;}
		if(name==CharacterCard.Name.PROFESSOR_PLUM){xpos = 23; ypos = 19;}
		
	}
	
	/**
	 * this method returns the character piece symbol for drawing their position on the board
	 * @return 
	 */
	public String peice() {
		String s =  "";
		if(name==CharacterCard.Name.MISS_SCARLET) {s="S";}
		if(name==CharacterCard.Name.COLONEL_MUSTARD) {s = "C";}
		if(name==CharacterCard.Name.MRS_WHITE) {s = "W";}
		if(name==CharacterCard.Name.MR_GREEN) {s="G";}
		if(name==CharacterCard.Name.MRS_PEACOCK){s = "p";}
		if(name==CharacterCard.Name.PROFESSOR_PLUM) {s= "P";}
		return s;
	}
	
	public void draw(Graphics g, Dimension area ) {
		Color playercolour = Color.BLACK;
		if(name==CharacterCard.Name.MISS_SCARLET) {playercolour= new Color(230,20,40); }
		if(name==CharacterCard.Name.COLONEL_MUSTARD) {playercolour= new Color(140,180,20);}
		if(name==CharacterCard.Name.MRS_WHITE) {playercolour= new Color(255 ,255, 255);}
		if(name==CharacterCard.Name.MR_GREEN) {playercolour= new Color(30,200,30);}
		if(name==CharacterCard.Name.MRS_PEACOCK){playercolour= new Color(230,20,195);}
		if(name==CharacterCard.Name.PROFESSOR_PLUM) {playercolour= new Color(230,0,125);}
		g.setColor(playercolour);
		g.fillOval(xpos*Tile.size, ypos*Tile.size, Tile.size, Tile.size);
	}
	
	public void setMoves(int moves) {
		this.moves = moves;
	}
	
	public int getMoves() {
		return this.moves;
	}
	
	/**
	 * moves player via direction
	 * @param d
	 */
	public void movePlayer(Tile.Direction d) {
		if(d==Tile.Direction.NORTH) {this.ypos-=1;}
		if(d==Tile.Direction.SOUTH) {this.ypos+=1;}
		if(d==Tile.Direction.EAST) {this.xpos+=1;}
		if(d==Tile.Direction.WEST) {this.xpos-=1;}
		this.moves-=1;
	}
	/**
	 * returns true if player is able to refute suggestion
	 * @param s
	 * @return
	 */
	public boolean canRefute(Suggestion s) {
		if(hand.contains(s.character) || hand.contains(s.room) || hand.contains(s.Weapon)) {
			return true;
		}
		return false;
	}
	
	/**
	 * returns card that can be used for refutation if it matches given string
	 * @param s
	 * @return
	 */
	public Card refute(String s) {
		Card refute = null;
		for(Card c : hand) {
			if(c.toString().equalsIgnoreCase(s)) {
				refute = c;
			}
		}
		
		return refute;
		
	}

}
