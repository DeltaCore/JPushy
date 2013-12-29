package JPushy.Listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import JPushy.Core.Core;
import JPushy.Core.Game;
import JPushy.Gui.GamePanel;
import JPushy.MultiPlayer.MPClient;
import JPushy.Types.Blocks.Block;

/**
 * 
 * @author Marcel Benning
 * 
 */
public class Input implements KeyListener {

	GamePanel	panel;
	MPClient	client;
	boolean	  enter	= false;
	boolean	  flag	= false;
	boolean	  debug	= Core.getSettings().getSettings(Core.getSettings().debug);

	public Input(GamePanel p, MPClient client) {
		this.panel = p;
		this.client = client;
	}

	@Override
	public void keyPressed(KeyEvent arg0) { // 0 north;1 east;2 south;3 west
		debug = Core.getSettings().getSettings(Core.getSettings().debug);
		if (debug)
			System.out.println("[Key] : " + arg0.getKeyCode() + " - " + arg0.getID());
		if (!Game.getPlayer().isNextMoveCanceld()) {
			switch (arg0.getKeyCode()) {
				case 38:
					if (!checkActivated(0)) {
						Game.getPlayer().movePlayer(0);
						client.move(0);
					} else {

					}
					break;
				case 39:
					if (!checkActivated(1)) {
						Game.getPlayer().movePlayer(1);
						client.move(1);
					} else {

					}
					break;
				case 40:
					if (!checkActivated(2)) {
						Game.getPlayer().movePlayer(2);
						client.move(2);
					} else {

					}
					break;
				case 37:
					if (!checkActivated(3)) {
						Game.getPlayer().movePlayer(3);
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
					Game.getPlayer().setFreezed(true);
					break;
				default:
					if (!flag)
						selectItem(arg0.getKeyCode());
			}
			panel.repaint();
			flag = false;
			if (!enter && Game.getPlayer().isNextMoveCanceld() == false)
				Game.getPlayer().setFreezed(false);
		}
	}

	private void selectItem(int keyCode) {
		int number = keyCode - 49;
		Game.getPlayer().getInventory().setSelectedSlot(number);
	}

	private boolean checkActivated(int dir) {
		flag = true;
		int x = Game.getPlayer().getX();
		int y = Game.getPlayer().getY();
		Block b = Game.gameThread.getLevel().getActiveStage().getBlock(x, y);
		if (enter) {
			Game.getPlayer().getInventory().getSlots()[Game.getPlayer().getInventory().getSelectedSlot()].getItem().onUse(Game.gameThread.getLevel().getActiveStage(), dir);
			Game.getPlayer().getInventory().update();
			enter = false;
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
