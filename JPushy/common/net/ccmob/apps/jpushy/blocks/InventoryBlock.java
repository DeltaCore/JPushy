/**
 * 
 */
package net.ccmob.apps.jpushy.blocks;

import net.ccmob.apps.jpushy.graphics.Picture;
import net.ccmob.apps.jpushy.input.Direction;
import net.ccmob.apps.jpushy.items.Item;
import net.ccmob.apps.jpushy.sp.player.Inventory;

/**
 * @author Marcel
 *
 */
public class InventoryBlock extends Block {

	public InventoryBlock(String name, int id, Picture img) {
	  super(name, id, img);
  }

	private Inventory inventory = null;

	/**
	 * @return the inventory
	 */
	public Inventory getInventory() {
		return inventory;
	}

	/**
	 * @param inventory the inventory to set
	 */
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	
	public boolean hasInventory(){
		return this.getInventory() != null;
	}
	
	public boolean pushItemInFromSide(Direction d, Item i){
		return false;
	}
	
	public boolean pushItemOutFromSide(Direction d, Item i){
		return false;
	}
	
}
