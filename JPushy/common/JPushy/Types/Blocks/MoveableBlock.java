package JPushy.Types.Blocks;

import JPushy.Types.Picture;
import JPushy.Types.Level.Level;
/**
 * 
 * @author Marcel Benning
 * 
 */
public class MoveableBlock extends Block {

	public MoveableBlock(Block b) {
		super(b);
	}

	public MoveableBlock(String name, int id, Picture img) {
		super(name, id, img);
	}

	public MoveableBlock(String name, int id, Picture img, boolean visible) {
		super(name, id, img, visible);
	}

	public MoveableBlock(String name, int id, Picture img, boolean playerAbleToWalkOn,
			boolean visible) {
		super(name, id, img, playerAbleToWalkOn, visible);
	}

	public MoveableBlock(String name, int id, Picture img, boolean playerAbleToWalkOn,
			boolean solid, boolean visible) {
		super(name, id, img, playerAbleToWalkOn, solid, visible);
	}

	public MoveableBlock(String name, int id, Picture img, boolean playerAbleToWalkOn,
			boolean solid, boolean visible, Block invincebleBlock) {
		super(name, id, img, playerAbleToWalkOn, solid, visible,
				invincebleBlock);
	}

	public MoveableBlock(String name, int id, Picture img, boolean playerAbleToWalkOn,
			boolean solid, boolean visible, Block invincebleBlock,
			boolean register) {
		super(name, id, img, playerAbleToWalkOn, solid, visible,
				invincebleBlock, register);
	}

	@Override
	public void onPush(int oldX, int oldY, int newX, int newY, int side, Level l) {
		super.onPush(oldX, oldY, newX, newY, side, l);
		Block bo = l.getActiveStage().getBlock(oldX, oldY);
		Block bn = l.getActiveStage().getBlock(newX, newY);
		bo.onOccupied(false, l);
		bn.onOccupied(true, l);
	}
	
}
