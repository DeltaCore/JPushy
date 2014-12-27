
package net.ccmob.apps.jpushy.sp.level;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.ccmob.apps.jpushy.blocks.Block;
import net.ccmob.apps.jpushy.blocks.Blocks;
import net.ccmob.apps.jpushy.blocks.MoveableBlock;
import net.ccmob.apps.jpushy.items.Item;
import net.ccmob.apps.jpushy.items.Items;
import net.ccmob.apps.jpushy.utils.Coord2D;
import net.ccmob.apps.jpushy.sp.level.editor.EditorSaveThread.BlockAction;
import net.ccmob.xml.XMLConfig;
import net.ccmob.xml.XMLConfig.XMLNode;

/**
 * 
 * @author Marcel Benning
 * 
 */

public class LevelLoader {

	public static Level load(String fileName) {
		Level level = new Level(fileName);
		XMLConfig levelFile = new XMLConfig("Data/lvl/" + fileName);
		XMLNode rootNode = levelFile.getRootNode();
		if (rootNode.getName().equals("level")) {
			XMLNode levelNode = rootNode;
			if (levelNode.attributeExists("name")) {
				level.setName((String) levelNode.getAttribute("name").getAttributeValue());
			} else {
				level.setName("Unnamed level");
			}
			if (levelNode.attributeExists("comment")) {
				if (levelNode.getChild("comment").nodeExists("line")) {
					ArrayList<String> lines = new ArrayList<String>();
					for (XMLNode commentLine : levelNode.getChild("comment").getChilds()) {
						if (commentLine.attributeExists("value"))
							lines.add((String) commentLine.getAttribute("value").getAttributeValue());
					}
				}
			}
			for (XMLNode child : levelNode.getChilds()) {
				if (child.getName().equalsIgnoreCase("stage") && child.attributeExists("id")) {
					try {
						Stage stage = new Stage(Integer.valueOf((String) child.getAttribute("id").getAttributeValue()));
						XMLNode stageNode = child;
						if (stageNode.nodeExists("blocks")) {
							BlockList normalBlocks = new BlockList();
							BlockList moveableBlocks = new BlockList();
							Coord2D bounds = getStageBounds(child);
							normalBlocks.init(bounds.getX(), bounds.getY());
							moveableBlocks.init(bounds.getX(), bounds.getY());
							for (int y = 0; y < bounds.getY(); y++) {
								for (int x = 0; x < bounds.getX(); x++) {
									normalBlocks.setBlock(x, y, Blocks.getBlockById(Blocks.air.getId()));
								}
							}
							XMLNode blocksNode = stageNode.getChild("blocks");
							for (XMLNode block : blocksNode.getChilds()) {
								if (block.getName().equals("block") && block.attributeExists("id") && block.attributeExists("x") && block.attributeExists("y") && block.attributeExists("uid")) {
									Block b = Blocks.getBlockById(Integer.valueOf((String) block.getAttribute("id").getAttributeValue()));
									b.onLoaded(Integer.valueOf((String) block.getAttribute("x").getAttributeValue()), Integer.valueOf((String) block.getAttribute("y").getAttributeValue()),
									    Integer.valueOf((String) child.getAttribute("id").getAttributeValue()), stage);
									if (!(b instanceof MoveableBlock)) {
										normalBlocks.setBlock(Integer.valueOf((String) block.getAttribute("x").getAttributeValue()),
										    Integer.valueOf((String) block.getAttribute("y").getAttributeValue()), b);
									} else {
										moveableBlocks.setBlock(Integer.valueOf((String) block.getAttribute("x").getAttributeValue()),
										    Integer.valueOf((String) block.getAttribute("y").getAttributeValue()), b);
									}
								}
							}
							stage.setBlocks(normalBlocks.getBlocks());
							stage.setMoveableBlocks(moveableBlocks.getBlocks());
							if (stageNode.nodeExists("items")) {
								XMLNode itemssNode = stageNode.getChild("items");
								for (XMLNode item : itemssNode.getChilds()) {
									if (item.getName().equals("item") && item.attributeExists("id") && item.attributeExists("x") && item.attributeExists("y") && item.attributeExists("uid")) {
										Item i = Items.getItemById(Integer.valueOf((String) item.getAttribute("id").getAttributeValue()));
										if (normalBlocks.getBlocks()[Integer.valueOf((String) item.getAttribute("x").getAttributeValue())][Integer.valueOf((String) item.getAttribute("y")
										    .getAttributeValue())] != null) {
											normalBlocks.getBlocks()[Integer.valueOf((String) item.getAttribute("x").getAttributeValue())][Integer.valueOf((String) item.getAttribute("y")
											    .getAttributeValue())].setKeptItem(i);
										}
									}
								}
							}
						}
						level.getStages().add(stage);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			if (levelNode.nodeExists("actions")) {
				for (XMLNode actions : levelNode.getChild("actions").getChilds()) {
					if (actions.attributeExists("uid") && actions.attributeExists("bSourceX") && actions.attributeExists("bSourceY") && actions.attributeExists("bDestX")
					    && actions.attributeExists("bDestY") && actions.attributeExists("bSourceL") && actions.attributeExists("bDestL")) {
						BlockAction action = new BlockAction();
						action.id = Integer.valueOf((String) actions.getAttribute("uid").getAttributeValue());
						action.blockSourceX = Integer.valueOf((String) actions.getAttribute("bSourceX").getAttributeValue());
						action.blockSourceY = Integer.valueOf((String) actions.getAttribute("bSourceY").getAttributeValue());
						action.blockDestX = Integer.valueOf((String) actions.getAttribute("bDestX").getAttributeValue());
						action.blockDestY = Integer.valueOf((String) actions.getAttribute("bDestY").getAttributeValue());
						action.blockLayerSource = Integer.valueOf((String) actions.getAttribute("bSourceL").getAttributeValue());
						action.blockLayerDest = Integer.valueOf((String) actions.getAttribute("bDestL").getAttributeValue());
						if (level.getStages().size() >= action.blockLayerSource) {
							level.getStages().get(action.blockLayerSource).getBlock(action.blockSourceX, action.blockSourceY)
							    .onLevelLoad(action.blockSourceX, action.blockSourceY, action.blockLayerSource, action);
						}
					}
				}
			}
		} else {
			System.out.println("Found corrupt level file. Is it maybe an old one ? Trying to parse it with the old level parser ...");
			return loadLevelFromFile(fileName);
		}
		return level;
	}

	public static Coord2D getStageBounds(XMLNode stageNode) {
		int maxX = 0, maxY = 0;
		int cX = 0, cY = 0;
		if (stageNode.nodeExists("blocks")) {
			for (XMLNode block : stageNode.getChild("blocks").getChilds()) {
				if (block.getName().equals("block") && block.attributeExists("id") && block.attributeExists("x") && block.attributeExists("y") && block.attributeExists("uid")) {
					cX = Integer.valueOf((String) block.getAttribute("x").getAttributeValue());
					cY = Integer.valueOf((String) block.getAttribute("y").getAttributeValue());
					if (cX > maxX) {
						maxX = cX;
					}
					if (cY > maxY) {
						maxY = cY;
					}
				}
			}
			return new Coord2D(maxX + 1, maxY + 1);
		}
		return null;
	}

	@Deprecated
	public static Level loadLevelFromFile(String filename) {
		boolean debug = true; // Core.getSettings().getSettings(Core.getSettings().debug);
		System.out.println("Loading level : " + filename);
		Blocks.wakeUpDummy();
		String line;
		Level level = new Level(filename);
		level.setFileName(filename);
		ArrayList<String> lines = loadLevelFile(filename);
		ArrayList<String> cfgLines = loadLevelConfig(filename);
		String start_regex = "^<stage id=([0-9])>$";
		String end_regex = "^</stage>";
		String levelRegEx = "^<level name=\"([a-zA-Z\\s0-9[-][_]]{1,})\" version='([a-zA-Z0-9.,]{1,})'>$";
		String commentStart = "^<comment>";
		String commentEnd = "^</comment>";
		BlockList blocks = new BlockList();
		BlockList secondLayer = new BlockList();

		int currentStageId = 0;
		boolean flag = false;
		Stage currentStage = null;
		int xCounter = 0;
		int yCounter = 0;
		char charValue = '0';
		int blockId = 0;
		int itemForBlock = -1;

		boolean commentFlag = false;

		for (int linesY = 0; linesY < lines.size(); linesY++) {
			line = lines.get(linesY);
			if (line.matches(levelRegEx)) {
				Pattern pattern = Pattern.compile(levelRegEx);
				Matcher matcher = pattern.matcher(line);
				if (matcher.matches()) {
					String name = matcher.group(1);
					String version = matcher.group(2);
					if (debug)
						System.out.println("Level : " + line + String.format("%n") + "Name : " + name + " version : " + version);
					level.setName(name);
					level.setVersion(version);
				}
			} else if (line.matches(start_regex)) {
				flag = true;
				Pattern pattern = Pattern.compile(start_regex);
				Matcher matcher = pattern.matcher(line);
				if (matcher.matches()) {
					int id = Integer.parseInt(matcher.group(1));
					currentStageId = id;
				}
				blocks = new BlockList();
				int width = getStageWidth(lines, currentStageId);
				int height = getStageLength(lines, currentStageId);
				blocks.init(width, height);
				secondLayer.init(width, height);
				if (debug)
					System.out.println("Levelsize : x: " + width + " y:" + height);
				currentStage = new Stage(currentStageId);
				yCounter = 0;
			} else if (line.matches(end_regex)) {
				currentStage.setBlocks(blocks.getBlocks());
				currentStage.setMoveableBlocks(secondLayer.getBlocks());
				level.registerStage(currentStage);
				flag = false;
				yCounter = 0;
			} else if (line.matches(commentStart)) {
				commentFlag = true;
			} else if (line.matches(commentEnd)) {
				commentFlag = false;
			} else if (commentFlag) {
				level.addComment(line);
			} else if (flag) {
				for (xCounter = 0; xCounter < line.length(); xCounter++) {
					charValue = line.charAt(xCounter);
					blockId = charValue - 48;
					Block b = Blocks.getBlockById(blockId);
					b = b.onConfigLoaded(xCounter, yCounter, currentStageId, cfgLines, currentStage);
					b.onLoaded(xCounter, yCounter, currentStageId, currentStage);
					itemForBlock = getItemForBlock(currentStageId, xCounter, yCounter, filename);
					if (itemForBlock != -1) {
						b.setKeptItem(Items.getItemById(itemForBlock));
					}
					if (b instanceof MoveableBlock) {
						blocks.setBlock(xCounter, yCounter, Blocks.getBlockById(0));
						secondLayer.setBlock(xCounter, yCounter, b);
					} else {
						blocks.setBlock(xCounter, yCounter, b);
						secondLayer.setBlock(xCounter, yCounter, null);
					}
				}
				yCounter++;
			}
		}
		return level;
	}

	public static int getStageLength(ArrayList<String> lines, int stageId) {
		boolean debug = true;// Core.getSettings().getSettings(Core.getSettings().debug);
		int length = 0;
		String start_regex = "^<stage id=([0-9])>$";
		String end_regex = "^</stage>";
		boolean flag = false;
		int start = 0;
		int end = 0;
		boolean overflow = false;
		for (int i = 0; i < lines.size(); i++) {
			if (lines.get(i).matches(start_regex)) {
				Pattern pattern = Pattern.compile(start_regex);
				Matcher matcher = pattern.matcher(lines.get(i));
				if (matcher.matches()) {
					int id = Integer.parseInt(matcher.group(1));
					if (stageId == id) {
						flag = true;
						start = i + 1;
					}
				}
			} else if (lines.get(i).matches(end_regex) && overflow == false && flag) {
				overflow = true;
				flag = false;
				end = i - 1;
			} else if (flag) {
				length++;
				if (debug)
					System.out.print(".");
			}
		}
		if (debug)
			System.out.println();
		if (debug)
			System.out.println("Stage (" + stageId + ") start : " + start + " end : " + String.valueOf(end));
		return length;
	}

	public static int getStageWidth(ArrayList<String> lines, int stageId) {
		int width = 0;
		String start_regex = "^<stage id=([0-9])>$";
		String end_regex = "^</stage>";
		boolean flag = false;
		for (int i = 0; i < lines.size(); i++) {
			if (lines.get(i).matches(start_regex)) {
				Pattern pattern = Pattern.compile(start_regex);
				Matcher matcher = pattern.matcher(lines.get(i));
				if (matcher.matches()) {
					int id = Integer.parseInt(matcher.group(1));
					if (stageId == id) {
						flag = true;
					}
				}
			} else if (lines.get(i).matches(end_regex)) {
				flag = false;
			} else if (flag) {
				if (lines.get(i).length() >= width) {
					width = lines.get(i).length();
				}
			}
		}
		return width;
	}

	public static int getLevelStages(ArrayList<String> lines) {
		int layers = 0;
		String start_regex = "^<stage id=([0-9])>$";
		for (int i = 0; i < lines.size(); i++) {
			if (lines.get(i).matches(start_regex)) {
				Pattern pattern = Pattern.compile(start_regex);
				Matcher matcher = pattern.matcher(lines.get(i));
				if (matcher.matches()) {
					layers++;
				}
			}
		}
		return layers;
	}

	public static int[] checkCFGCords(ArrayList<String> cfg, int stageId, int x, int y) {
		String regex = "^([0-9]{1,}),([0-9]{1,}),([0-9]{1,})=([0-9]{1,}),([0-9]{1,})$";
		for (String line : cfg) {
			if (line.matches(regex)) {
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(line);
				if (matcher.matches()) {
					int id = Integer.parseInt(matcher.group(1));
					int x1 = Integer.parseInt(matcher.group(2));
					int y1 = Integer.parseInt(matcher.group(3));
					int x2 = Integer.parseInt(matcher.group(4));
					int y2 = Integer.parseInt(matcher.group(5));
					if (x1 == x && y1 == y && id == stageId) {
						int[] ret = { x2, y2 };
						return ret;
					}
				}
			}
		}
		return new int[] { 0, 0 };
	}

	public static ArrayList<String> loadLevelFile(String filename) {
		ArrayList<String> returnString = new ArrayList<String>();
		if (!filename.startsWith("Data/lvl/")) {
			filename = "Data/lvl/" + filename;
		}
		File f = new File(filename);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line;
			while ((line = reader.readLine()) != null) {
				returnString.add(line);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnString;
	}

	public static ArrayList<String> loadLevelConfig(String filename) {
		ArrayList<String> returnString = new ArrayList<String>();
		String modPath = filename.replace(".lvl", ".cfg");
		if(modPath.endsWith(".cfg.cfg")){
			modPath = modPath.substring(0,modPath.indexOf(".cfg") + 4);
		}
		if (!modPath.startsWith("Data/lvl/")) {
			modPath = "Data/lvl/" + modPath;
		}
		File f = new File(modPath);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line;
			while ((line = reader.readLine()) != null) {
				returnString.add(line);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnString;
	}

	public static int getItemForBlock(int stage, int x, int y, String filename) {
		ArrayList<String> content = loadLevelConfig(filename);
		String regex = "^item=([0-9]),([0-9]),([0-9])=([0-9]);";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher("");
		for (int i = 0; i < content.size(); i++) {
			String line = content.get(i);
			matcher = pattern.matcher(line);
			if (matcher.matches()) {
				int id = Integer.parseInt(matcher.group(1));
				int x1 = Integer.parseInt(matcher.group(2));
				int y1 = Integer.parseInt(matcher.group(3));
				int itemid = Integer.parseInt(matcher.group(4));
				if (x1 == x && y1 == y && stage == id) {
					// if (Core.getSettings().getSettings(Core.getSettings().debug))
					// System.out.println("ID : " + id + " X: " + x + "-" + x1 + " Y: " +
					// y + "-" + y1 + " - " + itemid);
					return itemid;
				}
			} else {
				// if (Core.getSettings().getSettings(Core.getSettings().debug))
				// System.out.println("No match for line: " + line);
			}
		}
		return -1;
	}
}