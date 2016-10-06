package voxspell;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;

public class SpellingQuizPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public SpellingQuizPanel() {
		setSize(800,600);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblSpellingQuiz = new JLabel("Spelling Quiz");
		GridBagConstraints gbc_lblSpellingQuiz = new GridBagConstraints();
		gbc_lblSpellingQuiz.gridx = 1;
		gbc_lblSpellingQuiz.gridy = 0;
		add(lblSpellingQuiz, gbc_lblSpellingQuiz);

	}

}
