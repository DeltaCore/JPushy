package net.ccmob.apps.jpushy.modloader.eventArgs;

import net.ccmob.apps.jpushy.modloader.ModEventArg;

public class KeyEventArg extends ModEventArg {

	private int keyCode = 0;
	
	public KeyEventArg(int keyCode) {
	  this.keyCode = keyCode; 
  }
	
	public int getKeyCode() {
	  return keyCode;
  }
	
}
