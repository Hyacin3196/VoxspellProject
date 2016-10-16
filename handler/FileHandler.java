package handler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

/**
 * This is a class with static methods which
 * will be used to handle anything related to files
 */
public class FileHandler {
	private static final String wordFileName = "NZCER-spelling-lists.txt";
	private static final String optionFileName = ".options";

	/**
	 * gets all the words from the specified file
	 * @param fileName
	 * @return
	 */
	public static List<String> getWordList(String fileName){
		List<String> wordArray = new ArrayList<String>();
		File wordFile = new File(fileName);

		try {
			String line;
			BufferedReader reader = new BufferedReader(new FileReader(wordFile));
			while ((line = reader.readLine()) != null) {
				wordArray.add(line.trim());
			}
			reader.close();
		}catch (IOException e) {e.printStackTrace();}
		return wordArray;
	}

	/**
	 * gets options from the options file
	 * @return
	 */
	public static List<String> getOptions(){
		File optionFile = new File(optionFileName);
		List<String> options = new ArrayList<String>();
		try {
			if(!optionFile.exists()){
				setDefaultOptions();
			}

			Scanner optionScanner = new Scanner(optionFile);

			options.add(optionScanner.next());
			options.add(optionScanner.next());
			options.add(optionScanner.next());

			optionScanner.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
		/**
		 * sets the options in the BashCommand from the optionFile
		 */
		BashCommand.setVoice(options.get(0));
		BashCommand.setVoiceSpeed(Integer.parseInt(options.get(1)));
		BashCommand.setVoiceVolume(Double.parseDouble(options.get(2)));
		return options;
	}

	/**
	 * sets default options for VOXSPELL, can also be used to create a new option file
	 */
	public static void setDefaultOptions(){
		BashCommand.setVoice("kal_diphone");
		BashCommand.setVoiceSpeed(100);
		BashCommand.setVoiceVolume(1.0);
		try {
			File optionFile = new File(optionFileName);
			if(!optionFile.exists()){
				optionFile.createNewFile();
			}
			PrintWriter fileWriter = new PrintWriter(optionFile);
			fileWriter.println("kal_diphone 100 1.0");
			fileWriter.flush();
			fileWriter.close();
			BashCommand.setVoice("kal_diphone");
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	/**
	 * saves the chosen options in an options file when the system exits
	 * @param voiceOps
	 * @param videoOps
	 */
	public static void saveOptions(String voiceOps, int voiceSp, double voiceVol){
		try {
			File optionFile = new File(optionFileName);
			if(!optionFile.exists()){
				optionFile.createNewFile();
			}
			PrintWriter fileWriter = new PrintWriter(optionFile);
			fileWriter.println(voiceOps+" "+voiceSp+" "+voiceVol);
			fileWriter.flush();
			fileWriter.close();
			BashCommand.setVoice(voiceOps);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "OptionFile failed to create!", "File Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Statistics Section
	 */

	/**
	 * gets all the stats of all the files present in the stats folder
	 * @return
	 */
	public static HashMap<String,HashMap<String, List<Integer>>> getStatisticsOfAllFiles(){
		//creates stats for all spelling lists
		HashMap<String,HashMap<String, List<Integer>>> wordStats = new HashMap<String,HashMap<String, List<Integer>>>();
		String[] listOfStatFiles = FileHandler.getFolderContents("stats");

		try{
			for(String fileName : listOfStatFiles){
				//creates the stats of a spelling list
				HashMap<String, List<Integer>> listStats = new HashMap<String, List<Integer>>();

				File statFile = new File("stats/"+fileName);
				if(!statFile.exists()){
					statFile.createNewFile();
				}
				
				Scanner fileScanner = new Scanner(statFile);
				
				while(fileScanner.hasNextLine()){
					String line = fileScanner.nextLine();
					Scanner lineScanner = new Scanner(line);
					
					String word = lineScanner.next();
					int mastered = Integer.parseInt(lineScanner.next());
					int attempts = Integer.parseInt(lineScanner.next());
					
					List<Integer> result = new ArrayList<Integer>();
					result.add(mastered);
					result.add(attempts);
					
					listStats.put(word, result);
					
				}
				
				wordStats.put(fileName, listStats);	
			}
		}catch(IOException e){
			JOptionPane.showMessageDialog(null, "Stats file failed to create!", "File Error", JOptionPane.ERROR_MESSAGE);
		}
		return wordStats;
	}

	/**
	 * save the current stats in the wordListFile
	 * @param wordStats
	 */
	public static void saveStats(HashMap<String,HashMap<String, List<Integer>>> wordStats){
		wordStats.forEach((key, statsMap) -> {
			File statsFile = new File("stats/"+key);
			try {
				if(!statsFile.exists()){
					statsFile.createNewFile();
				}
				PrintWriter fileWriter = new PrintWriter(statsFile);
				fileWriter.print("");
				statsMap.forEach((word, result) -> {
					fileWriter.println(word+" "+result.get(0)+" "+result.get(1));
				});

				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "File failed to create!", "File Error", JOptionPane.ERROR_MESSAGE);
			}
		});
	}

	/**
	 * VoiceList Section
	 */

	/**
	 * Gets the list of files in the folder directory
	 * @param directory
	 * @return
	 */
	public static String[] getFolderContents(String directory){
		File folder = new File(directory);
		File[] listOfFiles = folder.listFiles();

		String[] voiceList = new String[listOfFiles.length];
		for (int i = 0; i < listOfFiles.length; i++) {
			voiceList[i] = listOfFiles[i].getName();
		}
		return voiceList;
	}


}
