package handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

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
	
	public static HashMap<String,HashMap<String, List<Integer>>> getStats(){
		return wordStatistics;
	}
	
	

	static {
		wordStatistics = FileHandler.getStatisticsOfAllFiles();
	}

	/**
	 * 
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
			updatedCount = resultsCount.get(MASTERED) + 1;
			resultsCount.remove(ATTEMPTS);
			resultsCount.add(ATTEMPTS, updatedCount);
		}else{

			int updatedCount = resultsCount.get(ATTEMPTS) + 1;
			resultsCount.remove(ATTEMPTS);
			resultsCount.add(ATTEMPTS, updatedCount);
		}
	}

	/**
	 * This method is used to retrieve word statistics in a 2D array
	 * so that it may be used in a JTable
	 */
	public static String[][] getWordStatisticsAsArray() {
		List<List<String>> statsList = new ArrayList<List<String>>();

		// Loop through all levels
		for (int i = 0; i < wordStatistics.size(); i++) {
			int level = i + 1;

			// Get words for that level
			HashMap<String, List<Integer>> wordMap = wordStatistics.get(i);		
			List<String> wordList = new ArrayList<String>(wordMap.keySet());
			Collections.sort(wordList, String.CASE_INSENSITIVE_ORDER);

			// Loop through words for that level
			for (String word : wordList) {
				List<Integer> resultsCount = wordMap.get(word);

				// Convert results counts to String
				String mastered = Integer.toString(resultsCount.get(MASTERED));
				String failed = Integer.toString(resultsCount.get(ATTEMPTS));

				// Create String array of stats and add to list
				String[] statsForCurrentWord = { Integer.toString(level), word, mastered, failed };
				statsList.add(Arrays.asList(statsForCurrentWord));			
			}
		}

		// Converting the list to a 2D array
		String[][] statsArray = new String[statsList.size()][5];
		int i = 0;

		for(List<String> statsForCurrentWord : statsList) {
			statsArray[i] = (String[]) statsForCurrentWord.toArray();
			i++;
		}

		return statsArray;

	}
	
	public static void saveStats(){
		FileHandler.saveStats(wordStatistics);
	}

}
