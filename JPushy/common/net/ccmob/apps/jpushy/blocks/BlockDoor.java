package net.ccmob.apps.jpushy.blocks;

import net.ccmob.apps.jpushy.graphics.Picture;
import net.ccmob.apps.jpushy.graphics.PictureLoader;
import net.ccmob.apps.jpushy.sp.level.Stage;
import net.ccmob.apps.jpushy.sp.player.Inventory;
import net.ccmob.apps.jpushy.sp.player.Player;

public class BlockDoor extends Block {

	Inventory	inv;
	boolean	  active	= false;

	public BlockDoor(String name, int id, Picture img) {
		super(name, id, img);
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
