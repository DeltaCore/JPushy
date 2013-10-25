package JPushy.Types.Items;

import java.util.ArrayList;

import JPushy.gfx.PictureLoader;
/**
 * 
 * @author Marcel Benning
 * 
 */
public class Items {

public static ArrayList<Item> itemRegistry = new ArrayList<Item>();
	
	public static final int maxItems = 32;
	
	public static final Item noitem = new Item("noitem", -1, PictureLoader.loadImageFromFile("base.png"));
	public static final Item key = new ItemKey("Key", 0, PictureLoader.loadImageFromFile("item.key.png"));
	public static final Item mining_gun = new MiningGun("Mining Gun", 1, PictureLoader.loadImageFromFile("item.mining.gun.png"));
	
	public static void registerItem(Item t){
		if(!checkItemId(t.getId())){
			itemRegistry.add(t);
			System.out.println("[BlockRegistry] New Item : " + t.getName() + ":" + t.getId());
		}else{
			System.out.println("Could'n register Item : " + t.getName() + " with id : " + t.getId());
		}
	}
	
	private static boolean checkItemId(int id){
		boolean flag = false;
		for(int i=0;i<itemRegistry.size();i++){
			if(itemRegistry.get(i) == null){
			}else{
				if(itemRegistry.get(i).getId() == id){
					flag = true;
					break;
				}
			}
		}
		return flag;
	}
	
	public static Item getItemById(int id){
		for(int i = 0;i<itemRegistry.size();i++){
			if(itemRegistry.get(i) == null){
			}else{
				if(itemRegistry.get(i).getId() == id){
					return itemRegistry.get(i);
				}
			}
		}
		return null;
	}
	
	public static void wakeUpDummy(){
		
	}
	
}
