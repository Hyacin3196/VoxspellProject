package voxspell;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

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
			options.add(optionScanner.next());

			optionScanner.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return options;
	}

	/**
	 * sets default options for VOXSPELL, can also be used to create a new option file
	 */
	public static void setDefaultOptions(){
		try {
			File optionFile = new File(optionFileName);
			if(!optionFile.exists()){
				optionFile.createNewFile();
			}
			PrintWriter fileWriter = new PrintWriter(optionFile);
			fileWriter.println("kal_diphone 100 1.0");
			fileWriter.println("always_ask");
			fileWriter.flush();
			fileWriter.close();
			BashCommand.setVoiceOptions("kal_diphone");
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	/**
	 * sets the chosen options in an options file
	 * @param voiceOps
	 * @param videoOps
	 */
	public static void setOptions(String voiceOps, int voiceSp, double voiceVol, String videoOps){
		try {
			File optionFile = new File(optionFileName);
			if(!optionFile.exists()){
				optionFile.createNewFile();
			}
			PrintWriter fileWriter = new PrintWriter(optionFile);
			fileWriter.println(voiceOps+" "+voiceSp+" "+voiceVol);
			fileWriter.println(videoOps);
			fileWriter.flush();
			fileWriter.close();
			BashCommand.setVoiceOptions(voiceOps);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	
}
