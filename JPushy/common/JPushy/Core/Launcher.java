package JPushy.Core;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import JPushy.Core.Natives.MacOSX;
import JPushy.Core.Natives.NativeHandler;
import JPushy.Settings.Settings;

public class Launcher extends JFrame {

	private JPanel	                    contentPane;

	/**
	 * Launch the application.
	 */

	public static Settings	            settings;

	public static final NativeHandler[]	nativeHandler	= new NativeHandler[] { new MacOSX("OS X") };

	Core	                              core	        = null;

	public static void main(String[] args) {
		String osName = System.getProperty("os.name");
		for (int i = 0; i < nativeHandler.length; i++) {
			if (osName.contains(nativeHandler[i].getOsName())) {
				System.out.println(nativeHandler[i].getOsName() + " deteced. Running Native handler for this system.");
				nativeHandler[i].setupSystemProperties();
			}
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Launcher frame = new Launcher();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		System.out.println("Loading Settings ...");
		settings = new Settings();
		System.out.println(Game.name + " V" + Game.version + " is starting ...");
	}

	private JMenuBar	   mainBar	                = new JMenuBar();

	private JMenu	       levelMenu	              = new JMenu("Level");
	private JMenuItem	   startLevel	              = new JMenuItem("Start");

	private JMenu	       serverMenu	              = new JMenu("Server");
	private JMenuItem	   connectToServer	        = new JMenuItem("Connect to server");
	private JMenuItem	   connectToLevelServer	    = new JMenuItem("Connect to level server");

	private JMenu	       generalMenu	            = new JMenu("General");	                  // General
	private JMenu	       generalSettingsMenu	    = new JMenu("Settings");	                  // General->Settings
	private JMenuItem	   generalSettingsControls	= new JMenuItem("Controls");	              // General->Settings->Controls
	private JMenuItem	   generalSettingsInterface	= new JMenuItem("Interface");	            // General->Settings->Interface
	private JMenuItem	   generalUpdates	          = new JMenuItem("Updates");	              // General->Updates
	private final JLabel	stateLabel	            = new JLabel("JPushy");

	/**
	 * Create the frame.
	 */
	public Launcher() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 809, 470);

		// Adding menus
		this.setJMenuBar(mainBar);

		this.getMainBar().add(this.getLevelMenu());
		this.getLevelMenu().add(this.getStartLevel());

		this.getMainBar().add(this.getServerMenu());
		this.getServerMenu().add(this.getConnectToServer());
		this.getServerMenu().add(this.getConnectToLevelServer());

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
		mainSplitPane.setResizeWeight(0.2);
		contentPane.add(mainSplitPane, BorderLayout.CENTER);

		// Level list

		JList list = new JList();
		stateLabel.setLabelFor(list);
		list.setBackground(UIManager.getColor("CheckBox.background"));
		mainSplitPane.setRightComponent(list);

		JSplitPane levelSplitPane = new JSplitPane();
		levelSplitPane.setEnabled(false);
		levelSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		mainSplitPane.setLeftComponent(levelSplitPane);

		JButton btnStart = new JButton("Start");
		levelSplitPane.setLeftComponent(btnStart);

		JTextArea txtLevelInfo = new JTextArea();
		txtLevelInfo.setText("Select a level");
		levelSplitPane.setRightComponent(txtLevelInfo);

		contentPane.add(stateLabel, BorderLayout.SOUTH);
	}

	String	levelRegEx	      = "<level name=\"([a-zA-Z0-9\\s]{1,})\" version='([a-zA-Z0-9.,\\s]{1,})'>";
	String	commentStartRegEx	= "^<comment>";
	String	commentEndRegEx	  = "^</comment>";

	public void updateLevels() {
		File dataFolder = new File("Data");
		for (File f : dataFolder.listFiles()) {
			if (f.isFile()) {
				if (f.getName().endsWith(".lvl")) {

				}
			}
		}
	}

	public void readLevel(String filename) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
			String line = "";
			Pattern level = Pattern.compile(levelRegEx);
			Matcher levelMatcher = level.matcher("");

			Pattern commentStart = Pattern.compile(commentStartRegEx);
			Matcher commentStartMatcher = level.matcher("");

			Pattern commentEnd = Pattern.compile(commentEndRegEx);
			Matcher commentEndMatcher = level.matcher("");
			while ((line = reader.readLine()) != null) {
				levelMatcher = level.matcher(line);
				commentStartMatcher = commentStart.matcher(line);
				commentEndMatcher = commentEnd.matcher(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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

}
