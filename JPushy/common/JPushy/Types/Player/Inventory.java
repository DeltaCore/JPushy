package JPushy.Types.Player;

import JPushy.Game;
import JPushy.Types.Items.Item;

public class Inventory {

	final int	      iSlots	= 8;
	InventorySlot[]	slots	 = new InventorySlot[iSlots];

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
			for (int i = 0; i < slots.length;) {
				slot = slots[i];
				t = slot.getItem();
				if (t != null) {
					if (t.getName().equalsIgnoreCase("noitem")) {
						slots[i].setItem(item);
						return true;
					} else {
						slotAmount = slot.getAmount();
						temp = slotAmount;
						temp++;
						if (temp > t.getMaxStackSize()) {
							Game.sendMessage("No Inventory space :(");
							return false;
						} else {
							slot.setAmount(slot.getAmount() + 1);
							return true;
						}
					}
				}
			}
		}
		return false;
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

}
