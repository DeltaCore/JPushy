package JPushy.Types.Blocks;

import JPushy.Types.gfx.Picture;
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
		if(this.isSwitchable()){
			this.setVisible(!this.isVisible());
			this.setPlayerAbleToWalkOn(!this.isPlayerAbleToWalkOn());
		}
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
