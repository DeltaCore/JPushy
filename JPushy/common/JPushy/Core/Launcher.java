package JPushy.Core;

import JPushy.Settings.Settings;

public class Launcher {
	/**
	 * 
	 * @author Marcel Benning
	 * 
	 */

	public static Settings	settings;

	public static void main(String[] args) {
		System.out.println("Loading Settings ...");
		settings = new Settings();
		System.out.println(Game.name + " V" + Game.version + " is starting ...");
		Core core = new Core(args, settings);
	}

}
