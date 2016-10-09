package voxspell;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class VoxspellFrame extends JFrame {

	private JPanel contentPane;
	
	private CardLayout _cardLayout;
	
	MenuPanel menu = new MenuPanel(this, _cardLayout, contentPane);
	PreSpellingQuizPanel preSpellingQuiz = new PreSpellingQuizPanel(this, _cardLayout, contentPane);
	SpellingQuizPanel spellingQuiz = new SpellingQuizPanel(this, _cardLayout, contentPane);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VoxspellFrame frame = new VoxspellFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VoxspellFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 100, 800, 600);
		//setSize(800,600);
		setResizable(true);
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		_cardLayout = new CardLayout();
		contentPane.setLayout(_cardLayout);
		setContentPane(contentPane);

		menu = new MenuPanel(this, _cardLayout, contentPane);
		preSpellingQuiz = new PreSpellingQuizPanel(this, _cardLayout, contentPane);
		spellingQuiz = new SpellingQuizPanel(this, _cardLayout, contentPane);
		contentPane.add(menu,"Menu");
		contentPane.add(preSpellingQuiz,"PreSpellingQuiz");
		contentPane.add(spellingQuiz,"SpellingQuiz");
		_cardLayout.show(contentPane, "Menu");
		
		
	}

}
