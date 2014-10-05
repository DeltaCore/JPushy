package net.ccmob.apps.jpushy.core.natives;

import javax.swing.UIManager;

import net.ccmob.apps.jpushy.core.Game;
import net.ccmob.apps.jpushy.core.JPushy;

public class MacOSX extends NativeHandler {

	public MacOSX(String osName) {
		super(osName);
	}

	public void setupSystemProperties() {
		System.out.println("Setting props");
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

	@Override
	public void setTitle(String name) {
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", Game.name + " V" + Game.version + " - " + name);
	}

}
