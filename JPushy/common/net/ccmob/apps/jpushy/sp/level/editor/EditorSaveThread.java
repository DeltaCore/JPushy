
package net.ccmob.apps.jpushy.sp.level.editor;

import java.util.ArrayList;

import net.ccmob.apps.jpushy.items.Items;
import net.ccmob.xml.XMLConfig;
import net.ccmob.xml.XMLConfig.XMLNode;

public class EditorSaveThread implements Runnable {

	private EditorPanel	gui;
	private String	    name;
	private String	    version;
	public boolean	    isRunning	= false;

	public EditorSaveThread(EditorPanel panel, String name, String version) {
		this.setGui(panel);
		this.name = name;
		this.version = version;
	}

	@Override
	public void run() {
		writeXMLLevel();
		this.getGui().getGui().getGame().updateLevels();
	}

	private void writeXMLLevel() {
		XMLNode levelNode = new XMLNode("level");
		levelNode.addAttribute("name", name);
		levelNode.addAttribute("version", version);
		int blockUID = 0;
		int itemUID = 0;
		int actionUID = 0;
		ArrayList<BlockAction> blockActions = new ArrayList<BlockAction>();
		for (int l = 0; l < this.getGui().getLayer(); l++) {
			XMLNode stage = new XMLNode("stage");
			stage.addAttribute("id", l + "");
			XMLNode blocks = new XMLNode("blocks");
			for (int y = 0; y < this.getGui().getLevelHeight(); y++) {
				for (int x = 0; x < this.getGui().getLevelWidth(); x++) {
					if (this.getGui().getBlockLayers().get(l).getOption(x, y).getX() != -1 && this.getGui().getBlockLayers().get(l).getOption(x, y).getY() != -1) {
						BlockAction action = new BlockAction();
						action.blockSourceX = x;
						action.blockSourceY = y;
						action.blockDestX = this.getGui().getBlockLayers().get(l).getOption(x, y).getX();
						action.blockDestY = this.getGui().getBlockLayers().get(l).getOption(x, y).getY();
						action.blockLayerSource = l;
						action.blockLayerDest = l;
						action.id = actionUID;
						blockActions.add(action);
						actionUID++;
					}
				}
			}

			for (int y = 0; y < this.getGui().getLevelHeight(); y++) {
				for (int x = 0; x < this.getGui().getLevelWidth(); x++) {
					XMLNode block = new XMLNode("block");
					block.addAttribute("uid", blockUID + "");
					block.addAttribute("id", this.getGui().getBlockLayers().get(l).getBlock(x, y).getId() + "");
					block.addAttribute("x", x + "");
					block.addAttribute("y", y + "");
					for (BlockAction action : blockActions) {
						if (action.blockSourceX == x && action.blockSourceY == y) {
							block.addAttribute("actionUID", action.id + "");
						}
					}
					blocks.addChild(block);
					blockUID++;
				}
			}
			stage.addChild(blocks);
			XMLNode items = new XMLNode("items");
			boolean addItems = false;
			int c;
			for (int y = 0; y < this.getGui().getLevelHeight(); y++) {
				for (int x = 0; x < this.getGui().getLevelWidth(); x++) {
					c = this.getGui().getItemLayers().get(l).get(x, y).getId();
					if (c != Items.noitem.getId()) {
						XMLNode item = new XMLNode("item");
						item.addAttribute("x", x + "");
						item.addAttribute("y", y + "");
						item.addAttribute("uid", itemUID + "");
						items.addChild(item);
						itemUID++;
						addItems = true;
					}
				}
			}
			if(addItems){
				stage.addChild(items);
			}
			levelNode.addChild(stage);
		}
		if(blockActions.size() > 0){
			XMLNode actions = new XMLNode("actions");
			for(BlockAction action : blockActions){
				XMLNode actionNode = new XMLNode("action");
				actionNode.addAttribute("uid", action.id + "");
				actionNode.addAttribute("bSourceX", action.blockSourceX + "");
				actionNode.addAttribute("bSourceY", action.blockSourceY + "");
				actionNode.addAttribute("bDestX", action.blockDestX + "");
				actionNode.addAttribute("bDestY", action.blockDestY + "");
				actionNode.addAttribute("bSourceL", action.blockLayerSource + "");
				actionNode.addAttribute("bDestL", action.blockLayerDest + "");
				actions.addChild(actionNode);
			}
			levelNode.addChild(actions);
		}
		XMLConfig config = new XMLConfig("Data/lvl/" + name + ".xml");
		config.setRootNode(levelNode);
		config.save("Data/lvl/" + name + ".xml");
	}

	public EditorPanel getGui() {
		return gui;
	}

	public void setGui(EditorPanel gui) {
		this.gui = gui;
	}

	public static class BlockAction {

		// NOTE ! THIS IS TEMPORARILY !

		public int	blockSourceX		 = 0;
		public int	blockSourceY		 = 0;

		public int	blockDestX		   = 0;
		public int	blockDestY		   = 0;

		public int	blockLayerSource	= 0;
		public int	blockLayerDest		= 0;

		public int	id		           = 0;

	}

}
