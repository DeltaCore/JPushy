package net.ccmob.apps.jpushy.core;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;

import net.ccmob.apps.jpushy.graphics.PictureLoader;
import net.ccmob.apps.jpushy.input.Input;
import net.ccmob.apps.jpushy.mp.local.MPClient;
import net.ccmob.apps.jpushy.mp.local.MPServer;
import net.ccmob.apps.jpushy.sp.level.Level;
import net.ccmob.apps.jpushy.sp.level.LevelItem;
import net.ccmob.apps.jpushy.sp.level.LevelLoader;
import net.ccmob.apps.jpushy.sp.player.Player;
import net.ccmob.apps.jpushy.ui.Chat;
import net.ccmob.apps.jpushy.ui.GamePanel;

public class LevelThread extends JFrame implements Runnable {

	private static final long	serialVersionUID	= 1L;
	private GamePanel	        gamePanel;
	private MPClient	        client;
	private MPServer	        server;
	private boolean	          running	         = true;
	private Level	            level;
	private Input	            input;
	private Player	          player;
	private Chat	            chat;

	public LevelThread(Game gui, LevelItem level) {
		super(level.getName() + " | JPushy");
		JPushy.getInstance().thread = this;
		this.setGamePanel(new GamePanel(this, 25));
		this.setChat(new Chat(this.getGamePanel()));
		this.setPlayer(new Player(this, PictureLoader.loadImageFromFile("char.png"), "Player"));
		this.setServer(new MPServer(this));
		this.setClient(new MPClient(this.getServer()));
		this.getServer().cmdHandler.addPlayer(this.getPlayer());
		loadLevel(level.getFile());
		this.getLevel().init(this.getPlayer());
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setPreferredSize(new Dimension(1000, 700));
		this.setSize(new Dimension(1000, 700));
		this.setLayout(new BorderLayout());
		this.add(this.getGamePanel());
		this.setInput(new Input(this, this.getGamePanel(), this.getClient()));
		this.addKeyListener(input);
		showControls();
	}
	
	private void showControls(){
		ArrayList<String> controls = new ArrayList<String>();
		controls.add("The controls : ");
		controls.add("W - Up");
		controls.add("S - Down");
		controls.add("A - Left");
		controls.add("D - Right");
		controls.add("Enter - Use an item");
		controls.add("	-> After you pressed enter , use W/A/S/D to determine ");
		controls.add("     a direction you wanna active the item");
		for(int i = 0;i<controls.size();i++){
			Game.sendMessage(controls.get(i));
		}
	}
	
	public LevelThread(String ip) {
		super("JPushy");
		this.setPlayer(new Player(this, PictureLoader.loadImageFromFile("char.png"), "Player"));
		this.setServer(new MPServer(this));
		this.setClient(new MPClient(this.getServer()));
		this.getServer().cmdHandler.addPlayer(this.getPlayer());
		String filename = client.join(ip);
		String levelRegEx = "<level name=\"([a-zA-Z0-9\\s]{1,})\" version='([a-zA-Z0-9.,\\s]{1,})'>";
		String commentStartRegEx = "^<comment>";
		String commentEndRegEx = "^</comment>";
		LevelItem levelItem = new LevelItem();
		try {
			String comment = "";
			BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
			String line = "";
			Pattern level = Pattern.compile(levelRegEx);
			Matcher levelMatcher = level.matcher("");

			Pattern commentStart = Pattern.compile(commentStartRegEx);
			Matcher commentStartMatcher = level.matcher("");

			Pattern commentEnd = Pattern.compile(commentEndRegEx);
			Matcher commentEndMatcher = level.matcher("");
			boolean flag = false;
			while ((line = reader.readLine()) != null) {
				levelMatcher = level.matcher(line);
				commentStartMatcher = commentStart.matcher(line);
				commentEndMatcher = commentEnd.matcher(line);
				if (levelMatcher.matches()) {
					levelItem.setName(levelMatcher.group(1));
					levelItem.setVersion(levelMatcher.group(2));
					System.out.println("[Level] Name : " + levelMatcher.group(1) + " version : " + levelMatcher.group(2) + " file: " + filename);
				} else if (commentStartMatcher.matches()) {
					flag = true;
				} else if (commentEndMatcher.matches()) {
					flag = false;
				} else if (flag) {
					comment += line + "\n";
				}
			}
			levelItem.setComment(comment);
			levelItem.setFile(filename);
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		loadLevel(levelItem.getFile());
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setPreferredSize(new Dimension(1000, 700));
		this.setSize(new Dimension(1000, 700));
		this.setLayout(new BorderLayout());
		this.setGamePanel(new GamePanel(this, 25));
		this.add(this.getGamePanel());
		this.setInput(new Input(this, this.getGamePanel(), this.getClient()));
		this.addKeyListener(input);
		this.setChat(new Chat(this.getGamePanel()));
		this.setTitle(levelItem.getName() + " | JPushy");
	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public void setGamePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	public MPClient getClient() {
		return client;
	}

	public void setClient(MPClient client) {
		this.client = client;
	}

	public MPServer getServer() {
		return server;
	}

	public void setServer(MPServer server) {
		this.server = server;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public void loadLevel(String file) {
		this.setLevel(LevelLoader.load(file));
	}

	public Input getInput() {
		return input;
	}

	public void setInput(Input input) {
		this.input = input;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}

	public void update() {
		this.repaint();
		this.getGamePanel().repaint();
	}

	public void start() {
		this.setVisible(true);
		this.getServer().start();
	}

	@Override
	public void run() {
		this.start();
	}

	public void pushUpdate() {
		update();
	}

	public void sendMessage(String msg) {
		this.getChat().sendMessage(msg);
	}
}
