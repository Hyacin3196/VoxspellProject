package voxspell;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Hashtable;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import handler.BashCommand;
import handler.FileHandler;
import worker.SoundWorker;

import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

public class PreSpellingQuizPanel extends JPanel {
	private VoxspellFrame _originFrame;
	private CardLayout _cardLayout;
	private JPanel _cardPanel;
	private JComboBox spellingListComboBox;
	private JLabel lblNumberOfWords;
	private JLabel lblVoice;
	private JComboBox voiceComboBox;
	private JButton btnContinue;
	private JButton btnBack;
	private JSlider sliderQuizSize;
	private JTextField quizSizeTextField;
	private JLabel lblChooseSpellingList;

	//private Quiz quiz;

	/**
	 * Create the panel.
	 */
	public PreSpellingQuizPanel(VoxspellFrame origin,CardLayout cardLayout, JPanel cards) {
		_originFrame = origin;
		_cardLayout = cardLayout;
		_cardPanel = cards;

		setSize(650,500);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 133, 279, 0, 62, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 21, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		//create list for the Spelling List Combo box
		String[] spellingListList =  FileHandler.getFolderContents("spelling_lists");

		//create list for the Voice List Combo box
		String[] voiceList = FileHandler.getFolderContents("/usr/share/festival/voices/english");

		//initialise Choose spellingList label
		lblChooseSpellingList = new JLabel("Choose Spelling List:");
		lblChooseSpellingList.setHorizontalAlignment(SwingConstants.RIGHT);
		lblChooseSpellingList.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		GridBagConstraints gbc_lblChooseSpellingList = new GridBagConstraints();
		gbc_lblChooseSpellingList.insets = new Insets(0, 0, 5, 5);
		gbc_lblChooseSpellingList.anchor = GridBagConstraints.EAST;
		gbc_lblChooseSpellingList.gridx = 1;
		gbc_lblChooseSpellingList.gridy = 1;
		this.add(lblChooseSpellingList, gbc_lblChooseSpellingList);

		//initialise Choose spellingList comboBox
		spellingListComboBox = new JComboBox(spellingListList);
		spellingListComboBox.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		spellingListComboBox.setSelectedIndex(0);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridwidth = 2;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 1;
		this.add(spellingListComboBox, gbc_comboBox);

		//initialise "Choose word number" label
		lblNumberOfWords = new JLabel("Number of Words to Test:");
		lblNumberOfWords.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNumberOfWords.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		GridBagConstraints gbc_lblNumberOfWords = new GridBagConstraints();
		gbc_lblNumberOfWords.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumberOfWords.gridx = 1;
		gbc_lblNumberOfWords.gridy = 2;
		this.add(lblNumberOfWords, gbc_lblNumberOfWords);

		//initialise slider for choosing quiz size
		sliderQuizSize = new JSlider();
		sliderQuizSize.setPreferredSize(new Dimension(350,50));
		sliderQuizSize.setOpaque(false);
		sliderQuizSize.setMajorTickSpacing(95);
		sliderQuizSize.setMinorTickSpacing(5);
		sliderQuizSize.setMinimum(5);
		sliderQuizSize.setValue(10);
		sliderQuizSize.setPaintTicks(true);
		sliderQuizSize.setPaintLabels(true);
		sliderQuizSize.setLabelTable(sliderQuizSize.createStandardLabels(95));
		GridBagConstraints gbc_sliderTests = new GridBagConstraints();
		gbc_sliderTests.gridwidth = 2;
		gbc_sliderTests.insets = new Insets(0, 0, 5, 5);
		gbc_sliderTests.gridx = 2;
		gbc_sliderTests.gridy = 2;
		this.add(sliderQuizSize, gbc_sliderTests);
		sliderQuizSize.addChangeListener(new ChangeListener(){
			//update quizSizeTextField when slider changes
			public void stateChanged(ChangeEvent arg0) {
				quizSizeTextField.setText(""+sliderQuizSize.getValue());
			}
		});

		//initialise text field to enter exact value of quiz size
		quizSizeTextField = new JTextField();
		quizSizeTextField.setFont(new Font("Courier 10 Pitch", Font.PLAIN, 12));
		quizSizeTextField.setText(""+sliderQuizSize.getValue());
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 4;
		gbc_textField.gridy = 2;
		this.add(quizSizeTextField, gbc_textField);
		quizSizeTextField.setColumns(10);
		quizSizeTextField.addKeyListener(new KeyAdapter() {
			// Only allows numeric characters to be typed
			public void keyReleased(KeyEvent e) {
				int quizSize;
				try{
					quizSize = Integer.parseInt(quizSizeTextField.getText());
				}catch(NumberFormatException ex){
					quizSize = 5;
				}
				if(quizSize<5){
					sliderQuizSize.setValue(5);
				}else if(quizSize>100){
					sliderQuizSize.setValue(100);
				}else{
					sliderQuizSize.setValue(quizSize);
				}
			}
			public void keyTyped(KeyEvent e){
				char c = e.getKeyChar();
				if(!Character.isDigit(c)){
					e.consume();
				}
			}
		});
		quizSizeTextField.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent arg0) {
				//do nothing
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				int size;
				try{
					size = Integer.parseInt(quizSizeTextField.getText());
				}catch(NumberFormatException ex){
					size = 5;
				}
				if(size<5){
					quizSizeTextField.setText("5");
				}else if(size>100){
					quizSizeTextField.setText("100");
				}
			}
		});

		//initialise "choose voice" label
		lblVoice = new JLabel("Voice:");
		lblVoice.setHorizontalAlignment(SwingConstants.RIGHT);
		lblVoice.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		GridBagConstraints gbc_lblVoice = new GridBagConstraints();
		gbc_lblVoice.anchor = GridBagConstraints.EAST;
		gbc_lblVoice.insets = new Insets(0, 0, 5, 5);
		gbc_lblVoice.gridx = 1;
		gbc_lblVoice.gridy = 3;
		this.add(lblVoice, gbc_lblVoice);
		
		//initialise "choose voice" combo box
		voiceComboBox = new JComboBox(voiceList);
		voiceComboBox.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		voiceComboBox.setSelectedItem(BashCommand.getVoice());
		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.gridwidth = 2;
		gbc_comboBox_1.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_1.gridx = 2;
		gbc_comboBox_1.gridy = 3;
		this.add(voiceComboBox, gbc_comboBox_1);
		voiceComboBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				BashCommand.setVoice((String)voiceComboBox.getSelectedItem());
			}
		});
		
		//initialise "continue" button
		btnContinue = new JButton("Continue");
		btnContinue.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try{
					int x = Integer.parseInt(quizSizeTextField.getText());
					if(x<5||x>100){
						throw new NumberFormatException();
					}else{
						_cardLayout.show(_cardPanel, "SpellingQuiz");
					}
					_originFrame._spellingQuiz.setWordList((String)spellingListComboBox.getSelectedItem());
					_originFrame._spellingQuiz.setListSize(sliderQuizSize.getValue());
					_originFrame._spellingQuiz.startQuiz();

				}catch(NumberFormatException e){
					JOptionPane.showMessageDialog(PreSpellingQuizPanel.this, "Invalid quiz number!", "Error", JOptionPane.ERROR_MESSAGE);
				}

				SoundWorker.playSound("pop.wav");
			}
		});
		GridBagConstraints gbc_btnContinue = new GridBagConstraints();
		gbc_btnContinue.insets = new Insets(0, 0, 0, 5);
		gbc_btnContinue.gridx = 3;
		gbc_btnContinue.gridy = 5;
		this.add(btnContinue, gbc_btnContinue);

		//initialise "back to menu" button
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				_cardLayout.show(_cardPanel, "Menu");

				SoundWorker.playSound("pop.wav");
			}
		});
		GridBagConstraints gbc_btnBack = new GridBagConstraints();
		gbc_btnBack.gridx = 4;
		gbc_btnBack.gridy = 5;
		this.add(btnBack, gbc_btnBack);
		
	}

	public void startPreQuiz(){
		voiceComboBox.setSelectedItem(BashCommand.getVoice());
	}

	/**
	 * Adds painting to the PreSpelling Panel
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		int w = getWidth();
		int h = getHeight();
		Color color1 = _originFrame.getTheme().get(0);
		Color color2 = _originFrame.getTheme().get(1);
		GradientPaint primary = new GradientPaint(0, 0, color1, w, 0, color2);
		GradientPaint shade = new GradientPaint(0f, 0f, new Color(0, 0, 0, 0),0f, h, new Color(0, 0, 0, 96));
		g2d.setPaint(primary);
		g2d.fillRect(0, 0, w, h);
		g2d.setPaint(shade);
		g2d.fillRect(0, 0, w, h);

	}

}

