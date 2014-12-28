package net.ccmob.apps.jpushy.mp.remote;

import java.net.Socket;

import net.ccmob.apps.jpushy.utils.Coord2D;

public class BlockPacket {

	public static final String packetPrefix = "--packet/";

	private Socket packetSocket;
	private String rawPacketContent;
	
	private Coord2D blockPos;
	private String packetContent;
	private boolean canceled = false;
	
	public BlockPacket(Socket socket, String rawPacketContent) throws InvalidPacketContentException {
	  if(rawPacketContent.startsWith(packetPrefix)){
			this.setRawPacketContent(rawPacketContent);
		  this.setPacketSocket(socket);
		  int prefixEnd = packetPrefix.length();
		  String xy = rawPacketContent.substring(prefixEnd, rawPacketContent.indexOf("\\", prefixEnd));
		  int x = Integer.valueOf(xy.split(";")[0]);
		  int y = Integer.valueOf(xy.split(";")[1]);
		  this.setBlockPos(new Coord2D(x, y));
		  this.setPacketContent(rawPacketContent.substring(rawPacketContent.indexOf("\\", prefixEnd) + 1));
	  }else{
	  	throw new InvalidPacketContentException();
	  }
  }

	/**
	 * @return the packetSocket
	 */
	public Socket getPacketSocket() {
		return packetSocket;
	}

	/**
	 * @param packetSocket the packetSocket to set
	 */
	private void setPacketSocket(Socket packetSocket) {
		this.packetSocket = packetSocket;
	}

	/**
	 * @return the rawPacketContent
	 */
	public String getRawPacketContent() {
		return rawPacketContent;
	}

	/**
	 * @param rawPacketContent the rawPacketContent to set
	 */
	private void setRawPacketContent(String rawPacketContent) {
		this.rawPacketContent = rawPacketContent;
	}

	/**
	 * @return the blockPos
	 */
	public Coord2D getBlockPos() {
		return blockPos;
	}

	/**
	 * @param blockPos the blockPos to set
	 */
	private void setBlockPos(Coord2D blockPos) {
		this.blockPos = blockPos;
	}

	/**
	 * @return the packetContent
	 */
	public String getPacketContent() {
		return packetContent;
	}

	/**
	 * @param packetContent the packetContent to set
	 */
	private void setPacketContent(String packetContent) {
		this.packetContent = packetContent;
	}
	
	/**
	 * @return the canceled
	 */
	public boolean isCanceled() {
		return canceled;
	}

	/**
	 * @param canceled the canceled to set
	 */
	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

	public static class InvalidPacketContentException extends Exception {
    private static final long serialVersionUID = 8362853397961122718L;
    
	}
	
}
