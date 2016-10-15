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

	public FeedbackTableModel(String[] columnNames){
		super(null,columnNames);
	}


	public boolean isCellEditable(int row, int column){
		return false;
	}

	/*public void addWord(String word, final boolean isCorrect){
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));

		JLabel label = new JLabel(word);
		label.setOpaque(false);
		label.setSize(new Dimension(100,100));

		JPanel icon = new JPanel(){
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
				int w = getWidth();
				int h = getHeight();
				String imageFile = "";
				if(isCorrect){
					imageFile = "icons/check.ico";
				}else{
					imageFile = "icons/error.ico";
				}
				try{
					BufferedImage image = ImageHandler.getImage(imageFile);
					g2d.drawImage(image, w, h, null);
				}catch(IOException e){
					JOptionPane.showMessageDialog(null, "Image "+imageFile+"not found","No Image", JOptionPane.ERROR_MESSAGE);
				}
			}
		};
		icon.setOpaque(false);

		panel.add(icon);
		panel.add(label);

		this.add(panel);

	}*/
	public void addWord(boolean isCorrect,String word, String answer){
		ImageIcon icon;
		if(isCorrect){
			icon = new ImageIcon("icons/check-7.png");
		} else {
			icon = new ImageIcon("icons/error-7.png");
		}
		this.addRow(new Object[]{icon,word,answer});
	}

	@Override
	public Class getColumnClass(int column)
	{
		if (column == 0) return ImageIcon.class; 
		return Object.class;
	}
}
