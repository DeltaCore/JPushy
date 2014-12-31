
package net.ccmob.apps.jpushy.mp.local;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.ccmob.apps.jpushy.core.Game;
import net.ccmob.apps.jpushy.mp.remote.BlockPacket;
import net.ccmob.apps.jpushy.mp.remote.BlockPacket.InvalidPacketContentException;

/**
 * 
 * @author Marcel Benning
 * 
 */
public class MPClient implements Runnable, ICommandHandler{

	MPServer	          server;
	private boolean	    connected	       = false;
	private Socket	    serverConnection	= null;
	private Thread serverConnectionThread = null;
	private boolean serverConnectionRunning = true;
	private boolean client = false;
	
	public MPClient(MPServer server) {
		this.server = server;
	}

	private enum LevelType {
		XML, LVL, UNDEFINED, NONE
	}

	public String join(String ip) {
		String cfg;
		String hostname = "";
		int port = 11941;
		String key = MPServer.getRandomKey();
		try {
			if (ip.equalsIgnoreCase("localhost")) {
				hostname = ip;
			} else {
				String regex_ip_port = "^([0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}):([0-9]{1,})";
				String regex_host_port = "^([a-zA-Z]{1,}.[a-zA-Z]{1,}):([0-9]{1,})";
				String regex_ip_noport = "^([0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3})";
				String regex_host_noport = "^([a-zA-Z]{1,}.[a-zA-Z]{1,})";
				if (ip.matches(regex_ip_port)) {
					Pattern pattern = Pattern.compile(regex_ip_port);
					Matcher matcher = pattern.matcher(ip);
					if (matcher.matches()) {
						hostname = matcher.group(1);
						port = Integer.parseInt(matcher.group(2));
					}
				} else if (ip.matches(regex_host_port)) {
					Pattern pattern = Pattern.compile(regex_host_port);
					Matcher matcher = pattern.matcher(ip);
					if (matcher.matches()) {
						hostname = matcher.group(1);
						port = Integer.parseInt(matcher.group(2));
					}
				} else if (ip.matches(regex_ip_noport)) {
					hostname = ip;
				} else if (ip.matches(regex_host_noport)) {
					hostname = ip;
				} else {
					return null;
				}
			}
			serverConnection = new Socket(InetAddress.getByName(hostname), port);

			System.out.println("[MP-Server] Connection to " + hostname + ":" + port);

			File mpFolder = new File("Data/lvl/mpcache/" + hostname + "/" + key);
			if (!mpFolder.exists())
				mpFolder.mkdirs();

			cfg = "Data/lvl/mpcache/" + hostname + "/" + key + "/" + "tmp.cfg";

			LevelType levelType = LevelType.NONE;
			String levelName = "";

			sendToServer("--getLevelType", serverConnection);
			String returnString = getFromServer();
			switch (returnString) {
				case "xml": {
					levelType = LevelType.XML;
					levelName = "Data/lvl/mpcache/" + hostname + "/" + key + "/level.xml";
					break;
				}
				case "lvl": {
					levelType = LevelType.LVL;
					levelName = "Data/lvl/mpcache/" + hostname + "/" + key + "/" + "tmp.lvl";
					cfg = "Data/lvl/mpcache/" + hostname + "/" + key + "/" + "tmp.cfg";
					break;
				}
				default: {
					if (returnString.startsWith("undef-")) {
						levelType = LevelType.UNDEFINED;
						levelName = "Data/lvl/mpcache/" + hostname + "/" + key + "/" + returnString.substring(returnString.indexOf('-') + 1);
					}
				}
			}
			
			File tmp = new File("Data/lvl/mpcache");
			tmp.getParentFile().mkdirs();
			
			tmp = new File("Data/lvl/mpcache/" + hostname);
			tmp.getParentFile().mkdirs();
			
			tmp = new File("Data/lvl/mpcache/" + hostname + "/" + key);
			tmp.getParentFile().mkdirs();
			
			/* Level File */
			File levelFile = new File(levelName);
			levelFile.getParentFile().mkdirs();
			
			String cmd = "--getLevel";
			sendToServer(cmd, serverConnection);
			String c = getFromServer();
			System.out.println("Levelfile: " + levelFile);
			FileWriter w = new FileWriter(levelFile);
			w.write(c);
			w.flush();
			w.close();

			switch (levelType) {
				case XML: {
					// no additional stuff needed
					break;
				}
				case LVL: {
					/* CFG File */
					File cfgFile = new File(cfg);

					cmd = "--getLevelCFG";
					sendToServer(cmd, serverConnection);
					c = "";
					c = getFromServer();
					w = new FileWriter(cfgFile);
					w.write(c);
					w.flush();
					w.close();
					break;
				}
				case UNDEFINED: {
					// need to implement later ...
				}
				default:
					break;
			}
			System.out.println("Setting levelfile");
			connected = true;
			System.out.println("Setting levelfile done.");
			this.setClient(true);
			switch (returnString) {
				case "xml": {
					System.out.println("Returning " + levelName);
					return levelName;
				}
				case "lvl": {
					System.out.println("Returning " + levelName);
					return levelName;
				}
				default: {
					if (returnString.startsWith("undef-")) {
						System.out.println("Returning " + "Data/lvl/mpcache/" + hostname + "/" + key + "/" + returnString.substring(returnString.indexOf('-') + 1));
						return "Data/lvl/mpcache/" + hostname + "/" + key + "/" + returnString.substring(returnString.indexOf('-') + 1);
					}
				}
			}
			this.serverConnectionThread = new Thread(this, "[MPClient] Server connection thread [" + this.serverConnection.getInetAddress().toString() + "]");
			this.serverConnectionThread.start();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	// Game player stuff

	public void set(int x, int y) {
		if (connected) {
			String cmd = "-setPlayer/" + server.getLauncher().getPlayer().getName() + "/" + x + "/" + y;
			// DatagramPacket packet = new DatagramPacket(cmd.getBytes(),
			// cmd.getBytes().length);
			sendToServer(cmd, serverConnection);
			if(!this.isClient()){
				this.server.broadcastToClientsFromSourceClient(cmd, null);
			}
		}
	}

	public void move(int dir) {
		if (connected) {
			String cmd = "-movePlayer/" + server.getLauncher().getPlayer().getName() + "/" + dir;
			// DatagramPacket packet = new DatagramPacket(cmd.getBytes(),
			// cmd.getBytes().length);
			sendToServer(cmd, serverConnection);
			if(!this.isClient()){
				this.server.broadcastToClientsFromSourceClient(cmd, null);
			}
		}
	}

	private boolean sendToServer(String cmd, Socket socket) {
		return sendToServer(cmd.getBytes(), socket);
	}

	private boolean sendToServer(byte[] data, Socket socket) {
		try {
			System.out.println("Sending to [" + socket.getInetAddress().toString() + "] => " + new String(data));
			socket.getOutputStream().write(data);
			socket.getOutputStream().flush();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	private String getFromServer() {
		StringBuilder b = new StringBuilder();
		try {
			while (serverConnection.getInputStream().available() == 0){
				try {
	        Thread.sleep(100);
        } catch (InterruptedException e) {
        }
			}
			while (serverConnection.getInputStream().available() > 0) {
				b.append((char) serverConnection.getInputStream().read());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Got connection from [" + serverConnection.getInetAddress().toString() + "] => " + b.toString());
		return b.toString();
	}

	public void loadPlayer() {
		System.out.println("Loading Player : " + server.getLauncher().getPlayer().getName());
		sendToServer("-addPlayer/" + server.getLauncher().getPlayer().getName(), serverConnection);
	}

	@Override
  public void run() {
		StringBuilder b;
		String msg = "";
	  while(this.isServerConnectionRunning()){
	  	try{
		  	while(this.serverConnection.getInputStream().available() == 0){
		  		Thread.sleep(100);
		  		if(!this.isServerConnectionRunning()){
		  			break;
		  		}
		  	}
		  	if(!isServerConnectionRunning())
		  		break;
		  	
		  	//else continue
		  	
		  	b = new StringBuilder();
		  	while(this.serverConnection.getInputStream().available() > 0){
		  		b.append((char) this.serverConnection.getInputStream().read());
		  	}
		  	msg = b.toString();
		  	try{
		  		BlockPacket p = new BlockPacket(this.serverConnection, msg);
		  		Game.getActiveLevel().onBlockPacketReceive(p);
		  	}catch(InvalidPacketContentException e){
		  		
		  	}
	  	}catch(Exception e){
	  		
	  	}
	  }
  }

	/**
	 * @return the serverConnectionRunning
	 */
	public boolean isServerConnectionRunning() {
		return serverConnectionRunning;
	}

	/**
	 * @param serverConnectionRunning the serverConnectionRunning to set
	 */
	public void setServerConnectionRunning(boolean serverConnectionRunning) {
		this.serverConnectionRunning = serverConnectionRunning;
	}

	@Override
  public void onCommand(String[] args, Socket packet, MPListenerThread thread) {
	  // TODO Auto-generated method stub
	  
  }

	@Override
  public void onCommand(String msg, Socket packet, MPListenerThread thread) {
	  // TODO Auto-generated method stub
	  
  }

	/**
	 * @return the client
	 */
	boolean isClient() {
		return client;
	}

	/**
	 * @param client the client to set
	 */
	void setClient(boolean client) {
		this.client = client;
	}

}
