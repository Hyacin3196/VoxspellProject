package voxspell;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class ThemePreview extends ImageIcon{
	
	private List<Color> _theme;
	
	private BufferedImage _image;
	
	/*public ThemePreview(List<Color> theme){
		super(100, 50, BufferedImage.TYPE_INT_ARGB);
		Color c1 = theme.get(0);
		Color c2 = theme.get(1);
		
		Graphics2D g2d = this.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		int w = getWidth();
		int h = getHeight();
		System.out.println(w+" "+h);
		Color color1 = new Color(c1.getRed(), c1.getGreen(), c1.getBlue(), 96);
		Color color2 = new Color(c2.getRed(), c2.getGreen(), c2.getBlue(), 96);
		GradientPaint primary = new GradientPaint(0, 0, color1, w, 0, color2);
		GradientPaint shade = new GradientPaint(0f, 0f, new Color(0, 0, 0, 0),0f, h, new Color(0, 0, 0, 96));
		g2d.setPaint(primary);
		g2d.fillRect(0, 0, w, h);
		g2d.setPaint(shade);
		g2d.fillRect(0, 0, w, h);
	}*/
	
	/*public ThemePreview(List<Color> theme){
		Color c1 = theme.get(0);
		Color c2 = theme.get(1);
		
		_image = new BufferedImage(100,50,BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g2d = _image.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		int w = _image.getWidth();
		int h = _image.getHeight();
		System.out.println(w+" "+h);
		Color color1 = new Color(c1.getRed(), c1.getGreen(), c1.getBlue(), 96);
		Color color2 = new Color(c2.getRed(), c2.getGreen(), c2.getBlue(), 96);
		GradientPaint primary = new GradientPaint(0, 0, color1, w, 0, color2);
		GradientPaint shade = new GradientPaint(0f, 0f, new Color(0, 0, 0, 0),0f, h, new Color(0, 0, 0, 96));
		g2d.setPaint(primary);
		g2d.fillRect(0, 0, w, h);
		g2d.setPaint(shade);
		g2d.fillRect(0, 0, w, h);	
	}*/
	
	public ThemePreview(BufferedImage image, List<Color> theme){
		super(image);
		_image = image;
		_theme = theme;
	}
	
	public List<Color> getTheme(){
		return _theme;
	}
	
}
