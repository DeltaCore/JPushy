package net.ccmob.apps.jpushy.sound;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

public class Sound {

	private String	path;
	private Clip	 clip;

	public Sound(String filename) {
		this.path = "Data/sound/" + filename;
	}

	public void play() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					AudioInputStream audio = AudioSystem.getAudioInputStream(new File(path));
					clip = AudioSystem.getClip(); // create clip object
					clip.open(audio);
					clip.start();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
		}, "Sound Thread : " + path);
		// System.out.println("Playing " + path);
	}

	public synchronized void stop() {
		if (clip.isActive())
			clip.stop();
	}

}