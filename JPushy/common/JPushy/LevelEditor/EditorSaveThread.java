package JPushy.LevelEditor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.swing.JOptionPane;

import JPushy.Types.Items.Items;

public class EditorSaveThread implements Runnable {

	private EditorPanel	gui;
	private String	    name;
	private String	    version;
	public boolean	    isRunning	= false;

	public EditorSaveThread(EditorPanel panel, String name, String version) {
		this.setGui(panel);
		this.name = name;
		this.version = version;
	}

	@Override
	public void run() {
		System.out.println("Blueh !");
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File("Data/lvl/" + name + ".lvl")));
			writer.write("<level name=\"" + name + "\" version='" + this.version + "'>\n");
			int c = 0;
			for (int l = 0; l < this.getGui().getLayer(); l++) {
				writer.write("<stage id=" + l + ">\n");
				for (int y = 0; y < this.getGui().getLevelHeight(); y++) {
					for (int x = 0; x < this.getGui().getLevelWidth(); x++) {
						writer.write((char) (this.getGui().getBlockLayers().get(l).getBlock(x, y).getId() + 48));
						this.getGui().incrementBar();
					}
					writer.write("\n");
				}
				writer.write("</stage>\n");
			}
			writer.close();
			writer = new BufferedWriter(new FileWriter(new File("Data/lvl/" + name + ".cfg")));
			for (int l = 0; l < this.getGui().getLayer(); l++) {
				for (int y = 0; y < this.getGui().getLevelHeight(); y++) {
					for (int x = 0; x < this.getGui().getLevelWidth(); x++) {
						c = this.getGui().getItemLayers().get(l).get(x, y).getId();
						if (c != Items.noitem.getId()) {
							c += 48;
							writer.write("item=" + l + "," + x + "," + y + "=" + (char) c + ";\n");
						}
						if (this.getGui().getBlockLayers().get(l).getOption(x, y).getX() != -1 && this.getGui().getBlockLayers().get(l).getOption(x, y).getY() != -1) {
							writer.write(l + "," + x + "," + y + "=" + this.getGui().getBlockLayers().get(l).getOption(x, y).getX() + "," + this.getGui().getBlockLayers().get(l).getOption(x, y).getY() + "\n");
						}
						this.getGui().incrementBar();
					}
				}
			}
			writer.close();
			JOptionPane.showMessageDialog(null, "Level " + name + " V" + version + " was saved to Data/lvl/" + name + ".lvl / .cfg");
			this.getGui().getGui().getGame().updateLevels();
		} catch (Exception e) {
		}
	}

	public EditorPanel getGui() {
		return gui;
	}

	public void setGui(EditorPanel gui) {
		this.gui = gui;
	}

}
