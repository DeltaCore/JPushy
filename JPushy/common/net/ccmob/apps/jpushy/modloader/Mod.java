
package net.ccmob.apps.jpushy.modloader;

public abstract class Mod {

	private int		ModID	= 0;
	private String	modName	= "";

	public Mod(int modID, String name) {
	this.setModID(modID);
	this.setModName(name);
	ModLoader.registerMod(this);
	}

	/**
	 * @return the modID
	 */
	public int getModID() {
	return this.ModID;
	}

	/**
	 * @param modID
	 *          the modID to set
	 */
	public void setModID(int modID) {
	this.ModID = modID;
	}

	/**
	 * @return the modName
	 */
	public String getModName() {
	return this.modName;
	}

	/**
	 * @param modName
	 *          the modName to set
	 */
	public void setModName(String modName) {
	this.modName = modName;
	}

	public abstract void onPreInit(); // loading resources

	public abstract void onInit(); // applying game changes

	public abstract void onPostInit(); // cross mod api and stuff

}
