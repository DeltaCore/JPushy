package JPushy.Core;

import javax.swing.SwingUtilities;

import JPushy.Core.Natives.MacOSX;
import JPushy.Core.Natives.NativeHandler;
import JPushy.LevelEditor.LevelEditorThread;
import JPushy.Settings.Settings;

public class Launcher {

	public static final NativeHandler[]	nativeHandler	= new NativeHandler[] { new MacOSX("OS X") };
	public static Settings	            settings;
	private static Game	                launcher;

	public static void main(String[] args) {
		String osName = System.getProperty("os.name");
		for (int i = 0; i < nativeHandler.length; i++) {
			if (osName.contains(nativeHandler[i].getOsName())) {
				System.out.println(nativeHandler[i].getOsName() + " deteced. Running Native handler for this system.");
				nativeHandler[i].setupSystemProperties();
			}
		}
		System.out.println("Loading Settings ...");
		settings = new Settings();
		System.out.println(Game.name + " V" + Game.version + " is starting ...");
		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("-ledit")) {
				new LevelEditorThread();
			}
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					try {
						launcher = new Game();
						launcher.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
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
