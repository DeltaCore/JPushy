package net.ccmob.apps.jpushy.sp.player;

import java.awt.Color;
import java.awt.Graphics;

import net.ccmob.apps.jpushy.graphics.GraphicUtils;
import net.ccmob.apps.jpushy.items.Item;
import net.ccmob.apps.jpushy.items.Items;

/**
 * 
 * @author Marcel Benning
 * 
 */
public class InventorySlot {

	private Item	item;
	private int	 amount;

	public InventorySlot() {
		this(Items.noitem, 1);
	}

	public InventorySlot(Item item) {
		this(item, 1);
	}

	public InventorySlot(Item item, int amount) {
		this.item = item;
		this.amount = amount;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public void decrement(){
		this.amount--;
	}
	
	public void increment(){
		this.amount++;
	}
	
	public void render(int x, int y, Graphics g) {
		g.setColor(Color.black);
		g.drawRect(x, y, 68, 68);
		g.fillRect(x, y, 68, 68);
		if (item != null) {
			if (this.getItem().getId() != Items.noitem.getId()) {
				g.drawImage(GraphicUtils.getImageFromPicture(getItem().getTexture()), x + 4, y + 4, 60, 60, null);
			}
		}
	}

	public void clear() {
		this.setItem(Items.noitem);
		this.setAmount(0);
	}

	@Override
	public String toString() {
		return "[" + this.getItem().getName() + "|" + this.getItem().getId() + "|" + this.getAmount() + "]";
	}

}
