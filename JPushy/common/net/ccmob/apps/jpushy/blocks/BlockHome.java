package net.ccmob.apps.jpushy.blocks;

import java.util.ArrayList;

import net.ccmob.apps.jpushy.graphics.Picture;
import net.ccmob.apps.jpushy.sp.level.Stage;

public class BlockHome extends Block {

	public BlockHome(String name, int id, Picture img) {
		super(name, id, img);
	}

	@Override
	public Block onConfigLoaded(int x, int y, int stageId, ArrayList<String> cfgLines, Stage stage) {
		stage.setHomeY(y);
		stage.setHomeX(x);
		// if (Core.getSettings().getSettings(Core.getSettings().debug))
		System.out.println("Home coords for stage:" + stage.getId() + " x:" + y + " y:" + x);
		return this;
	}

}
