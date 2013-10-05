package JPushy.Types.Blocks;

import JPushy.Types.Picture;
/**
 * 
 * @author Marcel Benning
 * 
 */
public class LevelTeleporter extends Block {

	public LevelTeleporter(JPushy.Types.Blocks.Block b) {
		super(b);
		// TODO Auto-generated constructor stub
	}

	public LevelTeleporter(String name, int id, Picture img) {
		super(name, id, img);
		// TODO Auto-generated constructor stub
	}

	public LevelTeleporter(String name, int id, Picture img, boolean visible) {
		super(name, id, img, visible);
		// TODO Auto-generated constructor stub
	}

	public LevelTeleporter(String name, int id, Picture img,
			boolean playerAbleToWalkOn, boolean visible) {
		super(name, id, img, playerAbleToWalkOn, visible);
		// TODO Auto-generated constructor stub
	}

	public LevelTeleporter(String name, int id, Picture img,
			boolean playerAbleToWalkOn, boolean solid, boolean visible) {
		super(name, id, img, playerAbleToWalkOn, solid, visible);
		// TODO Auto-generated constructor stub
	}

	public LevelTeleporter(String name, int id, Picture img,
			boolean playerAbleToWalkOn, boolean solid, boolean visible,
			JPushy.Types.Blocks.Block invincebleBlock) {
		super(name, id, img, playerAbleToWalkOn, solid, visible,
				invincebleBlock);
		// TODO Auto-generated constructor stub
	}

	public LevelTeleporter(String name, int id, Picture img,
			boolean playerAbleToWalkOn, boolean solid, boolean visible,
			JPushy.Types.Blocks.Block invincebleBlock, boolean register) {
		super(name, id, img, playerAbleToWalkOn, solid, visible,
				invincebleBlock, register);
		// TODO Auto-generated constructor stub
	}

}
