package JPushy.Types.ProgammingRelated;

import java.awt.Graphics2D;
import java.util.ArrayList;

import JPushy.Types.Blocks.Block;
import JPushy.Types.Blocks.Blocks;

public class BlockArray {

	private int	             width, height;

	private ArrayList<Block>	blocks	    = new ArrayList<Block>();

	private int	             wallHori	    = 2;
	private int	             wallVert	    = 3;
	private int	             wallCornerrd	= 9;
	private int	             wallCornerld	= 10;
	private int	             wallCornerul	= 11;
	private int	             wallCornerur	= 12;

	private void buildWall() {
		for (int y = 0; y < this.getHeight(); y++) {
			for (int x = 0; x < this.getWidth(); x++) {
				this.set(x, y, Blocks.air);
			}
		}
		for (int x = 0; x < this.getWidth(); x++) {
			this.set(x, 0, Blocks.getBlockById(2));
			this.set(x, this.getHeight() - 1, Blocks.getBlockById(2));
		}
		for (int y = 0; y < this.getHeight(); y++) {
			this.set(0, y, Blocks.getBlockById(3));
			this.set(this.getWidth() - 1, y, Blocks.getBlockById(3));
		}
		this.set(0, 0, Blocks.getBlockById(9));
		this.set(0, this.getHeight() - 1, Blocks.getBlockById(12));
		this.set(this.getWidth() - 1, 0, Blocks.getBlockById(10));
		this.set(this.getWidth() - 1, this.getHeight() - 1, Blocks.getBlockById(11));

		System.out.println("Layer content :");
		String line = "+";
		for (int y = 0; y < this.getHeight(); y++) {

			for (int i = 0; i < this.getWidth(); i++) {
				line += "--+";
			}
			System.out.println(line);
			System.out.print("|");
			for (int x = 0; x < this.getWidth(); x++) {
				if (this.get(x, y).getId() < 10) {
					System.out.print(" ");
				}
				System.out.print(this.get(x, y).getId());
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
		}
	}

	public BlockArray(int width, int height) {
		this.setWidth(width);
		this.setHeight(height);
		initArray();
		buildWall();
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

	public Block get(int x, int y) {
		if ((x <= this.getWidth() && x >= 0) && (y <= this.getHeight() && y >= 0)) {
			return this.getBlocks().get(this.getWidth() * y + x);
		} else {
			return null;
		}
	}

	public void set(int x, int y, Block block) {
		if ((x <= this.getWidth() && x >= 0) && (y <= this.getHeight() && y >= 0)) {
			this.getBlocks().set(this.getWidth() * y + x, block);
		} else {
			return;
		}
	}

	public void render(Graphics2D g) {
		for (int x = 0; x < this.getWidth(); x++) {
			for (int y = 0; y < this.getHeight(); y++) {
				if (this.get(x, y) != null) {
					g.drawImage(this.get(x, y).getTexture().getImg(), x * 40, y * 40, 40, 40, null);
				}
			}
		}
	}

}
