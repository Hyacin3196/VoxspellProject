package voxspell;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JSeparator;

public class PreSpellingQuizPanel extends JPanel {
	private VoxspellFrame _originFrame;
	private CardLayout _cardLayout;
	private JPanel _cardPanel;
	private JComboBox comboBox;
	private JLabel lblNumberOfWords;
	private JLabel lblChooseTheme;
	private JLabel lblVoice;
	private JComboBox comboBox_1;
	private JLabel lblVoiceVolume;
	private JLabel lblVoiceSpeed;
	private JButton btnContinue;
	private JButton btnBack;

	//private Quiz quiz;

	/**
	 * Create the panel.
	 */
	public PreSpellingQuizPanel(VoxspellFrame origin,CardLayout cardLayout, JPanel cards) {
		_originFrame = origin;
		_cardLayout = cardLayout;
		_cardPanel = cards;
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));



		setSize(800,600);
		JPanel panel = new JPanel();
		add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 317, 0, 62, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 21, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);

		JLabel lblChooseSpellingList = new JLabel("Choose Spelling List:");
		lblChooseSpellingList.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		GridBagConstraints gbc_lblChooseSpellingList = new GridBagConstraints();
		gbc_lblChooseSpellingList.insets = new Insets(0, 0, 5, 5);
		gbc_lblChooseSpellingList.anchor = GridBagConstraints.EAST;
		gbc_lblChooseSpellingList.gridx = 1;
		gbc_lblChooseSpellingList.gridy = 1;
		panel.add(lblChooseSpellingList, gbc_lblChooseSpellingList);

		comboBox = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridwidth = 2;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 1;
		panel.add(comboBox, gbc_comboBox);

		lblNumberOfWords = new JLabel("Number of Words to Test:");
		lblNumberOfWords.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		GridBagConstraints gbc_lblNumberOfWords = new GridBagConstraints();
		gbc_lblNumberOfWords.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumberOfWords.gridx = 1;
		gbc_lblNumberOfWords.gridy = 2;
		panel.add(lblNumberOfWords, gbc_lblNumberOfWords);

		lblChooseTheme = new JLabel("Choose Theme:");
		lblChooseTheme.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		GridBagConstraints gbc_lblChooseTheme = new GridBagConstraints();
		gbc_lblChooseTheme.insets = new Insets(0, 0, 5, 5);
		gbc_lblChooseTheme.gridx = 1;
		gbc_lblChooseTheme.gridy = 3;
		panel.add(lblChooseTheme, gbc_lblChooseTheme);

		lblVoice = new JLabel("Voice:");
		lblVoice.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		GridBagConstraints gbc_lblVoice = new GridBagConstraints();
		gbc_lblVoice.anchor = GridBagConstraints.EAST;
		gbc_lblVoice.insets = new Insets(0, 0, 5, 5);
		gbc_lblVoice.gridx = 1;
		gbc_lblVoice.gridy = 4;
		panel.add(lblVoice, gbc_lblVoice);

		comboBox_1 = new JComboBox();
		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.gridwidth = 2;
		gbc_comboBox_1.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_1.gridx = 2;
		gbc_comboBox_1.gridy = 4;
		panel.add(comboBox_1, gbc_comboBox_1);

		lblVoiceVolume = new JLabel("Voice Volume:");
		lblVoiceVolume.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		lblVoiceVolume.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblVoiceVolume = new GridBagConstraints();
		gbc_lblVoiceVolume.anchor = GridBagConstraints.EAST;
		gbc_lblVoiceVolume.insets = new Insets(0, 0, 5, 5);
		gbc_lblVoiceVolume.gridx = 1;
		gbc_lblVoiceVolume.gridy = 5;
		panel.add(lblVoiceVolume, gbc_lblVoiceVolume);

		lblVoiceSpeed = new JLabel("Voice Speed:");
		lblVoiceSpeed.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		GridBagConstraints gbc_lblVoiceSpeed = new GridBagConstraints();
		gbc_lblVoiceSpeed.insets = new Insets(0, 0, 5, 5);
		gbc_lblVoiceSpeed.gridx = 1;
		gbc_lblVoiceSpeed.gridy = 6;
		panel.add(lblVoiceSpeed, gbc_lblVoiceSpeed);

		btnContinue = new JButton("Continue");
		btnContinue.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				_cardLayout.show(_cardPanel, "SpellingQuiz");
				_originFrame.spellingQuiz.initiateQuiz();
			}
		});
		GridBagConstraints gbc_btnContinue = new GridBagConstraints();
		gbc_btnContinue.insets = new Insets(0, 0, 0, 5);
		gbc_btnContinue.gridx = 3;
		gbc_btnContinue.gridy = 9;
		panel.add(btnContinue, gbc_btnContinue);

		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				_cardLayout.show(_cardPanel, "Menu");
			}
		});
		GridBagConstraints gbc_btnBack = new GridBagConstraints();
		gbc_btnBack.gridx = 4;
		gbc_btnBack.gridy = 9;
		panel.add(btnBack, gbc_btnBack);
		_originFrame = origin;
		_cardLayout = cardLayout;
		_cardPanel = cards;
	}



}

