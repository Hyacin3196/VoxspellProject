package voxspell;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JSeparator;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import java.awt.Insets;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JSlider;
import javax.swing.JTable;

import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultCaret;

import handler.BashCommand;
import handler.FileHandler;
import handler.SoundHandler;

import javax.swing.JScrollPane;
import javax.swing.JComboBox;

public class SpellingQuizPanel extends JPanel {
	private VoxspellFrame _originFrame;
	private CardLayout _cardLayout;
	private JPanel _cardPanel;
	private JTextField textField;

	private List<String> options;/**[voice,video]*/
	private List<String> wordList;
	private String wordListName;
	private int listSize = 10;
	private List<String> inputList = new ArrayList<String>();
	private String word;
	private String userAnswer;
	private boolean _isCorrect = false;
	private boolean _firstTry = false;
	private boolean _wordDone = false;
	private boolean _quizDone = false;
	private boolean _answered = false;
	private static final int inputListSize = 10;
	private int _wordsDone = 0;
	private int _wordsCorrect = 0;
	private int i = 0;


	private SpeechWorker speaker = new SpeechWorker("");
	private JButton btnRepeat;
	private JButton btnOptions;
	private JButton btnBack;
	private JButton btnExit;
	private JButton btnNextWord;
	private JSlider sliderVoiceVolume;
	private JLabel lblVoiceVolume;
	private JSlider sliderVoiceSpeed;
	private JLabel lblVoiceSpeed;
	private JLabel lblvoiceSetting;
	private JSeparator separator_1;
	private JScrollPane scrollPane;
	private FeedbackTableModel fbModel;
	private JTable fbTable;
	private JPanel tryPanel;
	private JLabel lblProgress;
	private JSeparator separator;
	private JComboBox voiceComboBox;
	/**
	 * Create the panel.
	 */
	public SpellingQuizPanel(VoxspellFrame origin,CardLayout cardLayout, JPanel cards) {
		_originFrame = origin;
		_cardLayout = cardLayout;
		_cardPanel = cards;

		setSize(650,500);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{46, 27, 93, 81, 75, 71, 53, 0, 0};
		gridBagLayout.rowHeights = new int[]{31, 119, 34, 32, 72, 49, 0, 38, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		textField = new JTextField();
		textField.setFont(new Font("Comic Sans MS", Font.PLAIN, 26));
		textField.addKeyListener(new KeyAdapter() {
			// Only allows alphabetical characters to be typed (and ')
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();

				if(!Character.isAlphabetic(c) && c != '\'') {
					e.consume();
					i = 0;
				}

			}
			public void keyPressed(KeyEvent e){
				//cycle through inputed words
				if(e.getKeyCode()==KeyEvent.VK_UP){
					if(i>=inputList.size()-1){
						i=0;
					}else{
						i++;
					}
					textField.setText(inputList.get(i));
				}else if(e.getKeyCode()==KeyEvent.VK_DOWN){
					if(i<0){
						i=inputList.size()-1;
					}else{
						i--;
					}
					textField.setText(inputList.get(i));
				}else if(e.getKeyCode()==KeyEvent.VK_SLASH){
					if(speaker.isDone()){
						speaker = new SpeechWorker(word);;
						speaker.execute();
					}
				}
			}

		});
		textField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(speaker.isDone()){
					userAnswer = textField.getText();

					// Only check if input is not empty
					if (!userAnswer.trim().equalsIgnoreCase("")) {
						inputList.add(0,userAnswer);
						while(inputList.size()>inputListSize){
							inputList.remove(inputList.size()-1);
						}
						_answered = true;
						textField.setText("");
						tryPanel.repaint();
						checkIfCorrect(userAnswer);
					}
				}
			}
		});



		String[] columnNames = new String[]{"Result","Correct Answer","Your Answer"};
		fbModel = new FeedbackTableModel(columnNames);

		fbTable = new JTable(fbModel);
		fbTable.setAutoCreateRowSorter(true);
		fbTable.changeSelection(0, 0, false, false);

		scrollPane = new JScrollPane(fbTable);
		//scrollPane.setOpaque(false);
		scrollPane.setVisible(true);

		separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.gridwidth = 8;
		gbc_separator.insets = new Insets(0, 0, 5, 0);
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 0;
		add(separator, gbc_separator);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 4;
		gbc_scrollPane.gridheight = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 1;
		add(scrollPane, gbc_scrollPane);

		/*
		GridBagConstraints gbc_fbPanel = new GridBagConstraints();
		gbc_fbPanel.gridheight = 2;
		gbc_fbPanel.gridwidth = 4;
		gbc_fbPanel.insets = new Insets(0, 0, 5, 5);
		gbc_fbPanel.fill = GridBagConstraints.BOTH;
		gbc_fbPanel.gridx = 2;
		gbc_fbPanel.gridy = 2;
		add(fbPanel, gbc_fbPanel);*/

		lblProgress = new JLabel("");
		GridBagConstraints gbc_lblProgress = new GridBagConstraints();
		gbc_lblProgress.insets = new Insets(0, 0, 5, 5);
		gbc_lblProgress.gridx = 6;
		gbc_lblProgress.gridy = 2;
		add(lblProgress, gbc_lblProgress);

		tryPanel = new JPanel(){
			public void paintComponent(Graphics g){
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
				int w = getWidth();
				int h = getHeight();
				if(!_answered){
					g2d.setColor(Color.white);
					g2d.fillRect(0, 0, w, h);
				}else if(_firstTry&&_isCorrect){
					g2d.setColor(Color.green);
					g2d.fillRect(0, 0, w, h);
				}else if(!_firstTry&&_isCorrect){
					g2d.setColor(Color.green);
					g2d.fillRect(0, 0, w, h/2);
					g2d.setColor(Color.red);
					g2d.fillRect(0, h/2, w, h);
				}else if(!_wordDone&&!_isCorrect){
					g2d.setColor(Color.white);
					g2d.fillRect(0, 0, w, h/2);
					g2d.setColor(Color.red);
					g2d.fillRect(0, h/2, w, h);
				}else{
					g2d.setColor(Color.red);
					g2d.fillRect(0, 0, w, h);
				}
			}
		};
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 3;
		add(tryPanel, gbc_panel);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 4;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 3;
		add(textField, gbc_textField);
		textField.setColumns(10);

		btnRepeat = new JButton("Repeat");
		btnRepeat.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(speaker.isDone()){
					speaker = new SpeechWorker(word);//,sliderVoiceSpeed.getValue(),((double)sliderVoiceVolume.getValue())/10.0);
					speaker.execute();
				}
			}
		});
		GridBagConstraints gbc_btnRepeat = new GridBagConstraints();
		gbc_btnRepeat.insets = new Insets(0, 0, 5, 5);
		gbc_btnRepeat.gridx = 6;
		gbc_btnRepeat.gridy = 3;
		add(btnRepeat, gbc_btnRepeat);

		btnNextWord = new JButton("Next Word");
		btnNextWord.setEnabled(false);
		btnNextWord.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(speaker.isDone()){
					nextWord();
				}
			}
		});
		btnNextWord.addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					tryPanel.repaint();
					nextWord();
				}
			}
		});

		lblvoiceSetting = new JLabel("Voice Setting");
		lblvoiceSetting.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		lblvoiceSetting.setVerticalAlignment(SwingConstants.BOTTOM);
		GridBagConstraints gbc_lblvoiceSetting = new GridBagConstraints();
		gbc_lblvoiceSetting.gridheight = 2;
		gbc_lblvoiceSetting.gridwidth = 2;
		gbc_lblvoiceSetting.insets = new Insets(0, 0, 5, 5);
		gbc_lblvoiceSetting.gridx = 2;
		gbc_lblvoiceSetting.gridy = 4;
		add(lblvoiceSetting, gbc_lblvoiceSetting);


		sliderVoiceVolume = new JSlider(JSlider.VERTICAL);
		sliderVoiceVolume.setOpaque(false);
		sliderVoiceVolume.setMaximum(100);
		sliderVoiceVolume.setMinimum(0);
		sliderVoiceVolume.setValue(10);
		sliderVoiceVolume.setPaintTicks(true);
		sliderVoiceVolume.setPaintLabels(true);
		sliderVoiceVolume.setLabelTable(sliderVoiceVolume.createStandardLabels(50));
		sliderVoiceVolume.setMaximumSize(new Dimension(60,200));
		sliderVoiceVolume.setMinimumSize(new Dimension(60,100));
		GridBagConstraints gbc_sliderVoiceVolume = new GridBagConstraints();
		gbc_sliderVoiceVolume.gridheight = 2;
		gbc_sliderVoiceVolume.fill = GridBagConstraints.HORIZONTAL;
		gbc_sliderVoiceVolume.insets = new Insets(0, 0, 5, 5);
		gbc_sliderVoiceVolume.gridx = 4;
		gbc_sliderVoiceVolume.gridy = 4;
		add(sliderVoiceVolume, gbc_sliderVoiceVolume);
		sliderVoiceVolume.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				BashCommand.setVoiceVolume(Math.exp((sliderVoiceVolume.getValue()/25.0)-1));
				System.out.println(((JSlider) ce.getSource()).getValue());
			}
		});

		sliderVoiceSpeed = new JSlider(JSlider.VERTICAL);
		sliderVoiceSpeed.setOpaque(false);
		sliderVoiceSpeed.setMaximum(150);
		sliderVoiceSpeed.setMinimum(50);
		sliderVoiceSpeed.setValue(100);
		sliderVoiceSpeed.setMajorTickSpacing(50);
		sliderVoiceSpeed.setMinorTickSpacing(10);
		sliderVoiceSpeed.setPaintTicks(true);
		sliderVoiceSpeed.setPaintLabels(true);
		sliderVoiceSpeed.setLabelTable(sliderVoiceSpeed.createStandardLabels(50));
		sliderVoiceSpeed.setMaximumSize(new Dimension(60,200));
		sliderVoiceSpeed.setMinimumSize(new Dimension(60,100));
		GridBagConstraints gbc_sliderVoiceSpeed = new GridBagConstraints();
		gbc_sliderVoiceSpeed.gridheight = 2;
		gbc_sliderVoiceSpeed.insets = new Insets(0, 0, 5, 5);
		gbc_sliderVoiceSpeed.gridx = 5;
		gbc_sliderVoiceSpeed.gridy = 4;
		add(sliderVoiceSpeed, gbc_sliderVoiceSpeed);
		sliderVoiceSpeed.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				BashCommand.setVoiceSpeed(sliderVoiceSpeed.getValue());
				System.out.println(((JSlider) ce.getSource()).getValue());
			}
		});
		

		String[] voiceList = FileHandler.getFolderContents("/usr/share/festival/voices/english");
		voiceComboBox = new JComboBox(voiceList);
		voiceComboBox.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		voiceComboBox.setSelectedItem(BashCommand.getVoice());
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridwidth = 2;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 6;
		add(voiceComboBox, gbc_comboBox);
		voiceComboBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				BashCommand.setVoice((String)voiceComboBox.getSelectedItem());
			}
		});

		lblVoiceVolume = new JLabel("<html><center>Voice<br>Volume</center></html>");
		lblVoiceVolume.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		lblVoiceVolume.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblVoiceVolume = new GridBagConstraints();
		gbc_lblVoiceVolume.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblVoiceVolume.insets = new Insets(0, 0, 5, 5);
		gbc_lblVoiceVolume.gridx = 4;
		gbc_lblVoiceVolume.gridy = 6;
		add(lblVoiceVolume, gbc_lblVoiceVolume);

		lblVoiceSpeed = new JLabel("<html><center>Voice<br> Speed</center></html>");
		lblVoiceSpeed.setFont(new Font("Courier 10 Pitch", Font.BOLD, 12));
		GridBagConstraints gbc_lblVoiceSpeed = new GridBagConstraints();
		gbc_lblVoiceSpeed.insets = new Insets(0, 0, 5, 5);
		gbc_lblVoiceSpeed.gridx = 5;
		gbc_lblVoiceSpeed.gridy = 6;
		add(lblVoiceSpeed, gbc_lblVoiceSpeed);
		GridBagConstraints gbc_btnNextWord = new GridBagConstraints();
		gbc_btnNextWord.gridwidth = 5;
		gbc_btnNextWord.insets = new Insets(0, 0, 5, 5);
		gbc_btnNextWord.gridx = 2;
		gbc_btnNextWord.gridy = 7;
		add(btnNextWord, gbc_btnNextWord);

		separator_1 = new JSeparator();
		GridBagConstraints gbc_separator_1 = new GridBagConstraints();
		gbc_separator_1.insets = new Insets(0, 0, 5, 0);
		gbc_separator_1.gridx = 7;
		gbc_separator_1.gridy = 7;
		add(separator_1, gbc_separator_1);

		/*btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				_cardLayout.show(_cardPanel, "PreSpellingQuiz");
				SoundHandler.playSound("pop.wav");
			}
		});
		GridBagConstraints gbc_btnBack = new GridBagConstraints();
		gbc_btnBack.insets = new Insets(0, 0, 0, 5);
		gbc_btnBack.gridx = 6;
		gbc_btnBack.gridy = 8;
		add(btnBack, gbc_btnBack);*/

		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				_cardLayout.show(_cardPanel, "Menu");
				SoundHandler.playSound("pop.wav");
			}
		});
		GridBagConstraints gbc_btnExit = new GridBagConstraints();
		gbc_btnExit.gridx = 7;
		gbc_btnExit.gridy = 8;
		add(btnExit, gbc_btnExit);
	}



	public void startQuiz(){
		options = FileHandler.getOptions();

		/**
		 * get words from a file
		 */
		wordList = FileHandler.getWordList("spelling_lists/"+wordListName);
		Collections.shuffle(wordList);
		if(wordList.size()>listSize){
			wordList = wordList.subList(0, listSize);
			listSize = wordList.size();
		}
		word = wordList.get(0);

		//start progress
		_wordsDone = 0;
		_wordsCorrect = 0;
		lblProgress.setText(_wordsDone+"/"+listSize);

		//initialise state identifiers
		_firstTry = true;
		_isCorrect = false;
		_wordDone = false;
		_quizDone = false;
		_answered = false;

		//reset result table
		String[] columnNames = new String[]{"Result","Correct Answer","Your Answer"};
		fbModel = new FeedbackTableModel(columnNames);
		fbTable.setModel(fbModel);

		//reset sliders
		sliderVoiceSpeed.setValue(100);
		sliderVoiceVolume.setValue(10);

		//speak the word
		speaker = new SpeechWorker(word);
		speaker.execute();

		//update the panel colors
		tryPanel.repaint();
		System.out.println(wordList);


	}

	public void checkIfCorrect(String input){
		if(!_wordDone){
			if(input.equalsIgnoreCase(word)){
				_isCorrect = true;
				_wordDone = true;
				_wordsCorrect++;
				btnNextWord.setEnabled(true);
				btnNextWord.requestFocus();
				fbModel.addWord(true,word,userAnswer);
				SoundHandler.playSound("correct.wav");
			}else if(!input.equalsIgnoreCase(word)&&_firstTry){
				_isCorrect = false;
				_firstTry = false;
				SoundHandler.playSound("wrong.wav");
			}else if(!input.equalsIgnoreCase(word)&&!_firstTry){
				_isCorrect = false;
				_wordDone = true;
				btnNextWord.setEnabled(true);
				btnNextWord.requestFocus();
				fbModel.addWord(false,word,userAnswer);
				SoundHandler.playSound("wrong.wav");
			}
			tryPanel.repaint();
			fbTable.changeSelection(fbTable.getRowCount() - 1, 0, false, false);
		}
	}

	/**
	 * proceeds to next word when the user gives an answer
	 */
	public void nextWord(){
		if(_isCorrect || (!_isCorrect&&!_firstTry)){
			_wordsDone++;
			wordList.remove(word);
			System.out.println(wordList);
			if(wordList.size()<=0){
				_quizDone = true;

				QuizFinishFrame qfFrame = new QuizFinishFrame(_originFrame,_cardLayout,_cardPanel, "<html><center>You finished the quiz<br>"
						+ "You scored "+_wordsCorrect+"/"+listSize+"</center><html>");
				qfFrame.setVisible(true);
				_originFrame.setEnabled(false);
			}else{
				word = wordList.get(0);

				//debugWord.setText(word);

				speaker = new SpeechWorker(word);//,sliderVoiceSpeed.getValue(),((double)sliderVoiceVolume.getValue())/10.0);
				speaker.execute();
			}
		}
		textField.requestFocus();
		_firstTry = true;
		_isCorrect = false;
		_wordDone = false;
		_quizDone = false;
		_answered = false;
		tryPanel.repaint();
		btnNextWord.setEnabled(false);
		lblProgress.setText(_wordsDone+"/"+listSize);
	}


	public void setWordList(String name){
		wordListName = name;
	}

	public void setListSize(int size){
		listSize = size;
	}

	/**
	 * Adds painting to the Menu Panel
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		int w = getWidth();
		int h = getHeight();
		Color color1 = new Color(	0,	0,	255,96);
		Color color2 = new Color(	0,	255,255,96);
		GradientPaint primary = new GradientPaint(0, 0, color1, w, 0, color2);
		GradientPaint shade = new GradientPaint(0f, 0f, new Color(0, 0, 0, 0),0f, h, new Color(0, 0, 0, 96));
		g2d.setPaint(primary);
		g2d.fillRect(0, 0, w, h);
		g2d.setPaint(shade);
		g2d.fillRect(0, 0, w, h);

	}
}
