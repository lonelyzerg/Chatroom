package threads;

import java.io.DataInputStream;

public class SendingThread extends Thread{
	private DataInputStream in;
	private boolean stop = false;
	private static final String register_success = "[REGI]Success\n";
	private static final String register_prefix = "[REGI]";
	private static final String chat_prefix = "[CHAT]";
	private static final String suffix = "\n";
	private static final String quit_command = "\\q";
	
	public SendingThread(DataInputStream in) {
		
	}
}
