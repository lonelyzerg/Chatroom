package chatroom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

import exceptions.NameException;

public class Chatroom {
	private String host_string;
	private String host;
	private String username;
	private Connection connection;
	private static final int port = 999;

	public static void main(String[] args) throws IOException  {
		
		Chatroom c = new Chatroom();
		c.start();
	}

	public void start() throws IOException{
		//needs logic optimize. No need to enter the host again to change name.

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		//System.out.println("Please enter the host name and port number: ");
		//host_string = reader.readLine();
		System.out.println("Please enter the host name: ");
		host = reader.readLine();
		System.out.println("Please enter the username: ");
		username = reader.readLine();
		reader.close();
		try {
			connection = new Connection(host, port, username);
		}catch (UnknownHostException e){
			System.out.println("Host is invalid, please enter the host again!");
			start();
		}catch (NameException e) {
			System.out.println("This username is being used. please use another username");
			start();
		}
	}
	
}
