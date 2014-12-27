package net.ccmob.apps.jpushy.mp.local;

import java.net.InetAddress;
import java.net.Socket;
/**
 * 
 * @author Marcel Benning
 * 
 */
public class Connection {

	InetAddress ip;
	int port;
	String playerName;
	Socket s;
	
	public Connection(InetAddress ip, int port) {
		this.ip = ip;
		this.port = port;
	}
	
	public Connection(Socket s){
		this.s = s;
	}

	public InetAddress getIp() {
		return ip;
	}

	public void setIp(InetAddress ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	
	
}
