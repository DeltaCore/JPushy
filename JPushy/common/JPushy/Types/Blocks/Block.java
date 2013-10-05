package JPushy.Types.Blocks;

import java.awt.Image;

import JPushy.Blocks;
import JPushy.Types.Picture;
import JPushy.Types.Level.Level;
/**
 * 
 * @author Marcel Benning
 * 
 */
public class Block {

	private int id;
	private Picture img;
	private String name;
	private boolean playerAbleToWalkOn;
	private boolean solid;
	private boolean visible;
	private Block invincebleBlock;
	private boolean switchable;
	private boolean lever;
	private boolean ocupied;
	private Block occupiedByBlock;
	
	public Block(Block b){
		this.id = b.getId();
		this.img = b.getTexture();
		this.name = b.getName();
		this.playerAbleToWalkOn = b.isPlayerAbleToWalkOn();
		this.solid = b.isSolid();
		this.visible = b.isVisible();
		this.invincebleBlock = b.getInvincebleBlock();
		this.switchable = b.isSwitchable();
	}
	
	public Block(String name, int id, Picture img) {
		this(name, id, img, true);
	}

	public Block(String name, int id, Picture img, boolean visible) {
		this(name, id, img, true, false, visible);
	}

	public Block(String name, int id, Picture img, boolean playerAbleToWalkOn,
			boolean visible) {
		this(name, id, img, playerAbleToWalkOn, true, visible);
	}

	public Block(String name, int id, Picture img, boolean playerAbleToWalkOn,
			boolean solid, boolean visible) {
		this(name, id, img, playerAbleToWalkOn, solid, visible, Blocks.air);
	}

	public Block(String name, int id, Picture img, boolean playerAbleToWalkOn,
			boolean solid, boolean visible, Block invincebleBlock) {
		this(name, id, img, playerAbleToWalkOn, solid, visible, Blocks.air, true);
	}

	public Block(String name, int id, Picture img,
			boolean playerAbleToWalkOn, boolean solid, boolean visible,
			Block invincebleBlock, boolean register) {
		this.id = id;
		this.img = img;
		this.name = name;
		this.playerAbleToWalkOn = playerAbleToWalkOn;
		this.solid = solid;
		this.visible = visible;
		this.invincebleBlock = invincebleBlock;
		if(register){
			Blocks.registerBlock(this);
		}
		this.init();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Picture getTexture() {
		return img;
	}

	public void setTexture(Picture img) {
		this.img = img;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name + ":" + this.id;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Block getInvincebleBlock() {
		return invincebleBlock;
	}

	public void setInvincebleBlock(Block invincebleBlock) {
		this.invincebleBlock = invincebleBlock;
	}

	/* Interact methods */

	public boolean isPlayerAbleToWalkOn() {
		return playerAbleToWalkOn;
	}

	public void setPlayerAbleToWalkOn(boolean playerAbleToWalkOn) {
		this.playerAbleToWalkOn = playerAbleToWalkOn;
	}

	public boolean isSolid() {
		return solid;
	}

	public void setSolid(boolean solid) {
		this.solid = solid;
	}

	public boolean isSwitchable() {
		return switchable;
	}

	public void setSwitchable(boolean switchable) {
		this.switchable = switchable;
	}
	
	public boolean isLever() {
		return lever;
	}

	public void setLever(boolean lever) {
		this.lever = lever;
	}

	public Block getOccupiedByBlock() {
		return occupiedByBlock;
	}

	public boolean isOcupied() {
		return ocupied;
	}

	public void setOcupied(boolean ocupied) {
		this.ocupied = ocupied;
	}

	public void setOccupiedByBlock(Block occupiedByBlock) {
		this.occupiedByBlock = occupiedByBlock;
	}

	public void init(){}
	
	public void toggle(){System.out.println("Toggle native");}

	public void onWalk(int x, int y, Level l) {}

	public void onPush(int oldX, int oldY, int newX, int newY, int side, Level l) {}
	
	public void update(){}
	
	public void set(){}
	
	public void reset(){}
	
	public void onOccupied(boolean o, Level l){}
	
}
