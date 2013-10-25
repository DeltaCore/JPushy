package JPushy.Types.Player;

import java.awt.Color;
import java.awt.Graphics;

import JPushy.Types.Items.Item;
import JPushy.Types.Items.Items;
import JPushy.gfx.GraphicUtils;
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

}
