package JPushy.Types.Blocks;

import JPushy.Types.Level.Level;
import JPushy.Types.Level.Stage;
import JPushy.Types.Level.LV.LVData;
import JPushy.Types.gfx.Picture;
import JPushy.gfx.PictureLoader;

public class BaseBlock extends Block {

	public BaseBlock(String name, int id, Picture img) {
		super(name, id, img);
		this.setPlayerAbleToWalkOn(true);
		this.setSolid(true);
		this.setDestroyable(false);
		this.setVisible(true);
		this.setCanGetocupied(true);
	}

	private boolean checkToDisable(Stage s) {
		LVData data = s.getDataList().getDataByDataName("PLAYER_DISABLE");
		if (data != null) {
			if (data.isiSet()) {
				if (data.getInt() == 1) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean checkToDisablePush(Stage s) {
		LVData data = s.getDataList().getDataByDataName("PUSH_DISABLE");
		if (data != null) {
			if (data.isiSet()) {
				if (data.getInt() == 1) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void onWalk(int x, int y, Level l) {
		super.onWalk(x, y, l);
		if (checkToDisable(l.getActiveStage())) {
			this.setTexture(PictureLoader.loadImageFromFile("disabled.png"));
			this.setPlayerAbleToWalkOn(false);
			this.setCanGetocupied(false);
		} else if (checkToDisablePush(l.getActiveStage())) {
			this.setTexture(PictureLoader.loadImageFromFile("disabled.png"));
			this.setCanGetocupied(false);
		}
	}
}
