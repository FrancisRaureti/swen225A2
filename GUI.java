package clue;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.MenuItem;
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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.text.DefaultCaret;


//import code.GUI.Move;
/**
 * this class has aspects of the GUI class from the COMP261 assignment
 * but has been retrofitted to suit the swen225 assignment 2
 * there is lots of code that will imitate the code from the 221 assignment
 * as it was used as a reference for drawing up a good GUI
 * @author Raure
 *
 */
public class GUI {
	private JFrame frame;
	private static final int DEFAULT_DRAWING_HEIGHT = 840;
	private static final int DEFAULT_DRAWING_WIDTH = 680;
	private static final int TEXT_OUTPUT_ROWS = 5;
	private static boolean gameOn = false;
	private JComponent drawing; 
	private JTextArea textOutputArea;
	private JMenuBar theJMenu;
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
		setCurrentTurn(players.get(0));
		redraw();
		board = new Board(players);
		board.deal();
		Board.startTurn(getCurrentTurn());
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

		JButton Accuse = new JButton("Accuse");
		Accuse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				if(!currentTurn.hasAccused) {
					final JFrame confirm = new JFrame();	        
					int result = JOptionPane.showConfirmDialog(confirm, "Are you sure you Want to accuse? this can only be done once");
					if(result==JOptionPane.OK_OPTION) {
						Board.accuse();
					}
					else {

					}
				}else {
					getTextOutputArea().setText("You have already made an Accusation this game");
				}
			}
		});
		
		
		JButton endTurn = new JButton("End Turn");
		endTurn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				setCurrentTurn(nextPlayer(getCurrentTurn()));
				Board.startTurn(getCurrentTurn());
				redraw();
			}
		});
		JButton Suggest = new JButton("Suggest");
		Suggest.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent ev) {
				if(!currentTurn.hasAccused) {
					Board.suggest(getCurrentTurn());
					redraw();
				}else {
					getTextOutputArea().setText("You have already made an accusation therefore can no longer make suggestions");
				}
			}
		});
		
		JButton west = new JButton("\u2190");
		west.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				Board.move(getCurrentTurn(), Tile.Direction.WEST);
				redraw();
			}
		});

		JButton east = new JButton("\u2192");
		east.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				Board.move(getCurrentTurn(), Tile.Direction.EAST);
				redraw();
			}
		});

		JButton north = new JButton("\u2191");
		north.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				Board.move(getCurrentTurn(), Tile.Direction.NORTH);
				redraw();
			}
		});

		JButton south = new JButton("\u2193");
		south.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				Board.move(getCurrentTurn(), Tile.Direction.SOUTH);
				redraw();
			}
		});
		
		
		JMenu menu = new JMenu("Game");
		JMenuItem newGame = new JMenuItem("New Game");
		newGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				getNumPlayers();
				redraw();
			}
		});
		
		JMenuItem Quit = new JMenuItem("Quit");
		Quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0); 				
			}
		});
		
		theJMenu = new JMenuBar();
		
		menu.add(newGame);
		menu.add(Quit);
		theJMenu.add(menu);
		
		
		
		JPanel panel = new JPanel();
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));

		Border edge = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		panel.setBorder(edge);
		

	
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
		phases.add(Accuse);
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
				Board.onClick(e,getCurrentTurn());
				redraw();

				//in the sugestion phase when the player has selected cards
				if(Board.suggestionPhase) {
					if(Board.getSelectedWeapon()!=null && Board.getSelectedCharacter()!=null) {
						final JFrame confirm = new JFrame();	        
						int result = JOptionPane.showConfirmDialog(confirm, "Would you like to suggest these cards?");			        
						if (result==JOptionPane.YES_OPTION) {
							//submits cards for suggestion and begins the refutation phase
							Board.submitSuggest(getCurrentTurn());
							redraw();

						}else if(result==JOptionPane.CANCEL_OPTION) {
							// quits out of suggestion phase back to move phase
							Board.suggestionPhase=false;
							Board.setSelectedCharacter(null);
							Board.setSelectedWeapon(null);
							redraw();
						}
					}
				}

				if(Board.refutationPhase) {
					if(Board.getSelectedWeapon()!=null || Board.getSelectedCharacter()!=null || Board.getSelectedRoom()!=null) {
						final JFrame confirm = new JFrame();	        
						int result = JOptionPane.showConfirmDialog(confirm, "Is this the card you wish to refute with?");			        
						if (result==JOptionPane.YES_OPTION) {
							Board.submitRefute(getCurrentTurn());							
							redraw();
						}
					}
				}
				
				if(Board.AccusationPhase) {
					if(Board.getSelectedWeapon()!=null && Board.getSelectedCharacter()!=null && Board.getSelectedRoom()!=null) {
						final JFrame confirm = new JFrame();	        
						int result = JOptionPane.showConfirmDialog(confirm, "Are these the cards you wish to accuse with?");
						if (result==JOptionPane.YES_OPTION) {
							if(Board.solution.accusation(Board.getSelectedWeapon(),Board.getSelectedCharacter(), Board.getSelectedRoom())) {
								getTextOutputArea().setText("Game Over, the winner is " + currentTurn.toString());
								initialise();
							}else {
								currentTurn.hasAccused=true;
							}
						}
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
		frame.setJMenuBar(theJMenu);
		// always do these two things last, in this order.
		frame.pack();
		frame.setVisible(true);
		
	}

	protected void redraw(Graphics g) {
		if(gameOn) {
		board.draw(g, getDrawingAreaDimension(),getCurrentTurn());
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
	
	public static void Refutation(Player player, Suggestion s) {
		if(player==getCurrentTurn()) {}
		if(player.canRefute(s)) {
			Board.refutee = player;
		}
	}

	
	/**
	 * method that game runs from
	 * ends when game is over
	 * @param args
	 */
	public static void main(String[] args) {
		new GUI();
	}




	public static Player getCurrentTurn() {
		return currentTurn;
	}




	public static void setCurrentTurn(Player currentTurn) {
		GUI.currentTurn = currentTurn;
	}
	

}
