package JPushy.Listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import JPushy.Core.Game;
import JPushy.Gui.LevelSelector;
import JPushy.Gui.LevelServerConnector;
import JPushy.Gui.SettingsGui;
import JPushy.LevelEditor.EditorThread;

/**
 * 
 * @author Marcel Benning
 * 
 */
public class LevelSelectorListener implements ListSelectionListener, ActionListener {
	LevelSelector	 selector;
	private String	value	= "";
	EditorThread	 th	    = new EditorThread();
	boolean	       flag	  = false;

	public LevelSelectorListener(LevelSelector frame) {
		this.selector = frame;
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		value = selector.levels.getModel().getElementAt(selector.levels.getSelectedIndex()).toString();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equalsIgnoreCase("start")) {
			String username = JOptionPane.showInputDialog("Username : ");
			// Game.getPlayer().setName(username);
			int index = value.lastIndexOf("- ");
			String filename = value.substring(index + 2);
			selector.game.loadLevel(filename);
			Game.getPlayer().setName(username);
			return;
		} else if (cmd.equalsIgnoreCase("exit")) {
			System.exit(0);
		} else if (cmd.equals("connect")) {
			String ip = JOptionPane.showInputDialog("Server ip :");
			String username = JOptionPane.showInputDialog("Username : ");
			selector.game.loadLevel(ip, 1);
			Game.getPlayer().setName(username);
			selector.game.getMpClient().loadPlayer();
		} else if (cmd.equals("leveleditor")) {
			th = new EditorThread();
			th.setLevelSelector(selector);
			Thread t = new Thread(th);
			t.start();
		} else if (cmd.equalsIgnoreCase("settings")) {
			selector.settingsGui = new SettingsGui();
			selector.settingsGui.setVisible(true);
		} else if (cmd.equalsIgnoreCase("levelserver")) {
			selector.levelServerconnector = new LevelServerConnector(selector);
			selector.levelServerconnector.setVisible(true);
		}
	}
}
