package JPushy.LevelEditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import JPushy.Types.Blocks.Block;
import JPushy.Types.Blocks.Blocks;
import JPushy.Types.Items.Item;
import JPushy.Types.Items.Items;
import JPushy.Types.ProgammingRelated.BlockArray;
import JPushy.Types.ProgammingRelated.ItemArray;

public class EditorPanel extends JPanel {

	/**
	 * 
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

	private InputListener	        listener;

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

	public void onClick(int x, int y) {
		int cx = x / 40;
		int cy = y / 40;
		if (cy < this.getLevelHeight() && cx < this.getLevelWidth()) {
			String sel = this.getGui().getCurrentBlock().getSelectedItem().toString();
			int seli = this.getGui().getCurrentBlock().getSelectedIndex();
			if (seli >= Blocks.blockRegistry.size()) {
				Item i = Items.getItemById(Items.getIdByName(sel));
				this.getItemLayers().get(this.getCurrentLayer()).set(cx, cy, i);
				this.getBlockLayers().get(this.getCurrentLayer()).set(cx, cy, Blocks.getBlockByName("Air"));
			} else {
				Block b = Blocks.getBlockByName(sel);
				this.getBlockLayers().get(this.getCurrentLayer()).set(cx, cy, b);
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		render((Graphics2D) g);
	}

	public void render(Graphics2D g) {
		g.translate(originX, originY);
		g.scale(scale, scale);
		if (this.getBlockLayers().size() != 0) {
			this.getBlockLayers().get(currentLayer).render(g);
		}
		if (this.getItemLayers().size() != 0) {
			this.getItemLayers().get(currentLayer).render(g);
		}
		if (this.listener.getSelTileX() != 0 && this.listener.getSelTileY() != 0) {
			g.setColor(Color.blue);
			g.drawRect(this.listener.getSelTileX() * 40, this.listener.getSelTileY() * 40, 40, 40);
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
	 * @return the gui
	 */
	public LevelEditorGui getGui() {
		return gui;
	}

	/**
	 * @param gui
	 *          the gui to set
	 */
	public void setGui(LevelEditorGui gui) {
		this.gui = gui;
	}

	/**
	 * @return the layer
	 */
	public int getLayer() {
		return layer;
	}

	/**
	 * @param layer
	 *          the layer to set
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
	 * @return the layerUp
	 */
	public char getLayerUp() {
		return layerUp;
	}

	/**
	 * @param layerUp
	 *          the layerUp to set
	 */
	public void setLayerUp(char layerUp) {
		this.layerUp = layerUp;
	}

	/**
	 * @return the layerDown
	 */
	public char getLayerDown() {
		return layerDown;
	}

	/**
	 * @param layerDown
	 *          the layerDown to set
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

	private class InputListener implements MouseListener, MouseWheelListener, MouseMotionListener, KeyListener {

		private EditorPanel	gui;

		private int		      selTileX	= 0;
		private int		      selTileY	= 0;

		private boolean		  shift		 = false;

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
		public void mouseDragged(MouseEvent e) {
			if (e.getButton() == 3) {
				gui.originX += e.getX() - lastX;
				gui.originY += e.getY() - lastY;
				lastX = e.getX();
				lastY = e.getY();
				gui.repaint();
			} else if (e.getButton() == 1) {
				if ((e.getX() - gui.originX >= 0) && (e.getY() - gui.originY >= 0)) {
					gui.onClick(e.getX() - gui.originX, e.getY() - gui.originY);
					gui.repaint();
				}
			}
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (e.getButton() == 3) {
				gui.lastX = e.getX();
				gui.lastY = e.getY();
			} else if (e.getButton() == 2) {
				if ((e.getX() - gui.originX >= 0) && (e.getY() - gui.originY >= 0)) {
					int cx = (e.getX() - gui.originX) / 40;
					int cy = (e.getY() - gui.originY) / 40;
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
								String blockName = this.gui.getBlockLayers().get(gui.getCurrentLayer()).get(cx, cy).getName();
								if (blockName.equalsIgnoreCase(name)) {
									this.gui.getGui().getCurrentBlock().setSelectedIndex(i);
								}
							}
						}
					}
				}
			} else if (e.getButton() == 1) {
				if (shift) {
					this.selTileX = (e.getX() - gui.originX) / 40;
					this.selTileY = (e.getY() - gui.originY) / 40;
				} else {
					this.selTileX = this.selTileY = 0;
					if ((e.getX() - gui.originX >= 0) && (e.getY() - gui.originY >= 0)) {
						gui.onClick(e.getX() - gui.originX, e.getY() - gui.originY);
						gui.repaint();
					}
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyPressed(KeyEvent e) {
			System.out.println("down");
			if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
				this.shift = true;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			System.out.println("up");
			if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
				this.shift = false;
			}
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
		 * @return the gui
		 */
		public EditorPanel getGui() {
			return gui;
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
		 * @param selTileX
		 *          the selTileX to set
		 */
		public void setSelTileX(int selTileX) {
			this.selTileX = selTileX;
		}

		/**
		 * @return the selTileY
		 */
		public int getSelTileY() {
			return selTileY;
		}

		/**
		 * @param selTileY
		 *          the selTileY to set
		 */
		public void setSelTileY(int selTileY) {
			this.selTileY = selTileY;
		}

	}

}
