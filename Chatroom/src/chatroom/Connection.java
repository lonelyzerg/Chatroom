package chatroom;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import exceptions.NameException;

public class Connection {
	private String username;
	private String host;
	private int port;
	private Socket connection;
	private DataOutputStream out;
	private DataInputStream in;
	private boolean stop = false;
	private static final String register_success = "[REGI]Success\n";
	private static final String register_prefix = "[REGI]";
	private static final String chat_prefix = "[CHAT]";
	private static final String suffix = "\n";
	private static final String quit_command = "\\q";

	public Connection(String host_string, String username) throws UnknownHostException, IOException, NameException {
		this.username = username;
		String[] host_array = host_string.split(":");
		this.host = host_array[0];
		this.port = Integer.getInteger(host_array[1]);
		System.err.println("Connecting to server " + host_string);
		connection = new Socket(host, port);

		out = new DataOutputStream(connection.getOutputStream());
		in = new DataInputStream(connection.getInputStream());

		System.err.println("Registering with username " + username);
		if (register(username) == -1) {
			throw new NameException("name already registered");
		}
		startChatting();

	}

	public Connection(String host, int port, String username) {
		this.username = username;
		this.host = host;
		this.port = port;
	}

	public int startChatting() {
		

		

		return 1;
	}

	public int register(String username) {
		try {
			out.writeUTF(register_prefix + username + suffix);
			TimeUnit.SECONDS.sleep(2);
			String response = in.readUTF();
			if (!response.equals(register_success)) {
				throw new NameException("register fails");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public class SendingThread extends Thread {
		public void run() {
			try {
				String message;
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				while (!stop) {
					System.out.println(username + ":");
					message = reader.readLine();
					if (message.equals(quit_command)) {
						stop = true;
					}
					out.writeUTF(chat_prefix + message + suffix);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public class ReceivingThread extends Thread {
		public void run() {

		}
	}
}
