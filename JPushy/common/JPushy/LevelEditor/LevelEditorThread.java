package JPushy.LevelEditor;

import javax.swing.SwingUtilities;

import JPushy.Core.Game;
import JPushy.Core.Launcher;

public class LevelEditorThread {
	public LevelEditorThread(final Game game) {
		Launcher.setWindowTitle("Leveleditor");
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
