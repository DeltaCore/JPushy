package net.ccmob.apps.jpushy.sp.player;

import net.ccmob.apps.jpushy.core.Game;
import net.ccmob.apps.jpushy.items.Item;
import net.ccmob.apps.jpushy.items.Items;

/**
 * 
 * @author Marcel Benning
 * 
 */
public class Inventory {

	int	      iSlots	     = 8;
	InventorySlot[]	slots	       = new InventorySlot[iSlots];
	int	            selectedSlot	= 0;

	public Inventory() {
		for (int i = 0; i < slots.length; i++) {
			slots[i] = new InventorySlot();
		}
	}
	
	public Inventory(int slots){
		this.slots = new InventorySlot[slots];
		for (int i = 0; i < this.slots.length; i++) {
			this.slots[i] = new InventorySlot();
		}
	}

	public boolean addItem(Item item) {
		if (!item.getName().equalsIgnoreCase("noitem")) {
			//System.out.println("Item : " + item.toString());
			InventorySlot slot = null;
			Item t = null;
			int slotAmount = 0;
			int temp = 0;
			for (int i = 0; i < slots.length; i++) {
				slot = slots[i];
				t = slot.getItem();
				if (t != null) {
					if (t.getName().equalsIgnoreCase("noitem")) {
						slots[i].setItem(item);
						slots[i].setAmount(1);
						return true;
					} else {
						if (slot.getItem().getName().equalsIgnoreCase(item.getName())) {
							slotAmount = slot.getAmount();
							temp = slotAmount;
							temp++;
							if (temp > t.getMaxStackSize()) {
								if (i == slots.length - 1) {
									return false;
								} else {
									continue;
								}
							} else {
								slot.setAmount(slot.getAmount() + 1);
								return true;
							}
						}
					}
				}
			}
			Game.sendMessage("No space in your inventory left.");
			return false;
		}
		return false;
	}

	public void removeItem(Item item) {
		for (int i = 0; i < this.getSlots().length; i++) {
			if (this.getSlots()[i].getItem().getName().equalsIgnoreCase(item.getName())) {
				if (this.getSlots()[i].getAmount() >= 1) {
					this.getSlots()[i].setAmount(this.getSlots()[i].getAmount() - 1);
					break;
				} else {
					this.getSlots()[i].setItem(Items.noitem);
					break;
				}
			}
		}
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

	public Item getItemInHand() {
		return slots[getSelectedSlot()].getItem();
	}

	public void setItemInHand(Item i) {
		this.getSlots()[getSelectedSlot()].setItem(i);
	}

	public void removeItemInHand() {
		if (this.getSlots()[getSelectedSlot()].getAmount() >= 1) {
			this.getSlots()[getSelectedSlot()].setAmount(this.getSlots()[getSelectedSlot()].getAmount() - 1);
		} else {
			this.getSlots()[getSelectedSlot()].setItem(Items.noitem);
		}
	}
	
	public void update() {
		for (InventorySlot slot : this.getSlots()) {
			if (slot.getItem().getId() != -1) {
				if (slot.getAmount() <= 0) {
					//System.out.println("Clearing slot : " + slot.toString());
					slot.clear();
				}
			}
		}
	}

}
