package JPushy.LevelEditor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import JPushy.Types.Coord2D;
import JPushy.Types.Blocks.Block;
import JPushy.Types.Blocks.Blocks;
import JPushy.Types.Items.Item;
import JPushy.Types.Items.Items;
import JPushy.Types.Level.LevelLoader;
import JPushy.Types.ProgammingRelated.BlockArray;
import JPushy.Types.ProgammingRelated.ItemArray;

public class EditorPanel extends JPanel {

	/**
	 * This Editor Panel is the panel where you can push your level around and set
	 * blocks
	 */
	private static final long	    serialVersionUID	= 1L;

	private LevelEditorGui	      gui;

	private int	                  layer	            = 0;
	private int	                  levelWidth	      = 0;
	private int	                  levelHeight	      = 0;

	private int	                  currentLayer	    = 0;

	private ArrayList<BlockArray>	blockLayers	      = new ArrayList<BlockArray>();
	private ArrayList<ItemArray>	itemLayers	      = new ArrayList<ItemArray>();

	private char	                layerUp	          = '+';
	private char	                layerDown	        = '-';
	private char	                settingsToDefault	= 'd';

	private int	                  lastX	            = 0;
	private int	                  lastY	            = 0;

	private int	                  originX	          = 0;
	private int	                  originY	          = 0;
	private float	                scale	            = 1.0f;
	private int	                  opX	              = 0;
	private int	                  opY	              = 0;

	private InputListener	        listener;

	private final Font	          inPanelFont	      = new Font("Arial", Font.PLAIN, 18);
	
	public EditorPanel() {
		super();
		this.listener = new InputListener(this);
		this.addMouseListener(this.listener);
		this.addMouseMotionListener(this.listener);
		this.addMouseWheelListener(this.listener);
	}

	public void updateLayer(int layers, int width, int height) {
		this.setLevelHeight(height);
		this.setLevelWidth(width);
		while (this.getBlockLayers().size() < layers) {
			this.getBlockLayers().add(new BlockArray(width, height));
		}
		while (this.getItemLayers().size() < layers) {
			this.getItemLayers().add(new ItemArray(width, height));
		}
		for (int i = 0; i < this.getBlockLayers().size(); i++) {
			this.getBlockLayers().set(i, new BlockArray(width, height));
		}
		for (int i = 0; i < this.getItemLayers().size(); i++) {
			this.getItemLayers().set(i, new ItemArray(width, height));
		}
		this.repaint();
	}

	public void updateCoords(int x, int y) {
		System.out.println("update !");
		if (listener.getSelTileX() != -1 && listener.getSelTileY() != -1) {
			this.setOpX(x);
			this.setOpY(y);
			this.getBlockLayers().get(currentLayer).setOption(this.listener.selTileX, this.listener.selTileY, new Coord2D(x, y));
			this.repaint();
			System.out.println("Option X : " + this.getBlockLayers().get(currentLayer).getOption(this.listener.selTileX, this.listener.selTileX).getX() + " ; Option Y : " + this.getBlockLayers().get(currentLayer).getOption(this.listener.selTileX, this.listener.selTileX).getY());
		}
	}

	public void onClick(int x, int y) {
		int cx = (int) (x / (40 * this.getScale()));
		int cy = (int) (y / (40 * this.getScale()));
		if (cy < this.getLevelHeight() && cx < this.getLevelWidth()) {
			String sel = this.getGui().getCurrentBlock().getSelectedItem().toString();
			int seli = this.getGui().getCurrentBlock().getSelectedIndex();
			if (seli >= Blocks.blockRegistry.size()) {
				Item i = Items.getItemById(Items.getIdByName(sel));
				this.getItemLayers().get(this.getCurrentLayer()).set(cx, cy, i);
				this.getBlockLayers().get(this.getCurrentLayer()).setBlock(cx, cy, Blocks.getBlockByName("Air"));
			} else {
				Block b = Blocks.getBlockByName(sel);
				this.getBlockLayers().get(this.getCurrentLayer()).setBlock(cx, cy, b);
				if (b.getId() == Blocks.air.getId()) {
					this.getItemLayers().get(this.getCurrentLayer()).set(cx, cy, Items.getItemById(Items.noitem.getId()));
				}
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		render((Graphics2D) g);
	}

	public void render(Graphics2D g) {
		g.setColor(Color.black);
		g.drawLine(this.listener.getxPos(), 0, this.listener.getxPos(), (int)this.getBounds().getHeight());
		g.drawLine(0, this.listener.getyPos(), (int) this.getBounds().getWidth(), this.listener.getyPos());
		g.setFont(this.getFont());
		String s = "X : " + (int) ((this.listener.getxPos() - originX) / (40 * this.getScale())) + " Y : " + (int) ((this.listener.getyPos() - originY) / (40 * this.getScale()));
		g.drawString(s, 0, (int) g.getFontMetrics().getStringBounds(s, g).getHeight());
		g.translate(originX, originY);
		g.scale(scale, scale);
		if (this.getBlockLayers().size() != 0) {
			this.getBlockLayers().get(currentLayer).render(g);
		}
		if (this.getItemLayers().size() != 0) {
			this.getItemLayers().get(currentLayer).render(g);
		}
		if (this.listener.getSelTileX() != -1 && this.listener.getSelTileY() != -1) {
			g.setColor(Color.red);
			g.drawRect(this.listener.getSelTileX() * 40, this.listener.getSelTileY() * 40, 40, 40);
			g.setColor(Color.blue);
			if (this.getBlockLayers().get(currentLayer).getOption(this.listener.getSelTileX(), this.listener.getSelTileY()).getX() != -1 && this.getBlockLayers().get(currentLayer).getOption(this.listener.getSelTileX(), this.listener.getSelTileY()).getX() != -1) {
				g.drawRect(this.getBlockLayers().get(currentLayer).getOption(this.listener.getSelTileX(), this.listener.getSelTileY()).getX() * 40, this.getBlockLayers().get(currentLayer).getOption(this.listener.getSelTileX(), this.listener.getSelTileY()).getY() * 40, 40, 40);
			}
		}
	}

	public void saveLevel(String name, String version) {
		this.getGui().getProgressbar().setMaximum((this.getLayer() * this.getLevelHeight() * this.getLevelWidth()));
		this.getGui().getProgressbar().setEnabled(true);
		//this.getGui().getProgressbar().setIndeterminate(true);
		Thread t = new Thread(new EditorSaveThread(this, name, version));
		t.start();
	}

	public synchronized void incrementBar(){
		this.getGui().getProgressbar().setValue(this.getGui().getProgressbar().getValue() + 1);
	}
		
	public void loadLevel(String filename) {
		File lvlFile = new File("Data/lvl/" + filename + ".lvl");
		File cfgFile = new File("Data/lvl/" + filename + ".cfg");
		if (!lvlFile.exists() || !cfgFile.exists()) {
			System.out.println("File not found :/");
		} else {
			System.out.println("Loading level : " + filename);
			Blocks.wakeUpDummy();
			String line;
			ArrayList<String> lines = LevelLoader.loadLevelFile(lvlFile.getName());
			String start_regex = "^<stage id=([0-9])>$";
			String end_regex = "^</stage>";
			String levelRegEx = "^<level name=\"([a-zA-Z\\s0-9[-][_]]{1,})\" version='([a-zA-Z0-9.,]{1,})'>$";
			String commentStart = "^<comment>";
			String commentEnd = "^</comment>";

			int currentStageId = 0;
			boolean flag = false;
			int xCounter = 0;
			int yCounter = 0;
			char charValue = '0';
			int blockId = 0;
			int itemForBlock = -1;

			boolean commentFlag = false;

			int layers = LevelLoader.getLevelStages(lines);
			int width = LevelLoader.getStageWidth(lines, currentStageId);
			int height = LevelLoader.getStageLength(lines, currentStageId);
			for (int i = 0; i < layers; i++) {
				int x = LevelLoader.getStageWidth(lines, i);
				if (x > width) {
					width = x;
				}
				int y = LevelLoader.getStageLength(lines, i);
				if (y > height) {
					height = y;
				}
			}
			this.setBlockLayers(new ArrayList<BlockArray>());
			this.updateLayer(layers, width, height);
			System.out.println("Levelsize : x: " + width + " y:" + height);
			this.gui.getxSizeVal().setValue(width);
			this.gui.getySizeVal().setValue(height);
			this.gui.getLayerVal().setValue(layers);

			for (int linesY = 0; linesY < lines.size(); linesY++) {
				line = lines.get(linesY);
				if (line.matches(levelRegEx)) {
					Pattern pattern = Pattern.compile(levelRegEx);
					Matcher matcher = pattern.matcher(line);
					if (matcher.matches()) {
						String name = matcher.group(1);
						String version = matcher.group(2);
						this.gui.getLevelNameText().setText(name);
						this.gui.getLevelVersionText().setText(version);
						this.gui.repaint();
					}
				} else if (line.matches(start_regex)) {
					flag = true;
					Pattern pattern = Pattern.compile(start_regex);
					Matcher matcher = pattern.matcher(line);
					if (matcher.matches()) {
						int id = Integer.parseInt(matcher.group(1));
						currentStageId = id;
					}
					yCounter = 0;
				} else if (line.matches(end_regex)) {
					flag = false;
					yCounter = 0;
				} else if (line.matches(commentStart)) {
					commentFlag = true;
				} else if (line.matches(commentEnd)) {
					commentFlag = false;
				} else if (commentFlag) {
				} else if (flag) {
					for (xCounter = 0; xCounter < line.length(); xCounter++) {
						charValue = line.charAt(xCounter);
						blockId = charValue - 48;
						Block b = Blocks.getBlockById(blockId);
						this.getBlockLayers().get(currentStageId).setBlock(xCounter, yCounter, b);

						itemForBlock = LevelLoader.getItemForBlock(currentStageId, xCounter, yCounter, cfgFile.getName());
						if (itemForBlock != -1) {
							this.getItemLayers().get(currentStageId).set(xCounter, yCounter, Items.getItemById(itemForBlock));
						}
					}
					yCounter++;
				}
			}
		}
		ArrayList<String> lines = LevelLoader.loadLevelConfig(cfgFile.getName());
		ArrayList<String> levelLines = LevelLoader.loadLevelFile(lvlFile.getName());
		for (String line : lines) {
			String regex = "^([0-9]{1,}),([0-9]{1,}),([0-9]{1,})=([0-9]{1,}),([0-9]{1,})$";
			if (line.matches(regex)) {
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(line);
				if (matcher.matches()) {
					int id = Integer.parseInt(matcher.group(1));
					int x1 = Integer.parseInt(matcher.group(2));
					int y1 = Integer.parseInt(matcher.group(3));
					int x2 = Integer.parseInt(matcher.group(4));
					int y2 = Integer.parseInt(matcher.group(5));
					this.getBlockLayers().get(id).setOption(x1, y1, new Coord2D(x2, y2));
				}
			}
		}
	}

	/**
	 * @return the currentLayer
	 */
	public int getCurrentLayer() {
		return currentLayer;
	}

	/**
	 * @param currentLayer
	 *          the currentLayer to set
	 */
	public void setCurrentLayer(int currentLayer) {
		this.currentLayer = currentLayer;
	}

	/**
	 * @return the parent gui
	 */
	public LevelEditorGui getGui() {
		return gui;
	}

	/**
	 * @param parent
	 *          gui the parent gui to set
	 */
	public void setGui(LevelEditorGui gui) {
		this.gui = gui;
	}

	/**
	 * @return the amount of layers
	 */
	public int getLayer() {
		return layer;
	}

	/**
	 * @param layers
	 *          the amount of layers to set
	 */
	public void setLayer(int layer) {
		this.layer = layer;
	}

	/**
	 * @return the levelWidth
	 */
	public int getLevelWidth() {
		return levelWidth;
	}

	/**
	 * @param levelWidth
	 *          the levelWidth to set
	 */
	public void setLevelWidth(int levelWidth) {
		this.levelWidth = levelWidth;
	}

	/**
	 * @return the levelHeight
	 */
	public int getLevelHeight() {
		return levelHeight;
	}

	/**
	 * @param levelHeight
	 *          the levelHeight to set
	 */
	public void setLevelHeight(int levelHeight) {
		this.levelHeight = levelHeight;
	}

	/**
	 * @return the layerUp <- character for keyboard
	 */
	public char getLayerUp() {
		return layerUp;
	}

	/**
	 * @param layerUp
	 *          the layerUp to set <- character for keyboard
	 */
	public void setLayerUp(char layerUp) {
		this.layerUp = layerUp;
	}

	/**
	 * @return the layerDown <- character for keyboard
	 */
	public char getLayerDown() {
		return layerDown;
	}

	/**
	 * @param layerDown
	 *          the layerDown to set <- character for keyboard
	 */
	public void setLayerDown(char layerDown) {
		this.layerDown = layerDown;
	}

	/**
	 * @return the blockLayers
	 */
	public ArrayList<BlockArray> getBlockLayers() {
		return blockLayers;
	}

	/**
	 * @param blockLayers
	 *          the blockLayers to set
	 */
	public void setBlockLayers(ArrayList<BlockArray> blockLayers) {
		this.blockLayers = blockLayers;
	}

	/**
	 * @return the itemLayers
	 */
	public ArrayList<ItemArray> getItemLayers() {
		return itemLayers;
	}

	/**
	 * @param itemLayers
	 *          the itemLayers to set
	 */
	public void setItemLayers(ArrayList<ItemArray> itemLayers) {
		this.itemLayers = itemLayers;
	}

	/**
	 * @return the settingsToDefault
	 */
	public char getSettingsToDefault() {
		return settingsToDefault;
	}

	/**
	 * @param settingsToDefault
	 *          the settingsToDefault to set
	 */
	public void setSettingsToDefault(char settingsToDefault) {
		this.settingsToDefault = settingsToDefault;
	}

	/**
	 * @return the lastX
	 */
	public int getLastX() {
		return lastX;
	}

	/**
	 * @param lastX
	 *          the lastX to set
	 */
	public void setLastX(int lastX) {
		this.lastX = lastX;
	}

	/**
	 * @return the lastY
	 */
	public int getLastY() {
		return lastY;
	}

	/**
	 * @param lastY
	 *          the lastY to set
	 */
	public void setLastY(int lastY) {
		this.lastY = lastY;
	}

	/**
	 * @return the originX
	 */
	public int getOriginX() {
		return originX;
	}

	/**
	 * @param originX
	 *          the originX to set
	 */
	public void setOriginX(int originX) {
		this.originX = originX;
	}

	/**
	 * @return the originY
	 */
	public int getOriginY() {
		return originY;
	}

	/**
	 * @param originY
	 *          the originY to set
	 */
	public void setOriginY(int originY) {
		this.originY = originY;
	}

	/**
	 * @return the scale
	 */
	public float getScale() {
		return scale;
	}

	/**
	 * @param scale
	 *          the scale to set
	 */
	public void setScale(float scale) {
		this.scale = scale;
	}

	/**
	 * @return the listener
	 */
	public InputListener getListener() {
		return listener;
	}

	/**
	 * @param listener
	 *          the listener to set
	 */
	public void setListener(InputListener listener) {
		this.listener = listener;
	}

	public int getOpX() {
		return opX;
	}

	public void setOpX(int opX) {
		this.opX = opX;
	}

	public int getOpY() {
		return opY;
	}

	public void setOpY(int opY) {
		this.opY = opY;
	}

	/**
	 * @return the inPanelFont
	 */
	public Font getInPanelFont() {
		return inPanelFont;
	}

	private class InputListener implements MouseListener, MouseWheelListener, MouseMotionListener, KeyListener {

		private EditorPanel	gui;

		private int		      selTileX	= -1;
		private int		      selTileY	= -1;

		private boolean		  btns[]		= new boolean[] { false, false, false, false };

		private int		      xPos		 = 0, yPos = 0;

		public InputListener(EditorPanel gui) {
			this.setGui(gui);
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			gui.scale += (float) e.getWheelRotation() / 100;
			if (gui.scale < 0.5f) {
				gui.scale = 0.5f;
			}
			gui.repaint();
		}

		@Override
		// -> on windows the drag event won't work with e.getButton()
		public void mouseDragged(MouseEvent e) {
			if (btns[3]) {
				gui.originX += e.getX() - lastX;
				gui.originY += e.getY() - lastY;
				lastX = e.getX();
				lastY = e.getY();
				gui.repaint();
			} else if (btns[1]) {
				if ((e.getX() - gui.originX >= 0) && (e.getY() - gui.originY >= 0)) {
					gui.onClick(e.getX() - gui.originX, e.getY() - gui.originY);
					gui.repaint();
				}
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			this.setxPos(e.getX());
			this.setyPos(e.getY());
			this.gui.repaint();
		}

		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {

		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (e.isShiftDown() && ((e.getX() - gui.originX >= 0) && (e.getY() - gui.originY >= 0))) {
				this.selTileX = (e.getX() - gui.originX) / (int) (40 * this.gui.getScale());
				this.selTileY = (e.getY() - gui.originY) / (int) (40 * this.gui.getScale());
			} else if (e.isControlDown() && ((e.getX() - gui.originX >= 0) && (e.getY() - gui.originY >= 0))) {
				this.gui.getGui().getOptionsLabelXVal().setValue((e.getX() - gui.originX) / (int) (40 * this.gui.getScale()));
				this.gui.getGui().getOptionsLabelYVal().setValue((e.getY() - gui.originY) / (int) (40 * this.gui.getScale()));
			} else {
				this.selTileX = -1;
				this.selTileY = -1;
				switch (e.getButton()) {
					case 1: {
						btns[1] = true;
						break;
					}
					case 2: {
						btns[2] = true;
						break;
					}
					case 3: {
						btns[3] = true;
						break;
					}
				}
				if (btns[3]) {
					gui.lastX = e.getX();
					gui.lastY = e.getY();
				} else if (btns[2]) {
					if ((e.getX() - gui.originX >= 0) && (e.getY() - gui.originY >= 0)) {
						int cx = (e.getX() - gui.originX) / (int) (40 * this.gui.getScale());
						int cy = (e.getY() - gui.originY) / (int) (40 * this.gui.getScale());
						if (cx < this.gui.getWidth() && cy < this.gui.getHeight()) {
							System.out.println("Block : " + cx + ":" + cy);
							for (int i = 0; i < this.gui.getGui().getCurrentBlock().getModel().getSize(); i++) {
								String name = this.gui.getGui().getCurrentBlock().getModel().getElementAt(i).toString();
								if (i >= Blocks.blockRegistry.size()) {
									String itemName = this.gui.getItemLayers().get(gui.getCurrentLayer()).get(cx, cy).getName();
									if (!itemName.equalsIgnoreCase("noitem")) {
										if (itemName.equalsIgnoreCase(name)) {
											this.gui.getGui().getCurrentBlock().setSelectedIndex(i);
										}
									}
								} else {
									String blockName = this.gui.getBlockLayers().get(gui.getCurrentLayer()).getBlock(cx, cy).getName();
									if (blockName.equalsIgnoreCase(name)) {
										this.gui.getGui().getCurrentBlock().setSelectedIndex(i);
									}
								}
							}
						}
					}
				} else if (btns[1]) {
					if ((e.getX() - gui.originX >= 0) && (e.getY() - gui.originY >= 0)) {
						gui.onClick(e.getX() - gui.originX, e.getY() - gui.originY);
						gui.repaint();
					}
				}
			}
			gui.repaint();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			switch (e.getButton()) {
				case 1: {
					btns[1] = false;
					break;
				}
				case 2: {
					btns[2] = false;
					break;
				}
				case 3: {
					btns[3] = false;
					break;
				}
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {

		}

		@Override
		public void keyTyped(KeyEvent e) {
			System.out.println("downup");
			if ((e.getKeyChar() == gui.getLayerUp()) && (gui.currentLayer < layer - 1)) {
				gui.setCurrentLayer(gui.getCurrentLayer() + 1);
			}
			if ((e.getKeyChar() == gui.getLayerDown()) && (gui.currentLayer > 0)) {
				gui.setCurrentLayer(gui.getCurrentLayer() - 1);
			}
			gui.getGui().getCurrentLayerBox().setValue(gui.getCurrentLayer());
			if (e.getKeyChar() == gui.settingsToDefault) {
				gui.scale = 1f;
				gui.originX = 0;
				gui.originY = 0;
			}
		}

		/**
		 * @param gui
		 *          the gui to set
		 */
		public void setGui(EditorPanel gui) {
			this.gui = gui;
		}

		/**
		 * @return the selTileX
		 */
		public int getSelTileX() {
			return selTileX;
		}

		/**
		 * @return the selTileY
		 */
		public int getSelTileY() {
			return selTileY;
		}

		/**
		 * @return the xPos
		 */
		public int getxPos() {
			return xPos;
		}

		/**
		 * @param xPos
		 *          the xPos to set
		 */
		public void setxPos(int xPos) {
			this.xPos = xPos;
		}

		/**
		 * @return the yPos
		 */
		public int getyPos() {
			return yPos;
		}

		/**
		 * @param yPos
		 *          the yPos to set
		 */
		public void setyPos(int yPos) {
			this.yPos = yPos;
		}

	}

}