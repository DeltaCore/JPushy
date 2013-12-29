package JPushy.Gui;

import java.awt.Color;

import javax.swing.JFrame;

import JPushy.Core.Game;
import JPushy.Core.LevelScheduler;
import JPushy.Listener.Input;
import JPushy.Types.Player.Player;

/**
 * 
 * @author Marcel Benning
 * 
 */
public class MainFrame extends JFrame {

	LevelScheduler	levelHandler;
	GamePanel	     panel;
	Chat	         chat;
	Game	         game;

	public MainFrame(Game game, LevelScheduler l, Player p) {
		super("Level 1");
		this.setGame(game);
		this.levelHandler = l;
		setBackground(Color.BLACK);
		setBounds(100, 100, 600, 600);
		panel = new GamePanel(levelHandler, this, 10);
		chat = new Chat(panel);
		setContentPane(panel);
		addKeyListener(new Input(panel, game.getMpClient()));
		setBackground(Color.gray);
		this.setVisible(true);
	}

	public void update() {
		panel.repaint();
		panel.sizeSet = false;
	}

	public Chat getChat() {
		return chat;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

}
