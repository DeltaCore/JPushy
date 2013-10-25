package JPushy.Types.Blocks;

import JPushy.Types.Level.Level;
import JPushy.Types.gfx.Picture;
/**
 * 
 * @author Marcel Benning
 * 
 */
public class Button extends Block {

	public Button(String name, int id, Picture img) {
		super(name, id, img);
	}

	private int x = 0, y = 0;
		
	@Override
	public void init() {
		super.init();
		this.setLever(true);
	}
	
	@Override
	public void onOccupied(boolean o, Level l){
		if(o){
			Block b = l.getActiveStage().getBlock(x, y);
			b.set();
			l.getActiveStage().setBlock(x, y, b);
		}else{
			Block b = l.getActiveStage().getBlock(x, y);
			b.reset();
			l.getActiveStage().setBlock(x, y, b);
		}
	}
	
	public int getX() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
}
