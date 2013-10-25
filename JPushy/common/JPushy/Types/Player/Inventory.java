package JPushy.Types.Player;

import JPushy.Core.Game;
import JPushy.Types.Items.Item;
import JPushy.Types.Items.Items;
/**
 * 
 * @author Marcel Benning
 * 
 */
public class Inventory {

	final int	      iSlots	= 8;
	InventorySlot[]	slots	 = new InventorySlot[iSlots];
	int selectedSlot = 0;

	public Inventory() {
		for (int i = 0; i < slots.length; i++) {
			slots[i] = new InventorySlot();
		}
	}
	
	public boolean addItem(Item item) {
		if (!item.getName().equalsIgnoreCase("noitem")) {
			System.out.println("Item : " + item.toString());
			InventorySlot slot = null;
			Item t = null;
			int slotAmount = 0;
			int temp = 0;
			for (int i = 0; i < slots.length;i++) {
				slot = slots[i];
				t = slot.getItem();
				if (t != null) {
					if (t.getName().equalsIgnoreCase("noitem")) {
						slots[i].setItem(item);
						return true;
					} else {
						if(slot.getItem().getName().equalsIgnoreCase(item.getName())){
							slotAmount = slot.getAmount();
							temp = slotAmount;
							temp++;
							if (temp > t.getMaxStackSize()) {
								if(i == slots.length -1){
									return false;
								}else{
									continue;
								}
							} else {
								slot.setAmount(slot.getAmount() + 1);
								return true;
							}
						}
					}
				}
				Game.sendMessage("No Inv. space.");
			}return false;
		}
		return false;
	}
	
	public boolean removeItem(Item item){
		boolean key = false;
		for(int i = 0;i<this.getSlots().length;i++){
			if(this.getSlots()[i].getItem().getName().equalsIgnoreCase("Key") && !key){
				if(this.getSlots()[i].getAmount() >= 1){
					key = true;
					this.getSlots()[i].setAmount(this.getSlots()[i].getAmount() - 1);
				}else{
					key = true;
					this.getSlots()[i].setItem(Items.noitem);
				}
			}
		}
		return key;
	}
	
	public InventorySlot[] getSlots() {
		return slots;
	}

	public void setSlots(InventorySlot[] slots) {
		this.slots = slots;
	}

	public int getiSlots() {
		return iSlots;
	}

	public int getSelectedSlot() {
		return selectedSlot;
	}

	public void setSelectedSlot(int selectedSlot) {
		this.selectedSlot = selectedSlot;
	}

	public Item getItemInHand(){
		return slots[getSelectedSlot()].getItem();
	}
	
}
