package JPushy.Types.Blocks;

import JPushy.Items;
import JPushy.Player;
import JPushy.Types.Picture;
import JPushy.Types.Level.Level;
import JPushy.Types.Level.Stage;
import JPushy.Types.Player.Inventory;

public class BlockDoor extends Block {

	Inventory inv;
	boolean active = false;
	
	public BlockDoor(JPushy.Types.Blocks.Block b) {
		super(b);
		// TODO Auto-generated constructor stub
	}

	public BlockDoor(String name, int id, Picture img) {
		super(name, id, img);
		// TODO Auto-generated constructor stub
	}

	public BlockDoor(String name, int id, Picture img, boolean visible) {
		super(name, id, img, visible);
		// TODO Auto-generated constructor stub
	}

	public BlockDoor(String name, int id, Picture img,
			boolean playerAbleToWalkOn, boolean visible) {
		super(name, id, img, playerAbleToWalkOn, visible);
		// TODO Auto-generated constructor stub
	}

	public BlockDoor(String name, int id, Picture img,
			boolean playerAbleToWalkOn, boolean solid, boolean visible) {
		super(name, id, img, playerAbleToWalkOn, solid, visible);
		// TODO Auto-generated constructor stub
	}

	public BlockDoor(String name, int id, Picture img,
			boolean playerAbleToWalkOn, boolean solid, boolean destroyable,
			boolean visible, JPushy.Types.Blocks.Block invincebleBlock) {
		super(name, id, img, playerAbleToWalkOn, solid, destroyable, visible,
				invincebleBlock);
		// TODO Auto-generated constructor stub
	}

	public BlockDoor(String name, int id, Picture img,
			boolean playerAbleToWalkOn, boolean solid, boolean destroyable,
			boolean visible, JPushy.Types.Blocks.Block invincebleBlock,
			boolean register) {
		super(name, id, img, playerAbleToWalkOn, solid, destroyable, visible,
				invincebleBlock, register);
	}
	
	@Override
	public void init() {
		super.init();
		setPlayerAbleToWalkOn(false);
		setSolid(true);
	}
	
	@Override
	public void onBlockActivated(Stage stage, Player p) {
		super.onBlockActivated(stage, p);
		if(p.getInventory().removeItem(Items.key)){
			this.setPlayerAbleToWalkOn(true);
		}
	}
	
}
