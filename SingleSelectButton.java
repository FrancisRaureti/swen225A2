package clue;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class SingleSelectButton extends JPanel implements ActionListener {
	private final ArrayList<String> unchosenCharacters = new ArrayList<String>(Arrays.asList("Miss Scarlet","Col. Mustard","Mrs White","Mr Green","Mrs Peacock","Prof. Plum"));


	private static final long serialVersionUID = 1L;
	public SingleSelectButton() {
		super(new BorderLayout());

	}
	public CharacterCard.Name selectCharacter() {
		JPanel panel = new JPanel();
		//Create the buttons
		JRadioButton MISS_SCARLET = new JRadioButton("Miss Scarlet", true);
		JRadioButton COL_MUSTARD = new JRadioButton("Col. Mustard", false);
		JRadioButton MRS_WHITE = new JRadioButton("Mrs White", false);
		JRadioButton MR_GREEN = new JRadioButton("Mr Green", false);
		JRadioButton MRS_PEACOCK = new JRadioButton("Mrs Peacock", false);
		JRadioButton PROF_PLUM = new JRadioButton("Prof. Plum", false);

		//Adds all of the buttons to a group so that only a single button can be pressed at a time
		ButtonGroup bGroup = new ButtonGroup();
		bGroup.add(MISS_SCARLET);
		bGroup.add(COL_MUSTARD);
		bGroup.add(MRS_WHITE);
		bGroup.add(MR_GREEN);
		bGroup.add(MRS_PEACOCK);
		bGroup.add(PROF_PLUM);

		String selected = "";

		//Create the JRadioButton

		//Bounds of the buttons
		MISS_SCARLET.setBounds(75,50,100,30);
		COL_MUSTARD.setBounds(75,75,100,30);
		MRS_WHITE.setBounds(75,100,100,30);
		MR_GREEN.setBounds(75,125,100,30);
		MRS_PEACOCK.setBounds(75,150,100,30);
		PROF_PLUM.setBounds(75,175,100,30);

		if(unchosenCharacters.contains("Miss Scarlet")) {
			panel.add(MISS_SCARLET);
		}
		if(unchosenCharacters.contains("Col. Mustard")) {
			panel.add(COL_MUSTARD);
		}
		if(unchosenCharacters.contains("Mrs White")) {
			panel.add(MRS_WHITE);
		}
		if(unchosenCharacters.contains("Mr Green")) {
			panel.add(MR_GREEN);
		}
		if(unchosenCharacters.contains("Mrs Peacock")) {
			panel.add(MRS_PEACOCK);
		}
		if(unchosenCharacters.contains("Prof. Plum")) {
			panel.add(PROF_PLUM);
		}
		JOptionPane.showMessageDialog(null, panel, "Select character token", 3);
		//Add the Listener for each button to respond with
		//		MISS_SCARLET.addActionListener(actionListener);
		//		COL_MUSTARD.addActionListener(actionListener);
		//		MISS_SCARLET.addActionListener(actionListener);
		//		MRS_WHITE.addActionListener(actionListener);
		//		MR_GREEN.addActionListener(actionListener);
		//		MRS_PEACOCK.addActionListener(actionListener);
		//		PROF_PLUM.addActionListener(actionListener);

		//Gets the button that was selected
		for (Enumeration<AbstractButton> buttons = bGroup.getElements(); buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();

			if(button.isSelected()) {
				selected = button.getText();
			}
		}

		this.unchosenCharacters.remove(selected);
		return character(selected);

	}
	public CharacterCard.Name character(String character){

		switch(character) {
		case "Miss Scarlet": return CharacterCard.Name.MISS_SCARLET;
		case "Col Mustard": return CharacterCard.Name.COLONEL_MUSTARD;
		case "Mrs White": return CharacterCard.Name.MRS_WHITE;
		case "Mr Green": return CharacterCard.Name.MR_GREEN;
		case "Mrs Peacock" : return CharacterCard.Name.MRS_PEACOCK;
		case"Prof Plum":return CharacterCard.Name.PROFESSOR_PLUM;
		default:return CharacterCard.Name.MISS_SCARLET;
		}
	}
	public void actionPerformed(ActionEvent arg0) {

	}





}