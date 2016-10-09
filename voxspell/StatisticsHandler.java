package voxspell;

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
	public static final int FAULTED = 1;
	public static final int FAILED = 2;
	public static final int TOTAL_LEVELS; 
	private static List<String> failedWords;

	// List has elements equal to the level. So each level contains a hash map where the key
	// is the word, and the value is a list of integers with the corresponding elements:
	// index 0: mastered count
	// index 1: faulted count
	// index 2: failed count
	private static List<HashMap<String, Boolean>> wordStatistics;

	// This contains the count of correct spellings and count of total attempts.
	// This will be used to get accuracy statistics for level.
	private static List<List<Integer>> accuracy;

	static {
		wordStatistics = new ArrayList<HashMap<String, Boolean>>();
		accuracy = new ArrayList<List<Integer>>();
		failedWords = new ArrayList<String>();
		int temp = 0;

		/*try {
			temp = FileHandler.getTotalLevels();

		} catch (FileNotFoundException e) {
			//JOptionPane.showMessageDialog(null, "Warning! Spelling list file not found");
		}*/

		TOTAL_LEVELS = temp;

		for (int i = 0; i < temp; i++) {
			wordStatistics.add(new HashMap<String, Boolean>());

			List<Integer> counts = new ArrayList<Integer>(2);
			counts.add(0, 0);
			counts.add(1, 0);
			accuracy.add(counts);

		}




	}


	// method for adding/removing failed words

	/*
	 * This method updates the mastered/faulted/failed count for that word
	 * as well as the accuracy rate for the level.
	 */
	/*public static void updateStatistics(int level, String word, int result) {
		HashMap<String, List<Integer>> wordsFromLevel;
		List<Integer> accuracyFromLevel = accuracy.get(level - 1);

		// Gets the statistics of the words from the specified level
		if (wordStatistics.get(level - 1) == null) {
			wordsFromLevel = new HashMap<String, List<Integer>>();		
		} 
		else {
			wordsFromLevel = wordStatistics.get(level - 1);
		}

		List<Integer> resultsCount;

		// If the word is not in the hashmap
		if (!wordsFromLevel.containsKey(word)) {
			resultsCount = new ArrayList<Integer>(3);
			resultsCount.add(MASTERED, 0);
			resultsCount.add(FAULTED, 0);
			resultsCount.add(FAILED, 0);

			wordsFromLevel.put(word, resultsCount);

		}

		// Get counts and update appropriately
		resultsCount = wordsFromLevel.get(word);
		int updatedCount = resultsCount.get(result) + 1;
		resultsCount.remove(result);
		resultsCount.add(result, updatedCount);


		// This updates the total correct for the level
		if(result == MASTERED) {
			int totalCorrect = accuracyFromLevel.get(0) + 1;
			accuracyFromLevel.remove(0);
			accuracyFromLevel.add(0, totalCorrect);
		}

		// Updates the total attempts for the level
		int totalAttempts = accuracyFromLevel.get(1) + 1;
		accuracyFromLevel.remove(1);
		accuracyFromLevel.add(1, totalAttempts);

	}*/

	/*
	 * This method is used to retrieve the accuracy rate for a specified level
	 */
	public static int getAccuracy(int level) {
		List<Integer> accuracyFromLevel = accuracy.get(level - 1);
		int totalCorrect = accuracyFromLevel.get(0);
		int totalAttempts = accuracyFromLevel.get(1);

		// Get accuracy in % and round to an integer.
		return (int) Math.round(((double) totalCorrect / totalAttempts) * 100);
	}

	public static List<String> getFailedList(){
		return failedWords;
	}

	public static void addFailedWord(String failedWord){
		failedWords.add(failedWord);

	}

	public static void removeFailedWord(String failedWord){
		failedWords.remove(failedWord);
	}

	/*
	 * This method is used to retrieve word statistics in a 2D array
	 * so that it may be used in a JTable
	 */
	/*public static String[][] getWordStatisticsAsArray() {
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
				String faulted = Integer.toString(resultsCount.get(FAULTED));
				String failed = Integer.toString(resultsCount.get(FAILED));

				// Create String array of stats and add to list
				String[] statsForCurrentWord = { Integer.toString(level), word, mastered, faulted, failed };
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

	}*/

	/*
	 * This method clears all the word statistics
	 */
	/*public static void clearStats() {
		for(HashMap<String, List<Integer>> level : wordStatistics) {
			level.clear();
		}
		for(List<Integer> accuracyForLevel : accuracy) {
			accuracyForLevel.clear();
			accuracyForLevel.add(0, 0);
			accuracyForLevel.add(1, 0);

		}
		failedWords.clear();
	}*/

}
