package JPushy.Core;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import JPushy.Gui.LevelServerConnector;
import JPushy.MultiPlayer.MPClient;
import JPushy.Types.Level.Level;
import JPushy.Types.Level.LevelItem;
import JPushy.Types.Player.Player;

public class Game extends JFrame {

	private JPanel	               contentPane;

	/**
	 * Launch the application.
	 */

	public static final String	   name	                    = "JPushy";
	public static final String	   version	                = "0.1";

	LevelThread	                   thread;

	private DefaultListModel	     levelModel	              = new DefaultListModel();

	private JMenuBar	             mainBar	                = new JMenuBar();

	private JMenu	                 levelMenu	              = new JMenu("Level");
	private JMenuItem	             startLevel	              = new JMenuItem("Start");

	private JMenu	                 serverMenu	              = new JMenu("Server");
	private JMenuItem	             connectToServer	        = new JMenuItem("Connect to server");
	private JMenuItem	             connectToLevelServer	    = new JMenuItem("Connect to level server");

	private JMenu	                 generalMenu	            = new JMenu("General");	                  // General
	private JMenu	                 generalSettingsMenu	    = new JMenu("Settings");	                  // General->Settings
	private JMenuItem	             generalSettingsControls	= new JMenuItem("Controls");	              // General->Settings->Controls
	private JMenuItem	             generalSettingsInterface	= new JMenuItem("Interface");	            // General->Settings->Interface
	private JMenuItem	             generalUpdates	          = new JMenuItem("Updates");	              // General->Updates
	private final JLabel	         stateLabel	              = new JLabel("JPushy");

	private JList	                 levelList	              = new JList();
	private JTextArea	             txtLevelInfo	            = new JTextArea();
	private LevelSelectionListener	listener;

	ArrayList<LevelItem>	         levels	                  = new ArrayList<LevelItem>();

	/**
	 * Create the frame.
	 */
	public Game() {
		super(Game.name + " V" + Game.version);
		this.listener = new LevelSelectionListener(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 809, 470);

		// Adding menus
		this.setJMenuBar(mainBar);

		this.getMainBar().add(this.getLevelMenu());
		this.getLevelMenu().add(this.getStartLevel());

		this.getMainBar().add(this.getServerMenu());
		this.getServerMenu().add(this.getConnectToServer());
		this.getServerMenu().add(this.getConnectToLevelServer());
		this.getConnectToLevelServer().setActionCommand("connecttolevelserver");
		this.getConnectToLevelServer().addActionListener(listener);

		this.getConnectToServer().setActionCommand("connectToServer");
		this.getConnectToServer().addActionListener(listener);

		this.getMainBar().add(this.getGeneralMenu());
		this.getGeneralMenu().add(this.getGeneralSettingsMenu());
		this.getGeneralSettingsMenu().add(this.getGeneralSettingsControls());
		this.getGeneralSettingsMenu().add(this.getGeneralSettingsInterface());
		this.getGeneralMenu().add(this.getGeneralUpdates());

		// Setting content pane

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		// Main splitpane

		JSplitPane mainSplitPane = new JSplitPane();
		mainSplitPane.setResizeWeight(0.4);
		contentPane.add(mainSplitPane, BorderLayout.CENTER);

		// Level list

		stateLabel.setLabelFor(levelList);
		levelList.setBackground(UIManager.getColor("CheckBox.background"));
		levelList.addListSelectionListener(listener);
		mainSplitPane.setRightComponent(levelList);

		JSplitPane levelSplitPane = new JSplitPane();
		levelSplitPane.setEnabled(false);
		levelSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		mainSplitPane.setLeftComponent(levelSplitPane);

		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(listener);
		btnStart.setActionCommand("start");
		levelSplitPane.setLeftComponent(btnStart);

		txtLevelInfo.setText("Select a level");
		levelSplitPane.setRightComponent(txtLevelInfo);

		contentPane.add(stateLabel, BorderLayout.SOUTH);
		updateLevels();
	}

	String	levelRegEx	      = "^<level name=\"([a-zA-Z\\s0-9[-]]{1,})\" version='([a-zA-Z0-9.,]{1,})'>$";
	String	commentStartRegEx	= "^<comment>";
	String	commentEndRegEx	  = "^</comment>";

	public void updateSelectedLevel() {
		this.txtLevelInfo.setText(this.levels.get(this.levelList.getSelectedIndex()).getComment());
	}

	public void updateLevels() {
		levels = new ArrayList<LevelItem>();
		File dataFolder = new File("Data/lvl/");
		for (File f : dataFolder.listFiles()) {
			if (f.isFile()) {
				if (f.getName().endsWith(".lvl")) {
					levels.add(readLevel(f.getName()));
				}
			}
		}
		System.out.println("Levels total : " + levels.size());
		this.setLevelModel(new DefaultListModel());
		for (int i = 0; i < levels.size(); i++) {
			this.getLevelModel().addElement(levels.get(i).toString());
		}
		this.levelList.setModel(this.getLevelModel());
	}

	public LevelItem readLevel(String filename) {
		LevelItem levelItem = new LevelItem();
		try {
			String comment = "";
			BufferedReader reader = new BufferedReader(new FileReader(new File("Data/lvl/" + filename)));
			String line = "";
			Pattern level = Pattern.compile(levelRegEx);
			Matcher levelMatcher = level.matcher("");

			Pattern commentStart = Pattern.compile(commentStartRegEx);
			Matcher commentStartMatcher = level.matcher("");

			Pattern commentEnd = Pattern.compile(commentEndRegEx);
			Matcher commentEndMatcher = level.matcher("");
			boolean flag = false;
			while ((line = reader.readLine()) != null) {
				levelMatcher = level.matcher(line);
				commentStartMatcher = commentStart.matcher(line);
				commentEndMatcher = commentEnd.matcher(line);
				if (levelMatcher.matches()) {
					levelItem.setName(levelMatcher.group(1));
					levelItem.setVersion(levelMatcher.group(2));
					System.out.println("[Level] Name : " + levelMatcher.group(1) + " version : " + levelMatcher.group(2) + " file: " + filename);
				} else if (commentStartMatcher.matches()) {
					flag = true;
				} else if (commentEndMatcher.matches()) {
					flag = false;
				} else if (flag) {
					comment += line + "\n";
				}
			}
			levelItem.setComment(comment);
			levelItem.setFile(filename);
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return levelItem;
	}

	public void startGame() {
		this.thread = new LevelThread(this, levels.get(levelList.getSelectedIndex()));
		Thread t = new Thread(thread);
		t.start();
	}

	public void openConnection() {
		Thread t = new Thread(thread);
		t.start();
	}

	public void startGame(String filename) {
		this.thread = new LevelThread(filename);
		Thread t = new Thread(thread);
		t.start();
	}

	public JMenuBar getMainBar() {
		return mainBar;
	}

	public void setMainBar(JMenuBar mainBar) {
		this.mainBar = mainBar;
	}

	public JMenu getLevelMenu() {
		return levelMenu;
	}

	public void setLevelMenu(JMenu levelMenu) {
		this.levelMenu = levelMenu;
	}

	public JMenuItem getStartLevel() {
		return startLevel;
	}

	public void setStartLevel(JMenuItem startLevel) {
		this.startLevel = startLevel;
	}

	public JMenu getServerMenu() {
		return serverMenu;
	}

	public void setServerMenu(JMenu serverMenu) {
		this.serverMenu = serverMenu;
	}

	public JMenuItem getConnectToServer() {
		return connectToServer;
	}

	public void setConnectToServer(JMenuItem connectToServer) {
		this.connectToServer = connectToServer;
	}

	public JMenuItem getConnectToLevelServer() {
		return connectToLevelServer;
	}

	public void setConnectToLevelServer(JMenuItem connectToLevelServer) {
		this.connectToLevelServer = connectToLevelServer;
	}

	public JMenu getGeneralMenu() {
		return generalMenu;
	}

	public void setGeneralMenu(JMenu generalMenu) {
		this.generalMenu = generalMenu;
	}

	public JMenu getGeneralSettingsMenu() {
		return generalSettingsMenu;
	}

	public void setGeneralSettingsMenu(JMenu generalSettingsMenu) {
		this.generalSettingsMenu = generalSettingsMenu;
	}

	public JMenuItem getGeneralSettingsControls() {
		return generalSettingsControls;
	}

	public void setGeneralSettingsControls(JMenuItem generalSettingsControls) {
		this.generalSettingsControls = generalSettingsControls;
	}

	public JMenuItem getGeneralSettingsInterface() {
		return generalSettingsInterface;
	}

	public void setGeneralSettingsInterface(JMenuItem generalSettingsInterface) {
		this.generalSettingsInterface = generalSettingsInterface;
	}

	public JMenuItem getGeneralUpdates() {
		return generalUpdates;
	}

	public void setGeneralUpdates(JMenuItem generalUpdates) {
		this.generalUpdates = generalUpdates;
	}

	public DefaultListModel getLevelModel() {
		return levelModel;
	}

	public void setLevelModel(DefaultListModel levelModel) {
		this.levelModel = levelModel;
	}

	public static Player getPlayer() {
		return Launcher.getInstance().thread.getPlayer();
	}

	public static void pushUpdate() {
		Launcher.getInstance().thread.update();
	}

	public static void sendMessage(String msg) {
		Launcher.getInstance().thread.sendMessage(msg);
	}

	public static Level getLevel() {
		return Launcher.getInstance().thread.getLevel();
	}

	public JList getLevelList() {
		return levelList;
	}

	public void setLevelList(JList levelList) {
		this.levelList = levelList;
	}

	private class LevelSelectionListener implements ActionListener, ListSelectionListener {

		Game	gui;

		public LevelSelectionListener(Game gui) {
			this.gui = gui;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println(arg0.getActionCommand());
			if (arg0.getActionCommand().equals("start")) {
				gui.startGame();
			} else if (arg0.getActionCommand().equals("connectToServer")) {
				String ip = JOptionPane.showInputDialog("Server ip :");
				String username = JOptionPane.showInputDialog("Username : ");
				gui.connect(ip);
				Game.getPlayer().setName(username);
				gui.thread.getClient().loadPlayer();
				gui.openConnection();
			} else if (arg0.getActionCommand().equals("connecttolevelserver")) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						LevelServerConnector con = new LevelServerConnector(gui);
						con.setVisible(true);
					}
				});
			}
		}

		@Override
		public void valueChanged(ListSelectionEvent e) {
			gui.updateSelectedLevel();
		}

	}

	public static Level getActiveLevel() {
		return Launcher.getInstance().thread.getLevel();
	}

	public void connect(String ip) {
		this.thread = new LevelThread(ip);
	}

	public static void selectLevel(int id) {
		Launcher.getInstance().thread.getLevel().setActiveStage(id);
	}

	public static MPClient getClient() {
		return Launcher.getInstance().thread.getClient();
	}

	public static void stopGame() {
		Launcher.getInstance().thread.getServer().setRunning(false);
		try {
			Thread.sleep(1000);
		} catch (Exception e) {

		}
		Launcher.getInstance().thread.dispose();
	}

}
