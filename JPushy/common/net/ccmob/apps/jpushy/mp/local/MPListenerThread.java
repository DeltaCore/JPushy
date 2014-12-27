
package net.ccmob.apps.jpushy.mp.local;

import java.io.IOException;
import java.net.Socket;

public class MPListenerThread implements Runnable {

	private Socket	 client;
	private Thread	 t	     = null;
	private boolean	 running	= true;	;
	private MPServer	serverParent;

	public MPListenerThread(Socket client, MPServer server) {
		this.client = client;
		this.serverParent = server;
		t = new Thread(this, "[MP|" + client.getInetAddress().getHostAddress() + "]");
		t.start();
	}

	@Override
	public void run() {
		while (isRunning()) {
			try {
				StringBuilder b = new StringBuilder();
				while (client.getInputStream().available() == 0){
					Thread.sleep(100);
				}
				while (client.getInputStream().available() > 0) {
					b.append((char) client.getInputStream().read());
				}
				String msg = b.toString().trim();
				System.out.println("Connection from [" + client.getInetAddress().toString() + "] => " + msg);
				if (msg.length() > 0) {
					if (!this.serverParent.connectionMsg(msg, client)) {
						String[] cmds = msg.split("/");
						for (String s : cmds)
							System.out.println(s);
						this.serverParent.cmdHandler.onCommand(cmds, client, this);
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
