package JPushy.Types;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
/**
 * 
 * @author Marcel Benning
 * 
 */
public class Picture {

	private BufferedImage img;
	
	public Picture(String filename) throws IOException {
		try {
			img = ImageIO.read(new File("Data/img/" + filename));
		} catch (IOException e) {
			throw new IOException("File not found ! Please put it int this folder : Data/img/" + filename);
		}
	}

	public BufferedImage getImg() {
		return img;
	}

}
