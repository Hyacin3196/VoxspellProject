package handler;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;



public class BashCommand {
	static Process currentProcess;

	private static String _voice = "akl_nz_jdt_diphone";
	private static int _voiceSpeed = 100;
	private static double _voiceVolume = 1.0;

	
	static {
		List<String> listOption = FileHandler.getOptions();
		_voice = listOption.get(0);
	}
	
	public static void setVoice(String voice){
		_voice = voice;
		System.out.println(_voice);
	}
	public static void setVoiceSpeed(int speed){
		_voiceSpeed = speed;
	}
	public static void setVoiceVolume(double volume){
		_voiceVolume = volume;
	}
	public static void setDefault(){
		_voice = "akl_nz_jdt_diphone";
		_voiceSpeed = 100;
		_voiceVolume = 1.0;

	}
	
	public static String getVoice(){
		return _voice;
	}
	public static int getVoiceSpeed(){
		return _voiceSpeed;
	}
	public static double getVoiceVolume(){
		return _voiceVolume;
	}

	
	/**
	 * Allows users to use bash commands
	 * @param command
	 */
	public static void bashCommand(String command){
		try {
			ProcessBuilder pb = new ProcessBuilder("bash","-c",command);
			Process currentProcess = pb.start();

			currentProcess.waitFor();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Allows users to use bash commands that return an output
	 * @param command
	 * @return
	 */
	public static List<String> bashReturnCommand(String command){
		List<String> resultLines = new ArrayList<String>();
		try {
			ProcessBuilder pb = new ProcessBuilder("bash","-c",command);
			Process currentProcess = pb.start();
			BufferedReader stdout = new BufferedReader(new InputStreamReader(currentProcess.getInputStream()));
			int exitStatus = currentProcess.waitFor();

			if (exitStatus == 0){

				String line;
				while((line = stdout.readLine()) != null){
					resultLines.add(line);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultLines;
	}

	/**
	 * A bash command that converts text to speech using festival
	 * @param speech
	 */
	public static void say(String speech){
		bashCommand("echo \""+speech+"\" | festival --tts");
	}


	/**
	 * A bash command that converts text to speech using festival using a .scm file to modify the effects of the speech
	 * @param speech
	 */
	public static void sayFestival(String speech){
		try {
			String speechFileName = ".speech.scm";
			File speechFile = new File(speechFileName);
			if(!speechFile.exists()){
				speechFile.createNewFile();
			}
			PrintWriter writer = new PrintWriter(speechFile);
			writer.println("(voice_"+_voice+")");
			writer.println("(Parameter.set 'Audio_Command \"aplay -q -c 1 -t raw -f s16 -r $(($SR*"+_voiceSpeed+"/100)) $FILE\")");
			writer.println("(set! after_synth_hooks (list (lambda (utt) (utt.wave.rescale utt "+_voiceVolume+" t))))");
			writer.println("(SayText \""+speech+"\")");
			writer.print("(quit)");
			writer.flush();
			writer.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Voice Speech failed to execute!", "Voice Speech Error", JOptionPane.ERROR_MESSAGE);
		}
		bashCommand("festival -b .speech.scm");
	}
	
	public static void saveOptions(){
		FileHandler.saveOptions(_voice, _voiceSpeed, _voiceVolume);
	}
}
