package JPushy.Types.Blocks;

import java.awt.Color;

import JPushy.Types.Level.Level;
import JPushy.Types.gfx.Picture;

public class ColoredBallBox extends Block {

	private int	  ballsLeft	= 0;
	private Color	color	    = Color.white;

	public ColoredBallBox(String name, int id, Picture img, Color c) {
		super(name, id, img);
		this.setPlayerAbleToWalkOn(true);
		this.setSolid(true);
		this.setCanGetocupied(true);
		this.setDestroyable(false);
		this.setVisible(true);
		this.setColor(c);
	}

	@Override
	public void onBlockPushedOnMe(Block b, Level l) {
		super.onBlockPushedOnMe(b, l);
	}

	/**
	 * @return the ballsLeft
	 */
	public int getBallsLeft() {
		return ballsLeft;
	}

	/**
	 * @param ballsLeft
	 *          the ballsLeft to set
	 */
	public void setBallsLeft(int ballsLeft) {
		this.ballsLeft = ballsLeft;
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color
	 *          the color to set
	 */
	public Block setColor(Color color) {
		this.color = color;
		return this;
	}

}
