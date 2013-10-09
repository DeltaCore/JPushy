package JPushy.Gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import JPushy.Game;
import JPushy.GraphicUtils;
import JPushy.Items;
import JPushy.LevelScheduler;
import JPushy.PictureLoader;
import JPushy.Player;
import JPushy.Types.Blocks.Block;
import JPushy.Types.Player.Inventory;

/**
 * 
 * @author Marcel Benning
 * 
 */
public class GamePanel extends JPanel {
	
	LevelScheduler	         level;
	Block[][]	               blocks	       = null;
	Image	                   t;
	int	                     maxLines	     = 10;
	int	                     margin	       = 10;
	MainFrame	               frame;
	boolean	                 sizeSet	     = false;
	int	                     lastId	       = 0;
	Font	                   font	         = new Font("Arial", Font.PLAIN, 18);
	public ArrayList<String>	consoleLines	= new ArrayList<String>();
	Inventory	               inv;
	
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
						Block b = new Block("Dummy", -1, PictureLoader.loadImageFromFile("base.png"));
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
				Block b = new Block("Dummy", -1, PictureLoader.loadImageFromFile("base.png"));
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

		if (lastId != level.getLevel().getActiveStageI()) {
			sizeSet = false;
			lastId = level.getLevel().getActiveStageI();
		} else {
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
		int xOffset = 40;
		int yOffset = 0;
		try {
			for (y = 0; y < level.getLevel().getActiveStage().getBlocks().length; y++) {
				for (x = 0; x < level.getLevel().getActiveStage().getBlocks()[0].length; x++) {
					try {
						b = level.getLevel().getActiveStage().getBlock(x, y);
						try {
							if (b.isVisible()) {
								if (b.isOcupied()) {
									t = GraphicUtils.getImageFromPicture(b.getOccupiedByBlock().getTexture());
									g.drawImage(t, ((x * 40)), ((y * 40)), null);
								} else {
									t = GraphicUtils.getImageFromPicture(b.getTexture());
									g.drawImage(t, ((x * 40)), ((y * 40)), null);
									if (b.getKeptItem() != null) {
										if (!(b.getKeptItem() == Items.noitem))
											g.drawImage(GraphicUtils.getImageFromPicture(b.getKeptItem().getTexture()), ((x * 40)), ((y * 40)), null);
									}
								}
								sizeX = x;
							} else {
								t = GraphicUtils.getImageFromPicture(b.getInvincebleBlock().getTexture());
								g.drawImage(t, ((x * 40)), ((y * 40)), null);
							}
						} catch (Exception e) {

						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				sizeY = y;
				yOffset += 40;
			}
		} catch (Exception e) {
		}

		if (!sizeSet) {
			frame.setSize(sizeX * 40 + 40 + 16, (sizeY * 40) + (10 * (18 + 10)) + 10);
			sizeSet = true;
		}
		// Draw own player
		Player p = Game.getPlayer();
		g.drawImage(GraphicUtils.getImageFromPicture(p.getTexture()), p.getX() * 40, p.getY() * 40, null);

		// Draw other player
		try {
			ArrayList<Player> players = level.getMultiPlayerServer().cmdHandler.getPlayers();
			for (Player pl : players) {
				g.drawImage(GraphicUtils.getImageFromPicture(pl.getTexture()), pl.getX() * 40, pl.getY() * 40, null);
			}
		} catch (Exception e) {
			System.out.println("Failed drawin other player");
		}
		inv = Game.getPlayer().getInventory();
		g.setColor(Color.BLACK);
		yOffset = (int) (this.getBounds().getHeight() - 88);
		int windowWidth = (int) this.getBounds().getWidth();
		for (int i = 0; i < inv.getSlots().length; i++) {
			int xoffset = windowWidth - (i * 68) - 88;
			g.setColor(Color.BLACK);
			g.fillRect(xoffset, yOffset, 68, 68);
			g.setColor(Color.DARK_GRAY);
			g.fillRect(xoffset + 4, yOffset + 4, 60, 60);
			if (!inv.getSlots()[i].getItem().getName().equalsIgnoreCase("noitem")){
				g.drawImage(GraphicUtils.getImageFromPicture(inv.getSlots()[i].getItem().getTexture()), xoffset + 4, yOffset + 4, 60, 60, null);
				g.setFont(font);
				g.setColor(Color.WHITE);
				g.drawString(inv.getSlots()[i].getAmount() + "", xoffset + 2 + (int) g.getFontMetrics(font).getStringBounds(inv.getSlots()[i].getAmount() + "", g).getWidth(), yOffset + 2 + (int) g.getFontMetrics(font).getStringBounds(inv.getSlots()[i].getAmount() + "", g).getHeight());
			}
			if(inv.getSelectedSlot() == i){
				g.setColor(Color.BLUE);
				g.drawRect(xoffset + 1, yOffset + 1, 66, 66);
				g.drawRect(xoffset + 2, yOffset + 2, 64, 64);
			}
		}
	}
}
