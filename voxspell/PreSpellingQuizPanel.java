package voxspell;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

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
	private JSlider sliderTestNumber;
	private JSlider sliderVolume;
	private JSlider sliderSpeed;

	//private Quiz quiz;

	/**
	 * Create the panel.
	 */
	public PreSpellingQuizPanel(VoxspellFrame origin,CardLayout cardLayout, JPanel cards) {
		_originFrame = origin;
		_cardLayout = cardLayout;
		_cardPanel = cards;
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		setSize(650,500);
		JPanel panel = new JPanel();
		add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 133, 279, 0, 62, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 21, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);

		JLabel lblChooseSpellingList = new JLabel("Choose Spelling List:");
		lblChooseSpellingList.setHorizontalAlignment(SwingConstants.RIGHT);
		lblChooseSpellingList.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		GridBagConstraints gbc_lblChooseSpellingList = new GridBagConstraints();
		gbc_lblChooseSpellingList.insets = new Insets(0, 0, 5, 5);
		gbc_lblChooseSpellingList.anchor = GridBagConstraints.EAST;
		gbc_lblChooseSpellingList.gridx = 1;
		gbc_lblChooseSpellingList.gridy = 1;
		panel.add(lblChooseSpellingList, gbc_lblChooseSpellingList);

		String[] spellingListList = BashCommand.bashReturnCommand("ls spelling_lists").toArray(new String[0]);
		comboBox = new JComboBox(spellingListList);
		comboBox.setSelectedIndex(0);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridwidth = 2;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 1;
		panel.add(comboBox, gbc_comboBox);

		lblNumberOfWords = new JLabel("Number of Words to Test:");
		lblNumberOfWords.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNumberOfWords.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		GridBagConstraints gbc_lblNumberOfWords = new GridBagConstraints();
		gbc_lblNumberOfWords.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumberOfWords.gridx = 1;
		gbc_lblNumberOfWords.gridy = 2;
		panel.add(lblNumberOfWords, gbc_lblNumberOfWords);
		
		sliderTestNumber = new JSlider();
		sliderTestNumber.setPreferredSize(new Dimension(400,50));
		sliderTestNumber.setOpaque(false);
		sliderTestNumber.setMajorTickSpacing(95);
		sliderTestNumber.setMinorTickSpacing(5);
		sliderTestNumber.setMinimum(5);
		sliderTestNumber.setValue(10);
		sliderTestNumber.setPaintTicks(true);
		sliderTestNumber.setPaintLabels(true);
		sliderTestNumber.setLabelTable(sliderTestNumber.createStandardLabels(95));
		GridBagConstraints gbc_sliderTests = new GridBagConstraints();
		gbc_sliderTests.gridwidth = 2;
		gbc_sliderTests.insets = new Insets(0, 0, 5, 5);
		gbc_sliderTests.gridx = 2;
		gbc_sliderTests.gridy = 2;
		panel.add(sliderTestNumber, gbc_sliderTests);

		lblChooseTheme = new JLabel("               Choose Theme:");
		lblChooseTheme.setHorizontalAlignment(SwingConstants.RIGHT);
		lblChooseTheme.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		GridBagConstraints gbc_lblChooseTheme = new GridBagConstraints();
		gbc_lblChooseTheme.insets = new Insets(0, 0, 5, 5);
		gbc_lblChooseTheme.gridx = 1;
		gbc_lblChooseTheme.gridy = 3;
		panel.add(lblChooseTheme, gbc_lblChooseTheme);

		lblVoice = new JLabel("Voice:");
		lblVoice.setHorizontalAlignment(SwingConstants.RIGHT);
		lblVoice.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		GridBagConstraints gbc_lblVoice = new GridBagConstraints();
		gbc_lblVoice.anchor = GridBagConstraints.EAST;
		gbc_lblVoice.insets = new Insets(0, 0, 5, 5);
		gbc_lblVoice.gridx = 1;
		gbc_lblVoice.gridy = 4;
		panel.add(lblVoice, gbc_lblVoice);
		
		String[] voiceList = BashCommand.bashReturnCommand("ls /usr/share/festival/voices/english").toArray(new String[0]);
		comboBox_1 = new JComboBox(voiceList);
		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.gridwidth = 2;
		gbc_comboBox_1.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_1.gridx = 2;
		gbc_comboBox_1.gridy = 4;
		panel.add(comboBox_1, gbc_comboBox_1);

		lblVoiceVolume = new JLabel("Voice Volume:");
		lblVoiceVolume.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		lblVoiceVolume.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblVoiceVolume = new GridBagConstraints();
		gbc_lblVoiceVolume.anchor = GridBagConstraints.EAST;
		gbc_lblVoiceVolume.insets = new Insets(0, 0, 5, 5);
		gbc_lblVoiceVolume.gridx = 1;
		gbc_lblVoiceVolume.gridy = 5;
		panel.add(lblVoiceVolume, gbc_lblVoiceVolume);
		
		sliderVolume = new JSlider();
		GridBagConstraints gbc_sliderVolume = new GridBagConstraints();
		gbc_sliderVolume.gridwidth = 2;
		gbc_sliderVolume.insets = new Insets(0, 0, 5, 5);
		gbc_sliderVolume.gridx = 2;
		gbc_sliderVolume.gridy = 5;
		panel.add(sliderVolume, gbc_sliderVolume);

		lblVoiceSpeed = new JLabel("                 Voice Speed:");
		lblVoiceSpeed.setHorizontalAlignment(SwingConstants.RIGHT);
		lblVoiceSpeed.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		GridBagConstraints gbc_lblVoiceSpeed = new GridBagConstraints();
		gbc_lblVoiceSpeed.insets = new Insets(0, 0, 5, 5);
		gbc_lblVoiceSpeed.gridx = 1;
		gbc_lblVoiceSpeed.gridy = 6;
		panel.add(lblVoiceSpeed, gbc_lblVoiceSpeed);
		
		sliderSpeed = new JSlider();
		sliderSpeed.setOpaque(false);
		GridBagConstraints gbc_sliderSpeed = new GridBagConstraints();
		gbc_sliderSpeed.gridwidth = 2;
		gbc_sliderSpeed.insets = new Insets(0, 0, 5, 5);
		gbc_sliderSpeed.gridx = 2;
		gbc_sliderSpeed.gridy = 6;
		panel.add(sliderSpeed, gbc_sliderSpeed);

		btnContinue = new JButton("Continue");
		btnContinue.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				_cardLayout.show(_cardPanel, "SpellingQuiz");
				_originFrame.spellingQuiz.setWordList((String)comboBox.getSelectedItem());
				_originFrame.spellingQuiz.setListSize(sliderTestNumber.getValue());
				_originFrame.spellingQuiz.startQuiz();

				SoundHandler.playSound("pop.wav");
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

				SoundHandler.playSound("pop.wav");
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

