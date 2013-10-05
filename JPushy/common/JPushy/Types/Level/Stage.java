package JPushy.Types.Level;

import JPushy.Player;
import JPushy.Types.Blocks.Block;
/**
 * 
 * @author Marcel Benning
 * 
 */
public class Stage {

	private int id = 0;
	private int homeX = 0, homeY = 0;
	private Block[][] blocks;
	
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
	
	public void setBlock(int x, int y, Block b){
		blocks[y][x] = b;
	}
	
	public Block getBlock(int x, int y){
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
	
	public void init(Player p){
		p.setX(homeX);
		p.setY(homeY);
	}
	
}
