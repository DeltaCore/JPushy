package JPushy.Types.Items;

import JPushy.Core.Game;
import JPushy.Types.Blocks.Block;
import JPushy.Types.Blocks.Blocks;
import JPushy.Types.Level.Stage;
import JPushy.Types.gfx.Picture;
/**
 * 
 * @author Marcel Benning
 * 
 */
public class ItemKey extends Item {
	
	public ItemKey(String name, int id, Picture texture) {
		super(name, id, texture);
		setMaxStackSize(10);
	}
	//MS4wMzgzfHwxMzc3OTY3MjcwOTYwO05hTjsxMzgyMDEwNjYyOTE5fDAxMTAwMHw0OTcyNzIxNzA2ODcuNjM2OzE5NzM3NjIyODM4MzIuMDEyNzsxMjk3NDszMTszOTkyOTE4NDguMzIyMTc2MTY7MTY7LTE7LTE7MDswOzA7MDs2NTs1MDkxOTswOzF8NTEsNTEsMzcwNTUzOTQsMDs3LDcsNjQ2ODI0OCwwOzYsNiw0NTEzMDcsMDs1LDUsMTY0NzY2MywwOzUsNSw1OTUyNjc2LDA7OSw5LDI1MjAyMDU3LDA7MTAsMTAsMTgyNTA2NTMyLDA7MTAsMTAsMjExNjkxNDI2OCwwOzUsNSw0ODYwNzc0NzMyLDA7NTIxLDUyMSw3NzA5NzUxMzkzOTcsMDt8NDQ3Njc5MjQ4NjcyNTYzMTsyMjUxOTM3MjUyNjMyNTc1OzIyNTE4MDE5NjExNDgzODU7Mzk0MDY1MjQ5MjUzMTgzOTsyMjUxOTM3MjUyNjM5MTUxOzUyNDI4OXwzNTM4NDg0MTAyODQwMzE5OzIyNTM3MjgzODc5OTQxODU7MzM%3D%21END%21
	@Override
	public void onUse(Stage stage, int dir) {
		super.onUse(stage, dir);
		int x = Game.getPlayer().getX();
		int y = Game.getPlayer().getY();
		Block b = Blocks.getBlockById(0);
		if(dir == 0){
			b = Game.gameThread.getLevel().getActiveStage().getBlock(x, y - 1);
			b.onBlockActivated(Game.gameThread.getLevel().getActiveStage(), Game.getPlayer());
		}else if(dir == 1){
			b = Game.gameThread.getLevel().getActiveStage().getBlock(x + 1, y);
			b.onBlockActivated(Game.gameThread.getLevel().getActiveStage(), Game.getPlayer());
		}else if(dir == 2){
			b = Game.gameThread.getLevel().getActiveStage().getBlock(x, y + 1);
			b.onBlockActivated(Game.gameThread.getLevel().getActiveStage(), Game.getPlayer());
		}else if(dir == 3){
			b = Game.gameThread.getLevel().getActiveStage().getBlock(x - 1, y);
			b.onBlockActivated(Game.gameThread.getLevel().getActiveStage(), Game.getPlayer());
		}
	}
}
