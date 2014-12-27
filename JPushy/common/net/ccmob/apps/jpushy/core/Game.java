
package net.ccmob.apps.jpushy.core;

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
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.ccmob.apps.jpushy.modloader.ModLoader;
import net.ccmob.apps.jpushy.mp.local.MPClient;
import net.ccmob.apps.jpushy.sp.level.Level;
import net.ccmob.apps.jpushy.sp.level.LevelItem;
import net.ccmob.apps.jpushy.sp.level.editor.LevelEditorThread;
import net.ccmob.apps.jpushy.sp.player.Player;
import net.ccmob.apps.jpushy.ui.LevelServerConnector;
import net.ccmob.xml.XMLConfig;
import net.ccmob.xml.XMLConfig.XMLNode;

public class Game extends JFrame {

	private static final long	       serialVersionUID	        = 1L;

	private JPanel	                 contentPane;

	/**
	 * Launch the application.
	 */

	public static final String	     name	                    = "JPushy";
	public static final String	     version	                = "0.2.3";

	LevelThread	                     thread;

	private DefaultListModel<String>	levelModel	            = new DefaultListModel<String>();

	private JMenuBar	               mainBar	                = new JMenuBar();

	private JMenu	                   levelMenu	              = new JMenu("Level");
	private JMenuItem	               startLevel	              = new JMenuItem("Start");
	private JMenuItem	               createLevel	            = new JMenuItem("Open Leveleditor");

	private JMenu	                   serverMenu	              = new JMenu("Server");
	private JMenuItem	               connectToServer	        = new JMenuItem("Connect to server");
	private JMenuItem	               connectToLevelServer	    = new JMenuItem("Connect to level server");

	private JMenu	                   generalMenu	            = new JMenu("General");	                  // General
	private JMenu	                   generalSettingsMenu	    = new JMenu("Settings");	                  // General->Settings
	private JMenuItem	               generalSettingsControls	= new JMenuItem("Controls");	              // General->Settings->Controls
	private JMenuItem	               generalSettingsInterface	= new JMenuItem("Interface");	            // General->Settings->Interface
	private JMenuItem	               generalUpdates	          = new JMenuItem("Updates");	              // General->Updates
	private final JLabel	           stateLabel	              = new JLabel("JPushy");

	private JList<String>	                   levelList	              = new JList<String>();
	private JTextArea	               txtLevelInfo	            = new JTextArea();
	private LevelSelectionListener	 listener;

	ArrayList<LevelItem>	           levels	                  = new ArrayList<LevelItem>();

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
		this.getLevelMenu().add(this.getCreateLevel());
		this.getCreateLevel().setActionCommand("createLevel");
		this.getCreateLevel().addActionListener(listener);

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
		contentPane.setBorder(null);
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
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(levelList);
		mainSplitPane.setRightComponent(scrollPane);
		mainSplitPane.setBorder(null);

		JSplitPane levelSplitPane = new JSplitPane();
		levelSplitPane.setBorder(null);
		levelSplitPane.setEnabled(false);
		levelSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		mainSplitPane.setLeftComponent(levelSplitPane);

		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(listener);
		btnStart.setActionCommand("start");
		levelSplitPane.setLeftComponent(btnStart);

		txtLevelInfo.setText("Select a level");
		txtLevelInfo.setEditable(false);
		levelSplitPane.setRightComponent(txtLevelInfo);

		contentPane.add(stateLabel, BorderLayout.SOUTH);
		updateLevels();
		System.out.println("[ModLoader] Initializing mods ...");
		ModLoader.onInit();
		System.out.println("[ModLoader] Postloading mods ...");
		ModLoader.onPostInit();
	}

	String	levelRegEx	      = "^<level name=\"([a-zA-Z\\s0-9[-][_]]{1,})\" version='([a-zA-Z0-9.,]{1,})'>$";
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
				}else if(f.getName().endsWith(".xml")){
					levels.add(readXMLLevel(f.getName()));
				}
			}
		}
		this.setLevelModel(new DefaultListModel<String>());
		for (int i = 0; i < levels.size(); i++) {
			this.getLevelModel().addElement(levels.get(i).toString());
		}
		this.levelList.setModel(this.getLevelModel());
	}
	
	public LevelItem readXMLLevel(String fileName){
		LevelItem level = new LevelItem();
		level.setFile(fileName);
		XMLConfig levelFile = new XMLConfig("Data/lvl/" + fileName);
		XMLNode rootNode = levelFile.getRootNode();
		if (rootNode.getName().equals("level")) {
			XMLNode levelNode = rootNode;
			if (levelNode.attributeExists("name")) {
				level.setName((String) levelNode.getAttribute("name").getAttributeValue());
			} else {
				level.setName("Unnamed level");
			}
			if(levelNode.attributeExists("version")){
				level.setVersion((String) levelNode.getAttribute("version").getAttributeValue());
			}
			if (levelNode.attributeExists("comment")) {
				if (levelNode.getChild("comment").nodeExists("line")) {
					ArrayList<String> lines = new ArrayList<String>();
					for (XMLNode commentLine : levelNode.getChild("comment").getChilds()) {
						if (commentLine.attributeExists("value"))
							lines.add((String) commentLine.getAttribute("value").getAttributeValue());
					}
				}
			}
		}
		return level;
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
					//System.out.println("[Level] Name : " + levelMatcher.group(1) + " version : " + levelMatcher.group(2) + " file: " + filename);
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
		if(levelList.getSelectedIndex() != -1){
			this.thread = new LevelThread(this, levels.get(levelList.getSelectedIndex()));
			Thread t = new Thread(thread);
			t.start();
		}
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

	public DefaultListModel<String> getLevelModel() {
		return levelModel;
	}

	public void setLevelModel(DefaultListModel<String> levelModel) {
		this.levelModel = levelModel;
	}

	public static Player getPlayer() {
		return JPushy.getInstance().thread.getPlayer();
	}

	public static void pushUpdate() {
		JPushy.getInstance().thread.update();
	}

	public static void sendMessage(String msg) {
		JPushy.getInstance().thread.sendMessage(msg);
	}

	public static Level getLevel() {
		return JPushy.getInstance().thread.getLevel();
	}

	public JList<String> getLevelList() {
		return levelList;
	}

	public void setLevelList(JList<String> levelList) {
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
			} else if (arg0.getActionCommand().equals("createLevel")) {
				new LevelEditorThread(this.gui);
			}
		}

		@Override
		public void valueChanged(ListSelectionEvent e) {
			gui.updateSelectedLevel();
		}

	}

	public static Level getActiveLevel() {
		return JPushy.getInstance().thread.getLevel();
	}

	public void connect(String ip) {
		this.thread = new LevelThread(ip);
	}

	public static void selectLevel(int id) {
		JPushy.getInstance().thread.getLevel().setActiveStage(id);
	}

	public static MPClient getClient() {
		return JPushy.getInstance().thread.getClient();
	}

	public static void stopGame() {
		JPushy.getInstance().thread.getServer().setRunning(false);
		try {
			Thread.sleep(1000);
		} catch (Exception e) {

		}
		JPushy.getInstance().thread.dispose();
	}

	/**
	 * @return the createLevel
	 */
	public JMenuItem getCreateLevel() {
		return createLevel;
	}

	/**
	 * @param createLevel
	 *          the createLevel to set
	 */
	public void setCreateLevel(JMenuItem createLevel) {
		this.createLevel = createLevel;
	}

}
