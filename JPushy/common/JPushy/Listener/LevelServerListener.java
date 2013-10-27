package JPushy.Listener;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import JPushy.Core.Core;
import JPushy.Gui.CheckBoxListEntry;
import JPushy.Gui.LevelServerConnector;

/**
 * 
 * @author Marcel Benning
 * 
 */
public class LevelServerListener implements ActionListener {

	LevelServerConnector	gui;
	String					regex		= "<name=\"([a-zA-Z\\s]{1,})\";version='([a-zA-Z0-9.,]{1,})'>";
	Color					dark_green	= new Color(0, 100, 0);

	public LevelServerListener(LevelServerConnector gui) {
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String msg = e.getActionCommand();
		if (msg.equalsIgnoreCase("reloadlist")) {
			gui.setStaus("Sending commands to server " + Core.getSettings().getSetting(Core.getSettings().defaultLevelServer) + " ...", Color.getHSBColor(0.667f, 1f, 0.392f));
			gui.serverConnection.sendCommand(gui.serverConnection.getLevels);
			gui.setStaus("Receiving data ...", dark_green);
			String ret = gui.serverConnection.receive();
			gui.setStaus("Parsing information ...", dark_green);
			String[] levels = ret.split("#");
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(ret);
			gui.setStaus("Updating Level list ...", dark_green);
			gui.listModel = new DefaultListModel();
			int index = 0;
			for (int i = 0; i < levels.length; i++) {
				matcher = pattern.matcher(levels[i]);
				if (matcher.matches()) {
					String name = matcher.group(1);
					String version = matcher.group(2);
					System.out.println("Level : " + levels[i] + String.format("%n") + "Name : " + name + " version : " + version);
					gui.listModel.add(index, new CheckBoxListEntry(name + " - " + version, false));
					index++;
				}
				// gui.listModel.add(i, levels[i]);
			}
			gui.newCheckList(gui.listModel);
			gui.setStaus("Idle", Color.blue);
		} else if (msg.equalsIgnoreCase("loadfile")) {
			for (int i = 0; i < gui.checkBoxList.getModel().getSize(); i++) {
				Object c = gui.checkBoxList.getModel().getElementAt(i);
				if (c instanceof CheckBoxListEntry) {
					CheckBoxListEntry item = (CheckBoxListEntry) c;
					// System.out.println(item.getName());
					if (item.isSelected())
						loadFile(item);
				}
			}
			gui.mainGui.updateLevels();
		}
	}

	String	name;
	String	version;
	String	temp;
	int		index;

	private void loadFile(CheckBoxListEntry item) {
		try {
			temp = item.getText();
			index = temp.indexOf("-");
			name = temp.substring(0, index - 1);
			version = temp.substring(index + 2);
			gui.setStaus("Downloading level (" + name + ") ...", dark_green);
			System.out.println(name + ":" + version);
			gui.serverConnection.loadLevelFromServer(name);
			String content = gui.serverConnection.receive();
			File f = new File("Data/lvl/" + name + ".lvl");
			if (f.exists()) {
				int overwrite = JOptionPane.showConfirmDialog(null, "The file " + f.getAbsolutePath() + " already exists. \nDo you want to overwrite this file ?", "Level " + name + " already exists", JOptionPane.YES_NO_OPTION);
				if (overwrite == JOptionPane.YES_OPTION) {
					f.delete();
					f.createNewFile();
					BufferedWriter writer = new BufferedWriter(new FileWriter(f));
					writer.write(content);
					writer.close();
					gui.serverConnection.loadLevelConfigFromServer(name);
					content = gui.serverConnection.receive();
					f = new File("Data/lvl/" + name + ".cfg");
					if (overwrite == JOptionPane.YES_OPTION) {
						f.delete();
						f.createNewFile();
					}
					writer = new BufferedWriter(new FileWriter(f));
					writer.write(content);
					writer.close();
				}
			} else {
				BufferedWriter writer = new BufferedWriter(new FileWriter(f));
				writer.write(content);
				writer.close();
				gui.serverConnection.loadLevelConfigFromServer(name);
				content = gui.serverConnection.receive();
				f = new File("Data/lvl/" + name + ".cfg");
				writer = new BufferedWriter(new FileWriter(f));
				writer.write(content);
				writer.close();
			}
			gui.setStaus("Idle", Color.blue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
