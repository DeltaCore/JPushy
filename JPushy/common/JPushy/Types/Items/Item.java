package JPushy.Types.Items;

import JPushy.Core.Game;
import JPushy.Types.Level.Stage;
import JPushy.Types.gfx.Picture;

/**
 * 
 * @author Marcel Benning
 * 
 */
public class Item {

	private String	name;
	private int	    id;
	private Picture	texture;
	private boolean	damageBar;
	private int	    maxDMG;
	private int	    dmg;
	private int	    maxStackSize;

	public Item(String name, int id, Picture texture) {
		this.name = name;
		this.id = id;
		this.texture = texture;
		Items.registerItem(this);
	}

	public Item(String name, int id, Picture texture, boolean register) {
		this.name = name;
		this.id = id;
		this.texture = texture;
		if (register) {
			Items.registerItem(this);
		}
		this.setMaxStackSize(1);
	}

	public String getName() {
		return name;
	}

	public Item setName(String name) {
		this.name = name;
		return this;
	}

	public int getId() {
		return id;
	}

	public Item setId(int id) {
		this.id = id;
		return this;
	}

	public Picture getTexture() {
		return texture;
	}

	public Item setTexture(Picture texture) {
		this.texture = texture;
		return this;
	}

	public boolean isDamageBar() {
		return damageBar;
	}

	public Item setDamageBar(boolean damageBar) {
		this.damageBar = damageBar;
		return this;
	}

	public int getMaxDMG() {
		return maxDMG;
	}

	public Item setMaxDMG(int maxDMG) {
		this.maxDMG = maxDMG;
		return this.setDamageBar(true);
	}

	public int getDmg() {
		return dmg;
	}

	public Item setDmg(int dmg) {
		this.dmg = dmg;
		return this;
	}

	public int getMaxStackSize() {
		return maxStackSize;
	}

	public Item setMaxStackSize(int maxStackSize) {
		this.maxStackSize = maxStackSize;
		return this;
	}

	public void init() {
	}

	public void onUse(Stage stage, int dir, ItemUseEvent e) {
		if (!e.isHandled()) {
			if (!e.isCanceled()) {
				System.out.println("On use !");
				if (this.isDamageBar()) {
					this.setDmg(this.getDmg() + 1);
					if (this.getDmg() >= this.getMaxDMG()) {
						Game.getPlayer().getInventory().removeItemInHand();
					}
				} else {
					Game.getPlayer().getInventory().removeItemInHand();
				}
			}
		}
	}

	public void onPickup() {
		Game.sendMessage("You picked up " + this.getName());
	}

	@Override
	public String toString() {
		return this.getName() + "[" + this.getId() + "];";
	}

}
