package net.ccmob.apps.jpushy.blocks;

import net.ccmob.apps.jpushy.core.Game;
import net.ccmob.apps.jpushy.graphics.Picture;
import net.ccmob.apps.jpushy.sp.level.Level;
import net.ccmob.apps.jpushy.sp.level.Wincondition;

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
		super.onWalk(x,y,l);
		System.out.println("Ouch !");
		Game.pushUpdate();
		if (l.getActiveStage().getWinConditions().allCompleted()) {
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
		} else {
			Game.sendMessage("Sorry, you are not done yet :/");
			Game.sendMessage("Tasks : ");
			for (int i = 0; i < l.getActiveStage().getDataList().getList().size(); i++) {
				String s = l.getActiveStage().getDataList().getList().get(i).getDataName() + "[";
				if (l.getActiveStage().getDataList().getList().get(i).isiSet()) {
					s += l.getActiveStage().getDataList().getList().get(i).getInt() + "|";
				} else {
					s += "NoInt|";
				}
				if (l.getActiveStage().getDataList().getList().get(i).issSet()) {
					s += l.getActiveStage().getDataList().getList().get(i).getString() + "|";
				} else {
					s += "NoString|";
				}
				if (l.getActiveStage().getDataList().getList().get(i).isoSet()) {
					s += l.getActiveStage().getDataList().getList().get(i).getObject() + "]";
				} else {
					s += "NoObj]";
				}
				Game.sendMessage(s);
			}
			for (Wincondition w : l.getActiveStage().getWinConditions().getList()) {
				Game.sendMessage("->" + w.getName() + " : " + w.isComplete());
			}
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
