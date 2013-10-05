package JPushy;

import java.util.ArrayList;

import javax.swing.JTextArea;

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
public class LevelScheduler extends Thread{

	private String filename = "";
	static Player p;
	static MainFrame mainFrame;
	static boolean init = false;
	JTextArea jText = new JTextArea();
	private static Level l;
	private boolean isRunning = false;
	private static ArrayList<Level> levels = new ArrayList<Level>();
	private static int activeLevel = -1;
	MPServer multiPlayerServer;
	MPClient client;
	int mode = 0;
	
	public LevelScheduler(String filename) {
		this.filename = filename;
		isRunning = true;
		multiPlayerServer = new MPServer(this, 0);
		multiPlayerServer.start();
		client = new MPClient(multiPlayerServer);
		setLevel(LevelLoader.loadLevelFromFile(filename));
		System.out.println("Levelfile : " + getFilename());
		p = new Player(this, PictureLoader.loadImageFromFile("char.png"), "Player 1");
	}
	
	public LevelScheduler() {
		mode = 0;
	}
	
	public LevelScheduler(String ip, int i) {
		mode = i;
		isRunning = true;
		multiPlayerServer = new MPServer(this, i);
		multiPlayerServer.start();
		client = new MPClient(multiPlayerServer);
		setLevel(LevelLoader.loadLevelFromFile(filename));
		System.out.println("Levelfile : " + getFilename());
		p = new Player(this, PictureLoader.loadImageFromFile("char.png"), "Player 1");
		if(i == 1){
			client.join(ip);
		}
		/*setLevel(LevelLoader.loadLevelFromFile(filename));
		p = new Player(this, PictureLoader.loadImageFromFile("char.png"), "Player 1");
		//l.update();
		mainFrame = new MainFrame(this, p);
		//mainFrame.setSize(l.getSize(10, 10, 18));
		mainFrame.setTitle(getLevel().getName() + " V" + getLevel().getVersion());
		getLevel().init();
		init = true;
		//while(true);
		multiPlayerServer.cmdHandler.addPlayer(p);
		while(isRunning);
		mainFrame.dispose();*/
	}
	
	@Override
	public void run() {
		//l.update();
		mainFrame = new MainFrame(this, p);
		//mainFrame.setSize(l.getSize(10, 10, 18));
		mainFrame.setTitle(getLevel().getName() + " V" + getLevel().getVersion());
		getLevel().init();
		init = true;
		//while(true);
		multiPlayerServer.cmdHandler.addPlayer(p);
		while(isRunning);
		mainFrame.dispose();
	}
	
	public int registerLevel(Level l){
		if(levels.add(l)){
			return getIdByElement(l);
		}else{
			return -1;
		}
	}
	
	public int getIdByElement(Level l){
		for(int i = 0;i<levels.size();i++){
			if(levels.get(i) == l){
				return i;
			}
		}
		return -1;
	}
	
	public static Level getActiveLevel(){
		if(activeLevel == -1)
			return null;
		return levels.get(activeLevel);
	}
	
	public void selectLevel(int id){
		activeLevel = id;
	}
	
	public static Player getPlayer(){
		return p;
	}
	
	public static void win(String msg){
		sendMessage(msg);
	}
	
	public static void pushUpdate(){
		if(init){
			mainFrame.update();
			//mainFrame.
			//mainFrame.setSize(l.getSize(10, 10, 18));
		}
	}
	
	public static void sendMessage(String msg){
		mainFrame.getChat().sendMessage(msg);
	}

	public boolean isRunning() {
		return this.isRunning;
	}
	
	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	
	public void dispose(){
		mainFrame.dispose();
	}

	public static Level getLevel() {
		return l;
	}

	public static void setLevel(Level l) {
		LevelScheduler.l = l;
	}

	public MPServer getMultiPlayerServer() {
		return multiPlayerServer;
	}

	public void setMultiPlayerServer(MPServer multiPlayerServer) {
		this.multiPlayerServer = multiPlayerServer;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public MPClient getClient() {
		return client;
	}
	
}
