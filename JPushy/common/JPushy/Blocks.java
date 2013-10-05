package JPushy;

import java.util.ArrayList;

import JPushy.Types.Blocks.Block;
import JPushy.Types.Blocks.Finish;
import JPushy.Types.Blocks.MoveableBlock;
import JPushy.Types.Blocks.TeleportBase;
/**
 * 
 * @author Marcel Benning
 * 
 */
public class Blocks {
	
	public static ArrayList<Block> blockRegistry = new ArrayList<Block>();
	
	public static final int maxBlocks = 32;
	
	public static final Block air = new Block("Air", 0, PictureLoader.loadImageFromFile("base.png"), true, false, true);
	public static final Block wall = new Block("Wall", 1, PictureLoader.loadImageFromFile("wall.png"), false, true, true);
	public static final Block chest = new MoveableBlock("Chest", 2, PictureLoader.loadImageFromFile("chest.png"), false, false, true);
	public static final Block home = new Block("Home", 3, PictureLoader.loadImageFromFile("home.png"), true, true, true);
	public static final Block finish = new Finish("Finish", 4, PictureLoader.loadImageFromFile("finish.png"), true, true, true);
	public static final Block TeleportBase = new TeleportBase("Teleporter", 5, PictureLoader.loadImageFromFile("teleportbase.png"), true , true , true);
	public static final Block TeleportExit = new Block("Teleporter", 6, PictureLoader.loadImageFromFile("teleportend.png"), true , true , true);
	public static final Block bricks = new Block("BrickFloor", 7, PictureLoader.loadImageFromFile("brick.png"), false, true, true);
	//public static final Block button = new Button("Button", 7, PictureLoader.loadImageFromFile("button.png"), true, true, true);
	//public static final Block gate = new Gate("Gate", 8, PictureLoader.loadImageFromFile("wall.png"), false , true , true , Blocks.air);
	//public static final Block leverOff = new SwitchBlock("Switch", 9, PictureLoader.loadImageFromFile("leverOff.png"), true, true, true);
	//public static final Block leverOn = new SwitchBlock("Switch", 10, PictureLoader.loadImageFromFile("leverOn.png"), true, true ,true);
	//public static void Block button("")
	
	public static void registerBlock(Block b){
		if(!checkBlockId(b.getId())){
			blockRegistry.add(b);
			System.out.println("[BlockRegistry] New block : " + b.getName() + ":" + b.getId());
		}else{
			System.out.println("Could'n register block : " + b.getName() + " with id : " + b.getId());
		}
	}
	
	private static boolean checkBlockId(int id){
		boolean flag = false;
		for(int i=0;i<blockRegistry.size();i++){
			if(blockRegistry.get(i) == null){
			}else{
				if(blockRegistry.get(i).getId() == id){
					flag = true;
					break;
				}
			}
		}
		return flag;
	}
	
	public static Block getBlockById(int id){
		for(int i = 0;i<blockRegistry.size();i++){
			if(blockRegistry.get(i) == null){
			}else{
				if(blockRegistry.get(i).getId() == id){
					return blockRegistry.get(i);
				}
			}
		}
		return air;
	}
	
	public static void wakeUpDummy(){
		
	}
	
}
