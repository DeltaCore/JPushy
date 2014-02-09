package JPushy.Types.ProgammingRelated;

import JPushy.Types.Blocks.Block;

public class BlockList {

	Block[][]	blocks;
	boolean		set	= false;

	public BlockList() {
		set = false;
	}

	public BlockList(int x, int y) {
		blocks = new Block[y][x];
	}

	public void init(int x, int y) {
		blocks = new Block[y][x];
		set = true;
	}

	public Block[][] getBlocks() {
		if (set)
			return blocks;
		return null;
	}

	public void setBlock(Block[][] block) {
		if (set)
			this.blocks = block;
	}

	public void setBlock(int x, int y, Block b) {
		if (set)
			blocks[y][x] = b;
	}

}
