package voxspell;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.JSeparator;
import java.awt.Insets;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferStrategy;

import javax.swing.SwingConstants;
import javax.swing.border.Border;

import handler.BashCommand;
import handler.StatisticsHandler;
import worker.SoundWorker;
import worker.SpeechWorker;

import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel {
	private VoxspellFrame _originFrame;
	private JPanel _cardPanel;
	private CardLayout _cardLayout;

	private JLabel welcomeText;

	private JSeparator separator, separator_1, separator_2;
	
	private JComboBox themeChanger;
	
	private JButton btnSpellingQuiz, btnUserStats, btnOptions, btnExit;
	
	
	/**
	 * Create the panel.
	 */
	@SuppressWarnings("unchecked")
	public MenuPanel(VoxspellFrame origin, CardLayout cLayout, JPanel cardPanel) {
		_originFrame = origin;
		_cardLayout = cLayout;
		_cardPanel = cardPanel;
		
		setSize(650,500);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		separator = new JSeparator();
		separator.setMaximumSize(new Dimension(0,60));
		add(separator);

		// Title text
		welcomeText = new JLabel("VOXSPELL");
		welcomeText.setAlignmentX(CENTER_ALIGNMENT);
		welcomeText.setFont(new Font("Comic Sans MS", 1, 48));
		add(welcomeText);


		separator_1 = new JSeparator();
		separator_1.setMaximumSize(new Dimension(0,100));
		add(separator_1);
		
		
		themeChanger = new JComboBox(_originFrame.getThemeListAsPreview());
		themeChanger.setMaximumSize(new Dimension(100,30));
		themeChanger.setOpaque(false);
		themeChanger.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				_originFrame.setTheme(((ThemePreview)themeChanger.getSelectedItem()).getTheme());
				_originFrame.repaintAllComponents();
			}
		});
		add(themeChanger);

		btnSpellingQuiz = createAndAddButton("Spelling Quiz");
		addCardChangeListener(btnSpellingQuiz,"PreSpellingQuiz");
		btnSpellingQuiz.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				_originFrame._preSpellingQuiz.startPreQuiz();
			}
		});
		
		btnUserStats = createAndAddButton("User Statistics");
		addCardChangeListener(btnUserStats,"ViewStats");
		btnUserStats.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				_originFrame._viewStats.update();
			}
		});
		
		btnExit = createAndAddButton("Exit");
		btnExit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SoundWorker.playSound("boot.wav");

				if (JOptionPane.showConfirmDialog(MenuPanel.this, 
						"Are you sure to close this window?", "Really Closing?", 
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					StatisticsHandler.saveStats();
					BashCommand.saveOptions();
					System.exit(0);
				}
			}
		});


		separator_2 = new JSeparator();
		separator_2.setMaximumSize(new Dimension(0,30));
		add(separator_2);

		this.addMouseMotionListener(new MouseMotionListener(){
			@Override
			public void mouseMoved(MouseEvent arg0) {
				MenuPanel.this.repaint();
			}
			@Override
			public void mouseDragged(MouseEvent e) {
			}
		});
	}

	/**
	 * Used to create buttons at the fixed size and alignment and add them to the menu panel
	 */
	private JButton createAndAddButton(String buttonText) {
		JButton button = new JButton(buttonText);
		button.setMaximumSize(new Dimension(400,65));
		button.setAlignmentX(CENTER_ALIGNMENT);
		button.setFont(new Font("Comic Sans MS", 1, 20));
		button.addMouseMotionListener(new MouseMotionListener(){
			@Override
			public void mouseMoved(MouseEvent arg0) {
				MenuPanel.this.repaint();
			}
			@Override
			public void mouseDragged(MouseEvent e) {
			}
		});
		add(Box.createRigidArea(new Dimension(0,5)));
		add(button);
		return button;
	}

	/**
	 * Adds a function to a button to change screens when pressed
	 */
	private void addCardChangeListener(JButton button, final String cardName){
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_cardLayout.show(_cardPanel, cardName);
				SoundWorker.playSound("pop.wav");
			}
		});
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

