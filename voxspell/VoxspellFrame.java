package voxspell;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class VoxspellFrame extends JFrame {

	private JPanel contentPane;
	
	private CardLayout _cardLayout;

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
		
		contentPane.add(new MenuPanel(),"Menu");
		_cardLayout.show(contentPane, "Menu");
	}

}
