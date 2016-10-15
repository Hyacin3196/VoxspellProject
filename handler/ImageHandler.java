package handler;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class ImageHandler {
	public static BufferedImage getImage(String imageFile) throws IOException{
		BufferedImage image = ImageIO.read(new File(imageFile));
		return image;
	}
}
