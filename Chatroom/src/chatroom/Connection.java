package chatroom;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import exceptions.NameException;

public class Connection {
	private String username;
	private String host;
	private int port;
	private Socket connection;
	private DataOutputStream out;
	private BufferedReader in;
	private boolean stop = false;
	private static final String register_success = "[REGI]Success";
	private static final String register_failure = "[REGI]Failure";
	private static final String register_prefix = "[REGI]";
	private static final String exit_code = "[EXIT]\n";
	private static final String chat_prefix = "[CHAT]";
	private static final String suffix = "\n";
	private static final String quit_command = "\\q";
	private static final String charset_utf_8 = "UTF-8";

	public Connection(String host_string, String username) throws UnknownHostException, IOException, NameException {
		this.username = username;
		String[] host_array = host_string.split(":");
		this.host = host_array[0];
		this.port = Integer.getInteger(host_array[1]);
		startChatting();
	}

	public Connection(String host, int port, String username) throws UnknownHostException, IOException, NameException {
		this.username = username;
		this.host = host;
		this.port = port;
		startChatting();
	}

	public int startChatting() throws UnknownHostException, IOException, NameException {
		System.err.println("Connecting to server " + host + ":" + port);
		connection = new Socket(host, port);
		// connection.setSoTimeout(10000);

		out = new DataOutputStream(connection.getOutputStream());
		in = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset_utf_8));

		System.err.println("Registering with username " + username);
		if (register(username) == -1) {
			throw new NameException("name already registered");
		}

		SendingThread s = new SendingThread();
		ReceivingThread r = new ReceivingThread();

		s.start();
		r.start();

		try {
			s.join();
			r.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		connection.close();
		System.out.println("Thanks for using, bye!");
		return 1;
	}

	public int register(String username) {
		try {
			out.write((register_prefix + username + suffix).getBytes(charset_utf_8));
			out.flush();
			// TimeUnit.SECONDS.sleep(1);
			String response = in.readLine();

			if (response.equals(register_failure)) {
				throw new NameException("register fails");
			} else if (response.equals(register_success)) {
				System.err.println("Registered!");
				return 1;
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
					System.out.println("[" + username + "]: Enter your message");
					message = reader.readLine();
					if (message.equals(quit_command)) {
						stop = true;
						break;
					}
					out.write((chat_prefix + message + suffix).getBytes(charset_utf_8));
					out.flush();
					System.out.println(username + ": " + message);
				}
				reader.close();
				out.write(exit_code.getBytes(charset_utf_8));
				out.flush();
				Thread.sleep(1000);
				out.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public class ReceivingThread extends Thread {
		public void run() {
			try {
				this.getId();
				String[] message;
				while (!stop) {
					message = parse(in.readLine());
					System.err.println("received a message from server");
					System.out.println(message[1] + ": " + message[2]);
				}
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}
	}

	public String[] parse(String message) {
		String[] result = new String[3];
		int i = 5;
		int j = message.indexOf(']', i + 1);
		result[0] = message.substring(1, i);
		result[1] = message.substring(i + 2, j);
		result[2] = message.substring(j + 1);
		return result;
	}

}
