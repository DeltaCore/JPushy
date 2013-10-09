package JPushy.Types.Items;

import JPushy.Game;
import JPushy.Types.Picture;
import JPushy.Types.Level.Stage;

public class MiningGun extends Item {

	public MiningGun(String name, int id, Picture texture) {
		super(name, id, texture);
		// TODO Auto-generated constructor stub
	}

	public MiningGun(String name, int id, Picture texture, boolean register) {
		super(name, id, texture, register);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onUse(Stage stage) {
		super.onUse(stage);
		int y = Game.getPlayer().getY();
		for(int x = Game.getPlayer().getX();x<stage.getBlocks().length;x++){
			if(stage.getBlock(x, y).isDestroyable()){
				stage.getBlock(x, y).onDestroy();
				stage.destroyBlock(x, y);
				Game.sendMessage("Booom ! That block is gone ...");
				return;
			}
		}
		
	}
	
	
	
}
