package net.ccmob.apps.jpushy.blocks;

import java.util.TimerTask;

import net.ccmob.apps.jpushy.core.Game;

/**
 * 
 * @author Marcel Benning
 * 
 */
public class TeleportSceduler extends TimerTask {

	TeleportBase	teleporter;

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
