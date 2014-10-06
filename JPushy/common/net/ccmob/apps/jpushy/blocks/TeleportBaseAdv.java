package net.ccmob.apps.jpushy.blocks;

import java.util.ArrayList;
import java.util.Timer;

import net.ccmob.apps.jpushy.core.Game;
import net.ccmob.apps.jpushy.graphics.Picture;
import net.ccmob.apps.jpushy.sp.level.Level;
import net.ccmob.apps.jpushy.sp.level.LevelLoader;
import net.ccmob.apps.jpushy.sp.level.Stage;
import net.ccmob.apps.jpushy.sp.level.editor.EditorSaveThread.BlockAction;

public class TeleportBaseAdv extends TeleportBase {

	private int	endX	       = 0;
	private int	endY	       = 0;
	private int	thisX	       = 0;
	private int	thisY	       = 0;
	boolean	    teleport	   = false;
	private int	teleportTime	= 125;	// time to teleport after player walk on the
	                                  // teleporter (in ms)

	public TeleportBaseAdv(String name, int id, Picture img) {
		super(name, id, img);
	}

	@Override
	public void onWalk(int x, int y, Level l) {
		super.onWalk(x, y, l);
		System.out.println("Player cords : " + Game.getPlayer().getX() + "," + Game.getPlayer().getY());
		System.out.println("Ouch ! Get away from me ! My cords : " + endX + "," + endY);
		Block b = l.getActiveStage().getBlock(endX, endY);
		System.out.println(b.toString());
		if (l.getActiveStage().getBlock(endX, endY).getId() == Blocks.teleportAdv.getId()) {
			Game.sendMessage("Ouch ! Get away ! From me in about NOW !");
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
	public void afterInit(Stage s) {
		super.afterInit(s);
		if (s.getBlock(endX, endY) instanceof TeleportBaseAdv) {
			((TeleportBaseAdv) s.getBlock(endX, endY)).setEndX(this.getThisX());
			((TeleportBaseAdv) s.getBlock(endX, endY)).setEndY(this.getThisY());
		}
	}

	@Override
	public Block onConfigLoaded(int x, int y, int stageId, ArrayList<String> cfgLines, Stage stage) {
		System.out.println("X: " + x + " Y:" + y + " STAGE: " + stageId);
		this.setThisX(x);
		this.setThisY(y);
		int[] cfgCords = LevelLoader.checkCFGCords(cfgLines, stageId, x, y);
		if (cfgCords[0] == 0 && cfgCords[1] == 0) {
			System.out.println("No coords for me :(");
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
		this.setThisX(action.blockSourceX);
		this.setThisY(action.blockSourceY);
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

	/**
	 * @return the thisX
	 */
	public int getThisX() {
		return thisX;
	}

	/**
	 * @param thisX
	 *          the thisX to set
	 */
	public void setThisX(int thisX) {
		this.thisX = thisX;
	}

	/**
	 * @return the thisY
	 */
	public int getThisY() {
		return thisY;
	}

	/**
	 * @param thisY
	 *          the thisY to set
	 */
	public void setThisY(int thisY) {
		this.thisY = thisY;
	}

}
