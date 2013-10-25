package JPushy.Settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
/**
 * 
 * @author Marcel Benning
 * 
 */
public class SettingsReader {

	String				sFileName	= "";
	FileInputStream		fInput;
	FileOutputStream	fOutput;
	File				sFile;
	Properties			properties;
	Settings			settings;

	public SettingsReader(String settingsFile, Settings settings) {
		this.sFileName = settingsFile;
		this.settings = settings;
		sFile = new File(settingsFile);
		properties = new Properties();
		if (!sFile.exists()) {
			try {
				setupSettings();
				fInput = new FileInputStream(sFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				fInput = new FileInputStream(sFile);
				initSettings();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public String get(String node, String def) {
		String ret = properties.getProperty(node);
		if (ret == null) {
			set(node, def);
			ret = properties.getProperty(node);
		}
		return ret;
	}

	public String getSetting(Setting s) {
		return get(s.getName(), s.getValue());
	}

	public void initSettings() throws IOException {
		properties.loadFromXML(fInput);
	}

	public void setupSettings() throws IOException {
		fOutput = new FileOutputStream(sFile);
		Setting s;
		for (int i = 0; i < settings.getDefaultSettings().size(); i++) {
			s = settings.getDefaultSettings().get(i);
			properties.setProperty(s.getName(), s.getValue());
		}
		properties.storeToXML(fOutput, "JPushy settings file");
	}
	
	public void set(String node, String value) {
		properties.setProperty(node, value);
	}

	public boolean getBoolean(String node) {
		String arg = get(node, "false");
		if (arg.matches("^[tT][rR][uU][eE]")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean getBoolean(Setting s) {
		String arg = get(s.getName(), s.getValue());
		return arg.matches("^[tT][rR][uU][eE]");
	}

	public void setSetting(Setting s, String value){
		set(s.getName(), value);
	}
	
	public void save() {
		System.out.println("Saving properties...");
		try {
			fOutput = new FileOutputStream(sFile);
			properties.storeToXML(fOutput, "JPushy settings file");
		} catch (Exception e) {
			System.out.println("S*** ! look :");
			e.printStackTrace();
		}
		System.out.println("Done.");
	}

}
