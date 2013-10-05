package JPushy;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import JPushy.Gui.GamePanel;
import JPushy.MultiPlayer.MPClient;
/**
 * 
 * @author Marcel Benning
 * 
 */
public class Input implements KeyListener{

	GamePanel panel;
	MPClient client;
	
	public Input(GamePanel p, MPClient client) {
		this.panel = p;
		this.client = client;
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) { // 0 north ; 1 east ; 2 south ; 3 west
		switch(arg0.getKeyCode()){
		case 38:
			Game.getPlayer().movePlayer(0);
			client.move(0);
			break;
		case 39:
			Game.getPlayer().movePlayer(1);
			client.move(1);
			break;
		case 40:
			Game.getPlayer().movePlayer(2);
			client.move(2);
			break;
		case 37:
			Game.getPlayer().movePlayer(3);
			client.move(3);
			break;
		case 27:
			System.exit(0);
		}
		panel.repaint();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

}
