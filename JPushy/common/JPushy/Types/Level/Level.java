package JPushy.Types.Level;

import java.awt.Dimension;
import java.util.ArrayList;

import JPushy.Blocks;
import JPushy.Core;
import JPushy.Game;
import JPushy.PictureLoader;
import JPushy.Player;
import JPushy.Types.Coord2D;
import JPushy.Types.Blocks.Block;
/**
 * 
 * @author Marcel Benning
 * 
 */
public class Level {
	
	private int homeX = 1, homeY = 1;
	private int activeStage = 0;
	private ArrayList<Stage> stages = new ArrayList<Stage>();
	private String name = "", version = "";
	private ArrayList<String> comment = new ArrayList<String>();
	private String fileName = "";
	
	@Override
	public String toString() {
		String ret = "";
		for(int s = 0;s<stages.size();s++){
			ret += "[Stage : " + stages.get(s).getId() + "]";
			for (int i = 0; i < stages.get(s).getBlocks()[0].length; i++) {
				for (int j = 0; j < stages.get(s).getBlocks().length; j++) {
					ret += stages.get(s).getBlocks()[i][j].toString() + ";";
				}
				ret += String.format("%n");
			}
			ret += String.format("%n");
		}
		return ret;
	}
	
	public void update() {
		
	}
	
	public Dimension getSize(int lines, int margin, int fontsize) {
		int addMargin = lines * (fontsize + margin) + margin;
		int width = 0;
		int height = 0;
		for (int i = 0; i < stages.get(activeStage).getBlocks().length; i++) {
			if (stages.get(activeStage).getBlocks()[i].length * 40 >= width)
				width = stages.get(activeStage).getBlocks()[i].length * 40;
			height++;
		}
		width += 16;
		return new Dimension(width, height * 40 + 39 + addMargin);
	}
	
	/*public boolean notifyPlayerMove(int x, int y, int dir) { // über gibt die neue position und gibt ja/nein zurück ob es geht
		//Blöcke die n/e/s/w von neuen block sind
		Block bN = getBlock(x, y - 1);
		Block bS = getBlock(x, y + 1);
		Block bE = getBlock(x + 1, y);
		Block bW = getBlock(x - 1, y);
		Block block = getBlock(x, y);
		if (dir == 0) {
			if(block.isSolid()){
				return false;
			}else if(block.isPlayerAbleToWalkOn()){
				block.onWalk(x, y, this);
				block.onOccupied(true, this);
				bS.onOccupied(false, this);
				setBlock(x, y - 1, block);
				return true;
			}else if(!block.isSolid()){
				if(bN.isSolid()){
					return false;
				}
				block.onPush(x, y, x, y-1, 2, this);
				setBlock(x, y - 1, block);
				setBlock(x,y, Blocks.air);
				bN.setOccupiedByBlock(block);
				bN.setOcupied(true);
				bN.onOccupied(true, this);
				block.setOccupiedByBlock(Blocks.air);
				block.setOcupied(false);
				block.onOccupied(false, this);
				return true;
			}
		} else if (dir == 1) {
			if(block.isSolid()){
				return false;
			}else if(block.isPlayerAbleToWalkOn()){
				block.onWalk(x, y, this);
				block.onOccupied(true, this);
				bW.onOccupied(false, this);
				return true;
			}else if(!block.isSolid()){
				if(bW.isSolid()){
					return false;
				}
				block.onPush(x, y, x, y-1, 3, this);
				setBlock(x + 1, y, block);
				setBlock(x,y, Blocks.air);
				bW.setOccupiedByBlock(block);
				bW.setOcupied(true);
				bW.onOccupied(true, this);
				block.setOccupiedByBlock(Blocks.air);
				block.setOcupied(false);
				block.onOccupied(false, this);
				return true;
			}
		} else if (dir == 2) {
			if(block.isSolid()){
				return false;
			}else if(block.isPlayerAbleToWalkOn()){
				block.onWalk(x, y, this);
				block.onOccupied(true, this);
				bS.onOccupied(false, this);
				return true;
			}else if(!block.isSolid()){
				if(bS.isSolid()){
					return false;
				}
				block.onPush(x, y, x, y-1, 0, this);
				setBlock(x, y + 1, block);
				setBlock(x,y, Blocks.air);
				bS.setOccupiedByBlock(block);
				bS.setOcupied(true);
				bS.onOccupied(true, this);
				block.setOccupiedByBlock(Blocks.air);
				block.setOcupied(false);
				block.onOccupied(false, this);
				return true;
			}
		} else if (dir == 3) {
			if(block.isSolid()){
				return false;
			}else if(block.isPlayerAbleToWalkOn()){
				block.onWalk(x, y, this);
				block.onOccupied(true, this);
				bE.onOccupied(false, this);
				return true;
			}else if(!block.isSolid()){
				if(bE.isSolid()){
					return false;
				}
				block.onPush(x, y, x, y-1, 1, this);
				setBlock(x - 1, y, block);
				setBlock(x,y, Blocks.air);
				bE.setOccupiedByBlock(block);
				bE.setOcupied(true);
				bE.onOccupied(true, this);
				block.setOccupiedByBlock(Blocks.air);
				block.setOcupied(false);
				block.onOccupied(false, this);
				return true;
			}
		}
		return false;
	}*/

	public boolean moveBlock(int x, int y, int dir) {
		Block b = stages.get(activeStage).getBlocks()[y][x];
		//System.out.println(b.toString() + ":" + b.isOcupied());
		if (b.isSolid()) {
			return false;
		} else {
			Block t = new Block("Dummy", -1,
					PictureLoader.loadImageFromFile("base.png"), false, false,
					false, Blocks.air, false);
			if (dir == 0) {
				t = stages.get(activeStage).getBlocks()[y - 1][x];
			} else if (dir == 1) {
				t = stages.get(activeStage).getBlocks()[y][x + 1];
			} else if (dir == 2) {
				t = stages.get(activeStage).getBlocks()[y + 1][x];
			} else if (dir == 3) {
				t = stages.get(activeStage).getBlocks()[y][x - 1];
			}
			// ystem.out.println(t.toString());
			if (t.getId() == Blocks.air.getId()) {// Sides : 0 = north ; 1 =
													// east ; 2 = south ; 3 =
													// west
				if (dir == 0) {
					stages.get(activeStage).setBlock(x,y - 1,b);
					stages.get(activeStage).setBlock(x,y,Blocks.air);
					return true;
				} else if (dir == 1) {
					stages.get(activeStage).setBlock(x + 1,y,b);
					stages.get(activeStage).setBlock(x,y,Blocks.air);
					return true;
				} else if (dir == 2) {
					stages.get(activeStage).setBlock(x,y + 1,b);
					stages.get(activeStage).setBlock(x,y,Blocks.air);
					return true;
				} else if (dir == 3) {
					stages.get(activeStage).setBlock(x - 1,y,b);
					stages.get(activeStage).setBlock(x,y,Blocks.air);
					return true;
				} else {
					return false;
				}
			} else {
				if (t.getId() == -1) {
					return false;
				}
			}
		}
		return false;
	}

	public void registerStage(Stage stage){
		if(stage.getId() == 0){
			stages.add(stage);
		}else{
			if(!checkStageId(stage)){
				stages.add(stage);
				System.out.println("[Level] Loaded new stage with id : " + stage.getId());
			}
		}
	}

	private boolean checkStageId(Stage stage){
		for(int i = 0;i<stages.size();i++){
			if(stages.get(i).getId() == stage.getId()){
				return true;
			}
		}
		return false;
	}

	public int getActiveStageI() {
		return activeStage;
	}

	public void setActiveStage(int activeStage) {
		this.activeStage = activeStage;
	}
	
	public Stage getActiveStage(){
		return stages.get(activeStage);
	}
	
	public Coord2D getHomeFromStageId(int id){
		return new Coord2D(stages.get(activeStage).getHomeX(), stages.get(activeStage).getHomeY());
	}
	
	public void setHomeFromStageId(int id, int x, int y){
		for(int i = 0;i<stages.size();i++){
			if(stages.get(i).getId() == id){
				stages.get(i).setHomeX(x);
				stages.get(i).setHomeY(y);
			}
		}
	}
	
	public boolean isLastStage(){
		if((activeStage + 1) == stages.size()){
			return true;
		}else{
			return false;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public ArrayList<String> getComment() {
		return comment;
	}

	public void setComment(ArrayList<String> comment) {
		this.comment = comment;
	}
	
	public void addComment(String s){
		comment.add(s);
	}
	
	public void init(){
		for(String s: comment){
			Game.sendMessage(s);
		}
	}

	public String getFileName() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public ArrayList<Stage> getStages(){
		return stages;
	}
	
	public Stage getStage(int index){
		return stages.get(index);
	}
	
}
