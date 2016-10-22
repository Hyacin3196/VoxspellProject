package voxspell;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import handler.ImageHandler;

public class FeedbackTableModel extends DefaultTableModel{
	
	
	/**
	 * FeedBack Model Contructor
	 * @param columnNames
	 */
	public FeedbackTableModel(String[] columnNames){
		super(null,columnNames);
	}
	
	/**
	 * adds word to the feedback table and shows if user failed to answer correctly or not
	 * @param isCorrect
	 * @param word
	 * @param answer
	 */
	public void addWord(boolean isCorrect,String word, String answer){
		ImageIcon icon;
		if(isCorrect){
			icon = new ImageIcon("icons/check-7.png");
		} else {
			icon = new ImageIcon("icons/error-7.png");
		}
		this.addRow(new Object[]{icon,word,answer});
		
	}

	/**
	 * makes sure that the image icons show properly
	 */
	@Override
	public Class getColumnClass(int column)
	{
		if (column == 0) return ImageIcon.class; 
		return Object.class;
	}
	
	/**
	 * disables cell editing
	 */
	public boolean isCellEditable(int row, int column){
		return false;
	}
}
