package testing;

import worker.SoundWorker;

public class SoundTest {
	static SoundWorker singer = null;
	public static void main(String[] arg){
		singer = new SoundWorker("rattle.wav");
		singer.execute();
		while(!singer.isDone());
		if(singer.isDone()){
			singer = new SoundWorker("boot.wav");
			singer.execute();
		}
	}
}
