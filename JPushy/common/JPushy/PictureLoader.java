package JPushy;

import java.io.IOException;

import JPushy.Types.Picture;
/**
 * 
 * @author Marcel Benning
 * 
 */
public class PictureLoader {
	
	public static Picture loadImageFromFile(String filename){
		Picture p = null;
		try {
			p = new Picture(filename);
		} catch (IOException e) {
			try {
				p = new Picture("base.png");
			} catch (IOException e1) {
			}
			e.printStackTrace();
		}
		return p;
	}
	
//	public void setTexture(TextureLoader loader, Texture texture, BufferedImage image, Shape3D shape) {
//    	loader = new TextureLoader(image, "RGB",
//				TextureLoader.ALLOW_NON_POWER_OF_TWO);
//    	texture = loader.getTexture();
//    	Appearance appearance = shape.getAppearance();
//		texture.setBoundaryModeS(Texture.CLAMP_TO_BOUNDARY);
//		texture.setBoundaryModeT(Texture.CLAMP_TO_BOUNDARY);
//		texture.setBoundaryColor(new Color4f(0.0f, 1.0f, 0.5f, 0f));
//		// Set up the texture attributes
//		// could be REPLACE, BLEND or DECAL instead of MODULATE
//		
//		// front = getAppearance(new Color3f(Color.YELLOW));
//		Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
//		Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
//		Color3f red = new Color3f(0.7f, .15f, .15f);
//		appearance.setMaterial(new Material(red, black, red, white, 1.0f));		 
//		TextureAttributes texAttr = new TextureAttributes();
//		texAttr.setTextureMode(TextureAttributes.REPLACE);	
//		appearance.setTextureAttributes(texAttr);
//		appearance.setTexture(texture);
//		shape.setAppearance(appearance);
//	}
	
}
