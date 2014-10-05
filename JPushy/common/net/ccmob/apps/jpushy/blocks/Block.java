package net.ccmob.apps.jpushy.blocks;

import java.util.ArrayList;

import net.ccmob.apps.jpushy.core.Game;
import net.ccmob.apps.jpushy.graphics.Picture;
import net.ccmob.apps.jpushy.graphics.handler.RenderHandler;
import net.ccmob.apps.jpushy.items.Item;
import net.ccmob.apps.jpushy.items.Items;
import net.ccmob.apps.jpushy.sp.level.Level;
import net.ccmob.apps.jpushy.sp.level.Stage;
import net.ccmob.apps.jpushy.sp.player.Inventory;
import net.ccmob.apps.jpushy.sp.player.Player;

/**
 * 
 * @author Marcel Benning
 * 
 */
public class Block extends Object implements Cloneable {

	private int	    id;
	private Picture	texture;
	private String	name;
	private boolean	playerAbleToWalkOn;
	private boolean	solid;
	private boolean	visible;
	private Block	  invincebleBlock;
	private boolean	switchable;
	private boolean	lever;
	private boolean	ocupied;
	private boolean	destroyable;
	private boolean	register;
	private Block	  occupiedByBlock;
	private Item	  keptItem	     = Items.noitem;
	private boolean	canGetocupied	 = false;
	private boolean	movable	       = false;
	private boolean	optionRequired	= false;
	private int	    x	             = 0;
	private int	    y	             = 0;
	private RenderHandler	renderHandler = RenderHandler.defaultHandler;
	
	public Block(Block b) {
		this.id = b.getId();
		this.texture = b.getTexture();
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
		this.texture = img;
		this.name = name;
		this.playerAbleToWalkOn = true;
		this.solid = true;
		this.visible = true;
		this.invincebleBlock = Blocks.air;
		this.destroyable = false;
		this.register = register;
		this.keptItem = Items.noitem;
		this.init();
	}

	@Override
	public String toString() {
		return this.name + "[" + this.id + "];";
		// return this.name + "[" + this.id + "]:" + super.toString() + ";";
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *          the id to set
	 */
	public Block setId(int id) {
		this.id = id;
		return this;
	}

	/**
	 * @return the texture
	 */
	public Picture getTexture() {
		return texture;
	}

	/**
	 * @param texture
	 *          the texture to set
	 */
	public Block setTexture(Picture texture) {
		this.texture = texture;
		return this;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *          the name to set
	 */
	public Block setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * @return the playerAbleToWalkOn
	 */
	public boolean isPlayerAbleToWalkOn() {
		return playerAbleToWalkOn;
	}

	/**
	 * @param playerAbleToWalkOn
	 *          the playerAbleToWalkOn to set
	 */
	public Block setPlayerAbleToWalkOn(boolean playerAbleToWalkOn) {
		this.playerAbleToWalkOn = playerAbleToWalkOn;
		return this;
	}

	/**
	 * @return the solid
	 */
	public boolean isSolid() {
		return solid;
	}

	/**
	 * @param solid
	 *          the solid to set
	 */
	public Block setSolid(boolean solid) {
		this.solid = solid;
		return this;
	}

	/**
	 * @return the visible
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * @param visible
	 *          the visible to set
	 */
	public Block setVisible(boolean visible) {
		this.visible = visible;
		return this;
	}

	/**
	 * @return the invincebleBlock
	 */
	public Block getInvincebleBlock() {
		return invincebleBlock;
	}

	/**
	 * @param invincebleBlock
	 *          the invincebleBlock to set
	 */
	public Block setInvincebleBlock(Block invincebleBlock) {
		this.invincebleBlock = invincebleBlock;
		return this;
	}

	/**
	 * @return the switchable
	 */
	public boolean isSwitchable() {
		return switchable;
	}

	/**
	 * @param switchable
	 *          the switchable to set
	 */
	public Block setSwitchable(boolean switchable) {
		this.switchable = switchable;
		return this;
	}

	/**
	 * @return the lever
	 */
	public boolean isLever() {
		return lever;
	}

	/**
	 * @param lever
	 *          the lever to set
	 */
	public void setLever(boolean lever) {
		this.lever = lever;
	}

	/**
	 * @return the ocupied
	 */
	public boolean isOcupied() {
		return ocupied;
	}

	/**
	 * @param ocupied
	 *          the ocupied to set
	 */
	public Block setOcupied(boolean ocupied) {
		this.ocupied = ocupied;
		return this;
	}

	/**
	 * @return the destroyable
	 */
	public boolean isDestroyable() {
		return destroyable;
	}

	/**
	 * @param destroyable
	 *          the destroyable to set
	 */
	public Block setDestroyable(boolean destroyable) {
		this.destroyable = destroyable;
		return this;
	}

	/**
	 * @return the register
	 */
	public boolean isRegister() {
		return register;
	}

	/**
	 * @param register
	 *          the register to set
	 */
	public Block setRegister(boolean register) {
		this.register = register;
		return this;
	}

	/**
	 * @return the occupiedByBlock
	 */
	public Block getOccupiedByBlock() {
		return occupiedByBlock;
	}

	/**
	 * @param occupiedByBlock
	 *          the occupiedByBlock to set
	 */
	public Block setOccupiedByBlock(Block occupiedByBlock) {
		this.occupiedByBlock = occupiedByBlock;
		return this;
	}

	/**
	 * @return the keptItem
	 */
	public Item getKeptItem() {
		return keptItem;
	}

	/**
	 * @param keptItem
	 *          the keptItem to set
	 */
	public Block setKeptItem(Item keptItem) {
		this.keptItem = keptItem;
		return this;
	}

	/**
	 * @return the canGetocupied
	 */
	public boolean isCanGetocupied() {
		return canGetocupied;
	}

	/**
	 * @param canGetocupied
	 *          the canGetocupied to set
	 */
	public Block setCanGetocupied(boolean canGetocupied) {
		this.canGetocupied = canGetocupied;
		return this;
	}

	/**
	 * @return the movable
	 */
	public boolean isMovable() {
		return movable;
	}

	/**
	 * @param movable
	 *          the movable to set
	 */
	public Block setMovable(boolean movable) {
		this.movable = movable;
		return this;
	}

	/**
	 * @return the optionRequired
	 */
	public boolean isOptionRequired() {
		return optionRequired;
	}

	/**
	 * @param optionRequired
	 *          the optionRequired to set
	 */
	public Block setOptionRequired(boolean optionRequired) {
		this.optionRequired = optionRequired;
		return this;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x
	 *          the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y
	 *          the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the renderHandler
	 */
	public RenderHandler getRenderHandler() {
		return renderHandler;
	}

	/**
	 * @param renderHandler the renderHandler to set
	 */
	public void setRenderHandler(RenderHandler renderHandler) {
		this.renderHandler = renderHandler;
	}

	public void init() {
		if (this.isRegister()) {
			Blocks.registerBlock(this);
		}
	}

	public void afterInit(Stage s) {
	}

	public void toggle() {
	}

	public Block onConfigLoaded(int x, int y, int stageId, ArrayList<String> cfgLines, Stage stage) {
		return this;
	}

	public void onWalk(int x, int y, Level l) {
		Item item = this.getKeptItem();
		if (item != null) {
			boolean pickup = Game.getPlayer().getInventory().addItem(item);
			if (pickup) {
				Game.sendMessage("Item picked up : " + item.getName());
				this.setKeptItem(null);
			}
		}
	}

	public void onLoaded(int x, int y, int stageId, Stage stage) {
		this.setX(x);
		this.setY(y);
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

	public void onBlockPushedOnMe(Block b, Level l) {
	}

	public Block copy() {
		try {
			return (Block) this.clone();
		} catch (CloneNotSupportedException err) {
			throw new RuntimeException(err);
		}
	}

	public void onDestroy() {
	}

	public void onSpecialAction() {
	}
	
}
