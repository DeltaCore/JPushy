package JPushy.LevelServer;
/**
 * 
 * @author Marcel Benning
 * 
 */
public class ServerCommand {

	private String name;
	
	public ServerCommand(String cmd) {
		this.setName("--" + cmd.toLowerCase());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
