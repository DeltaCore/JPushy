package JPushy.Types.Blocks;

import JPushy.Items;
import JPushy.Player;
import JPushy.Types.Picture;
import JPushy.Types.Level.Level;
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
	public void onBlockActivated(Level l, Player p) {
		super.onBlockActivated(l, p);
		inv = p.getInventory();
		boolean key = false;
		for(int i = 0;i<inv.getSlots().length;i++){
			if(inv.getSlots()[i].getItem().getName().equalsIgnoreCase("Key") && !key){
				key = true;
				inv.getSlots()[i].setItem(Items.noitem);
			}
		}
		if(!active){
			this.active = true;
			setPlayerAbleToWalkOn(true);
		}
	}
	
}
