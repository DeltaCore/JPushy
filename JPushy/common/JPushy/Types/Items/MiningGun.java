package JPushy.Types.Items;

import JPushy.Blocks;
import JPushy.Game;
import JPushy.Types.Picture;
import JPushy.Types.Blocks.Block;
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
	public void onUse(Stage stage, int dir) {
		super.onUse(stage, dir);
		int y = Game.getPlayer().getY();
		int x = Game.getPlayer().getX();
		Block b = Blocks.getBlockById(0);
		if(dir == 0){
			b = Game.gameThread.getLevel().getActiveStage().getBlock(x, y - 1);
			b.onBlockActivated(Game.gameThread.getLevel().getActiveStage(), Game.getPlayer());
			stage.getBlock(x, y - 1).onDestroy();
			stage.destroyBlock(x, y - 1);
		}else if(dir == 1){
			b = Game.gameThread.getLevel().getActiveStage().getBlock(x + 1, y);
			b.onBlockActivated(Game.gameThread.getLevel().getActiveStage(), Game.getPlayer());
			stage.getBlock(x + 1, y).onDestroy();
			stage.destroyBlock(x + 1, y);
		}else if(dir == 2){
			b = Game.gameThread.getLevel().getActiveStage().getBlock(x, y + 1);
			b.onBlockActivated(Game.gameThread.getLevel().getActiveStage(), Game.getPlayer());
			stage.getBlock(x, y + 1).onDestroy();
			stage.destroyBlock(x, y + 1);
		}else if(dir == 3){
			b = Game.gameThread.getLevel().getActiveStage().getBlock(x - 1, y);
			b.onBlockActivated(Game.gameThread.getLevel().getActiveStage(), Game.getPlayer());
			stage.getBlock(x - 1, y).onDestroy();
			stage.destroyBlock(x - 1, y);
		}
	
	}
	
}
