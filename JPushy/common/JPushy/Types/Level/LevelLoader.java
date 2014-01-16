package JPushy.Types.Level;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import JPushy.Types.Blocks.Block;
import JPushy.Types.Blocks.Blocks;
import JPushy.Types.Blocks.MoveableBlock;
import JPushy.Types.Items.Items;
import JPushy.Types.ProgammingRelated.BlockList;

/**
 * 
 * @author Marcel Benning
 * 
 */

public class LevelLoader {

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
		String levelRegEx = "^<level name=\"([a-zA-Z\\s0-9[-]]{1,})\" version='([a-zA-Z0-9.,]{1,})'>$";
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
					itemForBlock = getItemForBlock(currentStageId, xCounter, yCounter, filename);
					if (itemForBlock != -1) {
						b.setKeptItem(Items.getItemById(itemForBlock));
					}
					if (b instanceof MoveableBlock) {
						System.out.println("MOVEABLE BLOCK ! : " + b.toString());
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

	private static int getStageLength(ArrayList<String> lines, int stageId) {
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

	private static int getStageWidth(ArrayList<String> lines, int stageId) {
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

	private static ArrayList<String> loadLevelFile(String filename) {
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

	private static ArrayList<String> loadLevelConfig(String filename) {
		ArrayList<String> returnString = new ArrayList<String>();
		String modPath = filename.replace(".lvl", ".cfg");
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

	private static int getItemForBlock(int stage, int x, int y, String filename) {
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
