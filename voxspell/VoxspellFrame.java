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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import handler.SoundHandler;

@SuppressWarnings("serial")
public class VoxspellFrame extends JFrame {

	private JPanel contentPane;
	
	private CardLayout _cardLayout;
	
	MenuPanel menu;
	PreSpellingQuizPanel preSpellingQuiz;
	SpellingQuizPanel spellingQuiz;
	ViewStatistics viewStats;

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
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(200, 100, 650, 500);
		//setSize(800,600);
		setResizable(false);
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		_cardLayout = new CardLayout();
		contentPane.setLayout(_cardLayout);
		setContentPane(contentPane);

		menu = new MenuPanel(this, _cardLayout, contentPane);
		preSpellingQuiz = new PreSpellingQuizPanel(this, _cardLayout, contentPane);
		spellingQuiz = new SpellingQuizPanel(this, _cardLayout, contentPane);
		viewStats = new ViewStatistics(this, _cardLayout, contentPane);
		
		contentPane.add(menu,"Menu");
		contentPane.add(preSpellingQuiz,"PreSpellingQuiz");
		contentPane.add(spellingQuiz,"SpellingQuiz");
		contentPane.add(viewStats,"ViewStats");
		_cardLayout.show(contentPane, "Menu");
		

		SoundHandler.playSound("boot.wav");
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	SoundHandler.playSound("boot.wav");
		        if (JOptionPane.showConfirmDialog(VoxspellFrame.this, 
		            "Are you sure to close this window?", "Really Closing?", 
		            JOptionPane.YES_NO_OPTION,
		            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
		        	System.exit(0);
		        }else{
		        }
		    }
		});
	}

}
