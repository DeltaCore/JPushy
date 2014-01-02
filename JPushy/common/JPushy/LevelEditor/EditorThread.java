package JPushy.LevelEditor;

import JPushy.Core.Game;

/**
 * 
 * @author Marcel Benning
 * 
 */
public class EditorThread implements Runnable {

	public OldLevelEditorGui	editor;
	private Game	           selector;

	public void setLevelSelector(Game s) {
		this.selector = s;
	}

	@Override
	public void run() {
		editor = new OldLevelEditorGui(selector);
	}

}
