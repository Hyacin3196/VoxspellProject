package voxspell;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JSeparator;
import java.awt.Insets;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel {

	private JLabel welcomeText;
	
	private JButton btnSpellingQuiz, btnUserStats, btnOptions, btnExit;
	/**
	 * Create the panel.
	 */
	public MenuPanel() {
		setSize(800,600);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		add(Box.createRigidArea(new Dimension(0,60)));

		// Title text
		welcomeText = new JLabel("VOXSPELL");
		welcomeText.setAlignmentX(CENTER_ALIGNMENT);
		welcomeText.setFont(new Font("Comic Sans MS", 1, 48));
		add(welcomeText);
		

		add(Box.createRigidArea(new Dimension(0,200)));

		btnSpellingQuiz = createAndAddButton("Spelling Quiz");
		btnUserStats = createAndAddButton("User Statistics");
		btnOptions = createAndAddButton("Options");
		btnExit = createAndAddButton("Exit");
	}

	/*
	 * Used to create buttons at the fixed size and alignment and add them to the menu panel
	 */
	private JButton createAndAddButton(String buttonText) {
		JButton button = new JButton(buttonText);
		button.setMaximumSize(new Dimension(400,65));
		button.setAlignmentX(CENTER_ALIGNMENT);
		button.setFont(new Font("Comic Sans MS", 1, 20));
		add(Box.createRigidArea(new Dimension(0,5)));
		add(button);
		return new JButton();
	}
}
