package JPushy.Types.Blocks;

import JPushy.Types.Picture;
import JPushy.Types.Level.Level;
/**
 * 
 * @author Marcel Benning
 * 
 */
public class Button extends Block {

	private int x = 0, y = 0;
	
	public Button(String name, int id, Picture img) {
		super(name, id, img);
		// TODO Auto-generated constructor stub
	}

	public Button(String name, int id, Picture img, boolean visible) {
		super(name, id, img, visible);
		// TODO Auto-generated constructor stub
	}

	public Button(String name, int id, Picture img, boolean playerAbleToWalkOn,
			boolean visible) {
		super(name, id, img, playerAbleToWalkOn, visible);
		// TODO Auto-generated constructor stub
	}

	public Button(String name, int id, Picture img, boolean playerAbleToWalkOn,
			boolean solid, boolean visible) {
		super(name, id, img, playerAbleToWalkOn, solid, visible);
		// TODO Auto-generated constructor stub
	}

	public Button(String name, int id, Picture img, boolean playerAbleToWalkOn,
			boolean solid, boolean visible, Block invincebleBlock) {
		super(name, id, img, playerAbleToWalkOn, solid, visible,
				invincebleBlock);
		// TODO Auto-generated constructor stub
	}

	public Button(String name, int id, Picture img, boolean playerAbleToWalkOn,
			boolean solid, boolean visible, Block invincebleBlock,
			boolean register) {
		super(name, id, img, playerAbleToWalkOn, solid, visible,
				invincebleBlock, register);
		// TODO Auto-generated constructor stub
	}
	
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
