package JPushy;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import JPushy.Gui.GamePanel;
import JPushy.MultiPlayer.MPClient;
import JPushy.Types.Blocks.Block;
/**
 * 
 * @author Marcel Benning
 * 
 */
public class Input implements KeyListener{

	GamePanel panel;
	MPClient client;
	boolean enter = false;
	boolean flag = false;
	
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
		case 10:
			enter = true;
			Game.sendMessage("Go in that direction you wanne activate a block !");
			break;
		default:
			if(!flag)
				selectItem(arg0.getKeyCode());
		}
		panel.repaint();
		enter = false;
		flag = false;
	}
	
	private void selectItem(int keyCode){
		int  number = keyCode - 49;
		Game.getPlayer().getInventory().setSelectedSlot(number);
	}
	
	private boolean checkActivated(int dir){
		flag = true;
		int x = Game.getPlayer().getX();
		int y = Game.getPlayer().getY();
		Game.getPlayer().getInventory().getSlots()[Game.getPlayer().getInventory().getSelectedSlot()].getItem().onUse(Game.gameThread.getLevel().getActiveStage());
		Block b = Game.gameThread.getLevel().getActiveStage().getBlock(x, y);
		if(enter){
			if(dir == 0){
				b = Game.gameThread.getLevel().getActiveStage().getBlock(x, y - 1);
				b.onBlockActivated(Game.gameThread.getLevel().getActiveStage(), Game.getPlayer());
			}else if(dir == 1){
				b = Game.gameThread.getLevel().getActiveStage().getBlock(x + 1, y);
				b.onBlockActivated(Game.gameThread.getLevel().getActiveStage(), Game.getPlayer());
			}else if(dir == 2){
				b = Game.gameThread.getLevel().getActiveStage().getBlock(x, y + 1);
				b.onBlockActivated(Game.gameThread.getLevel().getActiveStage(), Game.getPlayer());
			}else if(dir == 3){
				b = Game.gameThread.getLevel().getActiveStage().getBlock(x - 1, y);
				b.onBlockActivated(Game.gameThread.getLevel().getActiveStage(), Game.getPlayer());
			}
			enter = false;
		}else{
			
		}
		return false;
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
