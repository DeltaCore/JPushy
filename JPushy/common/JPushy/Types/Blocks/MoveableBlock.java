package JPushy.Types.Blocks;

import JPushy.Types.Level.Level;
import JPushy.Types.gfx.Picture;

/**
 * 
 * @author Marcel Benning
 * 
 */
public class MoveableBlock extends Block {

	public MoveableBlock(String name, int id, Picture img) {
		super(name, id, img);
	}

	@Override
	public void onPush(int oldX, int oldY, int newX, int newY, int side, Level l) {
		super.onPush(oldX, oldY, newX, newY, side, l);
	}

}
