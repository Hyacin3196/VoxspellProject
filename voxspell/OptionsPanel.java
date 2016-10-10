package voxspell;

import java.awt.CardLayout;

import javax.swing.JPanel;
import java.awt.GridBagLayout;

public class OptionsPanel extends JPanel{
	private VoxspellFrame _originFrame;
	private CardLayout _cardLayout;
	private JPanel _cardPanel;
	public OptionsPanel(VoxspellFrame origin,CardLayout cardLayout, JPanel cards) {
		_originFrame = origin;
		_cardLayout = cardLayout;
		_cardPanel = cards;

		setSize(650,500);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0};
		gridBagLayout.rowHeights = new int[]{0};
		gridBagLayout.columnWeights = new double[]{Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
	}
}
