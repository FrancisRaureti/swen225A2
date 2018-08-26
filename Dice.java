package clue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class Dice {
	public static final int SIZE = 40;
	public static final int DOTSIZE = 5;
	public static final int xpos = 600;
	public static final int D1YPOS =  Tile.size*26;
	public static final int D2YPOS =  Tile.size*28;
	
	public static int die1;
	public static int die2;
	
	
	public static int roll() {
		die1=(int) Math.ceil(Math.random()*6);
		die2=(int) Math.ceil(Math.random()*6);
		return die1+die2;
	}


	public void draw(Graphics g,Dimension area) {
		g.fillRect(xpos, D1YPOS, SIZE, SIZE);	
		g.fillRect(xpos, D2YPOS, SIZE, SIZE);
		g.setColor(Color.BLACK);
		g.drawRect(xpos, D1YPOS, SIZE, SIZE);
		g.drawRect(xpos, D2YPOS, SIZE, SIZE);
		if(die1== 1) {
			g.fillOval(xpos+SIZE/2,D1YPOS+SIZE/2, DOTSIZE, DOTSIZE);
		}else if(die1 == 2) {
			g.fillOval(xpos+SIZE*2/3,D1YPOS+SIZE*2/3, DOTSIZE, DOTSIZE);
			g.fillOval(xpos+SIZE/3,D1YPOS+SIZE/3, DOTSIZE, DOTSIZE);
		}else if(die1 == 3) {
			g.fillOval(xpos+SIZE*2/3,D1YPOS+SIZE/3, DOTSIZE, DOTSIZE);
			g.fillOval(xpos+SIZE/3,D1YPOS+SIZE*2/3, DOTSIZE, DOTSIZE);
			g.fillOval(xpos+SIZE/2,D1YPOS+SIZE/2, DOTSIZE, DOTSIZE);
		}else if(die1==4) {
			g.fillOval(xpos+SIZE*3/4,D1YPOS+SIZE*3/4, DOTSIZE, DOTSIZE);
			g.fillOval(xpos+SIZE/4,D1YPOS+SIZE/4, DOTSIZE, DOTSIZE);
			g.fillOval(xpos+SIZE/4,D1YPOS+SIZE*3/4, DOTSIZE, DOTSIZE);
			g.fillOval(xpos+SIZE*3/4,D1YPOS+SIZE/4, DOTSIZE, DOTSIZE);
		}else if (die1==5) {
			g.fillOval(xpos+SIZE*3/4,D1YPOS+SIZE*3/4, DOTSIZE, DOTSIZE);
			g.fillOval(xpos+SIZE/4,D1YPOS+SIZE/4, DOTSIZE, DOTSIZE);
			g.fillOval(xpos+SIZE/4,D1YPOS+SIZE*3/4, DOTSIZE, DOTSIZE);
			g.fillOval(xpos+SIZE*3/4,D1YPOS+SIZE/4, DOTSIZE, DOTSIZE);
			g.fillOval(xpos+SIZE/2,D1YPOS+SIZE/2, DOTSIZE, DOTSIZE);
		}else if(die1 == 6) {
			g.fillOval(xpos+SIZE/3,D1YPOS+SIZE/3, DOTSIZE, DOTSIZE);
			g.fillOval(xpos+SIZE*2/3,D1YPOS+SIZE/3, DOTSIZE, DOTSIZE);
			g.fillOval(xpos+SIZE/3,D1YPOS+SIZE*2/3, DOTSIZE, DOTSIZE);
			g.fillOval(xpos+SIZE*2/3,D1YPOS+SIZE*2/3, DOTSIZE, DOTSIZE);
			g.fillOval(xpos+SIZE/3,D1YPOS+SIZE/2, DOTSIZE, DOTSIZE);
			g.fillOval(xpos+SIZE*2/3,D1YPOS+SIZE/2, DOTSIZE, DOTSIZE);
		}
		if(die2== 1) {
			g.fillOval(xpos+SIZE/2,D2YPOS+SIZE/2, DOTSIZE, DOTSIZE);
		}else if(die2 == 2) {
			g.fillOval(xpos+SIZE*2/3,D2YPOS+SIZE*2/3, DOTSIZE, DOTSIZE);
			g.fillOval(xpos+SIZE/3,D2YPOS+SIZE/3, DOTSIZE, DOTSIZE);
		}else if(die2 == 3) {
			g.fillOval(xpos+SIZE*2/3,D2YPOS+SIZE/3, DOTSIZE, DOTSIZE);
			g.fillOval(xpos+SIZE/3,D2YPOS+SIZE*2/3, DOTSIZE, DOTSIZE);
			g.fillOval(xpos+SIZE/2,D2YPOS+SIZE/2, DOTSIZE, DOTSIZE);
		}else if(die2==4) {
			g.fillOval(xpos+SIZE*3/4,D2YPOS+SIZE*3/4, DOTSIZE, DOTSIZE);
			g.fillOval(xpos+SIZE/4,D2YPOS+SIZE/4, DOTSIZE, DOTSIZE);
			g.fillOval(xpos+SIZE/4,D2YPOS+SIZE*3/4, DOTSIZE, DOTSIZE);
			g.fillOval(xpos+SIZE*3/4,D2YPOS+SIZE/4, DOTSIZE, DOTSIZE);
		}else if (die2==5) {
			g.fillOval(xpos+SIZE*3/4,D2YPOS+SIZE*3/4, DOTSIZE, DOTSIZE);
			g.fillOval(xpos+SIZE/4,D2YPOS+SIZE/4, DOTSIZE, DOTSIZE);
			g.fillOval(xpos+SIZE/4,D2YPOS+SIZE*3/4, DOTSIZE, DOTSIZE);
			g.fillOval(xpos+SIZE*3/4,D2YPOS+SIZE/4, DOTSIZE, DOTSIZE);
			g.fillOval(xpos+SIZE/2,D2YPOS+SIZE/2, DOTSIZE, DOTSIZE);
		}else if(die2 == 6) {
			g.fillOval(xpos+SIZE/3,D2YPOS+SIZE/3, DOTSIZE, DOTSIZE);
			g.fillOval(xpos+SIZE*2/3,D2YPOS+SIZE/3, DOTSIZE, DOTSIZE);
			g.fillOval(xpos+SIZE/3,D2YPOS+SIZE*2/3, DOTSIZE, DOTSIZE);
			g.fillOval(xpos+SIZE*2/3,D2YPOS+SIZE*2/3, DOTSIZE, DOTSIZE);
			g.fillOval(xpos+SIZE/3,D2YPOS+SIZE/2, DOTSIZE, DOTSIZE);
			g.fillOval(xpos+SIZE*2/3,D2YPOS+SIZE/2, DOTSIZE, DOTSIZE);
		}
	}
}
