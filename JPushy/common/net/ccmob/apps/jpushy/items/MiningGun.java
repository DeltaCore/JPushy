package net.ccmob.apps.jpushy.items;

import net.ccmob.apps.jpushy.blocks.Block;
import net.ccmob.apps.jpushy.blocks.Blocks;
import net.ccmob.apps.jpushy.core.Game;
import net.ccmob.apps.jpushy.graphics.Picture;
import net.ccmob.apps.jpushy.sp.level.Stage;

/**
 * 
 * @author Marcel Benning
 * 
 */
public class MiningGun extends Item {

	public MiningGun(String name, int id, Picture texture) {
		super(name, id, texture);
		// TODO Auto-generated constructor stub
	}

	public MiningGun(String name, int id, Picture texture, boolean register) {
		super(name, id, texture, register);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onUse(Stage stage, int dir, ItemUseEvent e) {
		int y = Game.getPlayer().getY();
		int x = Game.getPlayer().getX();
		boolean flag = false;
		Block b = Blocks.getBlockById(0);
		if (dir == 0) {
			b = Game.getLevel().getActiveStage().getBlock(x, y - 1);
			if (b.isDestroyable()) {
				stage.getBlock(x, y - 1).onDestroy();
				stage.destroyBlock(x, y - 1);
				flag = true;
			}
		} else if (dir == 1) {
			b = Game.getLevel().getActiveStage().getBlock(x + 1, y);
			if (b.isDestroyable()) {
				stage.getBlock(x + 1, y).onDestroy();
				stage.destroyBlock(x + 1, y);
				flag = true;
			}
		} else if (dir == 2) {
			b = Game.getLevel().getActiveStage().getBlock(x, y + 1);
			if (b.isDestroyable()) {
				stage.getBlock(x, y + 1).onDestroy();
				stage.destroyBlock(x, y + 1);
				flag = true;
			}
		} else if (dir == 3) {
			b = Game.getLevel().getActiveStage().getBlock(x - 1, y);
			if (b.isDestroyable()) {
				stage.getBlock(x - 1, y).onDestroy();
				stage.destroyBlock(x - 1, y);
				flag = true;
			}
		}
		if (flag) {
			Game.getPlayer().getInventory().getItemInHand().setDmg(Game.getPlayer().getInventory().getItemInHand().getDmg() + 1);
			if (this.getDmg() >= this.getMaxDMG()) {
				Game.getPlayer().getInventory().removeItemInHand();
			}
		}
		e.setHandled(true);
		super.onUse(stage, dir, e);
	}
}
