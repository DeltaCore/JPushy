package JPushy.LevelServer;

import JPushy.MultiPlayer.ICommandHandler;

public class LevelServer {

	LevelServerCmdHandler cmdHandler;
	LevelHandler levelHandler;
	LevelConnectionHandler levelConnectionHandler;
	private int port = 11942;
	Thread server;
	
	public LevelServer() {
		cmdHandler = new LevelServerCmdHandler(this);
		levelHandler = new LevelHandler("Data/server/levels");
		LevelData[] data = levelHandler.getLevelData();
		levelConnectionHandler = new LevelConnectionHandler(port, this);
		for (int i = 0; i < data.length; i++) {
			System.out
					.println(data[i].getName() + " - " + data[i].getVersion());
		}
		server = new Thread(levelConnectionHandler, "JPushy - Dedicated LevelServer");
		server.start();
		while(levelConnectionHandler.isRunning());
	}

	public ICommandHandler getCMDHandler() {
		return cmdHandler;
	}

	public void setCMDHandler(LevelServerCmdHandler cMDHandler) {
		cmdHandler = cMDHandler;
	}

	public LevelHandler getLevelHandler() {
		return levelHandler;
	}

	public void setLevelHandler(LevelHandler levelHandler) {
		this.levelHandler = levelHandler;
	}

	public LevelConnectionHandler getLevelConnectionHandler() {
		return levelConnectionHandler;
	}

	public void setLevelConnectionHandler(
			LevelConnectionHandler levelConnectionHandler) {
		this.levelConnectionHandler = levelConnectionHandler;
	}

}
