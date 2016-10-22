package worker;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

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
		try {
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("sounds/"+fileName));
			AudioFormat format = inputStream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			Clip clip = (Clip)AudioSystem.getLine(info);
			clip.open(inputStream);
			clip.start();
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "<html>Sound file was unable to play!<br>Make sure none of the sounds files have been removed</html>", "Sound File Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	protected Void doInBackground() throws Exception {
		try {
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("sounds/"+_fileName));
			AudioFormat format = inputStream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			Clip clip = (Clip)AudioSystem.getLine(info);
			clip.open(inputStream);
			clip.start();
			clip.drain();
			clip.close();
			
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "<html>Sound file was unable to play!<br>Make sure none of the sounds files have been removed</html>", "Sound File Error", JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}
}
