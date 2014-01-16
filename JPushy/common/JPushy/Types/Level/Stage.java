package JPushy.Types.Level;

import JPushy.Types.Blocks.Block;
import JPushy.Types.Blocks.Blocks;
import JPushy.Types.Player.Player;

/**
 * 
 * @author Marcel Benning
 * 
 */
public class Stage {

	private Winconditions	winconditions	= new Winconditions();

	private int	          id	          = 0;
	private int	          homeX	        = 0, homeY = 0;
	private Block[][]	    blocks;
	private Block[][]	    moveableBlocks;

	public Stage(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Block[][] getBlocks() {
		return blocks;
	}

	public void setBlocks(Block[][] blocks) {
		this.blocks = blocks;
	}

	public void setBlock(int x, int y, Block b) {
		blocks[y][x] = b;
	}

	public Block getBlock(int x, int y) {
		return blocks[y][x];
	}

	public int getHomeX() {
		return homeX;
	}

	public void setHomeX(int homeX) {
		this.homeX = homeX;
	}

	public int getHomeY() {
		return homeY;
	}

	public void setHomeY(int homeY) {
		this.homeY = homeY;
	}

	public Block[][] getMoveableBlocks() {
		return moveableBlocks;
	}

	public void setMoveableBlocks(Block[][] moveableBlocks) {
		this.moveableBlocks = moveableBlocks;
	}

	public void setMoveableBlock(Block b, int x, int y) {
		this.getMoveableBlocks()[y][x] = b;
	}

	public Block getMoveableBlock(int x, int y) {
		return this.getMoveableBlocks()[y][x];
	}

	public void init(Player p) {
		p.setX(homeX);
		p.setY(homeY);
	}

	public void destroyBlock(int x, int y) {
		this.setBlock(x, y, Blocks.getBlockById(Blocks.air.getId()));
	}

	/**
	 * @return the winconditions
	 */
	public Winconditions getWinconditions() {
		return winconditions;
	}

	/**
	 * @param winconditions
	 *          the winconditions to set
	 */
	public void setWinconditions(Winconditions winconditions) {
		this.winconditions = winconditions;
	}

}
