package JPushy.LevelEditor;

import JPushy.Gui.LevelSelector;

public class EditorThread implements Runnable{

	public LevelEditor editor;
	private LevelSelector selector;
	
	public void setLevelSelector(LevelSelector s){
		this.selector = s;
	}
	
	@Override
	public void run() {
		editor = new LevelEditor(selector);	
	}

}
