package JPushy.Gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.JButton;

import JPushy.Game;
import JPushy.LevelSelectorListener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ListSelectionModel;
/**
 * 
 * @author Marcel Benning
 * 
 */

public class LevelSelector extends JFrame {

	private JPanel contentPane;
	public JList levels = new JList();
	LevelSelectorListener selectionListener;
	public Game game;
	
	private String levelEndRegEx = "^([a-zA-Z0-9]{1,}).lvl";
	
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
		btnExit.addActionListener(selectionListener);
		File dataFolder = new File("Data/lvl");
		
		String levelList[];
		int i = 0;
		for(File f : dataFolder.listFiles()){
			if(!f.isDirectory()){
				String tmp = f.getName();
				if(tmp.matches(levelEndRegEx)){
					i++;
				}
			}
		}
		levelList = new String[i];
		i = 0;
		for(File f : dataFolder.listFiles()){
			if(!f.isDirectory()){
				String tmp = f.getName();
				if(tmp.matches(levelEndRegEx)){
					levelList[i] = tmp;
					System.out.println("New Level file : " + tmp);
					i++;
				}
			}
		}
		String levelRegEx = "^^<level name=\"([a-zA-Z]{1,})\" version='([a-zA-Z0-9.,]{1,})'>$";
		String commentStart = "^<comment>";
		String commentEnd = "^</comment>";
		boolean comment = false;
		String Comment = "";
		DefaultListModel listModel = new DefaultListModel();
		for(String s: levelList){
			ArrayList<String> content = loadLevelFile(s);
			for(String line: content){
				if(comment){
					if(line.matches(commentEnd)){
						
					}else{
						Comment += line;
						System.out.println("[Comment] New comment line : " + line);
					}
				}
				if (line.matches(commentStart)) {
					comment = true;
				}else if (line.matches(commentEnd)) {
					comment = false;
					
				}else if (line.matches(levelRegEx)) {
					Pattern pattern = Pattern.compile(levelRegEx);
					Matcher matcher = pattern.matcher(line);
					if (matcher.matches()) {
						String name = matcher.group(1);
						String version = matcher.group(2);
						System.out.println("Level - Name : " + name + " version : " + version + " file: " + s);
						listModel.addElement(name + " V" + version + " - " + s);
					}
				}
			}
		}
		levels = new JList(listModel);
		levels.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		levels.addListSelectionListener(selectionListener);
		getContentPane().add(levels);
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
