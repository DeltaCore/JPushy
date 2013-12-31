package JPushy.LevelEditor;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

public class LevelEditorGui extends JFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LevelEditorGui frame = new LevelEditorGui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// JMenu

	private JMenuBar	   mainMenuBar	          = new JMenuBar();
	private JMenu	       mainMenu	              = new JMenu("Level");
	private JMenuItem	   levelLoad	            = new JMenuItem("Load");
	private JMenuItem	   levelSave	            = new JMenuItem("Save");

	// JSplitpane

	private JSplitPane	 splitPane	            = new JSplitPane();

	// JSpinner

	private JSpinner	   xsize	                = new JSpinner();
	private JSpinner	   ysize	                = new JSpinner();
	private JSpinner	   layer	                = new JSpinner();
	private JSpinner	   optcoordx	            = new JSpinner();
	private JSpinner	   optcoordy	            = new JSpinner();

	// JLabel

	private JLabel	     lblLevelsettings	      = new JLabel("LevelSettings");
	private JLabel	     lblXSize	              = new JLabel("X Size :");
	private JLabel	     lblYSize	              = new JLabel("Y Size :");
	private JLabel	     lblLayers	            = new JLabel("Layers :");
	private JLabel	     lblSelectedBlock	      = new JLabel("Current block :");
	private JLabel	     lblX	                  = new JLabel("X :");
	private JLabel	     lblNewLabel	          = new JLabel("Y :");
	private JLabel	     lblCoordinatesAreShown	= new JLabel("Coordinates are shown in");
	private JLabel	     lblFormOfA	            = new JLabel(" form of a blue box");

	// JChechBox

	private JCheckBox	   chckbxShowBlockIds	    = new JCheckBox("Show block ID's");

	// JComboBox

	private JComboBox	   comboBox	              = new JComboBox();

	// JPanel
	private JPanel	     panel	                = new JPanel();

	// JProgressBar

	private JProgressBar	progressBar	          = new JProgressBar();

	// JButton

	private JButton	     btnSave	              = new JButton("Save");

	// JTextArea

	private JTextField	 levelName;
	private JTextField	 levelVersion;

	// Canvas

	Canvas	             canvas	                = new Canvas();

	/**
	 * Create the frame.
	 */
	public LevelEditorGui() {
		super("Level editor");
		this.setJMenuBar(this.getMainMenuBar());
		this.getMainMenuBar().add(this.getMainMenu());

		this.getMainMenu().add(this.getLevelLoad());
		this.getMainMenu().add(this.getLevelSave());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 960, 617);

		this.getSplitPane().setResizeWeight(0.2);
		this.getContentPane().add(this.getSplitPane(), BorderLayout.CENTER);

		this.getSplitPane().setLeftComponent(this.getPanel());
		this.getPanel().setLayout(null);

		this.getLblLevelsettings().setBounds(6, 6, 83, 16);
		this.getPanel().add(this.getLblLevelsettings());

		this.getLblXSize().setBounds(6, 25, 61, 16);
		this.getPanel().add(this.getLblXSize());

		this.getXsize().setBounds(6, 53, 83, 28);
		this.getPanel().add(this.getXsize());

		this.getLblYSize().setBounds(6, 84, 61, 16);
		this.getPanel().add(this.getLblYSize());

		this.getYsize().setBounds(6, 112, 83, 28);
		this.getPanel().add(this.getYsize());

		this.getLblLayers().setBounds(6, 149, 61, 16);
		this.getPanel().add(this.getLblLayers());

		this.getLayer().setBounds(6, 175, 83, 28);
		this.getPanel().add(this.getLayer());

		this.getLblSelectedBlock().setBounds(6, 215, 107, 16);
		this.getPanel().add(this.getLblSelectedBlock());

		this.getComboBox().setBounds(6, 243, 154, 27);
		this.getPanel().add(this.getComboBox());

		this.getChckbxShowBlockIds().setBounds(6, 282, 154, 23);
		this.getPanel().add(this.getChckbxShowBlockIds());

		this.getProgressBar().setBounds(6, 540, 178, 20);
		this.getPanel().add(this.getProgressBar());

		this.getOptcoordx().setBounds(6, 337, 61, 28);
		this.getPanel().add(this.getOptcoordx());

		this.getOptcoordy().setBounds(79, 337, 61, 28);
		this.getPanel().add(this.getOptcoordy());

		this.getLblX().setBounds(16, 320, 61, 16);
		this.getPanel().add(this.getLblX());

		this.getLblNewLabel().setBounds(89, 320, 61, 16);
		this.getPanel().add(this.getLblNewLabel());

		this.getLblCoordinatesAreShown().setBounds(6, 377, 178, 16);
		this.getPanel().add(this.getLblCoordinatesAreShown());

		this.getLblFormOfA().setBounds(6, 395, 178, 16);
		this.getPanel().add(this.getLblFormOfA());

		this.setLevelName(new JTextField("Name"));
		this.getLevelName().setText("Name");
		this.getLevelName().setBounds(6, 439, 134, 28);
		this.getPanel().add(this.getLevelName());
		this.getLevelName().setColumns(10);

		this.setLevelVersion(new JTextField("1.0"));
		this.getLevelVersion().setText("1.0");
		this.getLevelVersion().setBounds(6, 469, 134, 28);
		this.getPanel().add(this.getLevelVersion());
		this.getLevelVersion().setColumns(10);

		this.getBtnSave().setBounds(6, 509, 178, 29);
		this.getPanel().add(this.getBtnSave());

		this.getSplitPane().setRightComponent(this.getCanvas());

	}

	public JMenuBar getMainMenuBar() {
		return mainMenuBar;
	}

	public void setMainMenuBar(JMenuBar mainMenuBar) {
		this.mainMenuBar = mainMenuBar;
	}

	public JMenu getMainMenu() {
		return mainMenu;
	}

	public void setMainMenu(JMenu mainMenu) {
		this.mainMenu = mainMenu;
	}

	public JMenuItem getLevelLoad() {
		return levelLoad;
	}

	public void setLevelLoad(JMenuItem levelLoad) {
		this.levelLoad = levelLoad;
	}

	public JMenuItem getLevelSave() {
		return levelSave;
	}

	public void setLevelSave(JMenuItem levelSave) {
		this.levelSave = levelSave;
	}

	public JSplitPane getSplitPane() {
		return splitPane;
	}

	public void setSplitPane(JSplitPane splitPane) {
		this.splitPane = splitPane;
	}

	public JSpinner getXsize() {
		return xsize;
	}

	public void setXsize(JSpinner xsize) {
		this.xsize = xsize;
	}

	public JSpinner getYsize() {
		return ysize;
	}

	public void setYsize(JSpinner ysize) {
		this.ysize = ysize;
	}

	public JSpinner getLayer() {
		return layer;
	}

	public void setLayer(JSpinner layer) {
		this.layer = layer;
	}

	public JSpinner getOptcoordx() {
		return optcoordx;
	}

	public void setOptcoordx(JSpinner optcoordx) {
		this.optcoordx = optcoordx;
	}

	public JSpinner getOptcoordy() {
		return optcoordy;
	}

	public void setOptcoordy(JSpinner optcoordy) {
		this.optcoordy = optcoordy;
	}

	public JLabel getLblLevelsettings() {
		return lblLevelsettings;
	}

	public void setLblLevelsettings(JLabel lblLevelsettings) {
		this.lblLevelsettings = lblLevelsettings;
	}

	public JLabel getLblXSize() {
		return lblXSize;
	}

	public void setLblXSize(JLabel lblXSize) {
		this.lblXSize = lblXSize;
	}

	public JLabel getLblYSize() {
		return lblYSize;
	}

	public void setLblYSize(JLabel lblYSize) {
		this.lblYSize = lblYSize;
	}

	public JLabel getLblLayers() {
		return lblLayers;
	}

	public void setLblLayers(JLabel lblLayers) {
		this.lblLayers = lblLayers;
	}

	public JLabel getLblSelectedBlock() {
		return lblSelectedBlock;
	}

	public void setLblSelectedBlock(JLabel lblSelectedBlock) {
		this.lblSelectedBlock = lblSelectedBlock;
	}

	public JLabel getLblX() {
		return lblX;
	}

	public void setLblX(JLabel lblX) {
		this.lblX = lblX;
	}

	public JLabel getLblNewLabel() {
		return lblNewLabel;
	}

	public void setLblNewLabel(JLabel lblNewLabel) {
		this.lblNewLabel = lblNewLabel;
	}

	public JLabel getLblCoordinatesAreShown() {
		return lblCoordinatesAreShown;
	}

	public void setLblCoordinatesAreShown(JLabel lblCoordinatesAreShown) {
		this.lblCoordinatesAreShown = lblCoordinatesAreShown;
	}

	public JLabel getLblFormOfA() {
		return lblFormOfA;
	}

	public void setLblFormOfA(JLabel lblFormOfA) {
		this.lblFormOfA = lblFormOfA;
	}

	public JCheckBox getChckbxShowBlockIds() {
		return chckbxShowBlockIds;
	}

	public void setChckbxShowBlockIds(JCheckBox chckbxShowBlockIds) {
		this.chckbxShowBlockIds = chckbxShowBlockIds;
	}

	public JComboBox getComboBox() {
		return comboBox;
	}

	public void setComboBox(JComboBox comboBox) {
		this.comboBox = comboBox;
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

	public JProgressBar getProgressBar() {
		return progressBar;
	}

	public void setProgressBar(JProgressBar progressBar) {
		this.progressBar = progressBar;
	}

	public JButton getBtnSave() {
		return btnSave;
	}

	public void setBtnSave(JButton btnSave) {
		this.btnSave = btnSave;
	}

	public JTextField getLevelName() {
		return levelName;
	}

	public void setLevelName(JTextField levelName) {
		this.levelName = levelName;
	}

	public JTextField getLevelVersion() {
		return levelVersion;
	}

	public void setLevelVersion(JTextField levelVersion) {
		this.levelVersion = levelVersion;
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}
}
