package JPushy.Types.Blocks;

import java.util.ArrayList;

import JPushy.Types.Level.Level;
import JPushy.Types.Level.LevelLoader;
import JPushy.Types.Level.Stage;
import JPushy.Types.gfx.Picture;

/**
 * 
 * @author Marcel Benning
 * 
 */
public class Button extends Block {

	public Button(String name, int id, Picture img) {
		super(name, id, img);
	}

	public Button(String string, int id, Picture loadImageFromFile, boolean b) {
		super(string, id, loadImageFromFile, b);
	}

	private int	x	= 0, y = 0;

	@Override
	public void init() {
		super.init();
		this.setLever(true);
	}

	@Override
	public void onOccupied(boolean o, Level l) {
		if (o) {
			Block b = l.getActiveStage().getBlock(x, y);
			b.set();
			l.getActiveStage().setBlock(x, y, b);
		} else {
			Block b = l.getActiveStage().getBlock(x, y);
			b.reset();
			l.getActiveStage().setBlock(x, y, b);
		}
	}

	public int getX() {
		return y;
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

	@Override
	public Block onConfigLoaded(int x, int y, int stageId, ArrayList<String> cfgLines, Stage stage) {
		int[] cfgCords = LevelLoader.checkCFGCords(cfgLines, stageId, x, y);
		if (cfgCords[0] == 0 && cfgCords[1] == 0) {
		} else {
			this.setX(cfgCords[0]);
			this.setY(cfgCords[1]);
		}
		return this;
	}

}
