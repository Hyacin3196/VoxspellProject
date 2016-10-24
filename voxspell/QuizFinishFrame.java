package voxspell;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import worker.SoundWorker;

import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JSeparator;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
/**
 * @author jdum654
 */
public class QuizFinishFrame extends JFrame {

	private CardLayout _cardLayout;
	private JPanel _cardPanel;
	private JPanel contentPane;
	private VoxspellFrame _originFrame;

	/**
	 * Create the frame.
	 */
	public QuizFinishFrame(VoxspellFrame origin,CardLayout cardLayout, JPanel cards, String congratulatory, boolean isPassed) {
		_originFrame = origin;
		_cardLayout = cardLayout;
		_cardPanel = cards;
		
		this.setTitle("Quiz Finished!");
		setBounds(_originFrame.getX()+100, _originFrame.getY()+100, 450, 300);
		contentPane = new JPanel(){

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
		};
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{108, 113, 98, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 32, 28, 0, 0, 16, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblScore = new JLabel(congratulatory);
		lblScore.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		GridBagConstraints gbc_lblScore = new GridBagConstraints();
		gbc_lblScore.gridwidth = 3;
		gbc_lblScore.gridheight = 3;
		gbc_lblScore.insets = new Insets(0, 0, 5, 0);
		gbc_lblScore.gridx = 0;
		gbc_lblScore.gridy = 1;
		contentPane.add(lblScore, gbc_lblScore);
		
		JButton btnRedoQuiz = new JButton("Redo Quiz");
		btnRedoQuiz.setFont(new Font("Comic Sans MS", Font.BOLD, 10));
		GridBagConstraints gbc_btnRedoQuiz = new GridBagConstraints();
		gbc_btnRedoQuiz.insets = new Insets(0, 0, 5, 5);
		gbc_btnRedoQuiz.gridx = 0;
		gbc_btnRedoQuiz.gridy = 5;
		contentPane.add(btnRedoQuiz, gbc_btnRedoQuiz);
		btnRedoQuiz.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				_originFrame._spellingQuiz.startQuiz();
				_originFrame.singer = new SoundWorker("boot.wav");
				_originFrame.singer.execute();
				QuizFinishFrame.this.dispatchEvent(new WindowEvent(QuizFinishFrame.this, WindowEvent.WINDOW_CLOSING));
				_originFrame.setEnabled(true);
			}
		});
		
		JButton btnPlayVideo = new JButton("Play Video Reward");
		btnPlayVideo.setEnabled(isPassed);
		btnPlayVideo.setFont(new Font("Comic Sans MS", Font.BOLD, 10));
		GridBagConstraints gbc_btnPlayVideo = new GridBagConstraints();
		gbc_btnPlayVideo.insets = new Insets(0, 0, 5, 5);
		gbc_btnPlayVideo.gridx = 1;
		gbc_btnPlayVideo.gridy = 5;
		contentPane.add(btnPlayVideo, gbc_btnPlayVideo);
		btnPlayVideo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				QuizFinishFrame.this.dispatchEvent(new WindowEvent(QuizFinishFrame.this, WindowEvent.WINDOW_CLOSING));
				_originFrame.setEnabled(true);
				_cardLayout.show(_cardPanel, "Menu");
				new VideoReward();
			}
		});
		
		JButton btnMenu = new JButton("Menu");
		btnMenu.setFont(new Font("Comic Sans MS", Font.BOLD, 10));
		GridBagConstraints gbc_btnMenu = new GridBagConstraints();
		gbc_btnMenu.insets = new Insets(0, 0, 5, 0);
		gbc_btnMenu.gridx = 2;
		gbc_btnMenu.gridy = 5;
		contentPane.add(btnMenu, gbc_btnMenu);
		btnMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				QuizFinishFrame.this.dispatchEvent(new WindowEvent(QuizFinishFrame.this, WindowEvent.WINDOW_CLOSING));
				_originFrame.setEnabled(true);
				_cardLayout.show(_cardPanel, "Menu");

				_originFrame.singer = new SoundWorker("boot.wav");
				_originFrame.singer.execute();
			}
		});
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent arg0) {
				_originFrame.setEnabled(true);
			}
		});
	}
}
