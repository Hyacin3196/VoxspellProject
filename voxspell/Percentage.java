package voxspell;

public class Percentage implements Comparable<Percentage> {
	private double _percentage;
	
	public Percentage(double accuracy){
		accuracy = accuracy*10000.0;
		accuracy = Math.round(accuracy);
		accuracy = accuracy/100.0;
		_percentage = accuracy;
	}
	@Override
	public int compareTo(Percentage o) {
		if(o._percentage>this._percentage){
			return 1;
		}else if(o._percentage<this._percentage){
			return -1;
		} else {
			return 0;
		}
	}
	
	public String toString(){
		return _percentage+"%";
	}
}
