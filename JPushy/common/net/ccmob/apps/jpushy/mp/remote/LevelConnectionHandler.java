package net.ccmob.apps.jpushy.mp.remote;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * 
 * @author Marcel Benning
 * 
 */
public class LevelConnectionHandler implements Runnable {

	private int				port;
	private LevelServer		levelServer;
	private DatagramSocket	socket;
	private boolean			running;

	public LevelConnectionHandler(int port, LevelServer mainServer) {
		this.setPort(port);
		this.setLevelServer(mainServer);
		this.setRunning(true);
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public LevelServer getLevelServer() {
		return levelServer;
	}

	public void setLevelServer(LevelServer levelServer) {
		this.levelServer = levelServer;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	@Override
	public void run() {
		try {
			socket = new DatagramSocket(port); // open port
			byte[] data = new byte[1024];
			DatagramPacket packet;
			String msg;
			while (isRunning()) {
				data = new byte[1024];
				packet = new DatagramPacket(data, data.length);
				printHashLine();
				newLine();
				newLine();
				try {
					socket.receive(packet);
				} catch (IOException e) {
					e.printStackTrace();
				}
				msg = new String(packet.getData()).trim();
				System.out.println("Connection from : " + packet.getAddress().toString() + ":" + packet.getPort() + " - " + packet.getAddress().getHostName().toString() + "\nMSG : " + msg);
				newLine();
				levelServer.getCMDHandler().onCommand(msg, packet);
				newLine();
				newLine();
				printHashLine();
				newLine();
				newLine();
				newLine();
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	private void newLine() {
		System.out.println();
	}

	private void printHashLine() {
		System.out.println("##########################################################################################");
	}

	public boolean sendPacket(DatagramPacket packet, String data) {
		return sendTo(data, packet);
	}

	private boolean sendTo(String cmd, DatagramPacket packet) {
		return sendTo(cmd.getBytes(), packet);
	}

	private boolean sendTo(byte[] data, DatagramPacket mPacket) {
		try {
			DatagramPacket packet = new DatagramPacket(data, data.length, mPacket.getSocketAddress());
			socket.send(packet);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

}