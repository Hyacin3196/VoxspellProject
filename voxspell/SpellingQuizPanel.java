package voxspell;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JSeparator;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JSlider;

public class SpellingQuizPanel extends JPanel {
	private VoxspellFrame _originFrame;
	private CardLayout _cardLayout;
	private JPanel _cardPanel;
	private JTextField textField;

	private List<String> options;/**[voice,video]*/
	private List<String> wordList;
	private List<String> inputList = new ArrayList<String>();
	private String word;
	private boolean isCorrect = false;
	private boolean firstTry = false;
	private boolean canProceed = false;
	private boolean quizDone = false;
	private int i = 0;


	private SpeechWorker speaker = new SpeechWorker("");
	private JSeparator separator;
	private JButton btnRepeat;
	private JButton btnOptions;
	private JButton btnBack;
	private JButton btnExit;
	private JLabel debugWord;
	private JButton btnNextWord;
	private JSlider slider;
	/**
	 * Create the panel.
	 */
	public SpellingQuizPanel(VoxspellFrame origin,CardLayout cardLayout, JPanel cards) {
		_originFrame = origin;
		_cardLayout = cardLayout;
		_cardPanel = cards;

		setSize(800,600);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 38, 28, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 331, -35, 32, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.insets = new Insets(0, 0, 5, 5);
		gbc_separator.gridx = 3;
		gbc_separator.gridy = 1;
		add(separator, gbc_separator);

		textField = new JTextField();
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
					System.out.println(true);
					if(i>=inputList.size()-1){
						i=0;
					}else{
						i++;
					}
					textField.setText(inputList.get(i));
				}else if(e.getKeyCode()==KeyEvent.VK_DOWN){
					System.out.println(true);
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
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 4;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 3;
		add(textField, gbc_textField);
		textField.setColumns(10);


		debugWord = new JLabel(" ");
		GridBagConstraints gbc_debugWord = new GridBagConstraints();
		gbc_debugWord.insets = new Insets(0, 0, 5, 5);
		gbc_debugWord.gridx = 3;
		gbc_debugWord.gridy = 2;
		add(debugWord, gbc_debugWord);

		btnRepeat = new JButton("Repeat");
		GridBagConstraints gbc_btnRepeat = new GridBagConstraints();
		gbc_btnRepeat.insets = new Insets(0, 0, 5, 5);
		gbc_btnRepeat.gridx = 5;
		gbc_btnRepeat.gridy = 3;
		add(btnRepeat, gbc_btnRepeat);

		btnNextWord = new JButton("Next Word");
		btnNextWord.setVisible(false);
		btnNextWord.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				nextWord();
			}
		});

		slider = new JSlider(JSlider.VERTICAL);
		slider.setMaximum(15);
		slider.setMinimum(2);
		slider.setPreferredSize(new Dimension(30,100));
		GridBagConstraints gbc_slider = new GridBagConstraints();
		gbc_slider.insets = new Insets(0, 0, 5, 5);
		gbc_slider.gridx = 2;
		gbc_slider.gridy = 4;
		add(slider, gbc_slider);
		GridBagConstraints gbc_btnNextWord = new GridBagConstraints();
		gbc_btnNextWord.insets = new Insets(0, 0, 5, 5);
		gbc_btnNextWord.gridx = 3;
		gbc_btnNextWord.gridy = 5;
		add(btnNextWord, gbc_btnNextWord);

		btnOptions = new JButton("Options");
		GridBagConstraints gbc_btnOptions = new GridBagConstraints();
		gbc_btnOptions.insets = new Insets(0, 0, 0, 5);
		gbc_btnOptions.gridx = 4;
		gbc_btnOptions.gridy = 6;
		add(btnOptions, gbc_btnOptions);

		btnBack = new JButton("Back");
		GridBagConstraints gbc_btnBack = new GridBagConstraints();
		gbc_btnBack.insets = new Insets(0, 0, 0, 5);
		gbc_btnBack.gridx = 5;
		gbc_btnBack.gridy = 6;
		add(btnBack, gbc_btnBack);

		btnExit = new JButton("Exit");
		GridBagConstraints gbc_btnExit = new GridBagConstraints();
		gbc_btnExit.gridx = 6;
		gbc_btnExit.gridy = 6;
		add(btnExit, gbc_btnExit);
	}

	public void initiateQuiz(){
		options = FileHandler.getOptions();
		wordList = FileHandler.getWordList("spelling_lists/easy.txt");
		Collections.shuffle(wordList);
		wordList = wordList.subList(0, 5);
		word = wordList.get(0);
		firstTry = true;
		debugWord.setText(word);
		speaker.execute();
		System.out.println(wordList);
	}

	public void checkIfCorrect(String input){
		textField.setText("");
		if(input.equalsIgnoreCase(word)){
			isCorrect = true;
			canProceed = true;
			btnNextWord.setVisible(true);
		}else if(!input.equalsIgnoreCase(word)&&firstTry){
			isCorrect = true;
			firstTry = false;
		}else if(!input.equalsIgnoreCase(word)&&!firstTry){
			isCorrect = false;
			canProceed = true;
			btnNextWord.setVisible(true);
		}
	}

	/**
	 * proceeds to next word when the user gives an answer
	 */
	public void nextWord(){
		if(isCorrect || (!isCorrect&&!firstTry)){
			wordList.remove(word);
			System.out.println(wordList);
			if(wordList.size()<0){
				quizDone = true;
				debugWord.setText("");
			}else{
				word = wordList.get(0);

				debugWord.setText(word);
			}
		}
		isCorrect = false;
		firstTry = true;
		canProceed = false;
		btnNextWord.setVisible(false);
	}

}
