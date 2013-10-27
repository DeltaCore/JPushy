package JPushy.Core;

import JPushy.Gui.LevelSelector;
import JPushy.LevelServer.LevelServer;
import JPushy.MultiPlayer.MPClient;
import JPushy.Types.Level.Level;
import JPushy.Types.Player.Player;

/**
 * 
 * @author Marcel Benning
 * 
 */
public class Game extends Thread {

	public static final String		name	= "J-Pushy";
	public static final String		version	= "0.0.3 b9";

	static LevelSelector			s;
	public static LevelScheduler	gameThread;
	public static LevelServer		levelServer;

	public Game(String[] args) {
		for (int i = 0; i < args.length; i++)
			System.out.println("args[" + i + "] : " + args[i]);
		if (args.length > 0) {
			if (args[0].matches("-noGui")) {
				if (args[1].matches("-lServer")) {
					levelServer = new LevelServer();
				}
			} else {
				if (args[1].matches("-noGui")) {
					if (args[2].matches("-lServer")) {
						levelServer = new LevelServer();
					}
				} else {
					s = new LevelSelector(this);
					s.setVisible(true);
				}
			}
		}else{
			System.out.println("No arguments");
			s = new LevelSelector(this);
			s.setVisible(true);
		}

	}

	public int registerLevel(Level l) {
		return gameThread.registerLevel(l);
	}

	private int getIdByElement(Level l) {
		return gameThread.getIdByElement(l);
	}

	public static Level getActiveLevel() {
		return gameThread.getActiveLevel();
	}

	public void selectLevel(int id) {
		gameThread.selectLevel(id);
	}

	public static Player getPlayer() {
		return gameThread.getPlayer();
	}

	public static void win(String msg) {
		sendMessage(msg);
	}

	public static void pushUpdate() {
		gameThread.pushUpdate();
	}

	public static void sendMessage(String msg) {
		gameThread.sendMessage(msg);
	}

	public void loadLevel(String level) {
		gameThread.multiPlayerServer.stop();
		gameThread = new LevelScheduler(level);
		gameThread.start();
	}

	public static void stopGame() {
		gameThread.setRunning(false);
		gameThread.dispose();
		gameThread = new LevelScheduler();
	}

	public void loadLevel(String ip, int i) {
		System.out.println("");
		gameThread = new LevelScheduler(ip, i);
		gameThread.start();
	}

	public static MPClient getClient() {
		return gameThread.client;
	}

}
