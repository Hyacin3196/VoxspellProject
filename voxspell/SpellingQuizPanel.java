package voxspell;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JSlider;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
	private boolean isCorrect = false;
	private boolean firstTry = false;
	private boolean canProceed = false;
	private boolean quizDone = false;
	private int i = 0;


	private SpeechWorker speaker = new SpeechWorker("");
	private JButton btnRepeat;
	private JButton btnOptions;
	private JButton btnBack;
	private JButton btnExit;
	private JLabel debugWord;
	private JButton btnNextWord;
	private JSlider sliderVoiceVolume;
	private JLabel lblVoiceVolume;
	private JSlider sliderVoiceSpeed;
	private JLabel lblVoiceSpeed;
	private JLabel lblvoiceSetting;
	private JSeparator separator_1;
	private JPanel panel;
	/**
	 * Create the panel.
	 */
	public SpellingQuizPanel(VoxspellFrame origin,CardLayout cardLayout, JPanel cards) {
		_originFrame = origin;
		_cardLayout = cardLayout;
		_cardPanel = cards;

		setSize(650,500);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{82, 93, 81, 75, 71, 53, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 119, 34, 32, 111, 0, 38, 42, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
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
				}
			}

		});
		textField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(speaker.isDone()){
					String input = textField.getText();

					// Only check if input is not empty
					if (!input.trim().equalsIgnoreCase("")) {
						inputList.add(0,input);
						while(inputList.size()>5){
							inputList.remove(inputList.size()-1);
						}
						checkIfCorrect(input);
					}
				}
			}
		});

		panel = new JPanel();
		panel.setOpaque(false);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 7;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 2;
		add(panel, gbc_panel);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 4;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 4;
		add(textField, gbc_textField);
		textField.setColumns(10);


		debugWord = new JLabel(" ");
		GridBagConstraints gbc_debugWord = new GridBagConstraints();
		gbc_debugWord.gridwidth = 4;
		gbc_debugWord.insets = new Insets(0, 0, 5, 5);
		gbc_debugWord.gridx = 1;
		gbc_debugWord.gridy = 3;
		add(debugWord, gbc_debugWord);

		btnRepeat = new JButton("Repeat");
		btnRepeat.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(speaker.isDone()){
					speaker = new SpeechWorker(word,sliderVoiceSpeed.getValue(),((double)sliderVoiceVolume.getValue())/10.0);
					speaker.execute();
				}
			}
		});
		GridBagConstraints gbc_btnRepeat = new GridBagConstraints();
		gbc_btnRepeat.insets = new Insets(0, 0, 5, 5);
		gbc_btnRepeat.gridx = 5;
		gbc_btnRepeat.gridy = 4;
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
					nextWord();
				}
			}
		});

		lblvoiceSetting = new JLabel("<html><center>Voice<br> Setting</center></html>");
		GridBagConstraints gbc_lblvoiceSetting = new GridBagConstraints();
		gbc_lblvoiceSetting.insets = new Insets(0, 0, 5, 5);
		gbc_lblvoiceSetting.gridx = 1;
		gbc_lblvoiceSetting.gridy = 5;
		add(lblvoiceSetting, gbc_lblvoiceSetting);

		sliderVoiceVolume = new JSlider(JSlider.VERTICAL);
		sliderVoiceVolume.setOpaque(false);
		sliderVoiceVolume.setMaximum(150);
		sliderVoiceVolume.setMinimum(0);
		sliderVoiceVolume.setValue(10);
		sliderVoiceVolume.setPaintTicks(true);
		sliderVoiceVolume.setPaintLabels(true);
		sliderVoiceVolume.setLabelTable(sliderVoiceVolume.createStandardLabels(50));
		sliderVoiceVolume.setMaximumSize(new Dimension(60,200));
		sliderVoiceVolume.setMinimumSize(new Dimension(60,100));
		GridBagConstraints gbc_sliderVoiceVolume = new GridBagConstraints();
		gbc_sliderVoiceVolume.fill = GridBagConstraints.HORIZONTAL;
		gbc_sliderVoiceVolume.insets = new Insets(0, 0, 5, 5);
		gbc_sliderVoiceVolume.gridx = 2;
		gbc_sliderVoiceVolume.gridy = 5;
		add(sliderVoiceVolume, gbc_sliderVoiceVolume);
		sliderVoiceVolume.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				System.out.println(((JSlider) ce.getSource()).getValue());
			}
		});

		sliderVoiceSpeed = new JSlider(JSlider.VERTICAL);
		sliderVoiceSpeed.setOpaque(false);
		sliderVoiceSpeed.setMaximum(200);
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
		gbc_sliderVoiceSpeed.insets = new Insets(0, 0, 5, 5);
		gbc_sliderVoiceSpeed.gridx = 3;
		gbc_sliderVoiceSpeed.gridy = 5;
		add(sliderVoiceSpeed, gbc_sliderVoiceSpeed);sliderVoiceSpeed.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				System.out.println(((JSlider) ce.getSource()).getValue());
			}
		});

		lblVoiceVolume = new JLabel("<html><center>Voice<br>Volume</center></html>");
		lblVoiceVolume.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblVoiceVolume = new GridBagConstraints();
		gbc_lblVoiceVolume.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblVoiceVolume.insets = new Insets(0, 0, 5, 5);
		gbc_lblVoiceVolume.gridx = 2;
		gbc_lblVoiceVolume.gridy = 6;
		add(lblVoiceVolume, gbc_lblVoiceVolume);

		lblVoiceSpeed = new JLabel("<html><center>Voice<br> Speed</center></html>");
		GridBagConstraints gbc_lblVoiceSpeed = new GridBagConstraints();
		gbc_lblVoiceSpeed.insets = new Insets(0, 0, 5, 5);
		gbc_lblVoiceSpeed.gridx = 3;
		gbc_lblVoiceSpeed.gridy = 6;
		add(lblVoiceSpeed, gbc_lblVoiceSpeed);
		GridBagConstraints gbc_btnNextWord = new GridBagConstraints();
		gbc_btnNextWord.gridwidth = 5;
		gbc_btnNextWord.insets = new Insets(0, 0, 5, 5);
		gbc_btnNextWord.gridx = 1;
		gbc_btnNextWord.gridy = 7;
		add(btnNextWord, gbc_btnNextWord);

		separator_1 = new JSeparator();
		GridBagConstraints gbc_separator_1 = new GridBagConstraints();
		gbc_separator_1.insets = new Insets(0, 0, 5, 0);
		gbc_separator_1.gridx = 6;
		gbc_separator_1.gridy = 7;
		add(separator_1, gbc_separator_1);

		btnOptions = new JButton("Options");
		GridBagConstraints gbc_btnOptions = new GridBagConstraints();
		gbc_btnOptions.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnOptions.insets = new Insets(0, 0, 0, 5);
		gbc_btnOptions.gridx = 4;
		gbc_btnOptions.gridy = 9;
		add(btnOptions, gbc_btnOptions);

		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				_cardLayout.show(_cardPanel, "PreSpellingQuiz");
				SoundHandler.playSound("pop.wav");
			}
		});
		GridBagConstraints gbc_btnBack = new GridBagConstraints();
		gbc_btnBack.insets = new Insets(0, 0, 0, 5);
		gbc_btnBack.gridx = 5;
		gbc_btnBack.gridy = 9;
		add(btnBack, gbc_btnBack);

		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				_cardLayout.show(_cardPanel, "Menu");
				SoundHandler.playSound("pop.wav");
			}
		});
		GridBagConstraints gbc_btnExit = new GridBagConstraints();
		gbc_btnExit.gridx = 6;
		gbc_btnExit.gridy = 9;
		add(btnExit, gbc_btnExit);
	}
	
	

	public void startQuiz(){
		options = FileHandler.getOptions();
		
		/**
		 * get words from a file
		 */
		wordList = FileHandler.getWordList("spelling_lists/"+wordListName);
		Collections.shuffle(wordList);
		wordList = wordList.subList(0, listSize);
		word = wordList.get(0);
		firstTry = true;

		/**
		 * reset sliders
		 */
		sliderVoiceSpeed.setValue(100);
		sliderVoiceVolume.setValue(10);
		if(false){
			debugWord.setVisible(false);
		}
		debugWord.setText(word);


		speaker = new SpeechWorker(word,sliderVoiceSpeed.getValue(),((double)sliderVoiceVolume.getValue())/10.0);
		speaker.execute();


		System.out.println(wordList);
	}

	public void checkIfCorrect(String input){
		textField.setText("");
		if(input.equalsIgnoreCase(word)){
			isCorrect = true;
			canProceed = true;
			btnNextWord.setEnabled(true);
			btnNextWord.requestFocus();
		}else if(!input.equalsIgnoreCase(word)&&firstTry){
			isCorrect = true;
			firstTry = false;
		}else if(!input.equalsIgnoreCase(word)&&!firstTry){
			isCorrect = false;
			canProceed = true;
			btnNextWord.setEnabled(true);
			btnNextWord.requestFocus();
		}
	}

	/**
	 * proceeds to next word when the user gives an answer
	 */
	public void nextWord(){
		if(isCorrect || (!isCorrect&&!firstTry)){
			wordList.remove(word);
			System.out.println(wordList);
			if(wordList.size()<=0){
				quizDone = true;
				debugWord.setText("...");
			}else{
				word = wordList.get(0);

				debugWord.setText(word);

				speaker = new SpeechWorker(word,sliderVoiceSpeed.getValue(),((double)sliderVoiceVolume.getValue())/10.0);
				speaker.execute();
			}
		}
		textField.requestFocus();
		isCorrect = false;
		firstTry = true;
		canProceed = false;
		btnNextWord.setEnabled(false);
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
