package net.ccmob.apps.jpushy.blocks;

import net.ccmob.apps.jpushy.graphics.Picture;

/**
 * 
 * @author Marcel Benning
 * 
 */
public class Gate extends Block {

	public Gate(String name, int id, Picture img) {
		super(name, id, img);
	}

	@Override
	public void init() {
		super.init();
		this.setSwitchable(true);
	}

	@Override
	public void toggle() {
		if (this.isSwitchable()) {
			this.setVisible(!this.isVisible());
			this.setPlayerAbleToWalkOn(!this.isPlayerAbleToWalkOn());
			this.setCanGetocupied(!this.isCanGetocupied());
		}
	}

	@Override
	public void reset() {
		this.setVisible(true);
		this.setPlayerAbleToWalkOn(false);
		this.setCanGetocupied(false);
	}

	@Override
	public void set() {
		this.setVisible(false);
		this.setPlayerAbleToWalkOn(true);
		this.setCanGetocupied(true);
	}

}
