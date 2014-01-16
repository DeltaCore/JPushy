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
		switch (dir) {
			case 0:
				dirNorth();
				break;
			case 1:
				dirEast();
				break;
			case 2:
				dirSouth();
				break;
			case 3:
				dirWest();
				break;
		}
		this.setCancelnextmove(false);
	}

	public void dirNorth() {
		if (y - 1 < 0) {
			return;
		}
		Block b = this.thread.getLevel().getActiveStage().getBlock(x, y - 1);
		Block mb = this.thread.getLevel().getActiveStage().getMoveableBlock(x, y - 1);
		if (mb != null) {
			if (!mb.isSolid()) {
				if (isSpaceAt(x, y - 2)) {
					mb.onPush(x, y - 1, x, y - 2, 0, this.thread.getLevel());
					this.thread.getLevel().moveBlockTo(x, y - 1, 0);
					y -= 1;
					b.onWalk(x, y, this.thread.getLevel());
				}
			}
		} else {
			if (b.isPlayerAbleToWalkOn()) {
				y -= 1;
				b.onWalk(x, y, this.thread.getLevel());
			}
		}
	}

	public void dirEast() {
		if (x + 1 > this.thread.getLevel().getActiveStage().getBlocks()[0].length) {
			return;
		}
		Block b = this.thread.getLevel().getActiveStage().getBlock(x + 1, y);
		Block mb = this.thread.getLevel().getActiveStage().getMoveableBlock(x + 1, y);
		if (mb != null) {
			if (!mb.isSolid()) {
				if (isSpaceAt(x + 2, y)) {
					mb.onPush(x + 1, y, x + 2, y, 1, this.thread.getLevel());
					this.thread.getLevel().moveBlockTo(x + 1, y, 1);
					x += 1;
					b.onWalk(x, y, this.thread.getLevel());
				}
			}
		} else {
			if (b.isPlayerAbleToWalkOn()) {
				x += 1;
				b.onWalk(x, y, this.thread.getLevel());
			}
		}
	}

	public void dirSouth() {
		if (y + 1 > this.thread.getLevel().getActiveStage().getBlocks().length) {
			return;
		}
		Block b = this.thread.getLevel().getActiveStage().getBlock(x, y + 1);
		Block mb = this.thread.getLevel().getActiveStage().getMoveableBlock(x, y + 1);
		if (mb != null) {
			if (!mb.isSolid()) {
				if (isSpaceAt(x, y + 2)) {
					mb.onPush(x, y + 1, x, y + 2, 2, this.thread.getLevel());
					this.thread.getLevel().moveBlockTo(x, y + 1, 2);
					y += 1;
					b.onWalk(x, y, this.thread.getLevel());
				}
			}
		} else {
			if (b.isPlayerAbleToWalkOn()) {
				y += 1;
				b.onWalk(x, y, this.thread.getLevel());
			}
		}
	}

	public void dirWest() {
		if (x - 1 < 0) {
			return;
		}
		Block b = this.thread.getLevel().getActiveStage().getBlock(x - 1, y);
		Block mb = this.thread.getLevel().getActiveStage().getMoveableBlock(x - 1, y);
		if (mb != null) {
			if (!mb.isSolid()) {
				if (isSpaceAt(x - 2, y)) {
					mb.onPush(x - 1, y, x - 2, y, 3, this.thread.getLevel());
					this.thread.getLevel().moveBlockTo(x - 1, y, 3);
					x -= 1;
					b.onWalk(x, y, this.thread.getLevel());
				}
			}
		} else {
			if (b.isPlayerAbleToWalkOn()) {
				x -= 1;
				b.onWalk(x, y, this.thread.getLevel());
			}
		}
	}

	public boolean isSpaceAt(int x, int y) {
		Block b = this.thread.getLevel().getActiveStage().getBlock(x, y);
		Block mb = this.thread.getLevel().getActiveStage().getMoveableBlock(x, y);
		if (mb != null) {
			System.out.println("MB : " + mb.toString());
		} else {
			System.out.println("EMPTY !");
		}
		if (mb != null)
			return false;
		if (!b.isPlayerAbleToWalkOn())
			return false;
		if (!b.isCanGetocupied())
			return false;

		return true;
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
