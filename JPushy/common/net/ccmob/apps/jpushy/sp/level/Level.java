package net.ccmob.apps.jpushy.sp.level;

import java.awt.Dimension;
import java.util.ArrayList;

import net.ccmob.apps.jpushy.blocks.Block;
import net.ccmob.apps.jpushy.blocks.Blocks;
import net.ccmob.apps.jpushy.core.Game;
import net.ccmob.apps.jpushy.graphics.PictureLoader;
import net.ccmob.apps.jpushy.sp.player.Player;
import net.ccmob.apps.jpushy.utils.Coord2D;

/**
 * 
 * @author Marcel Benning
 * 
 */
public class Level {

	private int	              activeStage	= 0;
	private ArrayList<Stage>	stages	    = new ArrayList<Stage>();
	private String	          name	      = "", version = "";
	private ArrayList<String>	comment	    = new ArrayList<String>();
	private String	          fileName	  = "";

	public Level(String filename) {
		this.setFileName(filename);
	}

	@Override
	public String toString() {
		String ret = "";
		for (int s = 0; s < stages.size(); s++) {
			ret += "[Stage : " + stages.get(s).getId() + "]";
			for (int i = 0; i < stages.get(s).getBlocks()[0].length; i++) {
				for (int j = 0; j < stages.get(s).getBlocks().length; j++) {
					ret += stages.get(s).getBlocks()[i][j].toString() + ";";
				}
				ret += String.format("%n");
			}
			ret += String.format("%n");
		}
		return ret;
	}

	public void update() {

	}

	public Dimension getSize(int lines, int margin, int fontsize) {
		int addMargin = lines * (fontsize + margin) + margin;
		int width = 0;
		int height = 0;
		for (int i = 0; i < stages.get(activeStage).getBlocks().length; i++) {
			if (stages.get(activeStage).getBlocks()[i].length * 40 >= width)
				width = stages.get(activeStage).getBlocks()[i].length * 40;
			height++;
		}
		width += 16;
		return new Dimension(width, height * 40 + 39 + addMargin);
	}

	public void moveBlockTo(int x, int y, int dir) {
		switch (dir) {
			case 0:
				stages.get(activeStage).setMoveableBlock(stages.get(activeStage).getMoveableBlock(x, y).copy(), x, y - 1);
				stages.get(activeStage).getBlock(x, y - 1).setOcupied(true);
				stages.get(activeStage).getBlock(x, y - 1).setOccupiedByBlock(stages.get(activeStage).getMoveableBlock(x, y));
				stages.get(activeStage).setMoveableBlock(null, x, y);
				stages.get(activeStage).getBlock(x, y).setOcupied(false);
				stages.get(activeStage).getBlock(x, y).onOccupied(false, this);
				stages.get(activeStage).getBlock(x, y - 1).onOccupied(true, this);
				break;
			case 1:
				stages.get(activeStage).setMoveableBlock(stages.get(activeStage).getMoveableBlock(x, y).copy(), x + 1, y);
				stages.get(activeStage).getBlock(x + 1, y).setOcupied(true);
				stages.get(activeStage).getBlock(x + 1, y).setOccupiedByBlock(stages.get(activeStage).getMoveableBlock(x, y));
				stages.get(activeStage).setMoveableBlock(null, x, y);
				stages.get(activeStage).getBlock(x, y).setOcupied(false);
				stages.get(activeStage).getBlock(x, y).onOccupied(false, this);
				stages.get(activeStage).getBlock(x + 1, y).onOccupied(true, this);
				break;
			case 2:
				stages.get(activeStage).setMoveableBlock(stages.get(activeStage).getMoveableBlock(x, y).copy(), x, y + 1);
				stages.get(activeStage).getBlock(x, y + 1).setOcupied(true);
				stages.get(activeStage).getBlock(x, y + 1).setOccupiedByBlock(stages.get(activeStage).getMoveableBlock(x, y));
				stages.get(activeStage).setMoveableBlock(null, x, y);
				stages.get(activeStage).getBlock(x, y).setOcupied(false);
				stages.get(activeStage).getBlock(x, y).onOccupied(false, this);
				stages.get(activeStage).getBlock(x, y + 1).onOccupied(true, this);
				break;
			case 3:
				stages.get(activeStage).setMoveableBlock(stages.get(activeStage).getMoveableBlock(x, y).copy(), x - 1, y);
				stages.get(activeStage).getBlock(x - 1, y).setOcupied(true);
				stages.get(activeStage).getBlock(x - 1, y).setOccupiedByBlock(stages.get(activeStage).getMoveableBlock(x, y));
				stages.get(activeStage).setMoveableBlock(null, x, y);
				stages.get(activeStage).getBlock(x, y).setOcupied(false);
				stages.get(activeStage).getBlock(x, y).onOccupied(false, this);
				stages.get(activeStage).getBlock(x - 1, y).onOccupied(true, this);
				break;
		}
	}

	public boolean moveBlock(int x, int y, int dir) {
		Block b = stages.get(activeStage).getBlocks()[y][x];
		// System.out.println(b.toString() + ":" + b.isOcupied());
		if (b.isSolid()) {
			return false;
		} else {
			Block t = new Block("Dummy", -1, PictureLoader.loadImageFromFile("base.png"), false);
			if (dir == 0) {
				t = stages.get(activeStage).getBlocks()[y - 1][x];
			} else if (dir == 1) {
				t = stages.get(activeStage).getBlocks()[y][x + 1];
			} else if (dir == 2) {
				t = stages.get(activeStage).getBlocks()[y + 1][x];
			} else if (dir == 3) {
				t = stages.get(activeStage).getBlocks()[y][x - 1];
			}
			// ystem.out.println(t.toString());
			if (t.getId() == Blocks.air.getId()) {// Sides : 0 = north ; 1 =
				// east ; 2 = south ; 3 =
				// west
				if (dir == 0) {
					stages.get(activeStage).setBlock(x, y - 1, b);
					stages.get(activeStage).setBlock(x, y, Blocks.air);
					return true;
				} else if (dir == 1) {
					stages.get(activeStage).setBlock(x + 1, y, b);
					stages.get(activeStage).setBlock(x, y, Blocks.air);
					return true;
				} else if (dir == 2) {
					stages.get(activeStage).setBlock(x, y + 1, b);
					stages.get(activeStage).setBlock(x, y, Blocks.air);
					return true;
				} else if (dir == 3) {
					stages.get(activeStage).setBlock(x - 1, y, b);
					stages.get(activeStage).setBlock(x, y, Blocks.air);
					return true;
				} else {
					return false;
				}
			} else {
				if (t.getId() == -1) {
					return false;
				}
			}
		}
		return false;
	}

	public void registerStage(Stage stage) {
		if (stage.getId() == 0) {
			stages.add(stage);
		} else {
			if (!checkStageId(stage)) {
				stages.add(stage);
				System.out.println("[Level] Loaded new stage with id : " + stage.getId());
			}
		}
	}

	private boolean checkStageId(Stage stage) {
		for (int i = 0; i < stages.size(); i++) {
			if (stages.get(i).getId() == stage.getId()) {
				return true;
			}
		}
		return false;
	}

	public int getActiveStageI() {
		return activeStage;
	}

	public void setActiveStage(int activeStage) {
		this.activeStage = activeStage;
	}

	public Stage getActiveStage() {
		return stages.get(activeStage);
	}

	public Coord2D getHomeFromStageId(int id) {
		return new Coord2D(stages.get(activeStage).getHomeX(), stages.get(activeStage).getHomeY());
	}

	public void setHomeFromStageId(int id, int x, int y) {
		for (int i = 0; i < stages.size(); i++) {
			if (stages.get(i).getId() == id) {
				stages.get(i).setHomeX(x);
				stages.get(i).setHomeY(y);
			}
		}
	}

	public boolean isLastStage() {
		if ((activeStage + 1) == stages.size()) {
			return true;
		} else {
			return false;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public ArrayList<String> getComment() {
		return comment;
	}

	public void setComment(ArrayList<String> comment) {
		this.comment = comment;
	}

	public void addComment(String s) {
		comment.add(s);
	}

	public void init(Player p) {
		for (String s : comment) {
			Game.sendMessage(s);
		}
		getActiveStage().init(p);
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public ArrayList<Stage> getStages() {
		return stages;
	}

	public Stage getStage(int index) {
		return stages.get(index);
	}

	public int getHeight() {
		return this.getActiveStage().getBlocks().length;
	}

	public int getWidth() {
		return this.getActiveStage().getBlocks()[0].length;
	}

}
