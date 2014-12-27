package net.ccmob.apps.jpushy.blocks;

import net.ccmob.apps.jpushy.blocks.ColoredBall.NamedColor;
import net.ccmob.apps.jpushy.core.Game;
import net.ccmob.apps.jpushy.graphics.Picture;
import net.ccmob.apps.jpushy.sp.level.Level;
import net.ccmob.apps.jpushy.sp.level.Stage;

public class ColoredBallBox extends Block {

	private int	  ballsLeft	= 0;
	private NamedColor	color	    = NamedColor.WHITE;
	private int	  x	        = 0;
	private int	  y	        = 0;

	public ColoredBallBox(String name, int id, Picture img, NamedColor c) {
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
	public NamedColor getColor() {
		return color;
	}

	/**
	 * @param color
	 *          the color to set
	 */
	public Block setColor(NamedColor color) {
		this.color = color;
		return this;
	}

	@Override
	public void onLoaded(int x, int y, int stageId, Stage stage) {
		this.x = x;
		this.y = y;
		stage.getWinConditions().newWinConWincondition("Ball " + this.getColor().getColorName().toLowerCase() + " Finished");
	}

	@Override
	public void onOccupied(boolean o, Level l) {
		if (o) {
			Stage s = l.getActiveStage();
			Game.sendMessage("Block at pos [" + x + "|" + y + "] " + s.getMoveableBlock(x, y));

			if (((ColoredBall) s.getMoveableBlock(x, y)).getColor() == this.getColor()) {
				Game.sendMessage("Yes ! Right type of ball");
				String dataName = ((ColoredBall) s.getMoveableBlock(x, y)).getData().getDataName();
				s.getMoveableBlock(x, y).onSpecialAction();
				s.setMoveableBlock(null, x, y);
				// s.getDataList().getDataByDataName(dataName).setInt(s.getDataList().getDataByDataName(dataName).getInt()
				// + 1);
				this.setOccupiedByBlock(null);
				this.setOcupied(false);
				int left = s.getDataList().getDataByDataName(dataName).getInt();
				if (left == 0) {
					s.getWinConditions().completeCondition("Ball " + this.getColor().toString() + " Finished");
				}
			} else {
				Game.sendMessage("MEEEP ! Wrong ball ...");
			}

		}
		super.onOccupied(o, l);
	}
}
