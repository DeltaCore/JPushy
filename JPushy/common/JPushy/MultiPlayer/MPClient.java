package JPushy.MultiPlayer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author Marcel Benning
 * 
 */
public class MPClient {

	MPServer	             server;
	private InetAddress	   serverIp;
	private int	           port;
	private DatagramSocket	socket;
	private boolean	       connected	= false;

	public MPClient(MPServer server) {
		this.server = server;
	}

	public String join(String ip) {
		String lvl;
		String hostname = "";
		int port = 11941;
		String key = server.getRandomKey();
		try {
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
			}

			if (ip == "localhost") {
				hostname = ip;
			}

			this.serverIp = InetAddress.getByName(hostname);
			this.port = port;
			socket = new DatagramSocket();

			System.out.println("[MP-Server] Connection to " + hostname + ":" + port);

			File mpFolder = new File("Data/lvl/mpcache/" + hostname + "/" + key);
			if (!mpFolder.exists())
				mpFolder.mkdirs();

			lvl = "Data/lvl/mpcache/" + hostname + "/" + key + "/" + "tmp.lvl";
			String cfg = "Data/lvl/mpcache/" + hostname + "/" + key + "/" + "tmp.cfg";

			/* Level File */
			File levelFile = new File(lvl);

			String cmd = "--getLevel";
			sendToServer(cmd);
			String c = getFromServer();
			FileWriter w = new FileWriter(levelFile);
			w.write(c);
			w.flush();
			w.close();
			/* CFG File */
			File cfgFile = new File(cfg);

			cmd = "--getLevelCFG";
			sendToServer(cmd);
			c = "";
			c = getFromServer();
			w = new FileWriter(cfgFile);
			w.write(c);
			w.flush();
			w.close();

			System.out.println("Setting levelfile");
			connected = true;
			System.out.println("Setting levelfile done.");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return "mpcache/" + hostname + "/" + key + "/" + "tmp.lvl";
	}

	// Game player stuff

	public void set(int x, int y) {
		if (connected) {
			String cmd = "-setPlayer/" + server.getLauncher().getPlayer().getName() + "/" + x + "/" + y;
			DatagramPacket packet = new DatagramPacket(cmd.getBytes(), cmd.getBytes().length);
			sendToServer(cmd);
		}
	}

	public void move(int dir) {
		if (connected) {
			String cmd = "-movePlayer/" + server.getLauncher().getPlayer().getName() + "/" + dir;
			DatagramPacket packet = new DatagramPacket(cmd.getBytes(), cmd.getBytes().length);
			sendToServer(cmd);
		}
	}

	private boolean sendToServer(String cmd) {
		return sendToServer(cmd.getBytes());
	}

	private boolean sendToServer(byte[] data) {
		DatagramPacket packet = new DatagramPacket(data, data.length, this.serverIp, this.port);
		try {
			socket.send(packet);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	private String getFromServer() {
		byte[] data = new byte[1024];
		DatagramPacket packet = new DatagramPacket(data, data.length);
		String msg = "";
		try {
			socket.receive(packet);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		msg = new String(packet.getData()).trim();
		return msg;
	}

	public void loadPlayer() {
		System.out.println("Loading Player : " + server.getLauncher().getPlayer().getName());
		sendToServer("-addPlayer/" + server.getLauncher().getPlayer().getName());
	}

}
