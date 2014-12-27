package net.ccmob.apps.jpushy.sp.level.editor;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import net.ccmob.apps.jpushy.blocks.Blocks;
import net.ccmob.apps.jpushy.graphics.GraphicUtils;
import net.ccmob.apps.jpushy.items.Items;

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
		if(selectedIndex != -1){
			if (selectedIndex > Blocks.blockRegistry.size()) {
				g.drawImage(GraphicUtils.getImageFromPicture(Items.getItemById(Items.getIdByName(this.getGui().getCurrentBlock().getItemAt(selectedIndex).toString())).getTexture()), 0, 0, 80, 80, null);
			} else {
				Blocks.getBlockByName(this.getGui().getCurrentBlock().getItemAt(selectedIndex).toString()).getRenderHandler().renderBlock(Blocks.getBlockByName(this.getGui().getCurrentBlock().getItemAt(selectedIndex).toString()), g, 0, 0, 80, 80);
			}
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
		if(selectedIndex != -1){
			this.selectedIndex = selectedIndex;
			this.repaint();
		}
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
