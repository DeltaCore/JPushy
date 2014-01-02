package JPushy.Types.Blocks;

import JPushy.Types.Level.Stage;
import JPushy.Types.Player.Inventory;
import JPushy.Types.Player.Player;
import JPushy.Types.gfx.Picture;
import JPushy.gfx.PictureLoader;

public class BlockDoor extends Block {

	Inventory	inv;
	boolean	  active	= false;

	public BlockDoor(String name, int id, Picture img) {
		super(name, id, img);
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
		System.out.println("Someone unlocked me !");
		if (p.getInventory().getItemInHand().getName().equalsIgnoreCase("key")) {
			this.setPlayerAbleToWalkOn(true);
			this.setTexture(PictureLoader.loadImageFromFile("door_open.png"));
			this.setVisible(false);
		} else {
			System.out.println("Sorry, tryed to unlocked me ... -->> !! Without a key !");
		}
	}

}
