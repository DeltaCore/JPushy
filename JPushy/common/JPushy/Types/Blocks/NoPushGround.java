package JPushy.Types.Blocks;

import JPushy.Types.Level.Level;
import JPushy.Types.Level.LV.LVData;
import JPushy.Types.gfx.Picture;

public class NoPushGround extends Block {

	public NoPushGround(String name, int id, Picture img) {
		super(name, id, img);
		this.setPlayerAbleToWalkOn(true);
		this.setSolid(true);
		this.setDestroyable(false);
		this.setVisible(true);
		this.setCanGetocupied(true);
	}

	@Override
	public void onWalk(int x, int y, Level l) {
		super.onWalk(x, y, l);
		if (!l.getActiveStage().getDataList().dataExists("PUSH_DISABLE")) {
			LVData data = new LVData("PUSH_DISABLE");
			data.setInt(1);
			l.getActiveStage().getDataList().addData(data);
			this.setCanGetocupied(false);
		}
	}

}
