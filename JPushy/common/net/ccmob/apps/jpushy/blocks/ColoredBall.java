/**
 * 
 */
package net.ccmob.apps.jpushy.blocks;

import java.awt.Color;
import java.util.ArrayList;

import net.ccmob.apps.jpushy.core.Game;
import net.ccmob.apps.jpushy.graphics.Picture;
import net.ccmob.apps.jpushy.sp.level.LVData;
import net.ccmob.apps.jpushy.sp.level.Stage;

/**
 * @author marcelbenning
 * 
 */
public class ColoredBall extends MoveableBlock {

	public enum NamedColor {
	  BLUE(Color.BLUE, "Blue"),
	  RED(Color.RED, "Red"),
	  GREEN(Color.GREEN, "Green"),
	  WHITE(Color.WHITE, "White");

	  private final Color awtColor;
	  private final String colorName;

	  private NamedColor(Color awtColor, String name) {
	    this.awtColor = awtColor;
	    this.colorName = name;
	  }
	  
	  public Color getAwtColor() {
	    return awtColor;
	  }

	  public String getColorName() {
	    return colorName;
	  }
	}
	
	private LVData	data;

	private NamedColor	 color	= NamedColor.WHITE;

	public static ColoredBall getColoredBall(NamedColor color) {
		for (int i = 0; i < Blocks.blockRegistry.size(); i++) {
			try {
				ColoredBall b = (ColoredBall) Blocks.getBlockById(i);
				if (b.getColor() == color) {
					return b;
				}
			} catch (Exception e) {
				
			}
		}
		return null;
	}

	public ColoredBall(String name, int id, Picture img, NamedColor c) {
		super(name, id, img);
		this.setDestroyable(false);
		this.setMovable(true);
		this.setPlayerAbleToWalkOn(false);
		this.setSolid(false);
		this.setVisible(true);
		this.setColor(c);
		data = new LVData(this.getColor().getColorName() + " balls left");
	}

	@Override
	public Block onConfigLoaded(int x, int y, int stageId, ArrayList<String> cfgLines, Stage stage) {
		return super.onConfigLoaded(x, y, stageId, cfgLines, stage);
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

	/**
	 * @return the data
	 */
	public LVData getData() {
		return data;
	}

	/**
	 * @param data
	 *          the data to set
	 */
	public void setData(LVData data) {
		this.data = data;
	}

	@Override
	public void onLoaded(int x, int y, int stageId, Stage stage) {
		if (!stage.getDataList().dataExists(data)) {
			stage.getDataList().addData(data);
		}
		LVData d = stage.getDataList().getDataByDataName(data.getDataName());
		d.setInt(d.getInt() + 1);
		stage.getDataList().setData(d);
	}

	@Override
	public void onSpecialAction() {
		Game.getActiveLevel().getActiveStage().getDataList().getDataByDataName(data.getDataName()).setInt(Game.getActiveLevel().getActiveStage().getDataList().getDataByDataName(data.getDataName()).getInt() - 1);
	}

}
