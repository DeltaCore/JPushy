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

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Picture getTexture() {
		return texture;
	}

	public void setTexture(Picture texture) {
		this.texture = texture;
	}

	public boolean isDamageBar() {
		return damageBar;
	}

	public void setDamageBar(boolean damageBar) {
		this.damageBar = damageBar;
	}

	public int getMaxDMG() {
		return maxDMG;
	}

	public void setMaxDMG(int maxDMG) {
		this.maxDMG = maxDMG;
	}

	public int getDmg() {
		return dmg;
	}

	public void setDmg(int dmg) {
		this.dmg = dmg;
	}

	public int getMaxStackSize() {
		return maxStackSize;
	}

	public void setMaxStackSize(int maxStackSize) {
		this.maxStackSize = maxStackSize;
	}

	public void init() {
	}

	public void onUse(Stage stage, int dir) {
	}

	public void onPickup() {
		Game.sendMessage("You picked up " + this.getName());
	}

	@Override
	public String toString() {
		return this.getName() + "[" + this.getId() + "];";
	}

}
