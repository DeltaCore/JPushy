package net.ccmob.apps.jpushy.mp.local;

import java.net.DatagramPacket;
/**
 * 
 * @author Marcel Benning
 * 
 */
public interface ICommandHandler {
	
	public void onCommand(String[] args, DatagramPacket packet);
	
	public void onCommand(String msg, DatagramPacket packet);
	
}
