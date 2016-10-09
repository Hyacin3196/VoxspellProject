package voxspell;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;



public class BashCommand {
	static Process currentProcess;

	private static String currentVoice = "kal_diphone";

	
	static {
		List<String> listOption = FileHandler.getOptions();
		currentVoice = listOption.get(0);
	}
	
	public static void setVoiceOptions(String voice){
		currentVoice = voice;
	}

	//Allows users to use bash commands
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

	//Allows users to use bash commands that return an output
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

	//A bash command that converts text to speech using festival
	public static void say(String speech){
		bashCommand("echo \""+speech+"\" | festival --tts");
	}


	public static void sayFestival(String speech){
		try {
			String speechFileName = ".speech.scm";
			File speechFile = new File(speechFileName);
			if(!speechFile.exists()){
				speechFile.createNewFile();
			}
			PrintWriter writer = new PrintWriter(speechFile);
			writer.println("(voice_"+currentVoice+")");
			writer.println("(SayText \""+speech+"\")");
			writer.print("(quit)");
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bashCommand("festival -b .speech.scm");
	}
}
