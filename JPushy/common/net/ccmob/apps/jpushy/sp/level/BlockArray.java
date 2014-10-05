package net.ccmob.apps.jpushy.sp.level;

import java.awt.Graphics2D;
import java.util.ArrayList;

import net.ccmob.apps.jpushy.blocks.Block;
import net.ccmob.apps.jpushy.blocks.Blocks;
import net.ccmob.apps.jpushy.utils.Coord2D;

public class BlockArray {

	private int	               width, height;

	private ArrayList<Block>	 blocks	  = new ArrayList<Block>();
	private ArrayList<Coord2D>	options	= new ArrayList<Coord2D>();

	// wallHori = 2;
	// wallVert = 3;
	// wallCornerrd = 9;
	// wallCornerld = 10;
	// wallCornerul = 11;
	// wallCornerur = 12;

	private void buildWall() {
		for (int y = 0; y < this.getHeight(); y++) {
			for (int x = 0; x < this.getWidth(); x++) {
				this.setBlock(x, y, Blocks.air);
			}
		}
		for (int x = 0; x < this.getWidth(); x++) {
			this.setBlock(x, 0, Blocks.getBlockById(2));
			this.setBlock(x, this.getHeight() - 1, Blocks.getBlockById(2));
		}
		for (int y = 0; y < this.getHeight(); y++) {
			this.setBlock(0, y, Blocks.getBlockById(3));
			this.setBlock(this.getWidth() - 1, y, Blocks.getBlockById(3));
		}
		this.setBlock(0, 0, Blocks.getBlockById(9));
		this.setBlock(0, this.getHeight() - 1, Blocks.getBlockById(12));
		this.setBlock(this.getWidth() - 1, 0, Blocks.getBlockById(10));
		this.setBlock(this.getWidth() - 1, this.getHeight() - 1, Blocks.getBlockById(11));

		System.out.println("Layer content :");
		String line = "+";
		for (int y = 0; y < this.getHeight(); y++) {

			for (int i = 0; i < this.getWidth(); i++) {
				line += "--+";
			}
			System.out.println(line);
			System.out.print("|");
			for (int x = 0; x < this.getWidth(); x++) {
				if (this.getBlock(x, y).getId() < 10) {
					System.out.print(" ");
				}
				System.out.print(this.getBlock(x, y).getId());
				System.out.print("|");
			}
			System.out.println();
			line = "+";
		}
		for (int i = 0; i < this.getWidth(); i++) {
			line += "--+";
		}
		System.out.println(line);
	}

	public BlockArray() {
		this.setHeight(10);
		this.setWidth(10);
		initArray();
		buildWall();
	}

	public void initArray() {
		for (int i = 0; i < this.getHeight() * this.getWidth(); i++) {
			this.getBlocks().add(Blocks.air);
			this.getOptions().add(new Coord2D(-1, -1));
		}
	}

	public BlockArray(int width, int height) {
		this.setWidth(width);
		this.setHeight(height);
		initArray();
		buildWall();
	}

	public BlockArray(int width, int height, int dummy) {
		this.setWidth(width);
		this.setHeight(height);
	}

	private ArrayList<Block> getBlocks() {
		return blocks;
	}

	public void setBlocks(ArrayList<Block> blocks) {
		this.blocks = blocks;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Block getBlock(int x, int y) {
		if ((x <= this.getWidth() && x >= 0) && (y <= this.getHeight() && y >= 0)) {
			return this.getBlocks().get(this.getWidth() * y + x);
		} else {
			return null;
		}
	}

	public Coord2D getOption(int x, int y) {
		if ((x <= this.getWidth() && x >= 0) && (y <= this.getHeight() && y >= 0)) {
			return this.getOptions().get(this.getWidth() * y + x);
		} else {
			return null;
		}
	}

	public void setBlock(int x, int y, Block block) {
		if ((x <= this.getWidth() && x >= 0) && (y <= this.getHeight() && y >= 0)) {
			this.getBlocks().set(this.getWidth() * y + x, block);
		} else {
			return;
		}
	}

	public void setOption(int x, int y, Coord2D c) {
		if ((x <= this.getWidth() && x >= 0) && (y <= this.getHeight() && y >= 0)) {
			this.getOptions().set(this.getWidth() * y + x, c);
		} else {
			return;
		}
	}

	public ArrayList<Coord2D> getOptions() {
		return options;
	}

	public void setOptions(ArrayList<Coord2D> options) {
		this.options = options;
	}

	public void render(Graphics2D g) {
		for (int x = 0; x < this.getWidth(); x++) {
			for (int y = 0; y < this.getHeight(); y++) {
				if (this.getBlock(x, y) != null) {
					if (this.getBlock(x, y).getId() != Blocks.air.getId())
						this.getBlock(x, y).getRenderHandler().renderBlock(this.getBlock(x,y), g, x, y);
						//g.drawImage(this.getBlock(x, y).getTexture().getImg(), x * 40, y * 40, 40, 40, null);
					g.drawRect(x * 40, y * 40, 40, 40);
				}
			}
		}
	}
}
