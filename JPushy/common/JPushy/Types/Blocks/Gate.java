package JPushy.Types.Blocks;

import JPushy.Types.gfx.Picture;
/**
 * 
 * @author Marcel Benning
 * 
 */
public class Gate extends Block {
	
	public Gate(Block b){
		super(b);
	}
	
	public Gate(String name, int id, Picture img) {
		super(name, id, img);
	}

	public Gate(String name, int id, Picture img, boolean visible) {
		super(name, id, img, visible);
	}
	
	public Gate(String name, int id, Picture img, boolean playerAbleToWalkOn,
			boolean visible) {
		super(name, id, img, playerAbleToWalkOn, visible);
	}

	public Gate(String name, int id, Picture img, boolean playerAbleToWalkOn,
			boolean solid, boolean visible) {
		super(name, id, img, playerAbleToWalkOn, solid, visible);
	}
	
	@Override
	public void init() {
		super.init();
		this.setSwitchable(true);
	}
	
	@Override
	public void toggle() {
		if(this.isSwitchable()){
			this.setVisible(!this.isVisible());
			this.setPlayerAbleToWalkOn(!this.isPlayerAbleToWalkOn());
		}
		super.toggle();
	}
	
	@Override
	public void set() {
		this.setVisible(true);
		this.setPlayerAbleToWalkOn(false);
	}
	
	@Override
	public void reset() {
		this.setVisible(false);
		this.setPlayerAbleToWalkOn(true);
	}
	
}
