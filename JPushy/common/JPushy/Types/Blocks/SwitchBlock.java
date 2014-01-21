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

	Picture	    activeTexture;
	Picture	    inActiveTexture;

	private int	exPos	= 0;
	private int	eyPos	= 0;

	public SwitchBlock(Block b, Picture active, Picture inActive) {
		super(b);
		this.activeTexture = active;
		this.inActiveTexture = inActive;
	}

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
		Block b = l.getActiveStage().getBlock(this.getExPos(), this.getEyPos());
		System.out.println(this.getExPos() + ":" + this.getEyPos());
		if (active) {
			b.set();
			this.setActive(false);
			this.setTexture(inActiveTexture);
		} else {
			b.reset();
			this.setActive(true);
			this.setTexture(activeTexture);
		}
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
			this.setExPos(cfgCords[0]);
			this.setEyPos(cfgCords[1]);
		}
		return this;
	}

	/**
	 * @return the exPos
	 */
	public int getExPos() {
		return exPos;
	}

	/**
	 * @param exPos
	 *          the exPos to set
	 */
	public void setExPos(int exPos) {
		this.exPos = exPos;
	}

	/**
	 * @return the xyPos
	 */
	public int getEyPos() {
		return eyPos;
	}

	/**
	 * @param xyPos
	 *          the xyPos to set
	 */
	public void setEyPos(int eyPos) {
		this.eyPos = eyPos;
	}

}
