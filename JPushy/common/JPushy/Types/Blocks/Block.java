package JPushy.Types.Blocks;

import java.util.ArrayList;

import JPushy.Core.Game;
import JPushy.Types.Items.Item;
import JPushy.Types.Items.Items;
import JPushy.Types.Level.Level;
import JPushy.Types.Level.Stage;
import JPushy.Types.Player.Player;
import JPushy.Types.Sound.Sound;
import JPushy.Types.gfx.Picture;

/**
 * 
 * @author Marcel Benning
 * 
 */
public class Block extends Object implements Cloneable {

	private int		id;
	private Picture	img;
	private String	name;
	private boolean	playerAbleToWalkOn;
	private boolean	solid;
	private boolean	visible;
	private Block	invincebleBlock;
	private boolean	switchable;
	private boolean	lever;
	private boolean	ocupied;
	private boolean	destroyable;
	private boolean	register;
	private Block	occupiedByBlock;
	private Item	keptItem		= Items.noitem;
	private Sound	sound;
	private boolean	bSound;
	private boolean	canGetocupied	= false;

	public Block(Block b) {
		this.id = b.getId();
		this.img = b.getTexture();
		this.name = b.getName();
		this.playerAbleToWalkOn = b.isPlayerAbleToWalkOn();
		this.solid = b.isSolid();
		this.visible = b.isVisible();
		this.invincebleBlock = b.getInvincebleBlock();
		this.switchable = b.isSwitchable();
		this.keptItem = b.getKeptItem();
	}

	public Block(String name, int id, Picture img) {
		this(name, id, img, true);
	}

	public Block(String name, int id, Picture img, boolean register) {
		this.id = id;
		this.img = img;
		this.name = name;
		this.playerAbleToWalkOn = true;
		this.solid = true;
		this.visible = true;
		this.invincebleBlock = Blocks.air;
		this.destroyable = false;
		this.register = register;
		this.keptItem = Items.noitem;
		this.bSound = false;
		this.init();
	}

	public int getId() {
		return id;
	}

	public Block setId(int id) {
		this.id = id;
		return this;
	}

	public Picture getTexture() {
		return img;
	}

	public Block setTexture(Picture img) {
		this.img = img;
		return this;
	}

	public String getName() {
		return name;
	}

	public Block setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public String toString() {
		return this.name + "[" + this.id + "];";
		// return this.name + "[" + this.id + "]:" + super.toString() + ";";
	}

	public boolean isVisible() {
		return visible;
	}

	public Block setVisible(boolean visible) {
		this.visible = visible;
		return this;
	}

	public Block getInvincebleBlock() {
		return invincebleBlock;
	}

	public Block setInvincebleBlock(Block invincebleBlock) {
		this.invincebleBlock = invincebleBlock;
		return this;
	}

	/* Interact methods */

	public boolean isPlayerAbleToWalkOn() {
		return playerAbleToWalkOn;
	}

	public Block setPlayerAbleToWalkOn(boolean playerAbleToWalkOn) {
		this.playerAbleToWalkOn = playerAbleToWalkOn;
		return this;
	}

	public boolean isSolid() {
		return solid;
	}

	public Block setSolid(boolean solid) {
		this.solid = solid;
		return this;
	}

	public boolean isSwitchable() {
		return switchable;
	}

	public Block setSwitchable(boolean switchable) {
		this.switchable = switchable;
		return this;
	}

	public boolean isLever() {
		return lever;
	}

	public Block setLever(boolean lever) {
		this.lever = lever;
		return this;
	}

	public Block getOccupiedByBlock() {
		return occupiedByBlock;
	}

	public boolean isOcupied() {
		return ocupied;
	}

	public Block setOcupied(boolean ocupied) {
		this.ocupied = ocupied;
		return this;
	}

	public Block setOccupiedByBlock(Block occupiedByBlock) {
		this.occupiedByBlock = occupiedByBlock;
		return this;
	}

	public boolean isDestroyable() {
		return destroyable;
	}

	public Block setDestroyable(boolean destroyable) {
		this.destroyable = destroyable;
		return this;
	}

	public Item getKeptItem() {
		return keptItem;
	}

	public void setKeptItem(Item keptItem) {
		this.keptItem = keptItem;
	}

	public boolean isRegister() {
		return register;
	}

	public Block setRegister(boolean register) {
		this.register = register;
		return this;
	}

	public Sound getSound() {
		return sound;
	}

	public Block setSound(Sound sound) {
		this.sound = sound;
		this.bSound = true;
		return this;
	}

	public boolean hasSound() {
		return bSound;
	}

	public void playSound() {
		if (hasSound()) {
			sound.play();
		}
	}

	public boolean canGetocupied() {
		return canGetocupied;
	}

	public Block setCanGetocupied(boolean canGetocupied) {
		this.canGetocupied = canGetocupied;
		return this;
	}

	public void init() {
		if (this.isRegister()) {
			Blocks.registerBlock(this);
		}
	}

	public void toggle() {
	}

	public Block onConfigLoaded(int x, int y, int stageId, ArrayList<String> cfgLines, Stage stage) {
		return this;
	}

	public void onWalk(int x, int y, Level l) {
		// System.out.println(this.toString());
		Item item = this.getKeptItem();
		if (item != null) {
			boolean pickup = Game.getPlayer().getInventory().addItem(item);
			if (pickup) {
				Game.sendMessage("Item picked up : " + item.getName());
				this.setKeptItem(null);
			}
		}
	}

	public void onPush(int oldX, int oldY, int newX, int newY, int side, Level l) {
	}

	public void update() {
	}

	public void set() {
	}

	public void reset() {
	}

	public void onOccupied(boolean o, Level l) {
	}

	public void onBlockActivated(Stage stage, Player p) {
	}

	public Block copy(Block c) {
		try {
			return (Block) c.clone();
		} catch (CloneNotSupportedException err) {
			throw new RuntimeException(err);
		}
	}

	public void onDestroy() {

	}

}
