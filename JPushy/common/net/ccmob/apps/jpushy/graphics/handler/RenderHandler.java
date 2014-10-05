
package net.ccmob.apps.jpushy.graphics.handler;

import java.awt.Graphics2D;

import net.ccmob.apps.jpushy.blocks.Block;
import net.ccmob.apps.jpushy.graphics.GraphicUtils;

public class RenderHandler {
	
	public static RenderHandler defaultHandler = new RenderHandler();

	public void renderBlock(Block b, Graphics2D g, int blockX, int blockY) {
		g.drawImage(GraphicUtils.getImageFromPicture(b.getTexture()), (40 * blockX), (40 * blockY), 40, 40, null);
	}

	public void renderBlock(Block b, Graphics2D g, int blockX, int blockY, int width, int height){
		g.drawImage(GraphicUtils.getImageFromPicture(b.getTexture()), (40 * blockX), (40 * blockY), width, height, null);
	}
	
}
