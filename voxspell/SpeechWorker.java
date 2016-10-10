package voxspell;

import javax.swing.SwingWorker;

public class SpeechWorker extends SwingWorker<Void,Void>{
	String _speech = null;
	int _speed = 100;
	double _volume = 1.0;
	SpeechWorker(String input){
		_speech = input;
	}
	SpeechWorker(String input, int speed, double volume){
		_speech = input;
		_speed = speed;
		_volume = volume;
	}

	protected Void doInBackground() throws Exception {
		BashCommand.sayFestival(_speech,_speed,_volume);
		return null;
	}
}
