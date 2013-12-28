package JPushy.Core.Natives;

import javax.swing.UIManager;

import JPushy.JPushy;
import JPushy.Core.Game;

public class MacOSX extends NativeHandler {

	private JPushy	game	= null;

	public MacOSX(String osName) {
		super(osName);
	}

	public void setupSystemProperties() {
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", Game.name + " V" + Game.version);
		System.setProperty("apple.awt.brushMetalLook", "true");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
	}

	@Override
	public void postInit(JPushy game) {

	}

}
