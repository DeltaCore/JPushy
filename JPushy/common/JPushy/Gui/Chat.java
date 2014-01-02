package JPushy.Gui;

/**
 * 
 * @author Marcel Benning
 * 
 */
public class Chat {

	GamePanel	              gamePanel;

	public static final int	MAX_LINES	= 10;

	public Chat(GamePanel panel) {
		this.gamePanel = panel;
	}

	public void sendMessage(String msg) {
		gamePanel.getConsoleLines().add(msg);
	}

}
