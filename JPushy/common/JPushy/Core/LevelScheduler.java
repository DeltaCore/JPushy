package JPushy.Core;

import java.util.ArrayList;

import javax.swing.JTextArea;

import JPushy.Gui.MainFrame;
import JPushy.MultiPlayer.MPClient;
import JPushy.MultiPlayer.MPServer;
import JPushy.Types.Level.Level;
import JPushy.Types.Level.LevelLoader;
import JPushy.Types.Player.Player;
import JPushy.gfx.PictureLoader;

/**
 * 
 * @author Marcel Benning
 * 
 */
public class LevelScheduler extends Thread {

	private String	                filename	  = "";
	static Player	                  p;
	static MainFrame	              mainFrame;
	static boolean	                init	      = false;
	JTextArea	                      jText	      = new JTextArea();
	private static Level	          l;
	private boolean	                isRunning	  = false;
	private static ArrayList<Level>	levels	    = new ArrayList<Level>();
	private static int	            activeLevel	= -1;
	int	                            mode	      = 0;
	private Game	                  game;

	public LevelScheduler(String filename, MPServer server, Game game) {
		this.setGame(game);
		this.filename = filename;
		this.getGame().setMpServer(new MPServer());
		this.getGame().setClient(new MPClient(this.getGame().getMpServer()));
		isRunning = true;
		setLevel(LevelLoader.loadLevelFromFile(filename));
		System.out.println("Levelfile : " + getFilename());
		p = new Player(this, PictureLoader.loadImageFromFile("char.png"), "Player 1");
	}

	public LevelScheduler() {
		mode = 0;
	}

	public LevelScheduler(String ip, int i, MPClient client, MPServer server, Game game) {
		this.setGame(game);
		mode = i;
		isRunning = true;
		setLevel(LevelLoader.loadLevelFromFile(filename));
		System.out.println("Levelfile : " + getFilename());
		p = new Player(this, PictureLoader.loadImageFromFile("char.png"), "Player 1");
		if (i == 1) {
			client.join(ip);
		}
		/*
		 * setLevel(LevelLoader.loadLevelFromFile(filename)); p = new Player(this,
		 * PictureLoader.loadImageFromFile("char.png"), "Player 1"); //l.update();
		 * mainFrame = new MainFrame(this, p); //mainFrame.setSize(l.getSize(10, 10,
		 * 18)); mainFrame.setTitle(getLevel().getName() + " V" +
		 * getLevel().getVersion()); getLevel().init(); init = true; //while(true);
		 * multiPlayerServer.cmdHandler.addPlayer(p); while(isRunning);
		 * mainFrame.dispose();
		 */
	}

	@Override
	public void run() {
		// l.update();
		mainFrame = new MainFrame(this.getGame(), this, p);
		// mainFrame.setSize(l.getSize(10, 10, 18));
		mainFrame.setTitle(getLevel().getName() + " V" + getLevel().getVersion());
		getLevel().init();
		init = true;
		// while(true);
		this.getGame().mpServer.cmdHandler.addPlayer(p);
		while (isRunning)
			;
		mainFrame.dispose();
	}

	public int registerLevel(Level l) {
		if (levels.add(l)) {
			return getIdByElement(l);
		} else {
			return -1;
		}
	}

	public int getIdByElement(Level l) {
		for (int i = 0; i < levels.size(); i++) {
			if (levels.get(i) == l) {
				return i;
			}
		}
		return -1;
	}

	public static Level getActiveLevel() {
		if (activeLevel == -1)
			return null;
		return levels.get(activeLevel);
	}

	public void selectLevel(int id) {
		activeLevel = id;
	}

	public static Player getPlayer() {
		return p;
	}

	public static void win(String msg) {
		sendMessage(msg);
	}

	public static void pushUpdate() {
		if (init) {
			mainFrame.update();
			// mainFrame.
			// mainFrame.setSize(l.getSize(10, 10, 18));
		}
	}

	public static void sendMessage(String msg) {
		mainFrame.getChat().sendMessage(msg);
	}

	public boolean isRunning() {
		return this.isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public void dispose() {
		mainFrame.dispose();
	}

	public static Level getLevel() {
		return l;
	}

	public static void setLevel(Level l) {
		LevelScheduler.l = l;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

}
