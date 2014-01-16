/**
 * 
 */
package JPushy.Types.Blocks;

import java.awt.Color;
import java.util.ArrayList;

import JPushy.Types.Level.Stage;
import JPushy.Types.gfx.Picture;

/**
 * @author marcelbenning
 * 
 */
public class ColoredBall extends MoveableBlock {

	private Block	b;
	private Color	color	= Color.white;

	public ColoredBall(String name, int id, Picture img, Color c) {
		super(name, id, img);
		this.setColor(c);
	}

	@Override
	public Block onConfigLoaded(int x, int y, int stageId, ArrayList<String> cfgLines, Stage stage) {
		return super.onConfigLoaded(x, y, stageId, cfgLines, stage);
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
	public void setColor(Color color) {
		this.color = color;
	}

}
