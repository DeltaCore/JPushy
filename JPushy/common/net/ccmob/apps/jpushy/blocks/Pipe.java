/**
 * 
 */

package net.ccmob.apps.jpushy.blocks;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import net.ccmob.apps.jpushy.graphics.Picture;
import net.ccmob.apps.jpushy.graphics.handler.RenderHandler;
import net.ccmob.apps.jpushy.input.Direction;
import net.ccmob.apps.jpushy.items.Item;
import net.ccmob.apps.jpushy.sp.player.Inventory;

/**
 * @author Marcel
 *
 */
public class Pipe extends InventoryBlock {

	private PipeMode	                               pipeMode;
	private Direction	                               rotation;

	static Pipe	                                     straight1;
	static Pipe	                                     straight2;
	static Pipe	                                     straight3;
	static Pipe	                                     straight4;

	static Pipe	                                     cstraight1;
	static Pipe	                                     cstraight2;
	static Pipe	                                     cstraight3;
	static Pipe	                                     cstraight4;

	static Pipe	                                     edge1;
	static Pipe	                                     edge2;
	static Pipe	                                     edge3;
	static Pipe	                                     edge4;

	private ArrayList<PipeTextureHandler.TextureIds>	neededTextures	= new ArrayList<PipeTextureHandler.TextureIds>();

	public Pipe(PipeMode pipeMode, Direction rot, String name, int id) {
		this(name, id, null);
		this.setPipeMode(pipeMode);
		this.setRotation(rot);
		switch(pipeMode){
			case STRAIGHT:{
				this.setInventory(new Inventory(1));
				break;
			}
			case CROSS_ONEWAY:{
				this.setInventory(new Inventory(3));
				break;
			}
			case EDGE:{
				this.setInventory(new Inventory(1));
				break;
			}
			case CROSS_STRAIGHT:{
				this.setInventory(new Inventory(2));
				break;
			}
			case T_ONEWAY:{
				this.setInventory(new Inventory(2));
			}
			default:
				break;
		}
	}

	private Pipe(String name, int id, Picture img) {
		super(name, id, img);
		this.setCanGetocupied(false);
		this.setDestroyable(false);
		this.setLever(false);
		this.setMovable(false);
		this.setOptionRequired(false);
		this.setPlayerAbleToWalkOn(false);
		this.setSolid(true);
		this.setSwitchable(false);
		this.setVisible(true);
		this.setRenderHandler(PipeRenderHandler.instance);
	}

	@Override
	public boolean pushItemInFromSide(Direction d, Item i) {

		switch(pipeMode){
			case STRAIGHT:{
				return this.pushItemStraigt(i, d);
			}
			case CROSS_ONEWAY:{
				this.setInventory(new Inventory(3));
				break;
			}
			case EDGE:{
				this.setInventory(new Inventory(1));
				break;
			}
			case CROSS_STRAIGHT:{
				this.setInventory(new Inventory(2));
				break;
			}
			case T_ONEWAY:{
				this.setInventory(new Inventory(2));
			}
			default:
				break;
		}
		return false;
	}
	
	private boolean pushItemStraigt(Item i, Direction d){
		if(d == this.getRotation()){
			if(isSpaceFor(i, 0)){
				this.getInventory().getSlots()[0].setItem(i);
				return true;
			}
		}
		return false;
	}
	
	public boolean isSpaceFor(Item in, int slot){
		if(this.getInventory().getSlots().length > slot)
			return false;
		return (this.getInventory().getSlots()[slot].getAmount() == 0);
	}
	
	public static void load() {
		straight1 = new Pipe(PipeMode.STRAIGHT, Direction.NORTH, "Pipe Straight 1", 38);
		straight2 = new Pipe(PipeMode.STRAIGHT, Direction.EAST, "Pipe Straight 2", 39);
		straight3 = new Pipe(PipeMode.STRAIGHT, Direction.SOUTH, "Pipe Straight 3", 40);
		straight4 = new Pipe(PipeMode.STRAIGHT, Direction.WEST, "Pipe Straight 4", 41);

		cstraight1 = new Pipe(PipeMode.CROSS_STRAIGHT, Direction.NORTH, "Pipe Cross 1", 42);
		cstraight2 = new Pipe(PipeMode.CROSS_STRAIGHT, Direction.EAST, "Pipe Cross 2", 43);
		cstraight3 = new Pipe(PipeMode.CROSS_STRAIGHT, Direction.SOUTH, "Pipe Cross 3", 44);
		cstraight4 = new Pipe(PipeMode.CROSS_STRAIGHT, Direction.WEST, "Pipe Cross 4", 45);
		
		edge1 = new Pipe(PipeMode.EDGE, Direction.NORTH, "Pipe Edge 1", 46);
		edge2 = new Pipe(PipeMode.EDGE, Direction.EAST, "Pipe Edge 2", 47);
		edge3 = new Pipe(PipeMode.EDGE, Direction.SOUTH, "Pipe Edge 3", 48);
		edge4 = new Pipe(PipeMode.EDGE, Direction.WEST, "Pipe Edge 4", 49);
	}

	/**
	 * @return the pipeMode
	 */
	public PipeMode getPipeMode() {
		return pipeMode;
	}

	/**
	 * @param pipeMode
	 *          the pipeMode to set
	 */
	public void setPipeMode(PipeMode pipeMode) {
		this.pipeMode = pipeMode;
	}

	/**
	 * @return the neededTextures
	 */
	public ArrayList<PipeTextureHandler.TextureIds> getNeededTextures() {
		return neededTextures;
	}

	/**
	 * @param neededTextures
	 *          the neededTextures to set
	 */
	public void setNeededTextures(ArrayList<PipeTextureHandler.TextureIds> neededTextures) {
		this.neededTextures = neededTextures;
	}

	/**
	 * @return the rotation
	 */
	public Direction getRotation() {
		return rotation;
	}

	/**
	 * @param rotation
	 *          the rotation to set
	 */
	public void setRotation(Direction rotation) {
		this.rotation = rotation;
	}

	public static enum PipeMode {
		STRAIGHT, CROSS_STRAIGHT, EDGE, T_ONEWAY, CROSS_ONEWAY
	}

	public static class PipeRenderHandler extends RenderHandler {

		public static PipeRenderHandler	instance	= new PipeRenderHandler();

		@Override
		public void renderBlock(Block b, Graphics2D g, int blockX, int blockY) {
			if (b instanceof Pipe) {
				System.out.println(((Pipe) b).getPipeMode());
				switch (((Pipe) b).getPipeMode()) {
					case STRAIGHT: {
						renderStraight((Pipe) b, g, blockX, blockY, 40, 40);
						break;
					}
					case CROSS_STRAIGHT: {
						renderCrossStraight((Pipe) b, g, blockX, blockY, 40, 40);
						break;
					}
					case EDGE : {
						renderEdge((Pipe) b, g, blockX, blockY, 40, 40);
						break;
					}
					default: {
						super.renderBlock((Pipe) b, g, blockX, blockY, 40, 40);
						break;
					}
				}
			} else {
				super.renderBlock(b, g, blockX, blockY);
			}
		}

		@Override
		public void renderBlock(Block b, Graphics2D g, int blockX, int blockY, int width, int height) {
			if (b instanceof Pipe) {
				switch (((Pipe) b).getPipeMode()) {
					case STRAIGHT: {
						renderStraight((Pipe) b, g, blockX, blockY, width, height);
						break;
					}
					case CROSS_STRAIGHT: {
						renderCrossStraight((Pipe) b, g, blockX, blockY, width, height);
						break;
					}
					case EDGE : {
						renderEdge((Pipe) b, g, blockX, blockY, width, height);
						break;
					}
					default: {
						super.renderBlock((Pipe) b, g, blockX, blockY, width, height);
						break;
					}
				}
			} else {
				super.renderBlock(b, g, blockX, blockY);
			}
			// g.drawImage(GraphicUtils.getImageFromPicture(b.getTexture()), (40 *
			// blockX), (40 * blockY), width, height, null);
		}

		private void renderStraight(Pipe p, Graphics2D g, int xp, int yp, int width, int height) {
			int x = (40 * xp);
			int y = (40 * yp);
			if (p.getRotation() == Direction.EAST || p.getRotation() == Direction.WEST) {
				g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.T_Middle), x, y, width, height, null);
				g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.O_B_Left), x, y, width, height, null);
				g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.O_B_Right), x, y, width, height, null);
				g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.I_B_Top), x, y, width, height, null);
				g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.I_B_Bottom), x, y, width, height, null);
				g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.T_Left), x, y, width, height, null);
				g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.T_Right), x, y, width, height, null);
			} else {
				g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.T_Middle), x, y, width, height, null);
				g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.O_B_Top), x, y, width, height, null);
				g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.O_B_Bottom), x, y, width, height, null);
				g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.I_B_Left), x, y, width, height, null);
				g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.I_B_Right), x, y, width, height, null);
				g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.T_Bottom), x, y, width, height, null);
				g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.T_Top), x, y, width, height, null);
			}
		}

		private void renderCrossStraight(Pipe p, Graphics2D g, int xp, int yp, int width, int height) {
			int x = (40 * xp);
			int y = (40 * yp);
			g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.T_Middle), x, y, width, height, null);
			g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.O_B_Left), x, y, width, height, null);
			g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.O_B_Right), x, y, width, height, null);
			g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.O_B_Top), x, y, width, height, null);
			g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.O_B_Bottom), x, y, width, height, null);
			g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.T_Bottom), x, y, width, height, null);
			g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.T_Left), x, y, width, height, null);
			g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.T_Right), x, y, width, height, null);
			g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.T_Top), x, y, width, height, null);
			g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.I_B_Left), x, y, width, height, null);
			g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.I_B_Right), x, y, width, height, null);
		}

		private void renderEdge(Pipe p, Graphics2D g, int xp, int yp, int width, int height) {
			int x = (40 * xp);
			int y = (40 * yp);
			switch (p.getRotation()) {
				case NORTH: {
					g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.T_Middle), x, y, width, height, null);
					g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.T_Top), x, y, width, height, null);
					g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.T_Right), x, y, width, height, null);
					g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.O_B_Top), x, y, width, height, null);
					g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.O_B_Right), x, y, width, height, null);
					g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.I_B_Bottom), x, y, width, height, null);
					g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.I_B_Left), x, y, width, height, null);
					break;
				}
				case EAST: {
					g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.T_Middle), x, y, width, height, null);
					g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.T_Bottom), x, y, width, height, null);
					g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.T_Right), x, y, width, height, null);
					g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.O_B_Bottom), x, y, width, height, null);
					g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.O_B_Right), x, y, width, height, null);
					g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.I_B_Top), x, y, width, height, null);
					g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.I_B_Left), x, y, width, height, null);
					break;
				}
				case SOUTH: {
					g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.T_Middle), x, y, width, height, null);
					g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.T_Left), x, y, width, height, null);
					g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.T_Bottom), x, y, width, height, null);
					g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.O_B_Left), x, y, width, height, null);
					g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.O_B_Bottom), x, y, width, height, null);
					g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.I_B_Right), x, y, width, height, null);
					g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.I_B_Top), x, y, width, height, null);
					break;
				}
				case WEST: {
					g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.T_Middle), x, y, width, height, null);
					g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.T_Left), x, y, width, height, null);
					g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.T_Top), x, y, width, height, null);
					g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.O_B_Left), x, y, width, height, null);
					g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.O_B_Top), x, y, width, height, null);
					g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.I_B_Right), x, y, width, height, null);
					g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.I_B_Bottom), x, y, width, height, null);
					break;
				}
				default: {
					break;
				}
			}
			/*g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.T_Middle), x, y, width, height, null);
			g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.O_B_Left), x, y, width, height, null);
			g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.O_B_Right), x, y, width, height, null);
			g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.O_B_Top), x, y, width, height, null);
			g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.O_B_Bottom), x, y, width, height, null);
			g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.T_Bottom), x, y, width, height, null);
			g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.T_Left), x, y, width, height, null);
			g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.T_Right), x, y, width, height, null);
			g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.T_Top), x, y, width, height, null);
			g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.I_B_Left), x, y, width, height, null);
			g.drawImage(PipeTextureHandler.getTextureById(PipeTextureHandler.TextureIds.I_B_Right), x, y, width, height, null);*/
		}

	}

	public static class PipeBlockHandler {

		public PipeBlockHandler() {
			Pipe.load();
		}

	}

	public static class PipeTextureHandler {

		private enum TextureIds {
			I_B_Right, I_B_Left, I_B_Top, I_B_Bottom, O_B_Right, O_B_Left, O_B_Top, O_B_Bottom, T_Right, T_Left, T_Top, T_Bottom, T_Middle
		}

		private static String[]	TexturePaths	= { "inner_barrier_right.png", "inner_barrier_left.png", "inner_barrier_top.png", "inner_barrier_bottom.png", "outer_barrier_right.png",
		                                         "outer_barrier_left.png", "outer_barrier_top.png", "outer_barrier_bottom.png", "tube_right.png", "tube_left.png", "tube_top.png",
		                                         "tube_bottom.png", "tube_middle.png" };

		public static Picture[]	PipeTextures	= { loadSaveTexture(TextureIds.I_B_Right), loadSaveTexture(TextureIds.I_B_Left), loadSaveTexture(TextureIds.I_B_Top),
		                                         loadSaveTexture(TextureIds.I_B_Bottom), loadSaveTexture(TextureIds.O_B_Right), loadSaveTexture(TextureIds.O_B_Left),
		                                         loadSaveTexture(TextureIds.O_B_Top), loadSaveTexture(TextureIds.O_B_Bottom), loadSaveTexture(TextureIds.T_Right),
		                                         loadSaveTexture(TextureIds.T_Left), loadSaveTexture(TextureIds.T_Top), loadSaveTexture(TextureIds.T_Bottom),
		                                         loadSaveTexture(TextureIds.T_Middle) };

		public static BufferedImage getTextureById(TextureIds id) {
			return PipeTextures[id.ordinal()].getImg();
		}

		private static Picture loadSaveTexture(TextureIds id) {
			try {
				return new Picture("pipes/" + TexturePaths[id.ordinal()]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

	}

}