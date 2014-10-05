package net.ccmob.apps.jpushy.mp.remote;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author Marcel Benning
 * 
 */
public class LevelHandler {

	private String	path;
	private String	p	         = "";
	private String	levelRegEx	= "^^<level name=\"([a-zA-Z\\s[-]0-9]{1,})\" version='([a-zA-Z0-9.,\\s]{1,})'>$";

	public LevelHandler(String path) {
		this.setPath(path);
	}

	public LevelData[] getLevelData() {
		LevelData[] data = new LevelData[0];
		File dataFolder = new File(path);
		int i = 0;
		for (File f : dataFolder.listFiles()) {
			if (!f.isDirectory()) {
				String tmp = f.getName();
				if (tmp.endsWith(".lvl")) {
					i++;
				}
			}
		}
		data = new LevelData[i];
		i = 0;
		for (File f : dataFolder.listFiles()) {
			if (!f.isDirectory()) {
				String tmp = f.getName();
				if (tmp.endsWith(".lvl")) {
					data[i] = readLevelData(tmp);
					i++;
				}
			}
		}
		return data;
	}

	public String buildLevelDataString() {
		LevelData[] data = getLevelData();
		String send = "";
		for (int i = 0; i < data.length; i++) {
			send += "#<name=\"" + data[i].getName() + "\";version='" + data[i].getVersion() + "'>";
		}
		return send;
	}

	public LevelData readLevelData(String lvlName) {
		p = path + "/" + lvlName;
		// System.out.println("Path : " + p);
		File f = new File(p);
		LevelData data = new LevelData("", "");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.matches(levelRegEx)) {
					Pattern pattern = Pattern.compile(levelRegEx);
					Matcher matcher = pattern.matcher(line);
					if (matcher.matches()) {
						String name = matcher.group(1);
						String version = matcher.group(2);
						// System.out.println("Level : " + line + String.format("%n") +
						// "Name : " + name + " version : " + version);
						data.setName(name);
						data.setVersion(version);
					}
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
