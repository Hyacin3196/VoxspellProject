package voxspell;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import handler.SoundHandler;
import handler.StatisticsHandler;

/*
 * This class is used to visually update and display
 * the statistics of the words attempted
 */
@SuppressWarnings("serial")
public class ViewStatistics extends JPanel{
	private VoxspellFrame _origin;
	private CardLayout _cardLayout;
	private JPanel _cardPanel;
	
	private JTable table;
	private JScrollPane scrollPane;
	private JPanel buttonPanel;
	private JButton backToMenu,clear;
	
	public ViewStatistics(VoxspellFrame origin, CardLayout cardLayout, JPanel cards) {
		_origin=origin;
		_cardLayout=cardLayout;
		_cardPanel=cards;
		setLayout(new BorderLayout());
		
		String[] headers = { "Level", "Word", "Mastered", "Faulted", "Failed" };
		String[][] rowData = StatisticsHandler.getWordStatisticsAsArray();
		
		// Creates JTable and makes it so users can't edit cells
	    table = new JTable(rowData, headers) {
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
				SoundHandler.playSound("pop.wav");
				_cardLayout.show(_cardPanel, "Menu");
			}		
		}); 
		
		// Set up back button
        clear = new JButton("Clear Statistics");
        clear.setPreferredSize(new Dimension(200,30));
        clear.setAlignmentX(CENTER_ALIGNMENT);
        clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SoundHandler.playSound("pop.wav");
				_cardLayout.show(_cardPanel, "Menu");
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
		String[] headers = { "Level", "Word", "Mastered", "Faulted", "Failed" };
		String[][] rowData = StatisticsHandler.getWordStatisticsAsArray();
		
		DefaultTableModel model = new DefaultTableModel(rowData, headers);
		table.setModel(model);
		
		
	}
	

}
