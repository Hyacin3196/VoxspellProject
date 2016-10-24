package voxspell;

import java.awt.Image;

import javax.swing.ImageIcon;
/**
 * @author jdum654
 */
@SuppressWarnings("serial")
public class AccuracyBarIcon extends ImageIcon implements Comparable<AccuracyBarIcon>{

	private double _accuracy;

	public AccuracyBarIcon(Image image, double accuracy){
		super(image);
		_accuracy = accuracy;
	}

	@Override
	public int compareTo(AccuracyBarIcon accBI) {

		if(accBI._accuracy>this._accuracy){
			return 1;
		}if(accBI._accuracy<this._accuracy){
			return -1;
		}else{
			return 0;
		}
	}

}
