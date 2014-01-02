package JPushy.Types.Player;

import JPushy.Core.LevelThread;
import JPushy.Types.Blocks.Block;
import JPushy.Types.gfx.Picture;

/**
 * 
 * @author Marcel Benning
 * 
 */
public class Player {

	private int	        x	             = 1;
	private int	        y	             = 1;
	private LevelThread	thread;
	private Picture	    img;
	private boolean	    freezed;
	private String	    name	         = "Player";
	private Inventory	  inventory;
	private boolean	    cancelnextmove	= false;
	private int	        currentStage	 = 0;

	public Player(LevelThread levelThread, Picture img, String name) {
		this.thread = levelThread;
		this.img = img;
		this.name = name;
		this.inventory = new Inventory();
	}

	public void movePlayer(int dir) {// Sides : 0 = north ; 1 = east ; 2 = south ;
																	 // 3 = west
		if (isFreezed()) {
			setCancelnextmove(false);
			return;
		}
		Block bo = thread.getLevel().getActiveStage().getBlock(x, y);
		if (dir == 0 && checkNorthWalk()) {
			if (!isNextMoveCanceld()) {
				y -= 1;
				Block b = thread.getLevel().getActiveStage().getBlock(x, y);
				b.onWalk(x, y, thread.getLevel());
			}
		} else if (dir == 1 && checkEastWalk()) {
			if (!isNextMoveCanceld()) {
				x += 1;
				Block b = thread.getLevel().getActiveStage().getBlock(x, y);
				b.onWalk(x, y, thread.getLevel());
			}
		} else if (dir == 2 && checkSouthWalk()) {
			if (!isNextMoveCanceld()) {
				y += 1;
				Block b = thread.getLevel().getActiveStage().getBlock(x, y);
				b.onWalk(x, y, thread.getLevel());
			}
		} else if (dir == 3 && checkWestWalk()) {
			if (!isNextMoveCanceld()) {
				x -= 1;
				Block b = thread.getLevel().getActiveStage().getBlock(x, y);
				b.onWalk(x, y, thread.getLevel());
			}
		} else if (dir == 0 && checkNorthPush()) {
			Block b = thread.getLevel().getActiveStage().getBlock(x, y - 1);
			b.onPush(x, y - 1, x, y - 2, 2, thread.getLevel());
			if (thread.getLevel().moveBlock(x, y - 1, dir)) {
				if (!isNextMoveCanceld())
					y -= 1;
			}
		} else if (dir == 1 && checkEastPush()) {
			Block b = thread.getLevel().getActiveStage().getBlock(x + 1, y);
			b.onPush(x + 1, y, x + 2, y, 3, thread.getLevel());
			if (thread.getLevel().moveBlock(x + 1, y, dir)) {
				if (!isNextMoveCanceld())
					x += 1;
			}
		} else if (dir == 2 && checkSouthPush()) {
			Block b = thread.getLevel().getActiveStage().getBlock(x, y + 1);
			b.onPush(x, y + 1, x, y + 2, 0, thread.getLevel());
			if (thread.getLevel().moveBlock(x, y + 1, dir)) {
				if (!isNextMoveCanceld())
					y += 1;
			}
		} else if (dir == 3 && checkWestPush()) {
			Block b = thread.getLevel().getActiveStage().getBlock(x - 1, y);
			if (thread.getLevel().moveBlock(x - 1, y, dir)) {
				if (!isNextMoveCanceld())
					x -= 1;
			}
		}
		this.setCancelnextmove(false);
	}

	public boolean checkNorthWalk() {
		if (y - 1 <= 0) {
			return false;
		} else {
			Block b = thread.getLevel().getActiveStage().getBlocks()[y - 1][x];
			return b.isPlayerAbleToWalkOn();
		}
	}

	public boolean checkEastWalk() {
		if (x + 1 > thread.getLevel().getActiveStage().getBlocks()[0].length) {
			return false;
		} else {
			Block b = thread.getLevel().getActiveStage().getBlocks()[y][x + 1];
			return b.isPlayerAbleToWalkOn();
		}
	}

	public boolean checkSouthWalk() {
		if (y + 1 > thread.getLevel().getActiveStage().getBlocks().length) {
			return false;
		} else {
			Block b = thread.getLevel().getActiveStage().getBlocks()[y + 1][x];
			return b.isPlayerAbleToWalkOn();
		}
	}

	public boolean checkWestWalk() {
		if (x - 1 <= 0) {
			return false;
		} else {
			Block b = thread.getLevel().getActiveStage().getBlocks()[y][x - 1];
			return b.isPlayerAbleToWalkOn();
		}
	}

	public boolean checkNorthPush() {
		if (y - 1 <= 0) {
			return false;
		} else {
			Block b = thread.getLevel().getActiveStage().getBlocks()[y - 1][x];
			return !b.isSolid();
		}
	}

	public boolean checkEastPush() {
		if (x + 1 > thread.getLevel().getActiveStage().getBlocks()[0].length) {
			return false;
		} else {
			Block b = thread.getLevel().getActiveStage().getBlocks()[y][x + 1];
			return !b.isSolid();
		}
	}

	public boolean checkSouthPush() {
		if (y + 1 > thread.getLevel().getActiveStage().getBlocks().length) {
			return false;
		} else {
			Block b = thread.getLevel().getActiveStage().getBlocks()[y + 1][x];
			return !b.isSolid();
		}
	}

	public boolean checkWestPush() {
		if (x - 1 <= 0) {
			return false;
		} else {
			Block b = thread.getLevel().getActiveStage().getBlocks()[y][x - 1];
			return !b.isSolid();
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Picture getTexture() {
		return img;
	}

	public void setTexture(Picture img) {
		this.img = img;
	}

	public boolean isFreezed() {
		return freezed;
	}

	public void setFreezed(boolean freezed) {
		this.freezed = freezed;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public boolean isNextMoveCanceld() {
		return cancelnextmove;
	}

	public void setCancelnextmove(boolean cancelnextmove) {
		this.cancelnextmove = cancelnextmove;
	}

	public void cancelNextMove() {
		setCancelnextmove(true);
	}

}
