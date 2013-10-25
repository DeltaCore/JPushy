package JPushy.MultiPlayer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

import JPushy.Core.LevelScheduler;
import JPushy.Types.Player.Player;
import JPushy.gfx.PictureLoader;
/**
 * 
 * @author Marcel Benning
 * 
 */
public class MPCommandHandler implements ICommandHandler{
	
	LevelScheduler levelHandler;
	ArrayList<Player> players = new ArrayList<Player>();
	MPServer server;
	
	public MPCommandHandler(LevelScheduler levelHandler, MPServer server) {
		this.levelHandler = levelHandler;
		this.server = server;
	}
	
	@Override
	public void onCommand(String[] args, DatagramPacket packet) {
		if(args.length == 2){
			if(args[0].equals("-addPlayer")){
				String playername = args[1];
				System.out.println("New player : " + playername);
				addPlayer(playername);
				levelHandler.pushUpdate();
				levelHandler.sendMessage("Player " + playername + " joined your game.");
				Connection c = server.getConnection(packet);
				c.setPlayerName(playername);
				server.updateConnection(c);
			}else if(args[0].equals("-exit")){
				String playername = args[1];
				removePlayerByName(playername);
				levelHandler.sendMessage("Player " + playername + " left your game.");
				levelHandler.pushUpdate();
			}
		}else if(args.length == 3){
			String arg = args[0];
			String playername = args[1];
			int dir = Integer.parseInt(args[2]);
			Player target = getPlayerByName(playername);
			if(target != null){
				if(arg.equals("-movePlayer")){
					target.movePlayer(dir);
					levelHandler.pushUpdate();
				}
			}
		}
	}
	
	/*
	 * -movePlayer/playername/dir
	 * -addPlayer/Playername
	 * -setPlayer/playername
	 */
	
	private Player getPlayerByName(String name){
		for(int i = 0;i<players.size();i++){
			if(players.get(i) != null){
				if(players.get(i).getName().equals(name)){
					return players.get(i);
				}
			}
		}
		return null;
	}
	
	private void removePlayerByName(String name){
		for(int i = 0;i<players.size();i++){
			if(players.get(i).getName().equals(name)){
				players.set(i, null);
			}
		}
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	
	public void addPlayer(String playername){
		Player p = new Player(levelHandler, PictureLoader.loadImageFromFile("char.png"), playername);
		addPlayer(p);
	}
	
	public void addPlayer(Player player){
		players.add(player);
	}

	@Override
	public void onCommand(String msg, DatagramPacket packet) {
	}
	
}
