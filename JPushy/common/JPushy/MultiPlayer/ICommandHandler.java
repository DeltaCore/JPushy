package JPushy.MultiPlayer;

import java.net.DatagramPacket;
/**
 * 
 * @author Marcel Benning
 * 
 */
public interface ICommandHandler {
	
	public void onCommand(String[] args, DatagramPacket packet);
	
}
