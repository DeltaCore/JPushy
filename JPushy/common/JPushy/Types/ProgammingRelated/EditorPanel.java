package JPushy.Types.ProgammingRelated;

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
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JPanel;

import JPushy.LevelEditor.LevelEditorGui;
import JPushy.Types.Blocks.Block;
import JPushy.Types.Items.Item;

public class EditorPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {

	private LevelEditorGui	      gui;
	private BufferStrategy	      bs;
	private Graphics2D	          graphics;
	private Block	                tempBlock;
	private Item	                tempItem;

	private int	                  layer	       = 0;
	private int	                  width	       = 0;
	private int	                  height	     = 0;

	private int	                  currentLayer	= 0;

	private ArrayList<BlockArray>	layers	     = new ArrayList<BlockArray>();

	private char	                layerUp	     = '+';
	private char	                layerDown	   = '-';

	public EditorPanel() {
		super();
	}

	@Override
	public void paint(Graphics g) {
		System.out.println("paint");
		super.paint(g);
		this.setGraphics((Graphics2D) g);
		render((Graphics2D) g);
	}

	@Override
	public void paintComponents(Graphics g) {
		System.out.println("paintcomp");
		super.paintComponents(g);
		this.setGraphics((Graphics2D) g);
		render((Graphics2D) g);
	}

	@Override
	public void paintAll(Graphics g) {
		System.out.println("paintall");
		super.paintAll(g);
		this.setGraphics((Graphics2D) g);
		render((Graphics2D) g);
	}

	public void render(Graphics2D g) {
		Block b;
		Item i;
		this.getLayers().get(currentLayer).render(this.getGraphics());
		g.setColor(Color.BLACK);
		g.draw3DRect(0, 0, 40, 40, true);
		System.out.println("Render !");
	}

	@Override
	public void repaint() {
		super.repaint();
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		this.repaint();
		System.out.println("Resized !");
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		this.getGui().repaint();
		this.getGui().repaint(0);
		this.setBackground(Color.WHITE);
		this.repaint(0);
		this.setVisible(true);
		System.out.println("Entered");
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		if ((e.getKeyChar() == this.getLayerUp()) && (this.currentLayer < layer - 1)) {
			// this.setCurrentLayer(this.getCurrentLayer() + 1);
		}
		if ((e.getKeyChar() == this.getLayerDown()) && (this.currentLayer > 0)) {
			// this.setCurrentLayer(this.getCurrentLayer() - 1);
		}
		// this.getGui().getCurrentLayer().setValue(this.getCurrentLayer());
	}

	public BufferStrategy getBs() {
		return bs;
	}

	public void setBs(BufferStrategy bs) {
		this.bs = bs;
	}

	public LevelEditorGui getGui() {
		return gui;
	}

	public void setGui(LevelEditorGui gui) {
		this.gui = gui;
	}

	public Graphics2D getGraphics() {
		return graphics;
	}

	public void setGraphics(Graphics2D graphics) {
		this.graphics = graphics;
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
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width
	 *          the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height
	 *          the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the layers
	 */
	public ArrayList<BlockArray> getLayers() {
		return layers;
	}

	/**
	 * @param layers
	 *          the layers to set
	 */
	public void setLayers(ArrayList<BlockArray> layers) {
		this.layers = layers;
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

}
