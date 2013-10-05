package JPushy.Types.Blocks;

import JPushy.Blocks;
import JPushy.Types.Picture;
import JPushy.Types.Level.Level;
/**
 * 
 * @author Marcel Benning
 * 
 */
public class SwitchBlock extends Block {
	public SwitchBlock(Block b) {
		super(b);
	}
}
//	private int x = 0, y = 0;
//	private boolean active;
//	
//	public SwitchBlock(Block b) {
//		super(b);
//		// TODO Auto-generated constructor stub
//	}
//	
//	public SwitchBlock(String name, int id, Picture img) {
//		super(name, id, img);
//		// TODO Auto-generated constructor stub
//	}
//
//	public SwitchBlock(String name, int id, Picture img, boolean visible) {
//		super(name, id, img, visible);
//	}
//
//	public SwitchBlock(String name, int id, Picture img,
//			boolean playerAbleToWalkOn, boolean visible) {
//		super(name, id, img, playerAbleToWalkOn, visible);
//	}
//
//	public SwitchBlock(String name, int id, Picture img,
//			boolean playerAbleToWalkOn, boolean solid, boolean visible) {
//		super(name, id, img, playerAbleToWalkOn, solid, visible);
//	}
//
//	public SwitchBlock(String name, int id, Picture img,
//			boolean playerAbleToWalkOn, boolean solid, boolean visible,
//			Block invincebleBlock) {
//		super(name, id, img, playerAbleToWalkOn, solid, visible,
//				invincebleBlock);
//	}
//
//	public SwitchBlock(String name, int id, Picture img,
//			boolean playerAbleToWalkOn, boolean solid, boolean visible,
//			Block invincebleBlock, boolean register) {
//		super(name, id, img, playerAbleToWalkOn, solid, visible,
//				invincebleBlock, register);
//	}
//
//	@Override
//	public void init() {
//		super.init();
//	}
//	
//	@Override
//	public void onWalk(int x, int y, Level l) {
//		super.onWalk(x,y,l);
//		Block b = l.getBlock(this.x, this.y);
//		System.out.println(this.x+":"+this.y);
//		if(active){
//			b.set();
//			SwitchBlock s = (SwitchBlock) Blocks.leverOff;
//			s.setActive(false);
//			s.setX(this.x);
//			s.setY(this.y);
//			l.setBlock(this.x, this.y, b);
//			l.setBlock(x, y, s);
//		}else{
//			b.reset();
//			SwitchBlock s = (SwitchBlock) Blocks.leverOn;
//			s.setActive(true);
//			s.setX(this.x);
//			s.setY(this.y);
//			l.setBlock(this.x, this.y, b);
//			l.setBlock(x, y, s);
//		}
//	}
//	
//	public int getX() {
//		return x;
//	}
//
//	public void setX(int x) {
//		this.x = x;
//	}
//
//	public int getY() {
//		return y;
//	}
//
//	public void setY(int y) {
//		this.y = y;
//	}
//
//	public boolean isActive() {
//		return active;
//	}
//
//	public void setActive(boolean active) {
//		this.active = active;
//	}
//	
//}
