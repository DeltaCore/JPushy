package net.ccmob.apps.jpushy.blocks;

import java.util.ArrayList;

import net.ccmob.apps.jpushy.graphics.Picture;
import net.ccmob.apps.jpushy.sp.level.Level;
import net.ccmob.apps.jpushy.sp.level.LevelLoader;
import net.ccmob.apps.jpushy.sp.level.Stage;
import net.ccmob.apps.jpushy.sp.level.editor.EditorSaveThread.BlockAction;

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

	private int	exPos	= 0;
	private int	eyPos	= 0;

	@Override
	public void init() {
		super.init();
		this.setLever(true);
	}

	@Override
	public void onOccupied(boolean o, Level l) {
		System.out.println("Ouch !");
		Block b = l.getActiveStage().getBlock(this.getExPos(), this.getEyPos());
		System.out.println(this.getExPos() + ":" + this.getEyPos() + " -> " + b.toString());
		if (o) {
			b.set();
		} else {
			b.reset();
		}
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
	
	@Override
	public void onLevelLoad(int x, int y, int stageId, BlockAction action) {
	  super.onLevelLoad(x, y, stageId, action);
	  this.setExPos(action.blockDestX);
		this.setEyPos(action.blockDestY);
		System.out.println("X; " + this.getExPos() + " - Y; " + this.getEyPos());
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
	 * @return the eyPos
	 */
	public int getEyPos() {
		return eyPos;
	}

	/**
	 * @param eyPos
	 *          the eyPos to set
	 */
	public void setEyPos(int eyPos) {
		this.eyPos = eyPos;
	}

}
