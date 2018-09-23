package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;

import chatroom.Connection;
import exceptions.NameException;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class Window {

	private JFrame frame;
	private JTextField host_input;
	private JTextField username_input;
	private JTextArea send_box;
	private JTextArea message_box;
	private Connection connection;
	private String host;
	private String username;
	private static final int port = 999;

	/**
	 * Create the application.
	 */
	public Window() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					initialize();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void send(String string) {
		connection.send(string);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Calibri", Font.PLAIN, 12));
		frame.setTitle("Java Chat Room");
		frame.setBounds(500, 500, 558, 401);// width 8 //height 59
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		CardLayout card = new CardLayout();
		frame.getContentPane().setLayout(card);

		JPanel connect_panel = new JPanel();
		frame.getContentPane().add("connect_panel", connect_panel);
		card.show(frame.getContentPane(), "connect_panel");
		connect_panel.setLayout(null);

		JPanel title_panel = new JPanel();
		title_panel.setBounds(0, 0, 534, 61);
		connect_panel.add(title_panel);
		title_panel.setLayout(new BorderLayout(0, 0));

		JLabel title = new JLabel("Please Enter the Host and Username!");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Calibri", Font.PLAIN, 20));
		title_panel.add(title, BorderLayout.CENTER);

		JPanel prompt_panel = new JPanel();
		prompt_panel.setBounds(0, 71, 534, 240);
		connect_panel.add(prompt_panel);
		prompt_panel.setLayout(null);

		JLabel host_prompt = new JLabel("Host: ");
		host_prompt.setBounds(83, 70, 38, 20);
		prompt_panel.add(host_prompt);
		host_prompt.setHorizontalAlignment(SwingConstants.CENTER);
		host_prompt.setFont(new Font("Calibri", Font.PLAIN, 16));

		host_input = new JTextField();
		host_input.setBounds(167, 67, 220, 26);
		prompt_panel.add(host_input);
		host_input.setFont(new Font("Calibri", Font.PLAIN, 16));
		host_input.setColumns(10);

		JLabel username_prompt = new JLabel("Username: ");
		username_prompt.setBounds(83, 100, 74, 20);
		prompt_panel.add(username_prompt);
		username_prompt.setHorizontalAlignment(SwingConstants.CENTER);
		username_prompt.setFont(new Font("Calibri", Font.PLAIN, 16));

		username_input = new JTextField();
		username_input.setToolTipText("Enter your username here");
		username_input.setBounds(167, 97, 220, 26);
		prompt_panel.add(username_input);
		username_input.setFont(new Font("Calibri", Font.PLAIN, 16));
		username_input.setColumns(10);

		JPanel confirm_panel = new JPanel();
		confirm_panel.setBounds(213, 332, 100, 30);
		connect_panel.add(confirm_panel);
		confirm_panel.setLayout(new BorderLayout(0, 0));

		JButton connect_button = new JButton("Connect");
		connect_button.setFont(new Font("Calibri", Font.PLAIN, 15));
		confirm_panel.add(connect_button, BorderLayout.CENTER);

		JPanel chat_panel = new JPanel();
		frame.getContentPane().add("chat_panel", chat_panel);
		chat_panel.setLayout(null);

		JLabel message_lable = new JLabel("Message");
		message_lable.setFont(new Font("Calibri", Font.BOLD, 16));
		message_lable.setBounds(25, 10, 100, 20);
		chat_panel.add(message_lable);

		message_box = new JTextArea(8, 20);
		message_box.setFont(new Font("Calibri", Font.PLAIN, 15));
		message_box.setEditable(false);
		message_box.setWrapStyleWord(true);
		message_box.setLineWrap(true);
		message_box.setTabSize(4);
		JScrollPane message_scroll = new JScrollPane(message_box);
		message_scroll.setSize(480, 235);
		message_scroll.setLocation(25, 35);
		chat_panel.add(message_scroll);

		send_box = new JTextArea();
		send_box.setFont(new Font("Calibri", Font.PLAIN, 15));
		send_box.setLineWrap(true);
		send_box.setWrapStyleWord(true);
		send_box.setBounds(25, 290, 390, 65);
		JScrollPane input_scroll = new JScrollPane(send_box);
		input_scroll.setSize(390, 65);
		input_scroll.setLocation(25, 280);
		chat_panel.add(input_scroll);

		JButton send_button = new JButton("Send");
		send_button.setFont(new Font("Calibri", Font.BOLD, 17));
		send_button.setBounds(426, 280, 80, 65);
		chat_panel.add(send_button);

		connect_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {

				try {
					host = host_input.getText();
					username = username_input.getText();
					if (!(host.equals("") || username.equals(""))) {
						connection = new Connection(host, port, username, message_box);
						card.show(frame.getContentPane(), "chat_panel");
					}
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (NameException e) {
					card.show(frame.getContentPane(), "connect_panel");

				}

			}
		});

		send_box.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String message = send_box.getText();
					if (!message.equals("")) {
						send(message.replaceAll("\n", ""));
						send_box.setText("");
					}
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {

			}
		});

		send_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String message = send_box.getText();
				if (!message.equals("")) {
					send(message.replaceAll("\n", ""));
					send_box.setText("");
				}
			}
		});

		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(WindowEvent winEvt) {
				if (connection != null)
					connection.onClose();
//	        		try {
//						TimeUnit.SECONDS.sleep(3);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
				System.exit(0);
			}
		});
	}

}
