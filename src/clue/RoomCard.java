package clue;
import java.awt.Dimension;
import java.awt.Graphics;

public class RoomCard extends Card {
 
	private static int size = 30;
	
	public enum Name {
		Ballroom,
		Billiard_Room,
		Conservatory,
		Dining_room,
		Hall,
		Kitchen,
		Library,
		Lounge,
		Study;
	}

	private Name name;

	public RoomCard(Name name){
		this.name=name;
		super.name=this.toString();
	}

	public Name getName() {
		return this.name;
	}


	private static String[] roomCards = {"Ballroom","Billiard Room","Conservatory","Dining Room","Hall","Kitchen","Library","Lounge","Study"};
	
	@Override
	public String toString() {
		return roomCards[this.name.ordinal()];
	}
	
	@Override
	public boolean isEqualTo(String name) {
		if (this.toString().equals(name))return true;
		return false;
	}

	@Override
	public Card stringToCard(String input) {
		if(input.equals(this.toString()))return (Card)this;
		return null;
	}

	@Override
	public boolean isRefutable(Suggestion s) {
		if(s.room==this)return true;
		return false;
	}




}