package JPushy.Gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

import javax.swing.JPanel;

import JPushy.Blocks;
import JPushy.Game;
import JPushy.GraphicUtils;
import JPushy.Items;
import JPushy.LevelScheduler;
import JPushy.PictureLoader;
import JPushy.Player;
import JPushy.Types.Blocks.Block;
/**
 * 
 * @author Marcel Benning
 * 
 */
public class GamePanel extends JPanel {
	
	LevelScheduler level;
	Block[][] blocks = null;
	Image t;
	int maxLines = 10;
	int margin = 10;
	MainFrame frame;
	boolean sizeSet = false;
	int lastId = 0;
	Font font = new Font("Arial", Font.PLAIN, 18);
	public ArrayList<String> consoleLines = new ArrayList<String>();
	
	public GamePanel(LevelScheduler l, MainFrame frame, int showLines) {
		this.frame = frame;
		this.level = l;
		blocks = level.getLevel().getActiveStage().getBlocks();
		this.maxLines = showLines;
		int x = 0;
		int y = 0;
		boolean flag1 = false;
		boolean flag2 = false;
		for (int i = 0; i < l.getLevel().getActiveStage().getBlocks().length; i++) {
			if (!flag2) {
				for (int j = 0; j < l.getLevel().getActiveStage().getBlocks()[0].length; j++) {
					if (!flag2) {
						Block b = new Block("Dummy", -1,
								PictureLoader.loadImageFromFile("base.png"));
						try {
							b = l.getLevel().getActiveStage().getBlock(j, i);
							System.out.print(b.toString() + " - ");
							break;
						} catch (Exception e) {
							flag2 = true;
							x = j;
						}
					}
				}
			}
			if (!flag1) {
				Block b = new Block("Dummy", -1,
						PictureLoader.loadImageFromFile("base.png"));
				try {
					b = l.getLevel().getActiveStage().getBlock(0, i);
					System.out.print(b.toString() + " - ");
					break;
				} catch (Exception e) {
					flag1 = true;
					y = i - 1;
				}
			}
		}
		System.out.println("Level height : " + y + " Level width : " + x);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(lastId != level.getLevel().getActiveStageI()){
			sizeSet = false;
			lastId = level.getLevel().getActiveStageI();
		}else{
			
		}
		level.getLevel().update();
		Block b;
		int x = margin, y = 0;
		int startAt = consoleLines.size() - maxLines;
		String tmp;
		Rectangle2D rect;
		g.setColor(Color.black);
		g.setFont(font);
		int lastText = (int) this.getBounds().getHeight() - margin;
		int max = consoleLines.size();
		if (consoleLines.size() < maxLines) {
			max = maxLines;
		}
		for (int i = max; i >= 0; i--) {
			if (i > startAt) {
				if (i < consoleLines.size()) {
					tmp = consoleLines.get(i);
					rect = g.getFontMetrics().getStringBounds(tmp, g);
					g.drawString(tmp, x, lastText);
					lastText -= (rect.getBounds().height + margin);
				}
			}
		}
		int sizeX = 0, sizeY = 0;
		try {
			for (y = 0; y < level.getLevel().getActiveStage().getBlocks().length; y++) {
				for (x = 0; x < level.getLevel().getActiveStage().getBlocks()[0].length; x++) {
					try {
						b = level.getLevel().getActiveStage().getBlock(x, y);
						try {
							if (b.isVisible()) {
								if (b.isOcupied()) {
									t = GraphicUtils.getImageFromPicture(b
											.getOccupiedByBlock().getTexture());
									g.drawImage(t, ((x * 40)), ((y * 40)), null);
								} else {
									t = GraphicUtils.getImageFromPicture(b
											.getTexture());
									g.drawImage(t, ((x * 40)), ((y * 40)), null);
									if(b.getKeptItem() != null){
										if(!(b.getKeptItem() == Items.noitem))
											g.drawImage(GraphicUtils.getImageFromPicture(b.getKeptItem().getTexture()), ((x * 40)), ((y * 40)), null);
									}
								}
								sizeX = x;
							} else {
								t = GraphicUtils.getImageFromPicture(b
										.getInvincebleBlock().getTexture());
								g.drawImage(t, ((x * 40)), ((y * 40)), null);
							}
						} catch (Exception e) {

						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				sizeY = y;
			}
			
		} catch (Exception e) {
			
		}
		if(!sizeSet){
			frame.setSize(sizeX*40 + 40 + 16, (sizeY*40) + (10 * (18+10)) + 10);
			sizeSet = true;
		}
		// Draw own player
		Player p = Game.getPlayer();
		g.drawImage(GraphicUtils.getImageFromPicture(p.getTexture()),
				p.getX() * 40, p.getY() * 40, null);
		
		//Draw other player
		try{
			ArrayList<Player> players = level.getMultiPlayerServer().cmdHandler.getPlayers();
			for(Player pl : players){
				g.drawImage(GraphicUtils.getImageFromPicture(pl.getTexture()),
					pl.getX() * 40, pl.getY() * 40, null);
			}
		}catch(Exception e){
			System.out.println("Failed drawin other player");
		}
	}
	
}
