package JPushy.Gui;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import JPushy.Game;
import JPushy.LevelSelectorListener;
import JPushy.LevelEditor.Editor;

/**
 * 
 * @author Marcel Benning
 * 
 */

public class LevelSelector extends JFrame {

	private Editor	      editor;
	private JPanel	      contentPane;
	DefaultListModel	    listModel	= new DefaultListModel();
	public JList	        levels	  = new JList(listModel);
	LevelSelectorListener	selectionListener;
	public Game	          game;

	public LevelSelector(Game game) {
		this.game = game;
		selectionListener = new LevelSelectorListener(this);
		setTitle("Level selection");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 422, 236);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		contentPane.add(levels, BorderLayout.CENTER);

		JToolBar toolBar = new JToolBar();
		contentPane.add(toolBar, BorderLayout.NORTH);

		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(selectionListener);

		toolBar.add(btnStart);

		JButton btnExit = new JButton("Exit");
		toolBar.add(btnExit);

		JButton btnConnectToServer = new JButton("Connect to Server");
		btnConnectToServer.addActionListener(selectionListener);
		btnConnectToServer.setActionCommand("connect");

		toolBar.add(btnConnectToServer);

		JButton btnLevelEditor = new JButton("Level Editor");
		btnLevelEditor.addActionListener(selectionListener);
		btnLevelEditor.setActionCommand("leveleditor");
		toolBar.add(btnLevelEditor);
		btnExit.addActionListener(selectionListener);
		getContentPane().add(levels);
		levels.addListSelectionListener(selectionListener);
		readLevels();
	}

	public void updateLevels() {
		System.out.println("Update levels");
		readLevels();
	}

	public void readLevels() {
		remove(levels);
		File dataFolder = new File("Data/lvl");

		String levelList[];
		int i = 0;
		for (File f : dataFolder.listFiles()) {
			if (!f.isDirectory()) {
				String tmp = f.getName();
				if (tmp.endsWith(".lvl")) {
					i++;
				}
			}
		}
		levelList = new String[i];
		i = 0;
		for (File f : dataFolder.listFiles()) {
			if (!f.isDirectory()) {
				String tmp = f.getName();
				if (tmp.endsWith(".lvl")) {
					levelList[i] = tmp;
					System.out.println("New Level file : " + tmp);
					i++;
				}
			}
		}
		String levelRegEx = "<level name=\"([a-zA-Z0-9\\s]{1,})\" version='([a-zA-Z0-9.,\\s]{1,})'>";
		String commentStart = "^<comment>";
		String commentEnd = "^</comment>";
		boolean comment = false;
		listModel = new DefaultListModel();
		for (String s : levelList) {
			ArrayList<String> content = loadLevelFile(s);
			for (String line : content) {
				if (comment) {
					if (line.matches(commentEnd)) {

					} else {
						System.out.println("[Comment] New comment line : " + line);
					}
				}
				if (line.matches(commentStart)) {
					comment = true;
				} else if (line.matches(commentEnd)) {
					comment = false;
				} else if (line.matches(levelRegEx)) {
					Pattern pattern = Pattern.compile(levelRegEx);
					Matcher matcher = pattern.matcher(line);
					if (matcher.matches()) {
						String name = matcher.group(1);
						String version = matcher.group(2);
						System.out.println("Level - Name : " + name + " version : "
						    + version + " file: " + s);
						listModel.addElement(name + " V" + version + " - " + s);
					}
				}
			}
		}
		levels = new JList(listModel);
		this.repaint();
		levels.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		levels.addListSelectionListener(selectionListener);
		add(levels);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		Rectangle r = this.getBounds();
		this.setSize((int) r.getWidth() + 1, (int) r.getHeight());
		this.setSize((int) r.getWidth(), (int) r.getHeight());
	}

	private ArrayList<String> loadLevelFile(String filename) {
		ArrayList<String> returnString = new ArrayList<String>();
		File f = new File("Data/lvl/" + filename);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line;
			while ((line = reader.readLine()) != null) {
				returnString.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnString;
	}

}
