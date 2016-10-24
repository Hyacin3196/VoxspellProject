package worker;

import javax.swing.SwingWorker;

import handler.BashCommand;
/**
 * @author jdum654
 */
public class SpeechWorker extends SwingWorker<Void,Void>{
	
	
	
	String _speech = null;
	
	public SpeechWorker(String input){
		_speech = input;
	}
	protected Void doInBackground() throws Exception {
		BashCommand.sayFestival(_speech);
		return null;
	}
}
