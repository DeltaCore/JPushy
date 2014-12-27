package net.ccmob.apps.jpushy.mp.local;

import java.net.Socket;
/**
 * 
 * @author Marcel Benning
 * 
 */
public interface ICommandHandler {
	
	public void onCommand(String[] args, Socket packet, MPListenerThread thread);
	
	public void onCommand(String msg, Socket packet, MPListenerThread thread);
	
}
