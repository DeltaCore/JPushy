package net.ccmob.apps.jpushy.core;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import net.ccmob.apps.jpushy.blocks.Blocks;
import net.ccmob.apps.jpushy.core.natives.MacOSX;
import net.ccmob.apps.jpushy.core.natives.NativeHandler;
import net.ccmob.apps.jpushy.modloader.ModLoader;
import net.ccmob.apps.jpushy.settings.Settings;

public class JPushy {
	/**
	 * 
	 * @author Marcel Benning
	 * 
	 */

	public static void main(String[] args) {
	  main();
  }
	
	public static final NativeHandler[]	nativeHandler	= new NativeHandler[] { new MacOSX("OS X") };
	public static Settings	            settings;
	private static Game	                launcher;

	public static void main() {
		String osName = System.getProperty("os.name");
		for (int i = 0; i < nativeHandler.length; i++) {
			if (osName.contains(nativeHandler[i].getOsName())) {
				System.out.println(nativeHandler[i].getOsName() + " deteced. Running Native handler for this system.");
				nativeHandler[i].setupSystemProperties();
			}
		}
		System.out.println(Game.name + " V" + Game.version + " is starting ...");
		System.out.println("Loading Settings ...");
		settings = new Settings();
		System.out.println("[ModLoader] Loading Mods ...");
		Blocks.wakeUpDummy();
		ModLoader.loadMods();
		System.out.println("[ModLoader] Preloading mods...");
		ModLoader.onPreInit();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getLookAndFeel());
					launcher = new Game();
					launcher.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void setWindowTitle(String name) {
		String osName = System.getProperty("os.name");
		for (int i = 0; i < nativeHandler.length; i++) {
			if (osName.contains(nativeHandler[i].getOsName())) {
				System.out.println(nativeHandler[i].getOsName() + " deteced. Running Native handler for this system.");
				nativeHandler[i].setTitle(name);
			}
		}
	}

	public static Game getInstance() {
		return launcher;
	}

	
}
