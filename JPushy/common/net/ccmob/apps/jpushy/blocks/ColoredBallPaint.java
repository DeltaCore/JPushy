/**
 * 
 */
package net.ccmob.apps.jpushy.blocks;

import net.ccmob.apps.jpushy.blocks.ColoredBall.NamedColor;
import net.ccmob.apps.jpushy.core.Game;
import net.ccmob.apps.jpushy.graphics.Picture;
import net.ccmob.apps.jpushy.sp.level.LVData;
import net.ccmob.apps.jpushy.sp.level.Level;
import net.ccmob.apps.jpushy.sp.level.Stage;

/**
 * @author marcelbenning
 * 
 */
public class ColoredBallPaint extends Block {

	private NamedColor	color;

	public ColoredBallPaint(String name, int id, Picture img, NamedColor c) {
		super(name, id, img);
		this.setPlayerAbleToWalkOn(true);
		this.setSolid(true);
		this.setCanGetocupied(true);
		this.setDestroyable(false);
		this.setVisible(true);
		this.setColor(c);
		this.setInvincebleBlock(Blocks.getBlockById(Blocks.air.getId()));
	}

	@Override
	public void onOccupied(boolean o, Level l) {
		if (this.isVisible()) {
			if (o) {
				Stage s = l.getActiveStage();
				System.out.println(s.getMoveableBlock(getX(), getY()));
				System.out.println("Block at pos [" + this.getX() + "|" + this.getY() + "]");
				Game.sendMessage("Block at pos [" + this.getX() + "|" + this.getY() + "] " + s.getMoveableBlock(this.getX(), this.getY()).toString());
				if (s.getMoveableBlock(this.getX(), this.getY()) instanceof ColoredBall) {
					ColoredBall b = (ColoredBall) s.getMoveableBlock(this.getX(), this.getY());
					if(this.getColor().equals(b.getColor())){
						return;
					}
					String dataNameOld = ((ColoredBall) s.getMoveableBlock(this.getX(), this.getY())).getData().getDataName();
					String dataNameNew = this.getColor().getColorName() + " balls left";
					s.getMoveableBlock(this.getX(), this.getY()).onSpecialAction();
					s.setMoveableBlock(ColoredBall.getColoredBall(this.getColor()), this.getX(), this.getY());
					l.getActiveStage().getDataList().getDataByDataName(dataNameOld).setInt(l.getActiveStage().getDataList().getDataByDataName(dataNameOld).getInt() - 1);
					if (!l.getActiveStage().getDataList().dataExists(new LVData(dataNameNew))) {
						LVData d = new LVData(dataNameNew);
						d.setInt(1);
						l.getActiveStage().getDataList().addData(d);
					} else {
						l.getActiveStage().getDataList().getDataByDataName(dataNameNew).setInt(l.getActiveStage().getDataList().getDataByDataName(dataNameNew).getInt() + 1);
					}
					this.setVisible(false);
					l.getActiveStage().setBlock(this.getX(), this.getY(), Blocks.getBlockById(Blocks.air.getId()));
				} else {
					Game.sendMessage("MEEEP ! Wrong ball ...");
				}
			}
		}
		super.onOccupied(o, l);
	}

	@Override
	public void onLoaded(int x, int y, int stageId, Stage stage) {
		this.setX(x);
		this.setY(y);
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
	public void setColor(NamedColor color) {
		this.color = color;
	}
}
