package net.ccmob.apps.jpushy.sp.level.editor;

import javax.swing.SwingUtilities;

import net.ccmob.apps.jpushy.core.Game;
import net.ccmob.apps.jpushy.core.JPushy;

public class LevelEditorThread {
	public LevelEditorThread(final Game game) {
		JPushy.setWindowTitle("Leveleditor");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					LevelEditorGui frame = new LevelEditorGui(game);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
