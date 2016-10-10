package voxspell;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class SoundHandler {
	/**
	 * plays soundfiles, intended for button presses or correct/incorrect spellings
	 * @param fileName: name of file
	 */
	public static void playSound(String fileName){
		 try {
		        AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("sounds/"+fileName));
		        AudioFormat format = inputStream.getFormat();
	            DataLine.Info info = new DataLine.Info(Clip.class, format);
	            Clip clip = (Clip)AudioSystem.getLine(info);
		        clip.open(inputStream);
		        clip.start();
		    } catch(Exception ex) {
		        ex.printStackTrace();
		    }
	}
}
