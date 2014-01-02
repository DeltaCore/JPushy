package JPushy.LevelEditor;

import java.awt.BorderLayout;

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
import javax.swing.SwingUtilities;

import JPushy.Types.ProgammingRelated.EditorPanel;

public class LevelEditorGui extends JFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
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

	private boolean	     running	      = true;

	// JMenu

	private JMenuBar	   mainMenuBar	  = new JMenuBar();
	private JMenu	       mainMenu	      = new JMenu("Level");
	private JMenuItem	   levelLoad	    = new JMenuItem("Load");
	private JMenuItem	   levelSave	    = new JMenuItem("Save");

	// Editor

	private EditorPanel	 editorPanel	  = new EditorPanel();
	private JSplitPane	 splitPane_1	  = new JSplitPane();
	private JPanel	     panel_1	      = new JPanel();
	private JLabel	     label	        = new JLabel("LevelSettings");
	private JLabel	     label_1	      = new JLabel("X Size :");
	private JSpinner	   spinner	      = new JSpinner();
	private JLabel	     label_2	      = new JLabel("Y Size :");
	private JSpinner	   spinner_1	    = new JSpinner();
	private JLabel	     label_3	      = new JLabel("Layers :");
	private JSpinner	   spinner_2	    = new JSpinner();
	private JLabel	     label_4	      = new JLabel("Current block :");
	private JComboBox	   comboBox_1	    = new JComboBox();
	private JCheckBox	   checkBox	      = new JCheckBox("Show block ID's");
	private JProgressBar	progressBar_1	= new JProgressBar();
	private JSpinner	   spinner_3	    = new JSpinner();
	private JSpinner	   spinner_4	    = new JSpinner();
	private JLabel	     label_5	      = new JLabel("X :");
	private JLabel	     label_6	      = new JLabel("Y :");
	private JLabel	     label_7	      = new JLabel("Coordinates are shown in");
	private JLabel	     label_8	      = new JLabel(" form of a blue box");
	private JTextField	 textField	    = new JTextField("Name");
	private JTextField	 textField_1	  = new JTextField("1.0");
	private JButton	     button	        = new JButton("Save");
	private JSpinner	   spinner_5	    = new JSpinner();
	private JLabel	     label_9	      = new JLabel("Current layer :");

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
		this.setBounds(100, 100, 1000, 700);
		splitPane_1.setResizeWeight(0.225);

		getContentPane().add(splitPane_1, BorderLayout.CENTER);
		panel_1.setLayout(null);

		editorPanel.setGui(this);
		splitPane_1.setRightComponent(editorPanel);

		splitPane_1.setLeftComponent(panel_1);
		label.setBounds(6, 6, 83, 16);

		panel_1.add(label);
		label_1.setBounds(6, 25, 61, 16);

		panel_1.add(label_1);
		spinner.setBounds(6, 53, 83, 28);

		panel_1.add(spinner);
		label_2.setBounds(6, 84, 61, 16);

		panel_1.add(label_2);
		spinner_1.setBounds(6, 112, 83, 28);

		panel_1.add(spinner_1);
		label_3.setBounds(6, 149, 61, 16);

		panel_1.add(label_3);
		spinner_2.setBounds(6, 175, 83, 28);

		panel_1.add(spinner_2);
		label_4.setBounds(6, 301, 107, 16);

		panel_1.add(label_4);
		comboBox_1.setBounds(6, 329, 154, 27);

		panel_1.add(comboBox_1);
		checkBox.setBounds(6, 368, 154, 23);

		panel_1.add(checkBox);
		progressBar_1.setBounds(6, 626, 178, 20);

		panel_1.add(progressBar_1);
		spinner_3.setBounds(6, 423, 61, 28);

		panel_1.add(spinner_3);
		spinner_4.setBounds(79, 423, 61, 28);

		panel_1.add(spinner_4);
		label_5.setBounds(16, 406, 61, 16);

		panel_1.add(label_5);
		label_6.setBounds(89, 406, 61, 16);

		panel_1.add(label_6);
		label_7.setBounds(6, 463, 178, 16);

		panel_1.add(label_7);
		label_8.setBounds(6, 481, 178, 16);

		panel_1.add(label_8);
		textField.setColumns(10);
		textField.setBounds(6, 525, 134, 28);

		panel_1.add(textField);
		textField_1.setColumns(10);
		textField_1.setBounds(6, 555, 134, 28);

		panel_1.add(textField_1);
		button.setBounds(6, 595, 178, 29);

		panel_1.add(button);
		spinner_5.setBounds(6, 261, 83, 28);

		panel_1.add(spinner_5);
		label_9.setBounds(6, 233, 107, 16);

		panel_1.add(label_9);

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

}
