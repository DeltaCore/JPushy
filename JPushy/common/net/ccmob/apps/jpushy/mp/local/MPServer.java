package net.ccmob.apps.jpushy.mp.local;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Random;

import net.ccmob.apps.jpushy.core.Game;
import net.ccmob.apps.jpushy.core.LevelThread;

/**
 * 
 * @author Marcel Benning
 * 
 */
public class MPServer extends Thread {

	private LevelThread	    launcher;

	private int	            port	      = 11941;
	private boolean	        running	    = true;
	public MPCommandHandler	cmdHandler;
	int	                    mode	      = 0;
	private ServerSocket	socket;

	ArrayList<Connection>	  connections	= new ArrayList<Connection>();

	public MPServer(LevelThread gui) {
		cmdHandler = new MPCommandHandler(launcher, this);
		this.launcher = gui;
	}

	private void newConnection(Socket packet) {
		InetAddress ip = packet.getInetAddress();
		int port = packet.getPort();
		if (!checkInetAddress(ip) && !checkPort(port)) {
			connections.add(new Connection(packet));
		}
	}

	private boolean checkPort(int port) {
		for (int i = 0; i < connections.size(); i++) {
			if (connections.get(i).getPort() == port) {
				return true;
			}
		}
		return false;
	}

	private boolean checkInetAddress(InetAddress ip) {
		for (int i = 0; i < connections.size(); i++) {
			if (connections.get(i).getIp() == ip) {
				return true;
			}
		}
		return false;
	}

	public Connection getConnection(Socket packet) {
		InetAddress ip = packet.getInetAddress();
		int port = packet.getPort();
		for (int i = 0; i < connections.size(); i++) {
			if (ip.equals(connections.get(i).getIp())) {
				if (port == (connections.get(i).getPort())) {
					return connections.get(i);
				}
			}
		}
		return null;
	}

	public boolean updateConnection(Connection c) {
		InetAddress ip = c.getIp();
		int port = c.getPort();
		for (int i = 0; i < connections.size(); i++) {
			if (ip.equals(connections.get(i).getIp())) {
				if (port == (connections.get(i).getPort())) {
					connections.set(i, c);
					return true;
				}
			}
		}
		return false;
	}

	private String packFile(String filename) {
		String returnString = "";
		File f = new File("Data/lvl/" + filename);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line;
			while ((line = reader.readLine()) != null) {
				returnString += line + String.format("%n");
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnString;
	}

	@Override
	public void run() {
		if (mode == 0) {
			startServer();
		}
	}

	private void startServer() {
			try {
	      socket = new ServerSocket(port);
	   // cmdHandler = new MPCommandHandler(levelHandler, this);
				String msg;
				socket.setSoTimeout(1000);
				Socket client = null;
				while (isRunning()) {
					try{
						client = socket.accept();
					}catch(Exception e){
						
					}
					if(client == null){
						
					}else{
						newConnection(client);
						StringBuilder b = new StringBuilder();
						while(client.getInputStream().available() > 0){
							b.append(client.getInputStream().read());
						}
						msg = b.toString().trim();
						if (msg.length() > 0) {
							if (!connectionMsg(msg, client)) {
								String[] cmds = msg.split("/");
								for (String s : cmds)
									System.out.println(s);
								cmdHandler.onCommand(cmds, client);
							}
						}
					}
					client = null;
				}
				socket.close();
				System.out.println("MpServer terminated.");
      } catch (IOException e) {
	      e.printStackTrace();
      } // open port
	}

	public ArrayList<Connection> getConnections() {
		return connections;
	}

	private boolean connectionMsg(String msg, Socket packet) {
		if (msg.equalsIgnoreCase("--getLevel")) {
			String fileContent = packFile(Game.getLevel().getFileName());
			Connection c = getConnection(packet);
			sendTo(fileContent, c);
			return true;
		} else if (msg.equalsIgnoreCase("--getLevelCFG")) {
			String fileContent = packFile(Game.getLevel().getFileName().replace(".lvl", ".cfg"));
			Connection c = getConnection(packet);
			sendTo(fileContent, c);
			return true;
		}
		return false;
	}

	private boolean sendTo(String cmd, Connection c) {
		return sendTo(cmd.getBytes(), c);
	}

	private boolean sendTo(byte[] data, Connection c) {
		try {
			//this.socket.
			c.s.getOutputStream().write(data);
			c.s.getOutputStream().flush();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	// IO Stuff
	/*
	private static ArrayList<String> loadLevelFile(String filename) {
		ArrayList<String> returnString = new ArrayList<String>();
		File f = new File("Data/lvl/" + filename);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line;
			while ((line = reader.readLine()) != null) {
				returnString.add(line);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnString;
	}

	private static ArrayList<String> loadLevelConfig(String filename) {
		ArrayList<String> returnString = new ArrayList<String>();
		String modPath = filename.replace(".lvl", ".cfg");
		File f = new File("Data/lvl/" + modPath);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line;
			while ((line = reader.readLine()) != null) {
				returnString.add(line);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnString;
	}
*/
	public static String getRandomKey() {
		char[] aChars = { 'A', 'B', 'C', 'D', 'E', 'F', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };
		String key = "";
		Random r = new Random();
		for (int i = 0; i < 8; i++) {
			int c = r.nextInt(aChars.length);
			key += aChars[c];
		}
		return key;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public LevelThread getLauncher() {
		return launcher;
	}

	public void setLauncher(LevelThread launcher) {
		this.launcher = launcher;
	}

}
