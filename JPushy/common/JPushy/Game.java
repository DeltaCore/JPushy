package JPushy;

import java.util.ArrayList;

import javax.swing.JTextArea;

import JPushy.Gui.LevelSelector;
import JPushy.Gui.MainFrame;
import JPushy.MultiPlayer.MPClient;
import JPushy.MultiPlayer.MPServer;
import JPushy.Types.Level.Level;
import JPushy.Types.Level.LevelLoader;
/**
 * 
 * @author Marcel Benning
 * 
 */
public class Game extends Thread{

	public static final String name = "J-Pushy";
	public static final String version = "0.0.1 b15";
	
	static LevelSelector s;
	public static LevelScheduler gameThread;
	
	public Game() {	
		s = new LevelSelector(this);
		s.setVisible(true);
	}
	
	public int registerLevel(Level l){
		return gameThread.registerLevel(l);
	}
	
	private int getIdByElement(Level l){
		return gameThread.getIdByElement(l);
	}
	
	public static Level getActiveLevel(){
		return gameThread.getActiveLevel();
	}
	
	public void selectLevel(int id){
		gameThread.selectLevel(id);
	}
	
	public static Player getPlayer(){
		return gameThread.getPlayer();
	}
	
	public static void win(String msg){
		sendMessage(msg);
	}
	
	public static void pushUpdate(){
		gameThread.pushUpdate();
	}
	
	public static void sendMessage(String msg){
		gameThread.sendMessage(msg);
	}
	
	public void loadLevel(String level){
		gameThread = new LevelScheduler(level);
		gameThread.start();
	}
	
	public static void stopGame(){
		gameThread.setRunning(false);
		gameThread.dispose();
		gameThread = new LevelScheduler();
	}
	
	public void loadLevel(String ip, int i) {
		System.out.println("");
		gameThread = new LevelScheduler(ip, i);
		gameThread.start();
	}
	
	public static MPClient getClient(){
		return gameThread.client;
	}
	
}
