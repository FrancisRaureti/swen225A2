package clue;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class SingleSelectButton extends JPanel implements ActionListener {


	private static final long serialVersionUID = 1L;
	public SingleSelectButton() {
		super(new BorderLayout());


	}
	public String selectCharacter(ArrayList<String> unchosenCharacters) {

		//Create the buttons 
		JRadioButton MISS_SCARLET = new JRadioButton("Miss Scarlet", false);
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
			JFrame radioPanel = new JFrame();
			radioPanel.pack();
			radioPanel.setVisible(true);
			if(unchosenCharacters.contains("Miss Scarlet")) {
				radioPanel.add(MISS_SCARLET);
				System.out.print("Miss Scarlet");
			}
			if(unchosenCharacters.contains("Col. Mustard")) {
				radioPanel.add(COL_MUSTARD);
			}
			if(unchosenCharacters.contains("Mrs White")) {
				radioPanel.add(MRS_WHITE);	
			}
			if(unchosenCharacters.contains("Mr Green")) {
				radioPanel.add(MR_GREEN);
			}
			if(unchosenCharacters.contains("Mrs Peacock")) {
				radioPanel.add(MRS_PEACOCK);
			}
			if(unchosenCharacters.contains("Prof. Plum")) {
				radioPanel.add(PROF_PLUM);	
			}
		
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
		return selected;

		}
		public void actionPerformed(ActionEvent arg0) {

		}





	}
