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
public class SwitchBlock extends Block {

	Picture	activeTexture;
	Picture	inActiveTexture;

	public SwitchBlock(Block b, Picture active, Picture inActive) {
		super(b);
		this.activeTexture = active;
		this.inActiveTexture = inActive;
	}

	private int		x	= 0, y = 0;
	private boolean	active;

	public SwitchBlock(String name, int id, Picture active, Picture inActive) {
		super(name, id, inActive);
		this.activeTexture = active;
		this.inActiveTexture = inActive;
	}

	@Override
	public void init() {
		super.init();
	}

	@Override
	public void onWalk(int x, int y, Level l) {
		super.onWalk(x, y, l);
		Block b = l.getActiveStage().getBlock(this.x, this.y);
		System.out.println(this.x + ":" + this.y);
		if (active) {
			b.set();
			this.setActive(false);
			this.setX(this.x);
			this.setY(this.y);
			this.setTexture(inActiveTexture);
		} else {
			b.reset();
			this.setActive(true);
			this.setX(this.x);
			this.setY(this.y);
			this.setTexture(activeTexture);
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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
