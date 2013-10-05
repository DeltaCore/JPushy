package JPushy.Types.Blocks;

import java.util.Timer;

import JPushy.Blocks;
import JPushy.Core;
import JPushy.Game;
import JPushy.Types.Picture;
import JPushy.Types.Level.Level;
/**
 * 
 * @author Marcel Benning
 * 
 */
public class TeleportBase extends Block {
	
	private int endX = 0;
	private int endY = 0;
	boolean teleport = false;
	private int teleportTime = 125; // time to teleport after player walk on the teleporter (in ms)
	
	public TeleportBase(String name, int id, Picture img) {
		super(name, id, img);
	}

	public TeleportBase(String name, int id, Picture img, boolean visible) {
		super(name, id, img, visible);
	}
	
	public TeleportBase(String name, int id, Picture img,
			boolean playerAbleToWalkOn, boolean visible) {
		super(name, id, img, playerAbleToWalkOn, visible);
	}

	public TeleportBase(String name, int id, Picture img,
			boolean playerAbleToWalkOn, boolean solid, boolean visible) {
		super(name, id, img, playerAbleToWalkOn, solid, visible);
	}
	
	@Override
	public void onWalk(int x, int y, Level l) {
		super.onWalk(x, y, l);
		//System.out.println("Player cords : " + Core.getPlayer().getX() + "," + Core.getPlayer().getY());
		//System.out.println("Ouch ! Get away from me ! My cords : " + endX + "," + endY);
		Block b = l.getActiveStage().getBlock(endX, endY);
		//System.out.println(b.toString());
		if(l.getActiveStage().getBlock(endX, endY).getId() == Blocks.TeleportExit.getId()){
			//Game.sendMessage("Ouch ! Get away ! From me in about NOW !");
			Game.getPlayer().setFreezed(true);
			teleport = true;
			Timer timer = new Timer();
			timer.schedule(new TeleportSceduler(this), teleportTime);
		}
	}
	
	@Override
	public void update() {
		super.update();
		if(teleport){
			teleport = false;
			Game.getPlayer().setX(endX);
			Game.getPlayer().setY(endY);
			Game.getClient().set(endX, endY);
		}
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
