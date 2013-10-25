package JPushy.Gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.xml.ws.http.HTTPException;

import JPushy.Core.Core;
import JPushy.LevelServer.ServerCommand;
import JPushy.Listener.LevelServerListener;

/**
 * 
 * @author Marcel Benning
 * 
 */

public class LevelServerConnector extends JFrame {

	private JPanel				contentPane;
	JLabel						lblStatus;
	private LevelServerListener	actionListener;
	public DefaultListModel		listModel	= new DefaultListModel();
	public CheckBoxList			checkBoxList;
	public LevelSelector		mainGui;
	/**
	 * Create the frame.
	 */

	public ServerConnection		serverConnection;

	public LevelServerConnector(LevelSelector mainGui) {
		this.mainGui = mainGui;
		serverConnection = new ServerConnection(Core.getSettings().getSetting(Core.getSettings().defaultLevelServer), 11942);
		setTitle("Level Server");
		actionListener = new LevelServerListener(this);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 854, 437);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnServer = new JMenu("Server");
		menuBar.add(mnServer);

		JMenuItem mntmReloadList = new JMenuItem("Reload List");
		mnServer.add(mntmReloadList);
		mntmReloadList.setActionCommand("reloadlist");
		mntmReloadList.addActionListener(actionListener);

		JMenuItem mntmDownloadSelectedFiles = new JMenuItem("Download selected files");
		mnServer.add(mntmDownloadSelectedFiles);
		mntmDownloadSelectedFiles.setActionCommand("loadfile");
		mntmDownloadSelectedFiles.addActionListener(actionListener);

		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		lblStatus = new JLabel("Status : Idle");
		contentPane.add(lblStatus, BorderLayout.SOUTH);

		listModel = new DefaultListModel();
		listModel.add(0, new CheckBoxListEntry("Klick 'Server -> Reload List' to list available files on the Server", false));

		checkBoxList = new CheckBoxList(listModel);
		checkBoxList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		checkBoxList.setBackground(Color.LIGHT_GRAY);
		contentPane.add(checkBoxList, BorderLayout.CENTER);
	}

	public void newCheckList(DefaultListModel model) {
		contentPane.remove(checkBoxList);
		checkBoxList = new CheckBoxList(model);
		checkBoxList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		checkBoxList.setBackground(Color.LIGHT_GRAY);
		contentPane.add(checkBoxList, BorderLayout.CENTER);
	}

	public void setStaus(String status) {
		lblStatus.setText("Status : " + status);
	}

	public class ServerConnection {

		public final ServerCommand	ping				= new ServerCommand("ping");
		public final ServerCommand	getLevels			= new ServerCommand("getlevels");
		public final ServerCommand	downloadLevel		= new ServerCommand("loadLevel");
		public final ServerCommand	downloadLevelConfig	= new ServerCommand("loadLevel");

		private DatagramSocket		socket;
		private DatagramPacket		packet;
		private boolean				running;

		public ServerConnection(String hostname, int port) {
			try {
				socket = new DatagramSocket();
			} catch (Exception e) {
				System.err.println("Failed while create connection socket to LevelServer.");
				e.printStackTrace();
			}
		}

		public void loadLevelFromServer(String levelName) {
			sendCommand(downloadLevel, levelName);
		}

		public void loadLevelConfigFromServer(String levelName) {
			sendCommand(downloadLevelConfig, levelName);
		}

		private void sendCommand(ServerCommand cmd, String arg) {
			sendTo(cmd.getName() + "#" + arg);
		}

		public boolean sendCommand(ServerCommand cmd) {
			return sendTo(cmd.getName());
		}

		private boolean sendTo(String data) {
			return sendTo(data.getBytes());
		}

		private boolean sendTo(byte[] data) {
			try {
				DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getByName(Core.getSettings().getSetting(Core.getSettings().defaultLevelServer)), 11942);
				socket.send(packet);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}

		public String receive() {
			byte[] data = new byte[4096];
			packet = new DatagramPacket(data, data.length);
			try {
				if (checkServer()) {
					socket.receive(packet);
				} else {
					return "error";
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return new String(packet.getData()).trim();
		}

		public boolean isRunning() {
			return running;
		}

		public void setRunning(boolean running) {
			this.running = running;
		}

		private boolean checkServer() {
			return sendCommand(ping);
		}

	}

}
