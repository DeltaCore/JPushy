package JPushy.Types.Level;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import JPushy.Core.Core;
import JPushy.Types.Blocks.Block;
import JPushy.Types.Blocks.Blocks;
import JPushy.Types.Blocks.Button;
import JPushy.Types.Blocks.TeleportBase;
import JPushy.Types.Items.Items;
import JPushy.Types.ProgammingRelated.BlockList;
import JPushy.gfx.PictureLoader;

/**
 * 
 * @author Marcel Benning
 * 
 */

public class LevelLoader {

	public static Level loadLevelFromFile_old(String filename) {
		Blocks.wakeUpDummy();
		String line;
		Block[][] blocks = null;
		Level l = new Level();
		l.setFileName(filename);
		ArrayList<String> lines = loadLevelFile(filename);
		ArrayList<String> cfgLines = loadLevelConfig(filename);
		int x = 0, y = 0;

		String start_regex = "^<stage id=([0-9])>$";
		String end_regex = "^</stage>";
		String levelRegEx = "^^<level name=\"([a-zA-Z\\s]{1,})\" version='([a-zA-Z0-9.,]{1,})'>$";
		String commentStart = "^<comment>";
		String commentEnd = "^</comment>";

		Random r = new Random();

		int levelSizes[][] = new int[32][2];
		int stageCid = 0;
		boolean flag = false;
		int startY = 0;
		int bX = 0;
		boolean comment = false;
		String Comment = "";
		for (int i = 0; i < lines.size(); i++) {
			line = lines.get(i);
			if (comment) {
				if (line.matches(commentEnd)) {

				} else {
					l.addComment(line);
					System.out.println("[Comment] New comment line : " + line);
				}
			}
			if (line.matches(commentStart)) {
				comment = true;
			} else if (line.matches(commentEnd)) {
				comment = false;
			} else if (line.matches(levelRegEx)) {
				System.out.println("Yes !!!");
				Pattern pattern = Pattern.compile(levelRegEx);
				Matcher matcher = pattern.matcher(line);
				if (matcher.matches()) {
					String name = matcher.group(1);
					String version = matcher.group(2);
					System.out.println("Level : " + line + String.format("%n") + "Name : " + name + " version : " + version);
					l.setName(name);
					l.setVersion(version);
				}
			} else if (line.matches(start_regex)) {
				flag = true;
				Pattern pattern = Pattern.compile(start_regex);
				Matcher matcher = pattern.matcher(line);
				if (matcher.matches()) {
					int id = Integer.parseInt(matcher.group(1));
					stageCid = id;
				}
			} else if (line.matches(end_regex)) {
				flag = false;
				levelSizes[stageCid][0] = bX;
				levelSizes[stageCid][1] = startY;
				startY = 0;
				bX = 0;
			}
			bX = 0;
			int le = line.length();
			bX = le;
			y++;
			startY++;
		}
		blocks = new Block[1][1];
		System.out.println("Level data : ");
		// System.out.println("Lines : ");
		// for(int i = 0;i<lines.size();i++) System.out.println(lines.get(i));
		// System.out.println();
		// http://rubular.com/r/ypQuk06b0u
		flag = false;
		int stageId = -1;
		Stage stage = null;
		int yCounter = 0;
		Block b;
		for (int i = 0; i < lines.size(); i++) {
			line = lines.get(i);
			if (line.matches(start_regex)) {
				flag = true;
				Pattern pattern = Pattern.compile(start_regex);
				Matcher matcher = pattern.matcher(line);
				if (matcher.matches()) {
					int id = Integer.parseInt(matcher.group(1));
					stageId = id;
				}
				blocks = new Block[levelSizes[stageId][1]][levelSizes[stageId][0]];
				System.out.println("Levelsize : x: " + levelSizes[stageId][0] + " y:" + levelSizes[stageId][1]);
				stage = new Stage(stageId);
				yCounter = -1;
			} else if (line.matches(end_regex)) {
				stage.setBlocks(blocks);
				l.registerStage(stage);
				flag = false;
				yCounter = -1;
			} else if (flag) {
				int le = line.length();
				// System.out.println("Line : " + line + " length : " + le);
				for (int j = 0; j < le; j++) {
					char c = line.charAt(j);
					try {
						int val = c - 48;
						b = Blocks.getBlockById(0);
						System.out.println("Block id : " + val);
						if (val == Blocks.TeleportBase.getId()) {
							TeleportBase base = (TeleportBase) new TeleportBase("Teleporter", Blocks.TeleportBase.getId(), PictureLoader.loadImageFromFile("teleportbase.png")).setDestroyable(false).setSolid(true).setPlayerAbleToWalkOn(true);
							int[] cfgCords = checkCFGCords(cfgLines, stageId, j, yCounter);
							if (cfgCords[0] == 0 && cfgCords[1] == 0) {
							} else {
								base.setEndX(cfgCords[0]);
								base.setEndY(cfgCords[1]);
								b = base;
							}
						} else if (val == Blocks.button.getId()) {
							Button base = (Button) new Button("Button", Blocks.button.getId(), PictureLoader.loadImageFromFile("button.png"), false);
							int[] cfgCords = checkCFGCords(cfgLines, stageId, j, yCounter);
							if (cfgCords[0] == 0 && cfgCords[1] == 0) {
							} else {
								base.setX(cfgCords[0]);
								base.setY(cfgCords[1]);
								b = base;
							}
						} else if (val == Blocks.home.getId()) {
							b = Blocks.home;
							stage.setHomeY(yCounter);
							stage.setHomeX(j);
							System.out.println("Home coords for stage:" + stage.getId() + " x:" + i + " y:" + yCounter);
						} else {
							b = Blocks.getBlockById(val);
						}
						int itemForBlock = getItemForBlock(stageId, j, yCounter, filename);
						if (itemForBlock != -1) {
							b.setKeptItem(Items.getItemById(itemForBlock));
						}
						blocks[yCounter][j] = b;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				System.out.println();
				yCounter++;
			}
		}
		System.out.println("Level data end.");
		return l;
	}

	public static Level loadLevelFromFile(String filename) {
		boolean debug = Core.getSettings().getSettings(Core.getSettings().debug);
		Blocks.wakeUpDummy();
		String line;
		Level level = new Level();
		level.setFileName(filename);
		ArrayList<String> lines = loadLevelFile(filename);
		ArrayList<String> cfgLines = loadLevelConfig(filename);
		String start_regex = "^<stage id=([0-9])>$";
		String end_regex = "^</stage>";
		String levelRegEx = "^^<level name=\"([a-zA-Z\\s]{1,})\" version='([a-zA-Z0-9.,]{1,})'>$";
		String commentStart = "^<comment>";
		String commentEnd = "^</comment>";
		BlockList blocks = new BlockList();

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
				if (debug)
					System.out.println("Levelsize : x: " + width + " y:" + height);
				currentStage = new Stage(currentStageId);
				yCounter = 0;
			} else if (line.matches(end_regex)) {
				currentStage.setBlocks(blocks.getBlocks());
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
					blocks.setBlock(xCounter, yCounter, b);
				}
				yCounter++;
			}
		}
		return level;
	}

	private static int getStageLength(ArrayList<String> lines, int stageId) {
		boolean debug = Core.getSettings().getSettings(Core.getSettings().debug);
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
					if (Core.getSettings().getSettings(Core.getSettings().debug))
						System.out.println("ID : " + id + " X: " + x + "-" + x1 + " Y: " + y + "-" + y1 + " - " + itemid);
					return itemid;
				}
			} else {
				if (Core.getSettings().getSettings(Core.getSettings().debug))
					System.out.println("No match for line: " + line);
			}
		}
		return -1;
	}
}
