
package net.ccmob.apps.jpushy.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import net.ccmob.apps.jpushy.blocks.InventoryBlock;
import net.ccmob.apps.jpushy.core.Game;
import net.ccmob.apps.jpushy.core.LevelThread;
import net.ccmob.apps.jpushy.items.ItemUseEvent;
import net.ccmob.apps.jpushy.modloader.ModEventType;
import net.ccmob.apps.jpushy.modloader.ModLoader;
import net.ccmob.apps.jpushy.modloader.eventArgs.KeyEventArg;
import net.ccmob.apps.jpushy.mp.local.MPClient;
import net.ccmob.apps.jpushy.ui.GamePanel;

/**
 * 
 * @author Marcel Benning
 * 
 */
public class Input implements KeyListener {

	GamePanel	  panel;
	MPClient	  client;
	LevelThread	thread;
	boolean	    enter	= false;
	boolean	    flag	= false;
	boolean	    debug	= true;	// Core.getSettings().getSettings(Core.getSettings().debug);

	public Input(LevelThread thread, GamePanel p, MPClient client) {
		this.panel = p;
		this.thread = thread;
		this.client = client;
	}

	@Override
	public void keyPressed(KeyEvent arg0) { // 0 north;1 east;2 south;3 west
		debug = false;// Core.getSettings().getSettings(Core.getSettings().debug);
		if (debug)
			System.out.println("[Key] : " + arg0.getKeyCode() + " - " + arg0.getID());
		ModLoader.queueEvent(ModEventType.KeyInput, new KeyEventArg(arg0.getKeyCode()));
		if (!thread.getPlayer().isNextMoveCanceld()) {
			switch (arg0.getKeyCode()) {
				case 38:
					if (!checkActivated(Direction.NORTH)) {
						thread.getPlayer().movePlayer(0);
						client.move(0);
					} else {

					}
					break;
				case 39:
					if (!checkActivated(Direction.EAST)) {
						thread.getPlayer().movePlayer(1);
						client.move(1);
					} else {

					}
					break;
				case 40:
					if (!checkActivated(Direction.SOUTH)) {
						thread.getPlayer().movePlayer(2);
						client.move(2);
					} else {

					}
					break;
				case 37:
					if (!checkActivated(Direction.WEST)) {
						thread.getPlayer().movePlayer(3);
						client.move(3);
					} else {

					}
					break;
				case 27:
					Game.stopGame();
					break;
				case 10:
					enter = true;
					Game.sendMessage("Go in that direction you wanna activate a block !");
					thread.getPlayer().setFreezed(true);
					break;
				default:
					if (!flag)
						selectItem(arg0.getKeyCode());
			}
			panel.repaint();
			flag = false;
			if (!enter && thread.getPlayer().isNextMoveCanceld() == false)
				thread.getPlayer().setFreezed(false);
		}
	}

	private void selectItem(int keyCode) {
		int number = keyCode - 49;
		thread.getPlayer().getInventory().setSelectedSlot(number);
	}

	private boolean checkActivated(Direction d) {
		flag = true;
		if (enter) {
			if (thread.getLevel().getActiveStage().getBlock(thread.getPlayer().getX(), thread.getPlayer().getY()) instanceof InventoryBlock) {
				InventoryBlock b = (InventoryBlock) thread.getLevel().getActiveStage().getBlock(thread.getPlayer().getX(), thread.getPlayer().getY());
				if (b.pushItemInFromSide(d, thread.getPlayer().getInventory().getSlots()[thread.getPlayer().getInventory().getSelectedSlot()].getItem())) {
					thread.getPlayer().getInventory().getSlots()[thread.getPlayer().getInventory().getSelectedSlot()].decrement();
					thread.getPlayer().getInventory().update();
				}
			} else {
				thread.getPlayer().getInventory().getSlots()[thread.getPlayer().getInventory().getSelectedSlot()].getItem().onUse(thread.getLevel().getActiveStage(), d.ordinal(),
				    new ItemUseEvent());
				thread.getPlayer().getInventory().update();
				enter = false;
			}
		}
		return enter;
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
