package JPushy.Types.Blocks;

import java.util.ArrayList;

import JPushy.Types.Items.Items;
import JPushy.Types.Level.Level;
import JPushy.Types.Level.Stage;
import JPushy.Types.Player.Inventory;
import JPushy.Types.Player.Player;
import JPushy.Types.gfx.Picture;
import JPushy.gfx.PictureLoader;

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
	
	@Override
	public void init() {
		super.init();
		setPlayerAbleToWalkOn(false);
		setSolid(true);
		setInvincebleBlock(Blocks.getBlockById(Blocks.air.getId()));
	}
	
	@Override
	public void onBlockActivated(Stage stage, Player p) {
		super.onBlockActivated(stage, p);
		if(p.getInventory().removeItem(Items.key)){
			this.setPlayerAbleToWalkOn(true);
			this.setTexture(PictureLoader.loadImageFromFile("door_open.png"));
			this.setVisible(false);
		}
		playSound();
	}
	
}
