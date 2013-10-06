package JPushy;

import JPushy.Types.Picture;
import JPushy.Types.Blocks.Block;
import JPushy.Types.Player.Inventory;

/**
 * 
 * @author Marcel Benning
 * 
 */
public class Player {

	private int	           x	  = 1;
	private int	           y	  = 1;
	private LevelScheduler	level;
	private Picture	       img;
	private boolean	       freezed;
	private String	       name	= "Player";
	private Inventory	     inventory;

	public Player(LevelScheduler l, Picture img, String name) {
		this.level = l;
		this.img = img;
		this.name = name;
		this.inventory = new Inventory();
	}

	public void movePlayer(int dir) {// Sides : 0 = north ; 1 = east ; 2 = south
		// ; 3 = west
		if (isFreezed()) {
			return;
		}
		// Just air
		Block bo = level.getLevel().getActiveStage().getBlock(x, y);
		if (dir == 0 && checkNorthWalk()) {
			Block b = level.getLevel().getActiveStage().getBlock(x, y - 1);
			b.onWalk(x, y - 1, level.getLevel());
			y -= 1;
		} else if (dir == 1 && checkEastWalk()) {
			Block b = level.getLevel().getActiveStage().getBlock(x + 1, y);
			b.onWalk(x + 1, y, level.getLevel());
			x += 1;
		} else if (dir == 2 && checkSouthWalk()) {
			Block b = level.getLevel().getActiveStage().getBlock(x, y + 1);
			b.onWalk(x, y + 1, level.getLevel());
			y += 1;
		} else if (dir == 3 && checkWestWalk()) {
			Block b = level.getLevel().getActiveStage().getBlock(x - 1, y);
			b.onWalk(x - 1, y, level.getLevel());
			x -= 1;
		} else if (dir == 0 && checkNorthPush()) {
			Block b = level.getLevel().getActiveStage().getBlock(x, y - 1);
			b.onPush(x, y - 1, x, y - 2, 2, level.getLevel());
			if (level.getLevel().moveBlock(x, y - 1, dir)) {
				y -= 1;
			}
		} else if (dir == 1 && checkEastPush()) {
			Block b = level.getLevel().getActiveStage().getBlock(x + 1, y);
			b.onPush(x + 1, y, x + 2, y, 3, level.getLevel());
			if (level.getLevel().moveBlock(x + 1, y, dir)) {
				x += 1;
			}

		} else if (dir == 2 && checkSouthPush()) {
			Block b = level.getLevel().getActiveStage().getBlock(x, y + 1);
			b.onPush(x, y + 1, x, y + 2, 0, level.getLevel());
			if (level.getLevel().moveBlock(x, y + 1, dir)) {
				y += 1;
			}

		} else if (dir == 3 && checkWestPush()) {
			Block b = level.getLevel().getActiveStage().getBlock(x - 1, y);
			if (level.getLevel().moveBlock(x - 1, y, dir)) {
				x -= 1;
			}

		}
		/*
		 * if(dir == 0){ if(level.notifyPlayerMove(x, y - 1, dir)){ y -= 1; } }else
		 * if(dir == 1){ if(level.notifyPlayerMove(x + 1, y, dir)){ x += 1; } }else
		 * if(dir == 2){ if(level.notifyPlayerMove(x, y + 1, dir)){ y += 1; } }else
		 * if(dir == 3){ if(level.notifyPlayerMove(x - 1, y, dir)){
		 * 
		 * } }
		 */
	}

	public boolean checkNorthWalk() {
		if (y - 1 <= 0) {
			return false;
		} else {
			Block b = level.getLevel().getActiveStage().getBlocks()[y - 1][x];
			return b.isPlayerAbleToWalkOn();
		}
	}

	public boolean checkEastWalk() {
		if (x + 1 > level.getLevel().getActiveStage().getBlocks()[0].length) {
			return false;
		} else {
			Block b = level.getLevel().getActiveStage().getBlocks()[y][x + 1];
			return b.isPlayerAbleToWalkOn();
		}
	}

	public boolean checkSouthWalk() {
		if (y + 1 > level.getLevel().getActiveStage().getBlocks().length) {
			return false;
		} else {
			Block b = level.getLevel().getActiveStage().getBlocks()[y + 1][x];
			return b.isPlayerAbleToWalkOn();
		}
	}

	public boolean checkWestWalk() {
		if (x - 1 <= 0) {
			return false;
		} else {
			Block b = level.getLevel().getActiveStage().getBlocks()[y][x - 1];
			return b.isPlayerAbleToWalkOn();
		}
	}

	public boolean checkNorthPush() {
		if (y - 1 <= 0) {
			return false;
		} else {
			Block b = level.getLevel().getActiveStage().getBlocks()[y - 1][x];
			return !b.isSolid();
		}
	}

	public boolean checkEastPush() {
		if (x + 1 > level.getLevel().getActiveStage().getBlocks()[0].length) {
			return false;
		} else {
			Block b = level.getLevel().getActiveStage().getBlocks()[y][x + 1];
			return !b.isSolid();
		}
	}

	public boolean checkSouthPush() {
		if (y + 1 > level.getLevel().getActiveStage().getBlocks().length) {
			return false;
		} else {
			Block b = level.getLevel().getActiveStage().getBlocks()[y + 1][x];
			return !b.isSolid();
		}
	}

	public boolean checkWestPush() {
		if (x - 1 <= 0) {
			return false;
		} else {
			Block b = level.getLevel().getActiveStage().getBlocks()[y][x - 1];
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

	public LevelScheduler getLevel() {
		return level;
	}

	public void setLevel(LevelScheduler level) {
		this.level = level;
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

}
