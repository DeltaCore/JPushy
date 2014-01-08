package JPushy.LevelEditor;

import javax.swing.SwingUtilities;

import JPushy.Core.Launcher;

public class LevelEditorThread {
	public LevelEditorThread() {
		Launcher.setWindowTitle("Leveleditor");
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

}
