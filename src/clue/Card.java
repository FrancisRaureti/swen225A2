package clue;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public abstract class Card {
	
	String name;
	
	public static final int size = 75;
	public int xpos;
	public int ypos;
	
	public abstract Card stringToCard(String input);
	
	/**
	 * not a regular is equal method
	 * takes a string and returns true if string accurately describes cards name
	 * @param name
	 * @return boolean
	 */
	public abstract boolean isEqualTo(String name);
	
	public abstract String toString();
	
	public abstract boolean isRefutable(Suggestion s);

	public  void draw(Graphics g, Dimension area, int xpos, int ypos) {
		this.xpos=xpos;
		this.ypos=ypos;
		
		g.fillRect(xpos - size/2, ypos - size/2, size, (int)(size*1.5));	
		g.setColor(Color.BLACK);
		g.drawString(name, xpos-size/2, ypos-20);
		
	}
	
	public void drawOutline(Graphics g, Dimension area) {
		
		g.setColor(Color.WHITE);		
		g.drawRect(xpos - size/2, ypos - size/2, size, (int)(size*1.5));	
	}
	
	public void reset() {
		this.xpos=-100;
		this.ypos=-100;		
	}
}