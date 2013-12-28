package JPushy.Core.Natives;

import JPushy.JPushy;

public abstract class NativeHandler {

	private String	osName	= "";

	public NativeHandler(String osName) {
		this.setOsName(osName);
	}

	public abstract void setupSystemProperties();

	public abstract void postInit(JPushy game);

	public String getOsName() {
		return osName;
	}

	public void setOsName(String osName) {
		this.osName = osName;
	}

}
