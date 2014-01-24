package JPushy.Types.Level;

import JPushy.Types.Blocks.Block;
import JPushy.Types.Blocks.Blocks;
import JPushy.Types.Level.LV.LVDataList;
import JPushy.Types.Player.Player;

/**
 * 
 * @author Marcel Benning
 * 
 */
public class Stage {

	private int	          id	          = 0;
	private int	          homeX	        = 0, homeY = 0;
	private Block[][]	    blocks;
	private Block[][]	    moveableBlocks;

	private Winconditions	winConditions	= new Winconditions();
	private LVDataList	  dataList	    = new LVDataList();

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
		for (int y = 0; y < this.getBlocks().length; y++) {
			for (int x = 0; x < this.getBlocks()[0].length; x++) {
				this.getBlock(x, y).afterInit(this);
			}
		}
	}

	public void destroyBlock(int x, int y) {
		this.setBlock(x, y, Blocks.getBlockById(Blocks.air.getId()));
	}

	/**
	 * @return the winConditions
	 */
	public Winconditions getWinConditions() {
		return winConditions;
	}

	/**
	 * @param winConditions
	 *          the winConditions to set
	 */
	public void setWinConditions(Winconditions winConditions) {
		this.winConditions = winConditions;
	}

	/**
	 * @return the dataList
	 */
	public LVDataList getDataList() {
		return dataList;
	}

	/**
	 * @param dataList
	 *          the dataList to set
	 */
	public void setDataList(LVDataList dataList) {
		this.dataList = dataList;
	}

}
