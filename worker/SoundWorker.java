package worker;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import handler.BashCommand;
/**
 * @author jdum654
 */
public class SoundWorker extends SwingWorker<Void,Void>{
	String _fileName;
	public SoundWorker(String fileName){
		_fileName = fileName;
	}
	
	/**
	 * plays soundfiles, intended for button presses or correct/incorrect spellings
	 * @param fileName: name of file
	 */
	public static void playSound(String fileName){
		BashCommand.bashCommand("ffplay -autoexit sounds/"+fileName);
		/*try {
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("sounds/"+fileName));
			AudioFormat format = inputStream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			Clip clip;
			clip = (Clip)AudioSystem.getLine(info);
			clip.open(inputStream);
			clip.start();
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			//JOptionPane.showMessageDialog(null, "<html>Sound file was unable to play!</html>", "Sound File Error", JOptionPane.ERROR_MESSAGE);
			//System.exit(1);
		}*/
	}

	@Override
	protected Void doInBackground() throws Exception {
		BashCommand.bashCommand("ffplay -autoexit -nodisp -loglevel panic sounds/"+_fileName);
		return null;
	}
}
