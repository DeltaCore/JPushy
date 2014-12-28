
package net.ccmob.apps.jpushy.mp.local;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author Marcel Benning
 * 
 */
public class MPClient {

	MPServer	          server;
	private boolean	    connected	       = false;
	private Socket	    serverConnection	= null;

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

			if (ip == "localhost") {
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
			tmp.mkdir();
			
			tmp = new File("Data/lvl/mpcache/" + hostname);
			tmp.mkdir();
			
			tmp = new File("Data/lvl/mpcache/" + hostname + "/" + key);
			tmp.mkdir();
			
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

			switch (returnString) {
				case "xml": {
					return "Data/lvl/mpcache/" + hostname + "/" + key + "/level.xml";
				}
				case "lvl": {
					return "Data/lvl/mpcache/" + hostname + "/" + key + "/" + "tmp.lvl";
				}
				default: {
					if (returnString.startsWith("undef-")) {
						return "Data/lvl/mpcache/" + hostname + "/" + key + "/" + returnString.substring(returnString.indexOf('-') + 1);
					}
				}
			}
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
		}
	}

	public void move(int dir) {
		if (connected) {
			String cmd = "-movePlayer/" + server.getLauncher().getPlayer().getName() + "/" + dir;
			// DatagramPacket packet = new DatagramPacket(cmd.getBytes(),
			// cmd.getBytes().length);
			sendToServer(cmd, serverConnection);
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
	        //e.printStackTrace();
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

}
