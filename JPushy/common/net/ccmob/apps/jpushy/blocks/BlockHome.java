package net.ccmob.apps.jpushy.blocks;

import net.ccmob.apps.jpushy.graphics.Picture;
import net.ccmob.apps.jpushy.sp.level.Stage;

public class BlockHome extends Block {

	public BlockHome(String name, int id, Picture img) {
		super(name, id, img);
	}
	
	@Override
	public void onLoaded(int x, int y, int stageId, Stage stage) {
	  super.onLoaded(x, y, stageId, stage);
	  stage.setHomeY(y);
		stage.setHomeX(x);
	}

}
