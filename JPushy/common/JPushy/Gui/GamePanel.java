package JPushy.Gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import JPushy.Core.Game;
import JPushy.Core.LevelThread;
import JPushy.Types.Blocks.Block;
import JPushy.Types.Blocks.Blocks;
import JPushy.Types.Items.Item;
import JPushy.Types.Items.Items;
import JPushy.Types.Level.Level;
import JPushy.Types.Player.Inventory;
import JPushy.Types.Player.Player;
import JPushy.gfx.GraphicUtils;

/**
 * 
 * @author Marcel Benning
 * 
 */
public class GamePanel extends JPanel {

	private static final long	serialVersionUID	= 1L;

	private LevelThread	      thread;
	private int	              margin	         = 10;
	private Font	            font	           = new Font("Arial", Font.PLAIN, 18);
	private ArrayList<String>	consoleLines	   = new ArrayList<String>();
	private Level	            level;
	private Block	            tempBlock;
	private Item	            tempItem;
	private Image	            tempTexture;
	private int	              sizeY	           = 0, yOffset = 0;
	private Inventory	        inventory;

	public GamePanel(LevelThread levelThread, int i) {
		this.setThread(levelThread);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		render((Graphics2D) g);
	}

	public void render(Graphics2D g) {

		// Chat

		int x = margin, y = 0;
		int startAt = this.getConsoleLines().size() - this.getThread().getChat().MAX_LINES;
		String tmp;
		Rectangle2D rect;
		g.setColor(Color.black);
		g.setFont(font);
		int lastText = (int) this.getBounds().getHeight() - margin;
		int max = this.getConsoleLines().size();
		if (this.getConsoleLines().size() < this.getThread().getChat().MAX_LINES) {
			max = this.getThread().getChat().MAX_LINES;
		}
		for (int i = max; i >= 0; i--) {
			if (i > startAt) {
				if (i < this.getConsoleLines().size()) {
					tmp = this.getConsoleLines().get(i);
					rect = g.getFontMetrics().getStringBounds(tmp, g);
					g.drawString(tmp, x, lastText);
					lastText -= (rect.getBounds().height + margin);
				}
			}
		}

		// Block rendering

		this.level = thread.getLevel();
		for (y = 0; y < this.level.getHeight(); y++) {
			for (x = 0; x < this.level.getWidth(); x++) {
				this.tempBlock = this.level.getActiveStage().getBlock(x, y);
				try {
					if (this.tempBlock.isVisible()) {
						this.tempTexture = GraphicUtils.getImageFromPicture(Blocks.air.getTexture());
						g.drawImage(this.tempTexture, ((x * 40)), ((y * 40)), null);
						if (this.tempBlock.isOcupied()) {
							this.tempTexture = GraphicUtils.getImageFromPicture(this.tempBlock.getOccupiedByBlock().getTexture());
							g.drawImage(this.tempTexture, ((x * 40)), ((y * 40)), null);
						} else {
							this.tempTexture = GraphicUtils.getImageFromPicture(this.tempBlock.getTexture());
							g.drawImage(this.tempTexture, ((x * 40)), ((y * 40)), null);
						}
						if (this.tempBlock.getKeptItem() != null) {
							if (!(this.tempBlock.getKeptItem() == Items.noitem))
								g.drawImage(GraphicUtils.getImageFromPicture(this.tempBlock.getKeptItem().getTexture()), ((x * 40)), ((y * 40)), null);
						}
					} else {
						this.tempTexture = GraphicUtils.getImageFromPicture(this.tempBlock.getInvincebleBlock().getTexture());
						g.drawImage(this.tempTexture, ((x * 40)), ((y * 40)), null);
					}
				} catch (Exception e) {

				}
			}
			this.setSizeY(y);
			this.setyOffset(this.getyOffset() + 40);
		}

		// Player redering

		if (this.thread.getServer().cmdHandler.getPlayers().size() >= 1) {
			for (Player pl : this.thread.getServer().cmdHandler.getPlayers()) {
				System.out.println("Drawed Player " + pl.getName());
				g.drawImage(GraphicUtils.getImageFromPicture(pl.getTexture()), pl.getX() * 40, pl.getY() * 40, null);
			}
		} else {
			System.out.println("Player amount : " + this.thread.getServer().cmdHandler.getPlayers().size());
		}

		// Inventory

		this.setInventory(Game.getPlayer().getInventory());
		g.setColor(Color.BLACK);
		yOffset = (int) (this.getBounds().getHeight() - 88);
		int windowWidth = (int) this.getBounds().getWidth();
		for (int i = 0; i < this.getInventory().getSlots().length; i++) {
			int xoffset = windowWidth - (i * 68) - 88;
			g.setColor(Color.BLACK);
			g.fillRect(xoffset, yOffset, 68, 68);
			g.setColor(Color.DARK_GRAY);
			g.fillRect(xoffset + 4, yOffset + 4, 60, 60);
			if (!this.getInventory().getSlots()[i].getItem().getName().equalsIgnoreCase("noitem")) {
				g.drawImage(GraphicUtils.getImageFromPicture(this.getInventory().getSlots()[i].getItem().getTexture()), xoffset + 4, yOffset + 4, 60, 60, null);
				g.setFont(font);
				g.setColor(Color.WHITE);
				g.drawString(this.getInventory().getSlots()[i].getAmount() + "", xoffset + 2 + (int) g.getFontMetrics(font).getStringBounds(this.getInventory().getSlots()[i].getAmount() + "", g).getWidth(), yOffset + 2 + (int) g.getFontMetrics(font).getStringBounds(this.getInventory().getSlots()[i].getAmount() + "", g).getHeight());
				if (this.getInventory().getSlots()[i].getItem().isDamageBar()) {
					g.setColor(Color.BLACK);
					g.drawRect(xoffset + 9, yOffset + 49, 41, 4);
					g.setColor(Color.BLUE);
					g.fillRect(xoffset + 10, yOffset + 50, 40 - (40 / this.getInventory().getSlots()[i].getItem().getMaxDMG() * this.getInventory().getSlots()[i].getItem().getDmg()), 3);
				}
			}
			if (this.getInventory().getSelectedSlot() == i) {
				g.setColor(Color.BLUE);
				g.drawRect(xoffset + 1, yOffset + 1, 66, 66);
				g.drawRect(xoffset + 2, yOffset + 2, 64, 64);
			}
		}

	}

	public LevelThread getThread() {
		return thread;
	}

	public void setThread(LevelThread thread) {
		this.thread = thread;
	}

	public int getMargin() {
		return margin;
	}

	public void setMargin(int margin) {
		this.margin = margin;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public ArrayList<String> getConsoleLines() {
		return consoleLines;
	}

	public void setConsoleLines(ArrayList<String> consoleLines) {
		this.consoleLines = consoleLines;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public Block getTempBlock() {
		return tempBlock;
	}

	public void setTempBlock(Block tempBlock) {
		this.tempBlock = tempBlock;
	}

	public Item getTempItem() {
		return tempItem;
	}

	public void setTempItem(Item tempItem) {
		this.tempItem = tempItem;
	}

	public Image getTempTexture() {
		return tempTexture;
	}

	public void setTempTexture(Image tempTexture) {
		this.tempTexture = tempTexture;
	}

	public int getSizeY() {
		return sizeY;
	}

	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}

	public int getyOffset() {
		return yOffset;
	}

	public void setyOffset(int yOffset) {
		this.yOffset = yOffset;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

}
