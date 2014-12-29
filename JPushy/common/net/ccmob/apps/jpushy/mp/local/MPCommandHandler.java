package net.ccmob.apps.jpushy.mp.local;

import java.net.Socket;
import java.util.ArrayList;

import net.ccmob.apps.jpushy.core.LevelThread;
import net.ccmob.apps.jpushy.graphics.PictureLoader;
import net.ccmob.apps.jpushy.sp.player.Player;

/**
 * 
 * @author Marcel Benning
 * 
 */
public class MPCommandHandler implements ICommandHandler {

	LevelThread	      thread;
	ArrayList<Player>	players	= new ArrayList<Player>();
	MPServer	        server;

	public MPCommandHandler(LevelThread thread, MPServer server) {
		this.thread = thread;
		this.server = server;
	}

	@Override
	public void onCommand(String[] args, Socket packet, MPListenerThread t) {
		server.broadcastToClientsFromSourceClient(args, packet);
		if (args.length == 2) {
			if (args[0].equals("-addPlayer")) {
				String playername = args[1];
				System.out.println("New player : " + playername);
				addPlayer(playername);
				thread.pushUpdate();
				thread.sendMessage("Player " + playername + " joined your game.");
			} else if (args[0].equals("-exit")) {
				String playername = args[1];
				removePlayerByName(playername);
				thread.sendMessage("Player " + playername + " left your game.");
				thread.pushUpdate();
				t.setRunning(false);
			}
		} else if (args.length == 3) {
			String arg = args[0];
			String playername = args[1];
			int dir = Integer.parseInt(args[2]);
			Player target = getPlayerByName(playername);
			if (target != null) {
				if (arg.equals("-movePlayer")) {
					target.movePlayer(dir);
					thread.pushUpdate();
					server.broadcastToClientsFromSourceClient(args, packet);
				}
			}
		}
	}

	/*
	 * -movePlayer/playername/dir -addPlayer/Playername -setPlayer/playername
	 */

	private Player getPlayerByName(String name) {
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i) != null) {
				if (players.get(i).getName().equals(name)) {
					return players.get(i);
				}
			}
		}
		return null;
	}

	private void removePlayerByName(String name) {
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getName().equals(name)) {
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

	public void addPlayer(String playername) {
		Player p = new Player(thread, PictureLoader.loadImageFromFile("char.png"), playername);
		addPlayer(p);
	}
  
	public void addPlayer(Player player) {
		//System.out.println("Adding Player : " + player.getName());
		players.add(player);
	}
  
	@Override
	public void onCommand(String msg, Socket packet, MPListenerThread t) {
	}

}
