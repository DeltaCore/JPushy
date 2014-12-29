
package net.ccmob.apps.jpushy.mp.local;

import java.io.IOException;
import java.net.Socket;

import net.ccmob.apps.jpushy.core.Game;
import net.ccmob.apps.jpushy.mp.remote.BlockPacket;
import net.ccmob.apps.jpushy.mp.remote.BlockPacket.InvalidPacketContentException;

public class MPListenerThread implements Runnable {

	private Socket	        client;
	private Thread	        t	      = null;
	private boolean	        running	= true;	;
	private ICommandHandler	handler;

	public MPListenerThread(Socket client, ICommandHandler h) {
		this.client = client;
		this.handler = h;
		t = new Thread(this, "[MP|" + client.getInetAddress().getHostAddress() + "]");
		t.start();
	}

	@Override
	public void run() {
		BlockPacket packet;
		StringBuilder b;
		while (isRunning()) {
			try {
				b = new StringBuilder();
				while (client.getInputStream().available() == 0) {
					Thread.sleep(100);
					if (!this.isRunning()) {
						break;
					}
				}
				if (!this.isRunning()) {
					break;
				}
				while (client.getInputStream().available() > 0) {
					b.append((char) client.getInputStream().read());
				}
				String msg = b.toString().trim();
				System.out.println("Connection from [" + client.getInetAddress().toString() + "] => " + msg);
				if (msg.length() > 0) {
					if (!MPServer.connectionMsg(msg, client)) {
						try {
							packet = new BlockPacket(client, msg);
							Game.getActiveLevel().onBlockPacketReceive(packet);
						} catch (InvalidPacketContentException e) {
							String[] cmds = msg.split("/");
							for (String s : cmds)
								System.out.println(s);
							this.handler.onCommand(cmds, client, this);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			this.client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the running
	 */
	private boolean isRunning() {
		return running;
	}

	/**
	 * @param running
	 *          the running to set
	 */
	void setRunning(boolean running) {
		this.running = running;
	}

}
