package net.ccmob.apps.jpushy.blocks;

import java.util.ArrayList;
import java.util.Timer;

import net.ccmob.apps.jpushy.core.Game;
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
public class TeleportBase extends Block {

	private int	endX	       = 0;
	private int	endY	       = 0;
	boolean	    teleport	   = false;
	private int	teleportTime	= 125;	// time to teleport after player walk on the
	                                  // teleporter (in ms)

	public TeleportBase(String name, int id, Picture img) {
		super(name, id, img);
		this.setCanGetocupied(false);
		this.setDestroyable(false);
		this.setPlayerAbleToWalkOn(true);
		this.setSolid(true);
		this.setVisible(true);
	}

	@Override
	public void onWalk(int x, int y, Level l) {
		super.onWalk(x, y, l);
		if (l.getActiveStage().getBlock(endX, endY).getId() == Blocks.teleportExit.getId()) {
			Game.getPlayer().setFreezed(true);
			teleport = true;
			Timer timer = new Timer();
			timer.schedule(new TeleportSceduler(this), teleportTime);
		}
	}

	@Override
	public void update() {
		super.update();
		if (teleport) {
			teleport = false;
			Game.getPlayer().setX(endX);
			Game.getPlayer().setY(endY);
			Game.getClient().set(endX, endY);
		}
	}

	@Override
	public Block onConfigLoaded(int x, int y, int stageId, ArrayList<String> cfgLines, Stage stage) {
		//System.out.println("X: " + x + " Y:" + y + " STAGE: " + stageId);
		int[] cfgCords = LevelLoader.checkCFGCords(cfgLines, stageId, x, y);
		if (cfgCords[0] == 0 && cfgCords[1] == 0) {
			//System.out.println("No coords for me :(");
		} else {
			this.setEndX(cfgCords[0]);
			this.setEndY(cfgCords[1]);
		}
		return this;
	}
	
	@Override
	public void onLevelLoad(int x, int y, int stageId, BlockAction action) {
	  super.onLevelLoad(x, y, stageId, action);
	  this.setEndX(action.blockDestX);
		this.setEndY(action.blockDestY);
	}

	public int getEndX() {
		return endX;
	}

	public void setEndX(int endX) {
		this.endX = endX;
	}

	public int getEndY() {
		return endY;
	}

	public void setEndY(int endY) {
		this.endY = endY;
	}

}
