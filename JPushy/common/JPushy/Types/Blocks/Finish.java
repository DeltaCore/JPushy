package JPushy.Types.Blocks;

import JPushy.Core.Game;
import JPushy.Types.Level.Level;
import JPushy.Types.gfx.Picture;

/**
 * 
 * @author Marcel Benning
 * 
 */
public class Finish extends Block {

	public Finish(String name, int id, Picture img) {
		super(name, id, img);
	}

	@Override
	public void onWalk(int x, int y, Level l) {
		// super.onWalk(x,y,l);
		System.out.println("Ouch !");
		Game.pushUpdate();
		if (!l.isLastStage()) {
			Game.sendMessage("You won this stage ! Get ready for another one ...");
			l.setActiveStage(l.getActiveStageI() + 1);
			Game.getPlayer().setFreezed(true);
			l.getActiveStage().init(Game.getPlayer());
			Game.getPlayer().setFreezed(false);
			Game.getPlayer().cancelNextMove();
		} else {
			Game.sendMessage("Yay ! You won this easy game for Elementary kids :DD");
			Game.stopGame();
		}
		Game.pushUpdate();
	}

	@Override
	public void init() {
		super.init();
		setPlayerAbleToWalkOn(true);
		setSolid(true);
		setDestroyable(false);
		setVisible(true);
	}

}
