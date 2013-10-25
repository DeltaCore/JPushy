package JPushy.Gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import JPushy.Core.Game;
import JPushy.Core.LevelScheduler;
import JPushy.Types.Blocks.Block;
import JPushy.Types.Items.Items;
import JPushy.Types.Player.Inventory;
import JPushy.Types.Player.Player;
import JPushy.gfx.GraphicUtils;
import JPushy.gfx.PictureLoader;

/**
 * 
 * @author Marcel Benning
 * 
 */
public class GamePanel extends JPanel {
	

	private static final long serialVersionUID = 1L;
	
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
		blocks = LevelScheduler.getLevel().getActiveStage().getBlocks();
		this.maxLines = showLines;
		int x = 0;
		int y = 0;
		boolean flag1 = false;
		boolean flag2 = false;
		for (int i = 0; i < LevelScheduler.getLevel().getActiveStage().getBlocks().length; i++) {
			if (!flag2) {
				for (int j = 0; j < LevelScheduler.getLevel().getActiveStage().getBlocks()[0].length; j++) {
					if (!flag2) {
						Block b = new Block("Dummy", -1, PictureLoader.loadImageFromFile("base.png"));
						try {
							b = LevelScheduler.getLevel().getActiveStage().getBlock(j, i);
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
					b = LevelScheduler.getLevel().getActiveStage().getBlock(0, i);
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

		if (lastId != LevelScheduler.getLevel().getActiveStageI()) {
			sizeSet = false;
			lastId = LevelScheduler.getLevel().getActiveStageI();
		} else {
		}
		
		LevelScheduler.getLevel().update();
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
		int yOffset = 0;
		try {
			for (y = 0; y < LevelScheduler.getLevel().getActiveStage().getBlocks().length; y++) {
				for (x = 0; x < LevelScheduler.getLevel().getActiveStage().getBlocks()[0].length; x++) {
					try {
						b = LevelScheduler.getLevel().getActiveStage().getBlock(x, y);
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
		// Draw player
		try {
			ArrayList<Player> players = level.getMultiPlayerServer().cmdHandler.getPlayers();
			for (Player pl : players) {
				g.drawImage(GraphicUtils.getImageFromPicture(pl.getTexture()), pl.getX() * 40, pl.getY() * 40, null);
			}
		} catch (Exception e) {
			System.out.println("Failed drawin other player");
		}
		if(!Game.getPlayer().getInventory().getItemInHand().getName().equals("noitem"))
			g.drawImage(GraphicUtils.getImageFromPicture(Game.getPlayer().getInventory().getSlots()[Game.getPlayer().getInventory().getSelectedSlot()].getItem().getTexture()), Game.getPlayer().getX() * 40 + 20, Game.getPlayer().getY() * 40 + 20, 20, 20, null);
		
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
