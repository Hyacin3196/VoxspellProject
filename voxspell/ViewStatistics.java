package voxspell;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import handler.StatisticsHandler;
import worker.SoundWorker;

/*
 * This class is used to visually update and display
 * the statistics of the words attempted
 */
@SuppressWarnings("serial")
public class ViewStatistics extends JPanel{
	private VoxspellFrame _originFrame;
	private CardLayout _cardLayout;
	private JPanel _cardPanel;

	private JTable table;
	private JScrollPane scrollPane;
	private JPanel buttonPanel;
	private JButton backToMenu,clear;


	public ViewStatistics(VoxspellFrame origin, CardLayout cardLayout, JPanel cards) {
		_originFrame=origin;
		_cardLayout=cardLayout;
		_cardPanel=cards;
		setLayout(new BorderLayout());

		DefaultTableModel rowData = StatisticsHandler.getWordStatisticsAsTableModel();

		// Creates JTable and makes it so users can't edit cells
		table = new JTable(rowData) {
			public boolean isCellEditable(int row, int column) {                
				return false;  
			}
		};


		// Allows to sort by clicking on headers
		table.setAutoCreateRowSorter(true);

		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());


		// Set up back button
		backToMenu = new JButton("Back to Menu");
		backToMenu.setPreferredSize(new Dimension(200,30));
		backToMenu.setAlignmentX(CENTER_ALIGNMENT);
		backToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SoundWorker.playSound("pop.wav");
				_cardLayout.show(_cardPanel, "Menu");
			}		
		}); 

		// Set up back button
		clear = new JButton("Clear Statistics");
		clear.setPreferredSize(new Dimension(200,30));
		clear.setAlignmentX(CENTER_ALIGNMENT);
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SoundWorker.playSound("pop.wav");

				if (JOptionPane.showConfirmDialog(ViewStatistics.this, 
						"<html>Are you sure you want to clear your stats?<br>This can't be undone</html>", "Clear Stats", 
						JOptionPane.YES_NO_OPTION,
						JOptionPane.ERROR_MESSAGE) == JOptionPane.YES_OPTION){
					StatisticsHandler.clearStats();
				}

				update();
			}		
		});

		buttonPanel.add(clear,BorderLayout.WEST);
		buttonPanel.add(backToMenu,BorderLayout.EAST);

		// Add ability to scroll
		scrollPane = new JScrollPane(table);
		scrollPane.setVisible(true);

		// Add components
		add(scrollPane, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

	}

	/*
	 * This method updates the JTable with the updated statistics
	 */
	public void update() {
		DefaultTableModel model = StatisticsHandler.getWordStatisticsAsTableModel(); 
		table.setModel(model);

		table.getColumnModel().getColumn(0).setPreferredWidth(25);
		table.getColumnModel().getColumn(1).setPreferredWidth(25);
	}

	/**
	 * Adds painting to the Stats Panel
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
