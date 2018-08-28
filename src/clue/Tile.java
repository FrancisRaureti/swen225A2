package clue;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;


public class Tile {
	
	private int data;
	private boolean hasPlayer = false;
	private Boolean room;
	private Boolean hallway;
	List<Direction> walls;
	private int xpos;
	private int ypos;
	
	public static final int size = 28;
	
	public enum Direction{
		NORTH,
		EAST,
		SOUTH,
		WEST;
	}
	
	public Tile(int data, int xpos, int ypos) {
		this.setData(data);
		this.setXpos(xpos);
		this.setYpos(ypos);
		walls = new ArrayList<>();
		int r = 0;
		if(data-32>=0) {
			this.room = true;
			this.hallway = false;
			r = data-32;
		}else if(data-16>=0) {
			this.hallway = true;
			this.room = false;
			r = data-16;
		}else {
			r=data;
			this.hallway = false;
			this.room = false;
		}
		if(r-8>=0) {
			walls.add(Direction.WEST);
			r=r-8;
		}
		if(r-4>=0) {
			walls.add(Direction.SOUTH);
			r=r-4;
		}
		if(r-2>=0) {
			walls.add(Direction.EAST);
			r=r-2;
		}
		if(r-1>=0) {
			walls.add(Direction.NORTH);
			r=r-1;
		}
		
	}
	
	public String toString(){
		String s = " ";
		if(walls.contains(Direction.SOUTH)) {
			s = "_";
		}

		if(walls.contains(Direction.WEST)){
			s = "|" +s;
		}else {
			s= " "+s;
		}
		if(walls.contains(Direction.EAST)) {
			s=s+"|";
		}else {
			s=s+" ";
		}
		if(!room && !hallway) {
			s = "|#|";
		}
		return s;
	}
	
	public void draw(Graphics g, Dimension area) {
		g.fillRect(xpos*size, ypos*size, size, size);

	}

	public void drawWalls(Graphics g, Dimension area) {
		if(walls.contains(Direction.SOUTH)) {
			g.drawLine(xpos*size, ypos*size+size, xpos*size+size, ypos*size+size);
		}
		if(walls.contains(Direction.WEST)){
			g.drawLine(xpos*size, ypos*size, xpos*size, ypos*size+size);
		}
		if(walls.contains(Direction.EAST)) {
			g.drawLine(xpos*size+size, ypos*size, xpos*size+size, ypos*size+size);
		}
		if(walls.contains(Direction.NORTH)) {
			g.drawLine(xpos*size, ypos*size, xpos*size+size, ypos*size);
		}
	}
	public void setHasPlayer(boolean b) {
		this.hasPlayer=b;
	}
	
	public boolean getHasPlayer() {
		return this.hasPlayer;
	}

	public boolean isRoom() {
		return room;
	}
	
	public boolean ishall() {
		return hallway;
	}

	public int getXpos() {
		return xpos;
	}

	public void setXpos(int xpos) {
		this.xpos = xpos;
	}

	public int getYpos() {
		return ypos;
	}

	public void setYpos(int ypos) {
		this.ypos = ypos;
	}

	public int getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}
}