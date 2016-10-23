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
	
	/**
	 * 
	 * @param image
	 * @param theme
	 */
	public ThemePreview(BufferedImage image, List<Color> theme){
		super(image);
		_image = image;
		_theme = theme;
	}
	
	/**
	 * getter for theme
	 * @return
	 */
	public List<Color> getTheme(){
		return _theme;
	}
	
}
