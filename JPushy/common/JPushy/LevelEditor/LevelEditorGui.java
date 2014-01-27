package JPushy.LevelEditor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import JPushy.Core.Game;
import JPushy.Types.Blocks.Block;
import JPushy.Types.Blocks.Blocks;
import JPushy.Types.Items.Item;
import JPushy.Types.Items.Items;

public class LevelEditorGui extends JFrame {

	/**
	 * 
	 */
	private static final long	serialVersionUID	 = 1L;

	/**
	 * Launch the application.
	 */

	private boolean	          running	           = true;
	private GuiListener	      listener;

	// JMenu

	private JMenuBar	        mainMenuBar	       = new JMenuBar();
	private JMenu	            mainMenu	         = new JMenu("Level");
	private JMenuItem	        levelLoad	         = new JMenuItem("Load");
	private JMenuItem	        levelSave	         = new JMenuItem("Save");

	private EditorPanel	      editorPanel	       = new EditorPanel();
	private JSplitPane	      mainSplitPane	     = new JSplitPane();
	private JPanel	          settingsPanel	     = new JPanel();
	private JLabel	          settingsLabel	     = new JLabel("LevelSettings");
	private JLabel	          xSizeLabel	       = new JLabel("X Size :");
	private JSpinner	        xSizeVal	         = new JSpinner();
	private JLabel	          ySizeLabel	       = new JLabel("Y Size :");
	private JSpinner	        ySizeVal	         = new JSpinner();
	private JLabel	          layerLabel	       = new JLabel("Layers :");
	private JSpinner	        layerVal	         = new JSpinner();
	private JLabel	          labelCurrentBlock	 = new JLabel("Current block :");
	private JComboBox	        currentBlock	     = new JComboBox();
	private BlockPreviewPanel	blockPanel	       = new BlockPreviewPanel();

	private JButton	          btnWallConnections	= new JButton("Make wall connections");

	private JProgressBar	    progressbar	       = new JProgressBar();
	private JLabel	          optionsLabelX	     = new JLabel("X :");
	private JSpinner	        optionsLabelXVal	 = new JSpinner();
	private JLabel	          optionsLabelY	     = new JLabel("Y :");
	private JSpinner	        optionsLabelYVal	 = new JSpinner();
	private JButton	          optionsApply	     = new JButton("Apply");
	private JLabel	          labelInfo1	       = new JLabel("Coordinates are shown in");
	private JLabel	          labelInfo2	       = new JLabel(" form of a blue box");
	private JTextField	      levelNameText	     = new JTextField("Name");
	private JTextField	      levelVersionText	 = new JTextField("1.0");
	private JButton	          levelSaveBtn	     = new JButton("Save");
	private JSpinner	        currentLayerBox	   = new JSpinner();
	private JLabel	          labelCurrentLayer	 = new JLabel("Current layer :");

	private JButton	          applyXSize	       = new JButton("Apply X Size");
	private JButton	          applyYSize	       = new JButton("Apply Y Size");
	private JButton	          applyLayer	       = new JButton("Apply layer");
	private JButton	          selectLayer	       = new JButton("Jump to");

	private Game	            game;

	/**
	 * Create the frame.
	 */
	public LevelEditorGui(Game game) {
		super("Level editor");
		this.getSettingsPanel().setBorder(null);
		// JPanel cFrame = new JPanel();
		// cFrame.setBorder(null);
		// this.add(cFrame, BorderLayout.CENTER);
		// this.setContentPane(cFrame);
		this.setGame(game);
		this.setJMenuBar(this.getMainMenuBar());
		this.getMainMenuBar().add(this.getMainMenu());

		this.getLevelSave().setActionCommand("save");
		this.getLevelSave().addActionListener(this.getListener());

		this.getMainMenu().add(this.getLevelSave());
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(100, 100, 1000, 753);
		this.getMainSplitPane().setBorder(null);
		this.getMainSplitPane().setResizeWeight(0.225);

		this.getContentPane().add(this.getMainSplitPane(), BorderLayout.CENTER);
		// cFrame.add(this.getMainSplitPane(), BorderLayout.CENTER);
		this.getSettingsPanel().setLayout(null);

		this.getEditorPanel().setGui(this);
		this.getEditorPanel().setFocusable(true);
		this.getMainSplitPane().setRightComponent(this.getEditorPanel());

		this.getMainSplitPane().setLeftComponent(this.getSettingsPanel());
		this.getSettingsLabel().setBounds(6, 6, 83, 16);

		this.getSettingsPanel().add(this.getSettingsLabel());
		this.getxSizeLabel().setBounds(6, 25, 61, 16);

		this.getSettingsPanel().add(this.getxSizeLabel());
		this.getxSizeVal().setBounds(6, 53, 83, 28);
		this.getxSizeVal().setValue(8);

		this.getSettingsPanel().add(this.getxSizeVal());
		this.getySizeLabel().setBounds(6, 84, 61, 16);

		this.getSettingsPanel().add(this.getySizeLabel());
		this.getySizeVal().setBounds(6, 112, 83, 28);
		this.getySizeVal().setValue(8);

		this.getSettingsPanel().add(this.getySizeVal());
		this.getLayerLabel().setBounds(6, 149, 61, 16);

		this.getSettingsPanel().add(this.getLayerLabel());
		this.getLayerVal().setBounds(6, 175, 83, 28);
		this.getLayerVal().setValue(1);

		this.getSettingsPanel().add(this.getLayerVal());
		this.getLabelCurrentBlock().setBounds(6, 301, 107, 16);

		this.getSettingsPanel().add(this.getLabelCurrentBlock());

		this.getProgressbar().setBounds(6, 662, 178, 20);

		this.getSettingsPanel().add(this.getProgressbar());

		this.getOptionsLabelX().setBounds(6, 442, 61, 16);

		this.getSettingsPanel().add(this.getOptionsLabelX());
		this.getOptionsLabelY().setBounds(74, 442, 61, 16);

		this.getSettingsPanel().add(this.getOptionsLabelY());
		this.getLabelInfo1().setBounds(6, 499, 178, 16);

		this.getSettingsPanel().add(this.getLabelInfo1());
		this.getLabelInfo2().setBounds(6, 517, 178, 16);

		this.getSettingsPanel().add(this.getLabelInfo2());
		this.getLevelNameText().setColumns(10);
		this.getLevelNameText().setBounds(6, 561, 134, 28);

		this.getSettingsPanel().add(this.getLevelNameText());
		this.getLevelVersionText().setColumns(10);
		this.getLevelVersionText().setBounds(6, 591, 134, 28);

		this.getSettingsPanel().add(this.getLevelVersionText());

		this.getCurrentLayerBox().setBounds(6, 261, 83, 28);

		this.getSettingsPanel().add(this.getCurrentLayerBox());
		this.getLabelCurrentLayer().setBounds(6, 233, 107, 16);

		this.getSettingsPanel().add(this.getLabelCurrentLayer());

		this.getEditorPanel().updateLayer((Integer) this.getLayerVal().getValue(), (Integer) this.getxSizeVal().getValue(), (Integer) this.getySizeVal().getValue());

		DefaultComboBoxModel blockModel = new DefaultComboBoxModel();
		Blocks.wakeUpDummy();
		for (Block b : Blocks.blockRegistry) {
			blockModel.addElement(b.getName());
		}
		for (Item i : Items.itemRegistry) {
			blockModel.addElement(i.getName());
		}
		this.getCurrentBlock().setBounds(6, 329, 117, 27);

		this.getApplyLayer().setBounds(101, 176, 117, 29);
		this.getSettingsPanel().add(this.getApplyLayer());

		this.getApplyYSize().setBounds(101, 113, 117, 29);
		this.getSettingsPanel().add(this.getApplyYSize());

		this.getApplyXSize().setBounds(101, 54, 117, 29);
		this.getSettingsPanel().add(this.getApplyXSize());

		this.setListener(new GuiListener(this));
		this.getApplyLayer().setActionCommand("applylayer");
		this.getApplyLayer().addActionListener(this.getListener());

		this.getApplyYSize().setActionCommand("applyy");
		this.getApplyYSize().addActionListener(this.getListener());

		this.getApplyXSize().setActionCommand("applyx");
		this.getSelectLayer().setBounds(101, 262, 117, 29);
		this.getSelectLayer().setActionCommand("selectLayer");
		this.getSelectLayer().addActionListener(this.getListener());

		this.getSettingsPanel().add(this.getSelectLayer());

		this.getOptionsApply().setBounds(138, 461, 76, 27);
		this.getOptionsApply().setActionCommand("applyOptions");
		this.getOptionsApply().addActionListener(listener);
		this.getSettingsPanel().add(this.getOptionsApply());

		this.getApplyXSize().addActionListener(this.getListener());

		this.getLevelSaveBtn().setBounds(6, 631, 178, 29);
		this.getLevelSaveBtn().setActionCommand("save");
		this.getLevelSaveBtn().addActionListener(this.getListener());

		this.getSettingsPanel().add(this.getLevelSaveBtn());

		this.getOptionsLabelXVal().setBounds(6, 459, 58, 28);
		this.getOptionsLabelXVal().setName("optionx");
		this.getOptionsLabelXVal().addChangeListener(this.getListener());

		this.getSettingsPanel().add(this.getOptionsLabelXVal());
		this.getOptionsLabelYVal().setBounds(74, 459, 58, 28);
		this.getOptionsLabelYVal().setName("optiony");
		this.getOptionsLabelYVal().addChangeListener(this.getListener());

		this.getSettingsPanel().add(this.getOptionsLabelYVal());

		this.getBlockPanel().setBounds(134, 303, 80, 80);
		this.getBlockPanel().setGui(this);

		this.getCurrentBlock().setModel(blockModel);
		this.getCurrentBlock().setName("currentBlock");
		this.getCurrentBlock().setRenderer(this.getListener());

		this.getMainMenu().add(this.getLevelLoad());
		this.getLevelLoad().addActionListener(this.getListener());
		this.getLevelLoad().setActionCommand("load");

		this.getSettingsPanel().add(this.getCurrentBlock());

		this.getBtnWallConnections().setBounds(6, 413, 209, 29);
		this.getBtnWallConnections().setActionCommand("makeWallsFancy");
		this.getBtnWallConnections().addActionListener(this.getListener());
		this.getSettingsPanel().add(this.getBtnWallConnections());

		this.getSettingsPanel().add(this.getBlockPanel());
		this.addKeyListener(this.getEditorPanel().getListener());
		this.getSettingsPanel().addKeyListener(this.getEditorPanel().getListener());
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

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public EditorPanel getEditorPanel() {
		return editorPanel;
	}

	public void setEditorPanel(EditorPanel editorPanel) {
		this.editorPanel = editorPanel;
	}

	public JSplitPane getMainSplitPane() {
		return mainSplitPane;
	}

	public void setMainSplitPane(JSplitPane mainSplitPane) {
		this.mainSplitPane = mainSplitPane;
	}

	public JPanel getSettingsPanel() {
		return settingsPanel;
	}

	public void setSettingsPanel(JPanel settingsPanel) {
		this.settingsPanel = settingsPanel;
	}

	public JLabel getxSizeLabel() {
		return xSizeLabel;
	}

	public void setxSizeLabel(JLabel xSizeLabel) {
		this.xSizeLabel = xSizeLabel;
	}

	public JSpinner getxSizeVal() {
		return xSizeVal;
	}

	public void setxSizeVal(JSpinner xSizeVal) {
		this.xSizeVal = xSizeVal;
	}

	public JLabel getySizeLabel() {
		return ySizeLabel;
	}

	public void setySizeLabel(JLabel ySizeLabel) {
		this.ySizeLabel = ySizeLabel;
	}

	public JSpinner getySizeVal() {
		return ySizeVal;
	}

	public void setySizeVal(JSpinner ySizeVal) {
		this.ySizeVal = ySizeVal;
	}

	public JLabel getLayerLabel() {
		return layerLabel;
	}

	public void setLayerLabel(JLabel layerLabel) {
		this.layerLabel = layerLabel;
	}

	public JSpinner getLayerVal() {
		return layerVal;
	}

	public void setLayerVal(JSpinner layerVal) {
		this.layerVal = layerVal;
	}

	public JLabel getLabelCurrentBlock() {
		return labelCurrentBlock;
	}

	public void setLabelCurrentBlock(JLabel labelCurrentBlock) {
		this.labelCurrentBlock = labelCurrentBlock;
	}

	public JComboBox getCurrentBlock() {
		return currentBlock;
	}

	public void setCurrentBlock(JComboBox currentBlock) {
		this.currentBlock = currentBlock;
	}

	public JProgressBar getProgressbar() {
		return progressbar;
	}

	public void setProgressbar(JProgressBar progressbar) {
		this.progressbar = progressbar;
	}

	public JLabel getOptionsLabelX() {
		return optionsLabelX;
	}

	public void setOptionsLabelX(JLabel optionsLabelX) {
		this.optionsLabelX = optionsLabelX;
	}

	public JSpinner getOptionsLabelXVal() {
		return optionsLabelXVal;
	}

	public void setOptionsLabelXVal(JSpinner optionsLabelXVal) {
		this.optionsLabelXVal = optionsLabelXVal;
	}

	public JLabel getOptionsLabelY() {
		return optionsLabelY;
	}

	public void setOptionsLabelY(JLabel optionsLabelY) {
		this.optionsLabelY = optionsLabelY;
	}

	public JSpinner getOptionsLabelYVal() {
		return optionsLabelYVal;
	}

	public void setOptionsLabelYVal(JSpinner optionsLabelYVal) {
		this.optionsLabelYVal = optionsLabelYVal;
	}

	public JLabel getLabelInfo1() {
		return labelInfo1;
	}

	public void setLabelInfo1(JLabel labelInfo1) {
		this.labelInfo1 = labelInfo1;
	}

	public JLabel getLabelInfo2() {
		return labelInfo2;
	}

	public void setLabelInfo2(JLabel labelInfo2) {
		this.labelInfo2 = labelInfo2;
	}

	public JTextField getLevelNameText() {
		return levelNameText;
	}

	public void setLevelNameText(JTextField levelNameText) {
		this.levelNameText = levelNameText;
	}

	public JTextField getLevelVersionText() {
		return levelVersionText;
	}

	public void setLevelVersionText(JTextField levelVersionText) {
		this.levelVersionText = levelVersionText;
	}

	public JButton getLevelSaveBtn() {
		return levelSaveBtn;
	}

	public void setLevelSaveBtn(JButton levelSaveBtn) {
		this.levelSaveBtn = levelSaveBtn;
	}

	public JSpinner getCurrentLayerBox() {
		return currentLayerBox;
	}

	public void setCurrentLayerBox(JSpinner currentLayerBox) {
		this.currentLayerBox = currentLayerBox;
	}

	public JLabel getLabelCurrentLayer() {
		return labelCurrentLayer;
	}

	public void setLabelCurrentLayer(JLabel labelCurrentLayer) {
		this.labelCurrentLayer = labelCurrentLayer;
	}

	public JLabel getSettingsLabel() {
		return settingsLabel;
	}

	public void setSettingsLabel(JLabel settingsLabel) {
		this.settingsLabel = settingsLabel;
	}

	/**
	 * @return the applyXSize
	 */
	public JButton getApplyXSize() {
		return applyXSize;
	}

	/**
	 * @param applyXSize
	 *          the applyXSize to set
	 */
	public void setApplyXSize(JButton applyXSize) {
		this.applyXSize = applyXSize;
	}

	/**
	 * @return the applyYSize
	 */
	public JButton getApplyYSize() {
		return applyYSize;
	}

	/**
	 * @param applyYSize
	 *          the applyYSize to set
	 */
	public void setApplyYSize(JButton applyYSize) {
		this.applyYSize = applyYSize;
	}

	/**
	 * @return the applyLayer
	 */
	public JButton getApplyLayer() {
		return applyLayer;
	}

	/**
	 * @param applyLayer
	 *          the applyLayer to set
	 */
	public void setApplyLayer(JButton applyLayer) {
		this.applyLayer = applyLayer;
	}

	private class GuiListener extends DefaultListCellRenderer implements ActionListener, ChangeListener {

		private static final long	serialVersionUID	= 1L;
		private LevelEditorGui		gui;

		public GuiListener(LevelEditorGui gui) {
			this.setGui(gui);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			if (cmd.equalsIgnoreCase("applyx")) {
				this.getGui().getEditorPanel().updateLayer((Integer) this.getGui().getLayerVal().getValue(), (Integer) this.getGui().getxSizeVal().getValue(), (Integer) this.getGui().getySizeVal().getValue());
			} else if (cmd.equalsIgnoreCase("applyy")) {
				this.getGui().getEditorPanel().updateLayer((Integer) this.getGui().getLayerVal().getValue(), (Integer) this.getGui().getxSizeVal().getValue(), (Integer) this.getGui().getySizeVal().getValue());
			} else if (cmd.equalsIgnoreCase("applylayer")) {
				this.getGui().getEditorPanel().updateLayer((Integer) this.getGui().getLayerVal().getValue(), (Integer) this.getGui().getxSizeVal().getValue(), (Integer) this.getGui().getySizeVal().getValue());
			} else if (cmd.equalsIgnoreCase("save")) {
				this.getGui().getEditorPanel().saveLevel(this.getGui().getLevelNameText().getText(), this.getGui().getLevelVersionText().getText());
			} else if (cmd.equalsIgnoreCase("selectLayer")) {
				this.getGui().getEditorPanel().setCurrentLayer((Integer) this.getGui().getCurrentLayerBox().getValue());
				this.getGui().getEditorPanel().repaint();
			} else if (cmd.equalsIgnoreCase("applyOptions")) {
				this.getGui().getEditorPanel().updateCoords(((Integer) this.getGui().getOptionsLabelXVal().getValue()) - 1, ((Integer) this.getGui().getOptionsLabelYVal().getValue()) - 1);
			} else if (cmd.equalsIgnoreCase("load")) {
				String filename = JOptionPane.showInputDialog(null, "Level auswählen", "Bitte nur den datein namen des levels angeben (z.b. Beginners.cfg)");
				boolean flag = false;
				while (flag) {
					File f = new File("Data/lvl/" + filename + ".lvl");
					if (f.exists()) {
						flag = true;
					} else {
						filename = JOptionPane.showInputDialog(null, "Level auswählen", "Bitte nur den datein namen des levels angeben (z.b. Beginners.cfg)");
					}
				}
				this.getGui().getEditorPanel().loadLevel(filename);
			} else if (cmd.equalsIgnoreCase("makeWallsFancy")) {
				this.getGui().getEditorPanel().makeWallsFancy();
			}
		}

		/**
		 * @return the gui
		 */
		public LevelEditorGui getGui() {
			return gui;
		}

		/**
		 * @param gui
		 *          the gui to set
		 */
		public void setGui(LevelEditorGui gui) {
			this.gui = gui;
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			String name = ((JSpinner) e.getSource()).getName();
			if (name.equalsIgnoreCase("optionx") || name.equalsIgnoreCase("optiony")) {
				this.getGui().getEditorPanel().updateCoords(((Integer) this.getGui().getOptionsLabelXVal().getValue()), ((Integer) this.getGui().getOptionsLabelYVal().getValue()));
			}
		}

		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			if (isSelected) {
				this.getGui().getBlockPanel().setSelectedIndex(index);
				this.getGui().getBlockPanel().repaint();
			}
			return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		}
	}

	/**
	 * @return the listener
	 */
	public GuiListener getListener() {
		return listener;
	}

	/**
	 * @param listener
	 *          the listener to set
	 */
	public void setListener(GuiListener listener) {
		this.listener = listener;
	}

	/**
	 * @return the selectLayer
	 */
	public JButton getSelectLayer() {
		return selectLayer;
	}

	/**
	 * @param selectLayer
	 *          the selectLayer to set
	 */
	public void setSelectLayer(JButton selectLayer) {
		this.selectLayer = selectLayer;
	}

	public JButton getOptionsApply() {
		return optionsApply;
	}

	public void setOptionsApply(JButton optionsApply) {
		this.optionsApply = optionsApply;
	}

	/**
	 * @return the blockPanel
	 */
	public BlockPreviewPanel getBlockPanel() {
		return blockPanel;
	}

	/**
	 * @param blockPanel
	 *          the blockPanel to set
	 */
	public void setBlockPanel(BlockPreviewPanel blockPanel) {
		this.blockPanel = blockPanel;
	}

	/**
	 * @return the game
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * @param game
	 *          the game to set
	 */
	public void setGame(Game game) {
		this.game = game;
	}

	/**
	 * @return the btnWallConnections
	 */
	public JButton getBtnWallConnections() {
		return btnWallConnections;
	}

	/**
	 * @param btnWallConnections
	 *          the btnWallConnections to set
	 */
	public void setBtnWallConnections(JButton btnWallConnections) {
		this.btnWallConnections = btnWallConnections;
	}
}
