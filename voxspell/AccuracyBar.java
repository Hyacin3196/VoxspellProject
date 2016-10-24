package voxspell;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
/**
 * @author jdum654
 */
public class AccuracyBar extends BufferedImage{

	private double _accuracy;
	
	public AccuracyBar(double accuracy){
		super(150,10,BufferedImage.TYPE_INT_ARGB);
		_accuracy = accuracy;
		Graphics2D g2d = this.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		int w = getWidth();
		int h = getHeight();
		int x = 2;
		int y = 2;
		Color green = new Color(0,255,0,185);
		Color cyan = new Color(0,255,255,185);
		Color red = new Color(255,0,0,185);
		Color orange = new Color(255,255,0,187);
		GradientPaint primary = new GradientPaint(0, 0, green, w, 0, cyan);
		GradientPaint shade = new GradientPaint(0f, 0f, red,0f, h, orange);
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, x, h);
		g2d.fillRect(w-x, 0, w , h);
		g2d.fillRect(0, 0, w, y);
		g2d.fillRect(0, h-y, w, h);

		g2d.setPaint(primary);
		g2d.fillRect(x, y, (int) Math.round((w-2*x)*_accuracy), h-2*y);
		g2d.setPaint(shade);
		g2d.fillRect((int) Math.round(((w-2*x)*_accuracy)+x), y, (int) Math.round((w-2*x)*(1.0-_accuracy)), h-2*y);
	}
	
	public ImageIcon convertToImageIcon(){
		
		return new AccuracyBarIcon(this,_accuracy);
	}
}
