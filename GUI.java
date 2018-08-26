package clue;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.text.DefaultCaret;


//import code.GUI.Move;

public class GUI {
	private JFrame frame;
	private static final int DEFAULT_DRAWING_HEIGHT = 840;
	private static final int DEFAULT_DRAWING_WIDTH = 680;
	private static final int TEXT_OUTPUT_ROWS = 5;
	private static boolean gameOn = false;
	private JComponent drawing; 
	private JTextArea textOutputArea;
	static List<Player> players = new ArrayList<>();
	static Scanner sc = new Scanner(System.in);
	private static Boolean gameOver = false;
	private static Player currentTurn;
	private static Board board;
	
	
	public GUI() {
		initialise();

		
	}
	



	private void getNumPlayers() {
		players = new ArrayList<Player>();
		Object[] possibilities = {"3","4","5","6"};
		String input = (String)JOptionPane.showInputDialog(frame,"How many players would like to participate?","New Game",
				JOptionPane.PLAIN_MESSAGE,null,possibilities,"3");
		int numplayers =0;
		try {
			numplayers = Integer.parseInt(input);
		}catch(NumberFormatException e) {
			System.out.println("input not recognized please try again");
		}

		gameOn=true;
		for (int i = 0 ; i < numplayers ; i++) {
			Player p =new Player(CharacterCard.Name.values()[i]);
			players.add(p);
		}
		currentTurn = players.get(0);
		redraw();
		board = new Board(players);
		board.deal();
		Board.startTurn(currentTurn);
	}
	
	public void redraw() {
		frame.repaint();
	}
	
	public Dimension getDrawingAreaDimension() {
		return drawing.getSize();
	}
	
	public JTextArea getTextOutputArea() {
		return textOutputArea;
	}
	
	private void initialise() {
		JButton quit = new JButton("Quit");
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				System.exit(0); // cleanly end the program.
			}
		});
		JButton newgame = new JButton("New Game");
		newgame.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev) {
				getNumPlayers();
				redraw();
			}
		});
		
		JButton endTurn = new JButton("End Turn");
		endTurn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				currentTurn=nextPlayer(currentTurn);
				Board.startTurn(currentTurn);
				redraw();
			}
		});
		JButton Suggest = new JButton("Suggest");
		Suggest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				Board.suggest(currentTurn);
				redraw();

			}
		});
		
		JButton west = new JButton("\u2190");
		west.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				Board.move(currentTurn, Tile.Direction.WEST);
				redraw();
			}
		});

		JButton east = new JButton("\u2192");
		east.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				Board.move(currentTurn, Tile.Direction.EAST);
				redraw();
			}
		});

		JButton north = new JButton("\u2191");
		north.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				Board.move(currentTurn, Tile.Direction.NORTH);
				redraw();
			}
		});

		JButton south = new JButton("\u2193");
		south.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				Board.move(currentTurn, Tile.Direction.SOUTH);
				redraw();
			}
		});

		
		
		
		JPanel panel = new JPanel();
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));

		Border edge = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		panel.setBorder(edge);
		
		JPanel newquit = new JPanel();
		newquit.setLayout(new GridLayout(2, 1));
		// manually set a fixed size for the panel containing the load and quit
		// buttons (doesn't change with window resize).
		newquit.setMaximumSize(new Dimension(50, 100));
		newquit.add(quit);
		newquit.add(newgame);
		panel.add(newquit);
		panel.add(Box.createRigidArea(new Dimension(15, 0)));
	
		JPanel navigation = new JPanel();
		navigation.setMaximumSize(new Dimension(150, 60));
		navigation.setLayout(new GridLayout(2, 3));
		navigation.add(north);
		navigation.add(west);
		navigation.add(south);
		navigation.add(east);
		panel.add(navigation);
		panel.add(Box.createRigidArea(new Dimension(5, 0)));
		// glue is another invisible component that grows to take up all the
		// space it can on resize.
		
		JPanel phases = new JPanel();
		phases.setMaximumSize(new Dimension(300,60));
		phases.setLayout(new GridLayout(1,3));
		panel.add(Box.createHorizontalGlue());
		phases.add(endTurn);
		phases.add(Suggest);
		panel.add(phases);
		panel.add(Box.createRigidArea(new Dimension(5, 0)));
		

		// this prevents a bug where the component won't be
		// drawn until it is resized.
		panel.setVisible(true);
		
		drawing = new JComponent() {
			protected void paintComponent(Graphics g) {
				redraw(g);
			}
		};
		
		drawing.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				Board.onClick(e,currentTurn);
				redraw();
				if(Board.getSelectedWeapon()!=null && Board.getSelectedCharacter()!=null) {
				    final JFrame confirm = new JFrame();	        
			        int result = JOptionPane.showConfirmDialog(confirm, "Would you like to suggest these cards?");			        
			        if (result==JOptionPane.YES_OPTION) {
			        	Board.submitSuggest(currentTurn);
			        }else if(result==JOptionPane.CANCEL_OPTION) {
			        	Board.suggestionPhase=false;
			        	Board.setSelectedCharacter(null);
			        	Board.setSelectedWeapon(null);
			        	redraw();
			        }
				}
			}
		});
		drawing.setPreferredSize(new Dimension(DEFAULT_DRAWING_WIDTH,
				DEFAULT_DRAWING_HEIGHT));
		// this prevents a bug where the component won't be
		// drawn until it is resized.
		drawing.setVisible(true);
		
		textOutputArea = new JTextArea(TEXT_OUTPUT_ROWS, 0);
		textOutputArea.setLineWrap(true);
		textOutputArea.setWrapStyleWord(true); // pretty line wrap.
		textOutputArea.setEditable(false);
		JScrollPane scroll = new JScrollPane(textOutputArea);
		// these two lines make the JScrollPane always scroll to the bottom when
		// text is appended to the JTextArea.
		DefaultCaret caret = (DefaultCaret) textOutputArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		split.setDividerSize(5); // make the selectable area smaller
		split.setContinuousLayout(true); // make the panes resize nicely
		split.setResizeWeight(1); // always give extra space to drawings
		// JSplitPanes have a default border that makes an ugly row of pixels at
		// the top, remove it.
		split.setBorder(BorderFactory.createEmptyBorder());
		split.setTopComponent(drawing);
		split.setBottomComponent(scroll);

		
		frame = new JFrame("Clue");
		// this makes the program actually quit when the frame's close button is
		// pressed.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(panel, BorderLayout.NORTH);
		frame.add(split, BorderLayout.CENTER);

		// always do these two things last, in this order.
		frame.pack();
		frame.setVisible(true);
	}

	protected void redraw(Graphics g) {
		if(gameOn) {
		board.draw(g, getDrawingAreaDimension(),currentTurn);
		}
		
	}
	private static void accuse(Player p, Scanner sc) {
		List<Card> accusable = new ArrayList<>(); 
		for(Card c : board.getCards()) {
			if(!p.hand.contains(c) && !p.refuted.contains(c)) {
				accusable.add(c);
			}
		}
		System.out.println(p.name.toString() +" the cards you can suggest are: \n");
		for(Card c : accusable) {
			System.out.println(c.toString());
		}
		System.out.println("\n");
		WeaponCard w = null;
		CharacterCard ch = null;
		RoomCard room = null;
		while(true) {
			String input = sc.nextLine();
			for (Card c : accusable) {
				if(input.equals(c.toString())) {
					if(c instanceof WeaponCard)w=(WeaponCard)c;
					if(c instanceof CharacterCard)ch = (CharacterCard) c;
					if(c instanceof RoomCard)room = (RoomCard)c;
				}
			}
			if(w!=null && ch!=null && room!=null)break;
			if(room==null)System.out.println("you must input a room type from list");
			if(w==null)System.out.println("you must input a weapon type from list");
			if(ch==null)System.out.println("you must input a character type from list");
		}
		if(board.getSolution().accusation(w,ch,room)) {
			gameOver=true;
		}
	}
	
	/**
	 * returns next player in line for turn
	 * @param p
	 * @return
	 */
	public static Player nextPlayer(Player p) {
		int i = players.indexOf(p);
		i+=1;
		if(i>=players.size()) {
			i=0;
		}
		return players.get(i);
	}
	

	
	/**
	 * method that game runs from
	 * ends when game is over
	 * @param args
	 */
	public static void main(String[] args) {
		new GUI();
	}
	

}
