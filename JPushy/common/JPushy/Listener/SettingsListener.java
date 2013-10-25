package JPushy.Listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import JPushy.Core.Core;
import JPushy.Gui.SettingsGui;
/**
 * 
 * @author Marcel Benning
 * 
 */
public class SettingsListener implements ActionListener {
	
	String cmd;
	
	public SettingsGui gui;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		cmd = e.getActionCommand();
		switch (cmd) {
			case "save":
				saveSettings();
				break;
			case "exit":
				saveSettings();
				gui.setVisible(false);
				break;
			case "exit_nosave":
				gui.setVisible(false);
				break;
			default:
				break;
		}
	}

	public void setGui(SettingsGui gui) {
		this.gui = gui;
	}

	private void saveSettings(){
		Core.getSettings().setSetting(Core.getSettings().debug, gui.chckbxDebugMode.isSelected());
		Core.getSettings().setSetting(Core.getSettings().defaultLevelServer, gui.lvlServer.getText());
		Core.getSettings().setSetting(Core.getSettings().defaultUpdateServer, gui.updateServer.getText());
		Core.getSettings().save();
	}
	
}
