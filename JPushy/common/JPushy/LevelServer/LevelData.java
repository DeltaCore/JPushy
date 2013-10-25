package JPushy.LevelServer;
/**
 * 
 * @author Marcel Benning
 * 
 */
public class LevelData {

	private String name;
	private String version;
		
	public LevelData(String name, String version) {
		this.setName(name);
		this.setVersion(version);
	}
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
