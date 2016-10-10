package voxspell;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
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

	private JButton btnSpellingQuiz, btnUserStats, btnOptions, btnExit;
	/**
	 * Create the panel.
	 */
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
		separator_1.setMaximumSize(new Dimension(0,150));
		add(separator_1);

		btnSpellingQuiz = createAndAddButton("Spelling Quiz");
		addCardChangeListener(btnSpellingQuiz,"PreSpellingQuiz");
		btnUserStats = createAndAddButton("User Statistics");
		addCardChangeListener(btnUserStats,"ViewStats");
		btnOptions = createAndAddButton("Options");
		addCardChangeListener(btnOptions,"Menu");
		btnExit = createAndAddButton("Exit");
		btnExit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SoundHandler.playSound("pop.wav");
				System.exit(0);
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
				SoundHandler.playSound("pop.wav");
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
		Color color1 = new Color(	0,	0,	255,96);
		Color color2 = new Color(	0,	255,255,96);
		GradientPaint primary = new GradientPaint(0, 0, color1, w, 0, color2);
		GradientPaint shade = new GradientPaint(0f, 0f, new Color(0, 0, 0, 0),0f, h, new Color(0, 0, 0, 96));
		g2d.setPaint(primary);
		g2d.fillRect(0, 0, w, h);
		g2d.setPaint(shade);
		g2d.fillRect(0, 0, w, h);
		PointerInfo a = MouseInfo.getPointerInfo();
		Point point = a.getLocation();
		double x = point.getX()-this.getX()-_originFrame.getX();
		double y = point.getY()-this.getY()-_originFrame.getY();
		Point2D point2D = new Point2D.Double(x,y);
		int radius = 750;
		float[] dist = {0,1};
		Color[] colors = {new Color(127,127,255,64),new Color(0,0,0,0)};
		RadialGradientPaint radial = new RadialGradientPaint(point2D,radius,dist,colors);
		g2d.setPaint(radial);
		g2d.fillRect(0, 0, w, h);
	}
}

