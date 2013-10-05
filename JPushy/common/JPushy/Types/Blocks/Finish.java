package JPushy.Types.Blocks;

import JPushy.Core;
import JPushy.Game;
import JPushy.Types.Picture;
import JPushy.Types.Level.Level;
/**
 * 
 * @author Marcel Benning
 * 
 */
public class Finish extends Block{

	public Finish(String name, int id, Picture img, boolean playerAbleToWalkOn,
			boolean solid, boolean visible) {
		super(name, id, img, playerAbleToWalkOn, solid, visible);
	}
	
	@Override
	public void onWalk(int x, int y, Level l) {
		super.onWalk(x,y,l);
		Game.pushUpdate();
		if(!l.isLastStage()){
			Game.win("You won this stage ! Get ready for another one ...");
			l.setActiveStage(l.getActiveStageI() + 1);
			l.getActiveStage().init(Game.getPlayer());
		}else{
			Game.win("I mean , you won this easy game for Elementary kids :DD");
			Game.stopGame();
		}
		Game.pushUpdate();
	}
	
}