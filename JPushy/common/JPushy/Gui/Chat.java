package JPushy.Gui;

/**
 * 
 * @author Marcel Benning
 * 
 */
public class Chat {
	
	GamePanel gamePanel;
	
	public Chat(GamePanel panel) {
		this.gamePanel = panel;
	}

	public void sendMessage(String msg){
		gamePanel.consoleLines.add(msg);
	}
	
}
