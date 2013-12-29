package JPushy.Core;

import JPushy.Core.Natives.MacOSX;
import JPushy.Core.Natives.NativeHandler;
import JPushy.Settings.Settings;

public class JPushy {
	/**
	 * 
	 * @author Marcel Benning
	 * 
	 */

	public static Settings	            settings;

	public static final NativeHandler[]	nativeHandler	= new NativeHandler[] { new MacOSX("OS X") };

	Core	                              core	        = null;

	public JPushy(String[] args) {
		String osName = System.getProperty("os.name");
		for (int i = 0; i < nativeHandler.length; i++) {
			if (osName.contains(nativeHandler[i].getOsName())) {
				System.out.println(nativeHandler[i].getOsName() + " deteced. Running postInit.");
				nativeHandler[i].postInit(this);
			}
		}
		System.out.println("Loading Settings ...");
		settings = new Settings();
		System.out.println(Game.name + " V" + Game.version + " is starting ...");
		Core core = new Core(args, settings);
	}

	public static void main(String[] args) {
		String osName = System.getProperty("os.name");
		for (int i = 0; i < nativeHandler.length; i++) {
			if (osName.contains(nativeHandler[i].getOsName())) {
				System.out.println(nativeHandler[i].getOsName() + " deteced. Running Native handler for this system.");
				nativeHandler[i].setupSystemProperties();
			}
		}
		new JPushy(args);
	}

}
