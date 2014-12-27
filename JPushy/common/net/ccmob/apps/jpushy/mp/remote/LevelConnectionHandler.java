package net.ccmob.apps.jpushy.mp.remote;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import net.ccmob.apps.jpushy.mp.local.MPListenerThread;

/**
 * 
 * @author Marcel Benning
 * 
 */
public class LevelConnectionHandler implements Runnable {

	private int				port;
	private LevelServer		levelServer;
	private ServerSocket	socket;
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
			LevelServerCmdHandler handler = new LevelServerCmdHandler(this.levelServer);
      socket = new ServerSocket(port);
			socket.setSoTimeout(1000);
			while (isRunning()) {
				try{
					Socket client = socket.accept();
					new MPListenerThread(client, handler);
				}catch(Exception e){
					
				}
			}
			socket.close();
			System.out.println("MpServer terminated.");
    } catch (IOException e) {
      e.printStackTrace();
    }
	}
 /*
	private void newLine() {
		System.out.println();
	}

	private void printHashLine() {
		System.out.println("##########################################################################################");
	}
*/
	public boolean sendPacket(Socket packet, String data) {
		return sendTo(data, packet);
	}

	private boolean sendTo(String cmd, Socket packet) {
		return sendTo(cmd.getBytes(), packet);
	}

	private boolean sendTo(byte[] data, Socket mPacket) {
		try {
			mPacket.getOutputStream().write(data);
			mPacket.getOutputStream().flush();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

}
