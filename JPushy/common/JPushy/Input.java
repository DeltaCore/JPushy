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
	boolean enter = false;
	
	public Input(GamePanel p, MPClient client) {
		this.panel = p;
		this.client = client;
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) { // 0 north ; 1 east ; 2 south ; 3 west
		System.out.println("[Key] : " + arg0.getKeyCode());
		switch(arg0.getKeyCode()){
		case 38:
			if(!checkActivated(0)){
				Game.getPlayer().movePlayer(0);
				client.move(0);
			}else{
				
			}
			break;
		case 39:
			if(!checkActivated(1)){
				Game.getPlayer().movePlayer(1);
				client.move(1);
			}else{
				
			}
			break;
		case 40:
			if(!checkActivated(2)){
				Game.getPlayer().movePlayer(2);
				client.move(2);
			}else{
				
			}
			break;
		case 37:
			if(!checkActivated(3)){
				Game.getPlayer().movePlayer(3);
				client.move(3);
			}else{
				
			}
			break;
		case 27:
			System.exit(0);
		}
		panel.repaint();
	}

	private boolean checkActivated(int dir){
		if(enter){
			
		}else{
			
		}
		return false;
	}
	
	@Override
	public void keyReleased(KeyEvent arg0) {
		if(arg0.getKeyCode() == 10){
			enter = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		if(arg0.getKeyCode() == 10){
			enter = true;
		}
	}

}
