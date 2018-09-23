package chatroom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

import exceptions.NameException;
import ui.Window;

public class Chatroom {
	private String host_string;


	public static void main(String[] args) throws IOException, NameException {
		Chatroom c = new Chatroom();
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//			}
//		});

		try {

			c.start();
		} catch (NameException e) {
			System.out.println("This username is being used. please use another username");
			c.start();
		} catch (UnknownHostException e) {
			System.out.println("Host is invalid, please enter the host again!");
			c.start();
		}
	}

	public Chatroom() {

	}

	public void start() throws IOException, NameException {
		// needs logic optimize. No need to enter the host again to change name.

//		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		// System.out.println("Please enter the host name and port number: ");
		// host_string = reader.readLine();
//		System.out.println("Please enter the host name: ");
//		host = reader.readLine();
//		System.out.println("Please enter the username: ");
//		username = reader.readLine();
//		reader.close();
		Window w = new Window();
//		connection = new Connection(host, port, username);
		
		
	}

}
