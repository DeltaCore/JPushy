package net.ccmob.apps.jpushy.sp.level;

import java.awt.Graphics2D;
import java.util.ArrayList;

import net.ccmob.apps.jpushy.items.Item;
import net.ccmob.apps.jpushy.items.Items;

public class ItemArray {

	private int	            width, height;

	private ArrayList<Item>	items	= new ArrayList<Item>();

	public ItemArray() {
		this.setHeight(10);
		this.setWidth(10);
		initArray();
	}

	public void initArray() {
		for (int i = 0; i < this.getHeight() * this.getWidth(); i++) {
			this.getItems().add(Items.noitem);
		}
	}

	public ItemArray(int width, int height) {
		this.setWidth(width);
		this.setHeight(height);
		initArray();
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Item get(int x, int y) {
		if ((x <= this.getWidth() && x >= 0) && (y <= this.getHeight() && y >= 0)) {
			return this.getItems().get(this.getWidth() * y + x);
		} else {
			return null;
		}
	}

	public void set(int x, int y, Item item) {
		if ((x <= this.getWidth() && x >= 0) && (y <= this.getHeight() && y >= 0)) {
			this.getItems().set(this.getWidth() * y + x, item);
		} else {
			return;
		}
	}

	public void render(Graphics2D g) {
		for (int x = 0; x < this.getWidth(); x++) {
			for (int y = 0; y < this.getHeight(); y++) {
				if (this.get(x, y) != null && !this.get(x, y).getName().equalsIgnoreCase("noitem")) {
					g.drawImage(this.get(x, y).getTexture().getImg(), x * 40, y * 40, 40, 40, null);
					g.drawRect(x * 40, y * 40, 40, 40);
				}
			}
		}
	}

	/**
	 * @return the items
	 */
	public ArrayList<Item> getItems() {
		return items;
	}

	/**
	 * @param items
	 *          the items to set
	 */
	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}

}
