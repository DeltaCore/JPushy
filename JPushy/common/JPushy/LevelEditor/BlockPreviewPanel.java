package JPushy.LevelEditor;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import JPushy.Types.Blocks.Blocks;
import JPushy.Types.Items.Items;
import JPushy.gfx.GraphicUtils;

public class BlockPreviewPanel extends JPanel {

	private static final long	serialVersionUID	= 1L;
	private int	              selectedIndex	   = 0;
	private LevelEditorGui	  gui;

	public BlockPreviewPanel() {

	}

	@Override
	protected void paintComponent(Graphics g_) {
		super.paintComponent(g_);
		Graphics2D g = (Graphics2D) g_;
		g.clearRect(0, 0, 80, 80);
		if (selectedIndex > Blocks.blockRegistry.size()) {
			int itemIndex = selectedIndex - Blocks.blockRegistry.size(); System.out.println("Item index : " + itemIndex);
			g.drawImage(GraphicUtils.getImageFromPicture(Items.getItemById(Items.getIdByName(this.getGui().getCurrentBlock().getItemAt(itemIndex).toString())).getTexture()), 0, 0, 80, 80, null);
		} else {
			g.drawImage(GraphicUtils.getImageFromPicture(Blocks.getBlockByName(this.getGui().getCurrentBlock().getItemAt(selectedIndex).toString()).getTexture()), 0, 0, 80, 80, null);
		}
	}

	/**
	 * @return the selectedIndex
	 */
	public int getSelectedIndex() {
		return selectedIndex;
	}

	/**
	 * @param selectedIndex
	 *          the selectedIndex to set
	 */
	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
		this.repaint();
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

}
