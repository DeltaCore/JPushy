package net.ccmob.apps.jpushy.mp.local;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
/**
 * 
 * @author Marcel Benning
 * 
 */
public class Client {

	public static void print(String cmd , Socket sock) throws IOException{
		DataOutputStream ostream = new DataOutputStream(sock.getOutputStream());
		ostream.writeBytes(cmd);
	}
	
	public static void println(String cmd , Socket sock) throws IOException{
		DataOutputStream ostream = new DataOutputStream(sock.getOutputStream());
		ostream.writeBytes(cmd + "\n\r");
	}
	
}