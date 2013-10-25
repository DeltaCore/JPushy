package JPushy.Types.Blocks;

import java.util.TimerTask;

import JPushy.Core.Core;
import JPushy.Core.Game;
/**
 * 
 * @author Marcel Benning
 * 
 */
public class TeleportSceduler extends TimerTask{

	TeleportBase teleporter;
	
	public TeleportSceduler(TeleportBase base) {
		this.teleporter = base;
	}
	
	@Override
	public void run() {
		teleporter.update();
		Game.pushUpdate();
		Game.getPlayer().setFreezed(false);
	}

}
