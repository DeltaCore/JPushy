package JPushy.Types.Blocks;

import java.util.ArrayList;

import JPushy.Types.Level.Stage;
import JPushy.Types.gfx.Picture;

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
