package handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import voxspell.AccuracyBar;
import voxspell.Percentage;

/*
 * This class will contain arrays which holds the statistics
 * for the words spelled
 * 
 * It also contains methods for updating statistics as well as 
 * retrieving statistics
 */
public class StatisticsHandler {
	public static final int MASTERED = 0;
	public static final int ATTEMPTS = 1;

	/**
	 * Each hashmap in the wordStatistics represents the statistics from each spelling list
	 */
	private static HashMap<String,HashMap<String, List<Integer>>> wordStatistics;
	
	/**
	 * a getter for wordStatistics
	 * @return
	 */
	public static HashMap<String,HashMap<String, List<Integer>>> getStats(){
		return wordStatistics;
	}
	
	
	static {
		wordStatistics = FileHandler.getStatisticsOfAllFiles();
	}

	/**
	 * Updates the wordStatistics field whenever user answers the quiz
	 * @param spellList: name of the spelling list in the spelling_lists folder
	 * @param word: name of the word
	 * @param mastered: if mastered
	 */
	public static void updateStatistics(String spellList, String word, boolean mastered) {
		HashMap<String, List<Integer>> statsFromSpellList;
		
		statsFromSpellList = wordStatistics.get(spellList);
		if(statsFromSpellList==null){
			statsFromSpellList = new HashMap<String, List<Integer>>();
		}

		List<Integer> resultsCount;
		if (!statsFromSpellList.containsKey(word)) {
			resultsCount = new ArrayList<Integer>(2);
			resultsCount.add(MASTERED, 0);
			resultsCount.add(ATTEMPTS, 0);

			statsFromSpellList.put(word, resultsCount);
		}
		
		resultsCount = statsFromSpellList.get(word);
		if(mastered){
			int updatedCount = resultsCount.get(MASTERED) + 1;
			resultsCount.remove(MASTERED);
			resultsCount.add(MASTERED, updatedCount);
			updatedCount = resultsCount.get(ATTEMPTS) + 1;
			resultsCount.remove(ATTEMPTS);
			resultsCount.add(ATTEMPTS, updatedCount);
		}else{

			int updatedCount = resultsCount.get(ATTEMPTS) + 1;
			resultsCount.remove(ATTEMPTS);
			resultsCount.add(ATTEMPTS, updatedCount);
		}
		System.out.println(word+" "+resultsCount);
		
		wordStatistics.remove(spellList);
		wordStatistics.put(spellList, statsFromSpellList);
	}

	/**
	 * This method is used to retrieve word statistics in a 2D array
	 * so that it may be used in a JTable
	 */
	public static DefaultTableModel getWordStatisticsAsTableModel() {
		List<Object[]> statsList = new ArrayList<Object[]>();

		wordStatistics.forEach((spellingFileName, listStats) -> {
			listStats.forEach((word,result) -> {
				Object[] statsRow = new Object[4];
				
				statsRow[0] = spellingFileName;
				statsRow[1] = word;
				
				int masteryNum = result.get(0);
				int attemptNum = result.get(1);
				double accuracy = ((double)masteryNum)/((double)attemptNum);
				//String percentStr = ""+(Math.round(accuracy*10000.0)/100.0)+"%";\
				Percentage percent = new Percentage(accuracy);
				
				AccuracyBar accuracyBar = new AccuracyBar(accuracy);
				
				statsRow[2] = accuracyBar.convertToImageIcon();
				statsRow[3] = percent;
				
				statsList.add(statsRow);
			});
		});
		
		DefaultTableModel tableModel = new DefaultTableModel(){
			@Override
			public Class getColumnClass(int column)
			{
				if (column == 2) return ImageIcon.class; 
				if (column == 3) return Percentage.class; 
				return String.class;
			}
		};
		tableModel.setColumnIdentifiers(new String[]{"SpellingList", "Word", "Accuracy", ""});
		
		statsList.forEach((row) -> {
			tableModel.addRow(row);
		});
		
		return tableModel;
	}
	
	/**
	 * Saves statistics in a stats folder
	 */
	public static void saveStats(){
		FileHandler.saveStats(wordStatistics);
	}
	
	/**
	 * remove all files in the statistics folder and reset the wordStatistics field
	 */
	public static void clearStats(){
		wordStatistics = new HashMap<String,HashMap<String, List<Integer>>>();
		FileHandler.clearStats();
	}

}
