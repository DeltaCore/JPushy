package JPushy.Types.Blocks;

import java.awt.Color;
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

	public static final Block	     air	          = new Block("Air", 0, PictureLoader.loadImageFromFile("base.png")).setPlayerAbleToWalkOn(true).setSolid(true).setDestroyable(false).setVisible(true).setCanGetocupied(true);
	public static final Block	     wall	          = new Block("Wall - Base", 1, PictureLoader.loadImageFromFile("wall.png")).setPlayerAbleToWalkOn(false).setSolid(true).setDestroyable(false).setVisible(true);
	public static final Block	     wall1	        = new Block("Wall 1", 2, PictureLoader.loadImageFromFile("wall1.png")).setPlayerAbleToWalkOn(false).setSolid(true).setDestroyable(false).setVisible(true);
	public static final Block	     wall2	        = new Block("Wall 2", 3, PictureLoader.loadImageFromFile("wall2.png")).setPlayerAbleToWalkOn(false).setSolid(true).setDestroyable(false).setVisible(true);
	public static final Block	     wall3	        = new Block("Wall 3", 4, PictureLoader.loadImageFromFile("wall3.png")).setPlayerAbleToWalkOn(false).setSolid(true).setDestroyable(false).setVisible(true);
	public static final Block	     wall4	        = new Block("Wall 4", 5, PictureLoader.loadImageFromFile("wall4.png")).setPlayerAbleToWalkOn(false).setSolid(true).setDestroyable(false).setVisible(true);
	public static final Block	     wall5	        = new Block("Wall 5", 6, PictureLoader.loadImageFromFile("wall5.png")).setPlayerAbleToWalkOn(false).setSolid(true).setDestroyable(false).setVisible(true);
	public static final Block	     wall6	        = new Block("Wall 6", 7, PictureLoader.loadImageFromFile("wall6.png")).setPlayerAbleToWalkOn(false).setSolid(true).setDestroyable(false).setVisible(true);
	public static final Block	     wall7	        = new Block("Wall 7", 8, PictureLoader.loadImageFromFile("wall7.png")).setPlayerAbleToWalkOn(false).setSolid(true).setDestroyable(false).setVisible(true);
	public static final Block	     wall8	        = new Block("Wall 8", 9, PictureLoader.loadImageFromFile("wall8.png")).setPlayerAbleToWalkOn(false).setSolid(true).setDestroyable(false).setVisible(true);
	public static final Block	     wall9	        = new Block("Wall 9", 10, PictureLoader.loadImageFromFile("wall9.png")).setPlayerAbleToWalkOn(false).setSolid(true).setDestroyable(false).setVisible(true);
	public static final Block	     wall10	        = new Block("Wall 10", 11, PictureLoader.loadImageFromFile("wall10.png")).setPlayerAbleToWalkOn(false).setSolid(true).setDestroyable(false).setVisible(true);
	public static final Block	     wall11	        = new Block("Wall 11", 12, PictureLoader.loadImageFromFile("wall11.png")).setPlayerAbleToWalkOn(false).setSolid(true).setDestroyable(false).setVisible(true);
	public static final Block	     wall12	        = new Block("Wall 12", 13, PictureLoader.loadImageFromFile("wall12.png")).setPlayerAbleToWalkOn(false).setSolid(true).setDestroyable(false).setVisible(true);
	public static final Block	     wall13	        = new Block("Wall 13", 14, PictureLoader.loadImageFromFile("wall13.png")).setPlayerAbleToWalkOn(false).setSolid(true).setDestroyable(false).setVisible(true);
	public static final Block	     wall14	        = new Block("Wall 14", 15, PictureLoader.loadImageFromFile("wall14.png")).setPlayerAbleToWalkOn(false).setSolid(true).setDestroyable(false).setVisible(true);
	public static final Block	     wall15	        = new Block("Wall 15", 16, PictureLoader.loadImageFromFile("wall15.png")).setPlayerAbleToWalkOn(false).setSolid(true).setDestroyable(false).setVisible(true);
	public static final Block	     chest	        = new MoveableBlock("Chest", 17, PictureLoader.loadImageFromFile("chest.png")).setPlayerAbleToWalkOn(false).setSolid(false).setDestroyable(true).setVisible(true);
	public static final Block	     home	          = new BlockHome("Home", 18, PictureLoader.loadImageFromFile("home.png")).setPlayerAbleToWalkOn(true).setSolid(true).setDestroyable(false).setVisible(true);
	public static final Block	     finish	        = new Finish("Finish", 19, PictureLoader.loadImageFromFile("finish.png"));
	public static final Block	     TteleportBase	= new TeleportBase("Teleporter Entrance", 20, PictureLoader.loadImageFromFile("teleportbase.png"));
	public static final Block	     teleportExit	  = new Block("Teleporter Exit", 21, PictureLoader.loadImageFromFile("teleportend.png")).setPlayerAbleToWalkOn(true).setSolid(true).setDestroyable(false).setVisible(true);
	public static final Block	     teleportAdv	  = new TeleportBaseAdv("Two way teleporter", 22, PictureLoader.loadImageFromFile("teleportbaseadv.png"));
	public static final Block	     blockDoor	    = new BlockDoor("Door", 23, PictureLoader.loadImageFromFile("door_close.png"));
	public static final Block	     gate	          = new Gate("Gate", 24, PictureLoader.loadImageFromFile("gate.png")).setDestroyable(true).setPlayerAbleToWalkOn(false).setSolid(true).setInvincebleBlock(Blocks.getBlockById(0)).setVisible(true);
	public static final Block	     lever	        = new SwitchBlock("Lever", 25, PictureLoader.loadImageFromFile("leverOn.png"), PictureLoader.loadImageFromFile("leverOff.png")).setDestroyable(false).setSolid(true).setPlayerAbleToWalkOn(true);
	public static final Block	     button	        = new Button("Button", 26, PictureLoader.loadImageFromFile("button.png")).setDestroyable(false).setSolid(true).setPlayerAbleToWalkOn(true).setCanGetocupied(true);
	public static final Block	     blueBallBox	  = new ColoredBallBox("Blue ball box", 27, PictureLoader.loadImageFromFile("BlueBallBox.png"), Color.blue);
	public static final Block	     greenBallBox	  = new ColoredBallBox("Green ball box", 28, PictureLoader.loadImageFromFile("GreenBallBox.png"), Color.green);
	public static final Block	     redBallBox	    = new ColoredBallBox("Red ball box", 29, PictureLoader.loadImageFromFile("RedBallBox.png"), Color.red);
	public static final Block	     blueBall	      = new ColoredBall("Blue ball", 30, PictureLoader.loadImageFromFile("BlueBall.png"), Color.blue);
	public static final Block	     greenBall	    = new ColoredBall("Green ball", 31, PictureLoader.loadImageFromFile("GreenBall.png"), Color.green);
	public static final Block	     redBall	      = new ColoredBall("Red ball", 32, PictureLoader.loadImageFromFile("RedBall.png"), Color.red);
	public static final Block	     blueBallPaint	= new ColoredBallPaint("Blue ball paint", 33, PictureLoader.loadImageFromFile("BlueBallPaint.png"), Color.blue);
	public static final Block	     greenBallPaint	= new ColoredBallPaint("Green ball paint", 34, PictureLoader.loadImageFromFile("GreenBallPaint.png"), Color.green);
	public static final Block	     redBallPaint	  = new ColoredBallPaint("Red ball paint", 35, PictureLoader.loadImageFromFile("RedBallPaint.png"), Color.red);

	// continue with id 36

	public static void registerBlock(Block b) {
		if (!checkBlockId(b.getId())) {
			blockRegistry.add(b);
			System.out.println("[BlockRegistry] New block : " + b.getName() + ":" + b.getId() + " | Char : " + (char) (b.getId() + 48));
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
					return b = blockRegistry.get(i).copy();
				}
			}
		}
		return air.copy();
	}

	public static Block getBlockByName(String name) {
		for (int i = 0; i < blockRegistry.size(); i++) {
			if (blockRegistry.get(i) == null) {
			} else {
				if (blockRegistry.get(i).getName().equalsIgnoreCase(name)) {
					return blockRegistry.get(i).copy();
				}
			}
		}
		return air.copy();
	}

	public static void wakeUpDummy() {

	}

}
