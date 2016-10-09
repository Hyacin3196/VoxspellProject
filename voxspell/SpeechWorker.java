package voxspell;

import javax.swing.SwingWorker;

public class SpeechWorker extends SwingWorker<Void,Void>{
	String speech = null;
	SpeechWorker(String input){
		speech = input;
	}

	protected Void doInBackground() throws Exception {
		BashCommand.sayFestival(speech);
		return null;
	}
}
