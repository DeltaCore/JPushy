package JPushy.MultiPlayer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Random;

import JPushy.Core.LevelScheduler;

/**
 * 
 * @author Marcel Benning
 * 
 */
public class MPServer extends Thread {

	private LevelScheduler	levelHandler;

	private int	            port	      = 11941;
	private boolean	        running	    = true;
	public MPCommandHandler	cmdHandler;
	int	                    mode	      = 0;
	private DatagramSocket	socket;

	ArrayList<Connection>	  connections	= new ArrayList<Connection>();

	public MPServer(int port, LevelScheduler levelHandler, int mode) {
		this.port = port;
		this.levelHandler = levelHandler;
		cmdHandler = new MPCommandHandler(levelHandler, this);
		this.mode = mode;
	}

	public MPServer() {
	}

	public void init(LevelScheduler levelHandler, int mode, int port) {
		this.port = port;
		this.levelHandler = levelHandler;
		cmdHandler = new MPCommandHandler(levelHandler, this);
		this.mode = mode;
	}

	public MPServer(LevelScheduler levelHandler, int mode) {
		this(11941, levelHandler, mode);
	}

	private void newConnection(DatagramPacket packet) {
		InetAddress ip = packet.getAddress();
		int port = packet.getPort();
		if (!checkInetAddress(ip) && !checkPort(port)) {
			connections.add(new Connection(ip, port));
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

	public Connection getConnection(DatagramPacket packet) {
		InetAddress ip = packet.getAddress();
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
			socket = new DatagramSocket(port); // open port
			byte[] data = new byte[1024];
			cmdHandler = new MPCommandHandler(levelHandler, this);
			DatagramPacket packet;
			String msg;
			socket.setSoTimeout(1000);
			while (isRunning()) {
				packet = new DatagramPacket(data, data.length);
				try {
					socket.receive(packet);
				} catch (IOException e) {
				}
				newConnection(packet);
				msg = new String(packet.getData()).trim();
				if (msg.length() > 0) {
					if (!connectionMsg(msg, packet)) {
						String[] cmds = msg.split("/");
						for (String s : cmds)
							System.out.println(s);
						cmdHandler.onCommand(cmds, packet);
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Connection> getConnections() {
		return connections;
	}

	private boolean connectionMsg(String msg, DatagramPacket packet) {
		if (msg.equalsIgnoreCase("--getLevel")) {
			String fileContent = packFile(levelHandler.getFilename());
			Connection c = getConnection(packet);
			sendTo(fileContent, c);
			return true;
		} else if (msg.equalsIgnoreCase("--getLevelCFG")) {
			String fileContent = packFile(levelHandler.getFilename().replace(".lvl", ".cfg"));
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
		DatagramPacket packet = new DatagramPacket(data, data.length, c.getIp(), c.getPort());
		try {
			socket.send(packet);
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

	private static ArrayList<String> loadLevelFile(String filename) {
		ArrayList<String> returnString = new ArrayList<String>();
		File f = new File("Data/lvl/" + filename);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line;
			while ((line = reader.readLine()) != null) {
				returnString.add(line);
			}
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
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnString;
	}

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

	public LevelScheduler getLevelHandler() {
		return levelHandler;
	}

	public void setLevelHandler(LevelScheduler levelHandler) {
		this.levelHandler = levelHandler;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
