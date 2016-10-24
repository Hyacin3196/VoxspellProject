package handler;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
/**
 * @author jdum654
 */
public class ImageHandler {
	/**
	 * reads the image from a provided fileName
	 * @param imageFile
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage getImage(String imageFile) throws IOException{
		BufferedImage image = ImageIO.read(new File(imageFile));
		return image;
	}
}
