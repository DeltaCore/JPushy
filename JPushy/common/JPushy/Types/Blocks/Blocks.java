package JPushy.Types.Blocks;

import java.util.ArrayList;

import JPushy.gfx.PictureLoader;

/**
 * 
 * @author Marcel Benning
 * 
 */
public class Blocks {

	public static ArrayList<Block>	blockRegistry	= new ArrayList<Block>();

	public static final int	       maxBlocks	    = 32;

	// public static final Block dummy = new Block("DUMMY", -1,
	// PictureLoader.loadImageFromFile("base.png"));
	public static final Block	     air	          = new Block("Air", 0, PictureLoader.loadImageFromFile("base.png")).setPlayerAbleToWalkOn(true).setSolid(true).setDestroyable(false).setVisible(true);
	public static final Block	     wall	          = new Block("Wall", 1, PictureLoader.loadImageFromFile("wall.png")).setPlayerAbleToWalkOn(false).setSolid(true).setDestroyable(false).setVisible(true);
	public static final Block	     chest	        = new MoveableBlock("Chest", 2, PictureLoader.loadImageFromFile("chest.png")).setPlayerAbleToWalkOn(false).setSolid(false).setDestroyable(true).setVisible(true);
	public static final Block	     home	          = new Block("Home", 3, PictureLoader.loadImageFromFile("home.png")).setPlayerAbleToWalkOn(true).setSolid(true).setDestroyable(false).setVisible(true);
	public static final Block	     finish	        = new Finish("Finish", 4, PictureLoader.loadImageFromFile("finish.png"));
	public static final Block	     TeleportBase	  = new TeleportBase("Teleporter", 5, PictureLoader.loadImageFromFile("teleportbase.png")).setPlayerAbleToWalkOn(true).setSolid(true).setDestroyable(false).setVisible(true);
	public static final Block	     TeleportExit	  = new Block("Teleporter", 6, PictureLoader.loadImageFromFile("teleportend.png")).setPlayerAbleToWalkOn(true).setSolid(true).setDestroyable(false).setVisible(true);
	public static final Block	     bricks	        = new Block("BrickFloor", 7, PictureLoader.loadImageFromFile("brick.png")).setPlayerAbleToWalkOn(false).setSolid(true).setDestroyable(true).setVisible(true);
	public static final Block		 blockDoor 		= new BlockDoor("Door", 8, PictureLoader.loadImageFromFile("button.png"));
	
	public static void registerBlock(Block b) {
		if (!checkBlockId(b.getId())) {
			blockRegistry.add(b);
			System.out.println("[BlockRegistry] New block : " + b.getName() + ":" + b.getId());
		} else {
			System.out.println("Could'n register block : " + b.getName() + " with id : " + b.getId());
		}
	}

	private static boolean checkBlockId(int id) {
		boolean flag = false;
		for (int i = 0; i < blockRegistry.size(); i++) {
			if (blockRegistry.get(i) == null) {
			} else {
				if (blockRegistry.get(i).getId() == id) {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	public static Block getBlockById(int id) {
		Block b = null;
		for (int i = 0; i < blockRegistry.size(); i++) {
			if (blockRegistry.get(i) == null) {
			} else {
				if (blockRegistry.get(i).getId() == id) {
					return b = blockRegistry.get(i).copy(blockRegistry.get(i));
				}
			}
		}
		return air.copy(air);
	}

	public static void wakeUpDummy() {

	}

}
