package net.ccmob.apps.jpushy.settings;

import java.io.File;
import java.util.ArrayList;
/**
 * 
 * @author Marcel Benning
 * 
 */
public class Settings {
 
	public Setting				defaultUpdateServer	= new Setting("defaultUpdateServer", "http://devgensoft.de");
	public Setting				defaultLevelServer	= new Setting("defaultLevelServer", "http://devgensoft.de");
	public Setting				debug				= new Setting("debug", "false");

	/*
	 * 
	 */

	private SettingsReader		settingsReader;
	private Setting				tempSetting;

	public ArrayList<Setting>	defaultSettings		= new ArrayList<Setting>();

	public Settings() {
		initSettings();
		File sFolder = new File("Data/cfg");
		if (!sFolder.exists())
			sFolder.mkdir();
		this.settingsReader = new SettingsReader("Data/cfg/settings.xml", this);
	}

	public void initSettings() {
		addDefaultSetting(defaultUpdateServer);
		addDefaultSetting(defaultLevelServer);
		addDefaultSetting(debug);
	}

	public void addDefaultSetting(Setting s) {
		defaultSettings.add(s);
	}

	public void addDefaultSetting(String name, String value) {
		tempSetting = new Setting(name, value);
		addDefaultSetting(tempSetting);
	}

	public ArrayList<Setting> getDefaultSettings() {
		return defaultSettings;
	}

	private SettingsReader getSettingsReader() {
		return this.settingsReader;
	}

	public String getSetting(Setting s) {
		return this.getSettingsReader().getSetting(s);
	}

	public boolean getSettings(Setting s) {
		return this.getSettingsReader().getBoolean(s);
	}

	public void setSetting(Setting s, String value) {
		this.getSettingsReader().setSetting(s, value);
	}

	public void setSetting(Setting s, boolean value) {
		setSetting(s, String.valueOf(value));
	}

	public void setSetting(Setting s, int value) {
		setSetting(s, String.valueOf(value));
	}
	
	public void setSetting(Setting s, char value) {
		setSetting(s, String.valueOf(value));
	}
	
	public void save(){
		this.getSettingsReader().save();
	}
	
}
