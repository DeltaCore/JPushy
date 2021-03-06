package net.ccmob.apps.jpushy.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 
 * @author Marcel Benning
 * 
 */
public class SettingsListener implements ActionListener {

	String	           cmd;

	public SettingsGui	gui;

	@Override
	public void actionPerformed(ActionEvent e) {
		cmd = e.getActionCommand();
		if (cmd.equalsIgnoreCase("save")) {
			saveSettings();
		} else if (cmd.equalsIgnoreCase("exit")) {
			saveSettings();
			gui.setVisible(false);
		} else if (cmd.equalsIgnoreCase("exit_nosave")) {
			gui.setVisible(false);
		}
	}

	public void setGui(SettingsGui gui) {
		this.gui = gui;
	}

	private void saveSettings() {
		// Core.getSettings().setSetting(Core.getSettings().debug,
		// gui.chckbxDebugMode.isSelected());
		// Core.getSettings().setSetting(Core.getSettings().defaultLevelServer,
		// gui.lvlServer.getText());
		// Core.getSettings().setSetting(Core.getSettings().defaultUpdateServer,
		// gui.updateServer.getText());
		// Core.getSettings().save();
	}

}
